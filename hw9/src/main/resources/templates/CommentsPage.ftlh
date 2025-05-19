<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <!DOCTYPE html>

    <html>

    <head>
        <title>Comments Page</title>
        <style>
            /* Add the following style to apply white-space: pre-wrap; to comments */
            .comment-text {
                white-space: pre-wrap;
            }
        </style>
    </head>

    <body>
    <h1>Post:</h1>
    <p>Title: ${post.title}</p>
    <p>Content: ${post.text}</p>

    <h2>Comments:</h2>
    <#list comments as comment>
        <div>
            <!-- Apply the style to the comment text -->
            <p class="comment-text">${comment.user.login}: ${comment.text}</p>
        </div>
    </#list>

    <!-- Form for writing a new comment -->
    <form action="/post/${post.id}/writeComment" method="post">
        <label for="commentText">Your Comment:</label>
        <textarea id="commentText" name="text" rows="4" cols="50" required></textarea><br>
        <button type="submit">Submit Comment</button>
    </form>

    </body>

    </html>

</@c.page>
