<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>
<#function countPostsByUserId posts userId>
    <#assign count = 0>
    <#list posts as post>
        <#if post.userId == userId>
            <#assign count = count + 1>
        </#if>
    </#list>
    <#return count>
</#function>


<@c.page>
    <#assign u = c.findBy(users, "handle", handle)!/>

    <#if u?has_content>
        ${u.handle} / ${u.name} (${u.id})
        <#assign postCount = countPostsByUserId(posts,u.id)>

           <p> <a href="/posts?user_id=${u.id}">Number of posts:${postCount}</a> </p>

    <#else>
        No such user
    </#if>

</@c.page>
