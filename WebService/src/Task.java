package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
public class Task implements Serializable
{
    @Id
    @GeneratedValue
    //@Column(name="TASK_ID", nullable=false)
    private Long id;
    private String name;
    private String dueDate;
    //@ManyToOne
    //@JoinColumn(name="USER_ID", nullable=false)
    //private User user;

    public Task()
    {
        name = "";
        dueDate = "";
    }

    public Task(String name)
    {
        //this(null, name);
        this.name = name;
        this.dueDate = "";
    }


    public boolean equals(Task otherTask) {
        return otherTask.getId() == getId();
    }




    //public User getUser() { return user; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDueDate() { return dueDate; }
    public void setName(String name) { this.name = name; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}
