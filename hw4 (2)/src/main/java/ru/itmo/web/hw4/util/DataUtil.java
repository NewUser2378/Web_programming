package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov"),
            new User(6, "pashka", "Pavel Mavrin"),
            new User(9, "geranazavr555", "Georgiy Nazarov"),
            new User(11, "tourist", "Gennady Korotkevich")
    );



    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "first post", "Hello everyone from Mike Mirzayanov",1),
            new Post(5, "New Div3", "Привет, Codeforces!\n" +
                    "\n" +
                    "Во вторник, 19 декабря 2023 г. в 17:35 состоится Codeforces Round 916 (Div. 3) — очередной раунд для третьего дивизиона. В этом раунде будет 6-8 задач, по сложности подходящих для участников с рейтингом до 1600 (во всяком случае, мы надеемся на это). Но, конечно же, участники с рейтингом 1600 и выше могут зарегистрироваться на раунд вне конкурса.\n" +
                    "\n" +
                    "Раунд пройдет по правилам образовательных раундов. Таким образом, во время раунда задачи будут тестироваться на предварительных тестах, а после раунда будет 12-ти часовая фаза открытых взломов. Мы постарались сделать приличные тесты — так же как и вы, мы будем расстроены, если у многих будут падать решения после окончания контеста.\n" +
                    "\n" +
                    "У вас будет 2 часа и 15 минут на то, чтобы решить 6-8 задач. Штраф за неверную посылку будет равняться 10 минутам.\n" +
                    "\n" +
                    "Напоминаем, что в таблицу официальных результатов попадут только достоверные участники третьего дивизиона. Как написано по ссылке — это вынужденная мера для борьбы с неспортивным поведением. Для квалификации в качестве достоверного участника третьего дивизиона надо:\n" +
                    "\n" +
                    "принять участие не менее чем в пяти рейтинговых раундах (и решить в каждом из них хотя бы одну задачу),\n" +
                    "не иметь в рейтинге точку 1900 или выше.\n" +
                    "Независимо от того, являетесь вы достоверными участниками третьего дивизиона или нет, если ваш рейтинг менее 1600, то раунд для вас будет рейтинговым.\n" +
                    "\n" +
                    "Раунд основан на задачах муниципального этапа Всероссийской олимпиады школьников в Саратове и Саратовской области 2023/2024, поэтому если вы участвовали в нем — пожалуйста, воздержитесь от официального участия в этом раунде.\n" +
                    "\n" +
                    "Задачи вместе со мной придумывали и готовили Адилбек adedalic Далабаев, Иван BledDest Андросов, Максим Neon Мещеряков, Роман Roms Глазов и Александр fcspartakm Фролов. Отдельное спасибо Владиславу Vladosiya Власову за отличную координацию.\n" +
                    "\n" +
                    "Большое спасибо Михаилу MikeMirzayanov Мирзаянову за системы Polygon и Codeforces, без которых этот раунд бы не состоялся!\n" +
                    "\n" +
                    "Наконец, спасибо тестерам раунда FBI, Kalashnikov и phoenix1289 за ценные советы и предложения!\n" +
                    "\n" +
                    "Удачи в раунде! Надеюсь, задачи, которые мы подготовили, вам понравятся.\n" +
                    "\n" +
                    "UPD: по техническим причинам изменение рейтинга с Educational Codeforces Round 160 будет пересчитано после Div. 3",1),
            new Post(2, "New record", "finally",11),
            new Post(3, "My algo course", "You could see my new lectures on CT youtube channel",6),
            new Post(4, "Next lecture info", "i am going to give a next WEB lecture,please come everyone",9)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }

    }
}
