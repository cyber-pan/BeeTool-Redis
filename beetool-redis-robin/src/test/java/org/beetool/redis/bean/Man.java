package org.beetool.redis.bean;

import lombok.Data;


@Data
public class Man {
    private String name;
    private Integer age;

    public Man() {
        
    }

    public Man(String name, Integer age) {
        setAge(age);
        setName(name);
    }
}