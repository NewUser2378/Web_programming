<#-- @ftlvariable name="user_id" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign user_id_as_long = user_id?number>
    <#list posts as post>
        <#if post.userId == user_id_as_long>
            <@c.PostItem post=post/>
        </#if>
    </#list>
</@c.page>
