<#import "commons.ftlh" as c>

<@c.page>
    <!DOCTYPE html>
    <html lang="">
    <head>
        <title>User Profile</title>
    </head>
    <body>
    <h1>User Profile</h1>
    <p>ID: ${user.id}</p>
    <p>Login: ${user.login}</p>
    <p>Creation Time: ${user.creationTime}</p>
    </body>
    </html>

</@c.page>