package com.lizq.school.models.student;

import java.util.Date;

/**
 * Created by lizq on 2017/8/14.
 */
public class Student {
    private int id;
    private String name;
    private String gender;
    private Date birthday;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
