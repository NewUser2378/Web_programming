<#import "./common.ftlh" as c>

<@c.page>
        <#list posts as post>
                <@c.PostItem post=post/>
        </#list>

</@c.page>
