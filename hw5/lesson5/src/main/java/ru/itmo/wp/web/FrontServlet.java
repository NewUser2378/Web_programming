package ru.itmo.wp.web;

import freemarker.template.*;
import ru.itmo.wp.web.exception.NotFoundException;
import ru.itmo.wp.web.exception.RedirectException;
import ru.itmo.wp.web.page.IndexPage;
import ru.itmo.wp.web.page.NotFoundPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FrontServlet extends HttpServlet {
    private static final String BASE_PACKAGE = FrontServlet.class.getPackage().getName() + ".page";
    private static final String DEFAULT_ACTION = "action";

    private Configuration sourceConfiguration;
    private Configuration targetConfiguration;

    private Configuration newFreemarkerConfiguration(String templateDirName, boolean debug)
            throws ServletException { //загружаем конфигурации загрузки шаблонов
        File templateDir = new File(templateDirName);
        if (!templateDir.isDirectory()) {
            return null;
        }

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        try {
            configuration.setDirectoryForTemplateLoading(templateDir);
        } catch (IOException e) {
            throw new ServletException("Can't create freemarker configuration [templateDir="
                    + templateDir + "]");
        }
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(debug ? TemplateExceptionHandler.HTML_DEBUG_HANDLER :
                TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);

        return configuration;
    }

    @Override
    public void init() throws ServletException {//создаем конфигурации
        sourceConfiguration = newFreemarkerConfiguration(
                getServletContext().getRealPath("/") + "../../src/main/webapp/WEB-INF/templates", true);
        // конфигурация загрузки шаблонов из папки по явному пути (отладочная конфигурация)
        targetConfiguration = newFreemarkerConfiguration(//пробуем просто по имени папки
                getServletContext().getRealPath("WEB-INF/templates"), false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)// обрабатываем Get запросы
            throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)//обрабатываем  POST запросы
            throws ServletException, IOException {
        process(request, response);
    }


    private String getCurrentLanguage(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        HttpSession session = request.getSession();
        String sessionLang = (String) session.getAttribute("lang");

        if (lang != null && lang.matches("^[a-z]{2}$")) {
            session.setAttribute("lang", lang); // Устанавливаем выбранный язык в сессии
            return lang;
        } else if (sessionLang != null && !sessionLang.equals("en")) {
            return sessionLang; // Используем язык из сессии, если он не равен "en".
        } else {
            return "en"; // По умолчанию используем "en".
        }
    }



    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Route route = Route.newRoute(request);
        try {
            process(route, request, response);
        } catch (NotFoundException e) {
            try {
                process(Route.newNotFoundRoute(), request, response);
            } catch (NotFoundException notFoundException) {
                throw new ServletException(notFoundException);
            }
        }
    }

    private void process(Route route, HttpServletRequest request, HttpServletResponse response)
            throws NotFoundException, ServletException, IOException {
        Class<?> pageClass;//объект типа описатель класса, у него можем взять getname итп
        try {
            pageClass = Class.forName(route.getClassName());// пытаемся получить класс по его названию
        } catch (ClassNotFoundException e) {//если не найден класс с таким названием, то говорим, что не можем найти страницу
            throw new NotFoundException();
        }

        Method[] methods = pageClass.getDeclaredMethods();
        Method methodToInvoke = null;

        for (Method method : methods) {
            if (method.getName().equals(route.getAction())) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (isValidMethodParameters(parameterTypes)) {
                    methodToInvoke = method;
                    break;
                }
            }
        }

        if (methodToInvoke == null) {
            throw new NotFoundException();
        }

        Object page;
        try {
            page = pageClass.getDeclaredConstructor().newInstance();//по описателю класса создаем объект этого класса при помощи конструктора по умолчанию
        } catch (InstantiationException | IllegalAccessException
                 | NoSuchMethodException | InvocationTargetException e) {
            throw new ServletException("Can't create page [pageClass="
                    + pageClass + "]");
        }

        Map<String, Object> view = new HashMap<>();//создаем массив, куда будем добавлять данные
        methodToInvoke.setAccessible(true);//делаем чтобы могли запустить private метод
        try {
            Object[] methodParameters = prepareMethodParameters(methodToInvoke, request, view);//пытаемся добавить данные страницы
            methodToInvoke.invoke(page, methodParameters);
        } catch (IllegalAccessException e) {
            throw new ServletException("Can't invoke action method [pageClass="
                    + pageClass + ", method=" + methodToInvoke + "]");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RedirectException) {
                RedirectException redirectException = (RedirectException) cause;
                response.sendRedirect(redirectException.getTarget());
                return;
            } else {
                throw new ServletException("Can't invoke action method [pageClass="
                        + pageClass + ", method=" + methodToInvoke + "]", cause);
            }
        }

        Template template = newTemplate(pageClass.getSimpleName() + ".ftlh", request);// получаем шаблон с нужным именем
        response.setContentType("text/html");//устанавливаем тип данных для ответа
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            template.process(view, response.getWriter());
        } catch (TemplateException e) {
            if (sourceConfiguration == null) {
                throw new ServletException("Can't render template [pageClass="
                        + pageClass + ", action=" + methodToInvoke + "]", e);
            }
        }
    }

    private boolean isValidMethodParameters(Class<?>[] parameterTypes) {
        for (Class<?> paramType : parameterTypes) {
            if (paramType != HttpServletRequest.class && paramType != Map.class) {
                return false;
            }
        }
        return true;
    }

    private Object[] prepareMethodParameters(Method method, HttpServletRequest request, Map<String, Object> view) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] methodParameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == HttpServletRequest.class) {
                methodParameters[i] = request;
            } else if (parameterTypes[i] == Map.class) {
                methodParameters[i] = view;
            }
        }

        return methodParameters;
    }


    private Template newTemplate(String templateName, HttpServletRequest request) throws ServletException {
        String language = getCurrentLanguage(request);
        String localizedTemplateName = templateName.replace(".ftlh", "_" + language + ".ftlh");

        Template template = null;

        if (sourceConfiguration != null) {
            try {
                template = sourceConfiguration.getTemplate(localizedTemplateName);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName=" + localizedTemplateName + "]", e);
            }
        }

        if (template == null && targetConfiguration != null) {
            try {
                template = targetConfiguration.getTemplate(localizedTemplateName);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName=" + localizedTemplateName + "]", e);
            }
        }

        // Если template всё равно не был найден и язык не соответствует ожидаемым значениям, используем шаблон по умолчанию.
        if (template == null && !isValidLanguage(language)) {
            localizedTemplateName = "IndexPage_base.ftlh"; // Используем шаблон по умолчанию
            try {
                template = sourceConfiguration.getTemplate(localizedTemplateName);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName=" + localizedTemplateName + "]", e);
            }
        }

        // Если template всё равно не был найден, используем шаблон по умолчанию.
        if (template == null) {
            try {
                template = sourceConfiguration.getTemplate(templateName);
            } catch (TemplateNotFoundException e) {
                throw new ServletException("Can't find template [templateName=" + templateName + "]");
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName=" + templateName + "]", e);
            }
        }

        return template;
    }


    private boolean isValidLanguage(String language) {
        if (sourceConfiguration == null) {
            return false;
        }

        String templateName = "IndexPage_" + language + ".ftlh";
        try {
            sourceConfiguration.getTemplate(templateName);
            return true; // Шаблон существует для данного языка.
        } catch (TemplateNotFoundException e) {
            return false; // Шаблон не существует для данного языка.
        } catch (IOException e) {
            // Обработайте исключение, если что-то пошло не так при попытке загрузить шаблон.
            return false;
        }
    }




    private static class Route {// класс который зранит заданные имя класса, и метод обработки
        private final String className;
        private final String action;

        private Route(String className, String action) {
            this.className = className;
            this.action = action;
        }

        private String getClassName() {// возвращаем имя класса
            return className;
        }

        private String getAction() { // возвращаем имя метода
            return action;
        }

        private static Route newNotFoundRoute() {//метод в котором возвращаем страницу, на которой будет , что не удолось найти
            return new Route(NotFoundPage.class.getName(), DEFAULT_ACTION);
        }

        private static Route newIndexRoute() {//метод в котором возвращаем страницу по умолчанию
            return new Route(IndexPage.class.getName(), DEFAULT_ACTION);
        }

        private static Route newRoute(HttpServletRequest request) {//метод для маршрутизации: хотим найти контроллер,
            // создать экземпляр класса этого контроллера и делегировать этому контроллеру обработку запроса
            //возможно получить метод контроллера, если он указан, например \?action=test, будем вызвать именно его
            // если такого параметра в URI  нет, то будем вызывать action - метод параметра по умолчанию

            String uri = request.getRequestURI();//берем URI

            List<String> classNameParts = Arrays.stream(uri.split("/")) // разбиваем URI по "/"
                    .filter(part -> !part.isEmpty()) //при помощи  Arrays.stream().filter фильтруем пустые части,если они есть
                    .collect(Collectors.toList());// собираем оставшиеся в массивы

            // Например, из URI /misc/help получим ["","misc","help"] до фильтрации и ["misc","help"] после

            if (classNameParts.isEmpty()) {
                return newIndexRoute();// обрабатываем пустые URI по типу ////////
            }

            //Однако, на том же примере нам нужно получить файл с названием контроллера, то есть :
            //в нашем случае ru.itmo.wp.lesson5.web.page.misc.HelpPage

            //для того чтобы собрать такой из компонент в List<String> classNameParts используем StringBuilder


            StringBuilder simpleClassName = new StringBuilder(classNameParts.get(classNameParts.size() - 1));//передаем компоненты

            int lastDotIndex = simpleClassName.lastIndexOf(".");//смотрим,
            simpleClassName.setCharAt(lastDotIndex + 1,
                    Character.toUpperCase(simpleClassName.charAt(lastDotIndex + 1)));//делаем большим 1 символ
            classNameParts.set(classNameParts.size() - 1, simpleClassName.toString());

            String className = BASE_PACKAGE + "." + String.join(".", classNameParts) + "Page";//собираем все в 1 строку, в начале добавляя базовый путь

            String action = request.getParameter("action");//получаем из запроса имя метода, который должны использовать
            if (action == null || action.isEmpty()) {// если нет параметра action, то будем использовать параметр по умолчанию
                action = DEFAULT_ACTION;
            }

            return new Route(className, action);// возвращаем по сути пару с именем класса и именем параметра, (мы их хрангим как поля класса Route)
        }
    }
}