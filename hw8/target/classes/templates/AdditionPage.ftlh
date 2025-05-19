<#-- @ftlvariable name="enterForm" type="ru.itmo.wp.form.UserCredentials" -->
<#import "/spring.ftl" as spring>



<#import "commons.ftlh" as c>


    <@c.page>
        <div class="registration form-box">
            <div class="header">Add</div>
            <div class="body">
                <form method="post" action="">
                    <div class="field">
                        <div class="name">
                            <label for="content">Add</label>
                        </div>
                        <div class="value">
                            white-space: pre-wrap;
                            <textarea id="content" name="content">${additionForm.content!}</textarea>
                        </div>
                        <@c.error "additionForm.content"/>
                    </div>

                    <div class="error"></div>
                    <div class="button-field">
                        <input type="submit" value="Add">
                    </div>
                </form>
            </div>
        </div>
    </@c.page>


