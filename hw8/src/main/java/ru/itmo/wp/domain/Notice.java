package ru.itmo.wp.domain;

import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;




import javax.persistence.*;

@Entity
public class Notice {
    @Id
    @GeneratedValue
    private long id;


    @Lob
    @Size(min = 1, max = 255)
    private String content ;


    @CreationTimestamp
    private Date creationTime;

    public void setId(long id){
        this.id=id;
    }
    public long getId() {
        return id;
    }


    public void setContent(String content){
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }


}
