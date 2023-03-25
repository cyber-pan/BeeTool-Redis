package org.beetool.redis;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;
import lombok.Setter;


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