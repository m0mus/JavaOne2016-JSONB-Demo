package com.oracle.jsonb.demo.model;

public class Cat extends Animal {
    private Boolean cuddly;

    public Cat() {}

    public Cat(String name, int age, Boolean hasFur, Boolean cuddly) {
        super(name, age, hasFur);
        this.cuddly = cuddly;
    }

    public Boolean isCuddly() {
        return cuddly;
    }

    public void setCuddly(Boolean cuddly) {
        this.cuddly = cuddly;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", furry=" + isFurry() +
                ", cuddly=" + cuddly +
                "}";
    }
}
