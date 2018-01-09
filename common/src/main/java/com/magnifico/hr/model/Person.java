package com.magnifico.hr.model;


public class Person {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public Person(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
