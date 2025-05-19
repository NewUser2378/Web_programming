package ru.itmo.wp.form;

import ru.itmo.wp.domain.Notice;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;
@SuppressWarnings("unused")
public class NoticeCredentials {
    @Lob
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 16)

    private String content;
 
   
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
