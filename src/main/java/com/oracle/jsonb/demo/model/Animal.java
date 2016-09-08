package com.oracle.jsonb.demo.model;

public class Animal {
    private String name;
    private int age;
    private Boolean furry;

    public Animal() {}

    public Animal(String name, int age, Boolean furry) {
        this.name = name;
        this.age = age;
        this.furry = furry;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Boolean isFurry() {
        return furry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFurry(Boolean furry) {
        this.furry = furry;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", furry=" + furry +
                '}';
    }
}
