package com.oracle.jsonb.demo.model;

public class Dog extends Animal {
    private Boolean barking;

    public Dog() {}

    public Dog(String name, int age, Boolean hasFur, Boolean barking) {
        super(name, age, hasFur);
        this.barking = barking;
    }

    public Boolean isBarking() {
        return barking;
    }

    public void setBarking(Boolean barking) {
        this.barking = barking;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", furry=" + isFurry() +
                ", barking=" + barking +
                "}";
    }
}
