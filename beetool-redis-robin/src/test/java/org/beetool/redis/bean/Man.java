package org.beetool.redis.bean;

import lombok.Data;



public class Man {
    private String name;
    private Integer age;

    public Man() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Man(String name, Integer age) {
        setAge(age);
        setName(name);
    }
}