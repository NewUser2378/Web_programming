<#import "/spring.ftl" as spring>

<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/normalize.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/form.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/datatable.css'/>">
        <script src="<@spring.url '/js/jquery-3.6.1.min.js'/>"></script>
        <script src="<@spring.url '/js/notify.min.js'/>"></script>
        <script src="<@spring.url '/js/app.js'/>"></script>
    </head>
    <body>
    <header>
        <a href="/"><img src="<@spring.url 'img/logo.png'/>" alt="Codeforces" title="Codeforces"/></a>
        <div class="languages">
            <a href="#"><img src="<@spring.url 'img/gb.png'/>" alt="In English" title="In English"/></a>
            <a href="#"><img src="<@spring.url 'img/ru.png'/>" alt="In Russian" title="In Russian"/></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                ${user.login}
                |
                <a href="<@spring.url '/logout'/>">Logout</a>
            <#else>
                <a href="<@spring.url '/enter'/>">Enter</a>
                |
                <a href="<@spring.url '/register'/>">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <li><a href="<@spring.url '/'/>">Home</a></li>
                <li><a href="<@spring.url '/users/all'/>">Users</a></li>
            </ul>
        </nav>
    </header>
    <div class="middle">
        <style>
            .middle table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            .middle th, .middle td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }

            .middle th {
                background-color: #f2f2f2; /* Цвет фона заголовков таблицы */
            }

            .middle tr:nth-child(even) {
                background-color: #f9f9f9; /* Цвет фона четных строк */
            }

            .middle .noData {
                background-color: #ffe6e6; /* Цвет фона для строки "No data" */
            }

            .middle .footer {
                margin-top: 20px;
            }

            .content {
                white-space: pre-wrap;
            }
        </style>
        <aside>
            <section>
                <div class="header">
                    Pay attention
                </div>
                <h1>Notices</h1>
                <table>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Content</th>
                        <th>Created</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if notices?size==0>
                        <tr class="noData">
                            <td colspan="3">
                                No data
                            </td>
                        </tr>
                    <#else>
                        <#list notices as  notice>
                            <tr>
                                <td class="id">${notice.id}</td>
                                <td class="content">${notice.content}</td>
                                <td class="creationTime">${notice.creationTime}</td>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
                <div class="footer">
                    <a href="#">View all</a>
                </div>
            </section>
        </aside>

        <main>
            <#nested/>
        </main>
    </div>
    <footer>
        <a href="/">Ejudge</a> &copy; 2077 by Mike Mirzayanov
    </footer>
    <script>
        $(function () {
            <#if message??>
            notify("${message?js_string}");
            </#if>
        })
    </script>
    </body>
    </html>
</#macro>

<#macro error field>
    <@spring.bind field/>
    <#if spring.status.errorMessage??>
        <div class="error">${spring.status.errorMessage}</div>
    </#if>
</#macro>