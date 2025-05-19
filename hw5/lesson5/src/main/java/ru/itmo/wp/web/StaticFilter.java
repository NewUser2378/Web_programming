package ru.itmo.wp.web;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class StaticFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        //хотим смотреть в запрос, и если запрос соответствует статическому файлу из папки web ресурсов, ТО возвращаем этот файлик
        String uri = request.getRequestURI();//получаем URI
        String rootRealPath = getServletContext().getRealPath("/");

        File file = new File(new File(rootRealPath, "../../src/main/webapp"), uri);//заходим в src и идем в папку
        // и добавляем uri к пути , пробуем найти его
        if (!file.isFile()) {
            file = new File(rootRealPath, uri);// такого файла нет - пробуем заменить его файлом,
            // который назодим по абсолютному пути и добавляем к нему URI
        }

        if (file.isFile()) {// ЕСЛИ ТАКОЙ файл в директории уже есть, то отдаем его в response
            response.setContentType(getServletContext().getMimeType(file.getCanonicalPath()));
            response.setContentLengthLong(file.length());// сколько передаем
            Files.copy(file.toPath(), response.getOutputStream());// копируем содержимое в response
        } else {
            chain.doFilter(request, response);// идем в цепочку обработки, что уходит в FRONT SERVLET
        }
    }
}
