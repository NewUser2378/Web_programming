<#-- @ftlvariable name="post_id" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<#assign post_id_long = post_id?number>

<@c.page>
    <#assign p = c.findBy(posts, "id", post_id_long)!/>

    <#if p?has_content>
        <@c.PostItem post=p showFullText=true/>

    <#else>
        No such post
    </#if>
</@c.page>
