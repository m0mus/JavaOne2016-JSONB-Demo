package com.oracle.jsonb.demo.model;

/**
 * A pet owner.
 */
public class Person {
    private String name;
    private Animal pet;

    public Person() {}

    public Person(String name, Animal pet) {
        this.name = name;
        this.pet = pet;
    }

    public String getName() {
        return name;
    }

    public Animal getPet() {
        return pet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPet(Animal pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pet=" + pet +
                '}';
    }
}
