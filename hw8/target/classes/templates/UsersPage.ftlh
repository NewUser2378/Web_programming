<#-- @ftlvariable name="enterForm" type="ru.itmo.wp.form.UserCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size == 0>
                <tr class="noData">
                    <td colspan="5">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                    <tr>
                        <td class="id">${user.id}</td>
                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>
                        <td class="status">${user.status}</td>
                        <td>
                            <form action="/users/updateStatus" method="post">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="hidden" name="newStatus" value="${user.status} 'ENABLED' ? 'DISABLED' : 'ENABLED'}">
                                <button type="submit">${user.status} 'ENABLED' ? 'Disable' : 'Enable'}</button>
                            </form>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
