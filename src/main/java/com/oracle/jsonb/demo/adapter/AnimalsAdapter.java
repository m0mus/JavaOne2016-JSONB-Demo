package com.oracle.jsonb.demo.adapter;

import com.oracle.jsonb.demo.model.Animal;
import com.oracle.jsonb.demo.model.Cat;
import com.oracle.jsonb.demo.model.Dog;

import javax.json.bind.adapter.JsonbAdapter;

import static com.oracle.jsonb.demo.adapter.AnimalsAdapter.AnimalAdapted.TYPE.CAT;
import static com.oracle.jsonb.demo.adapter.AnimalsAdapter.AnimalAdapted.TYPE.DOG;

public class AnimalsAdapter implements JsonbAdapter<Animal, AnimalsAdapter.AnimalAdapted> {
    public static final class AnimalAdapted {
        public enum TYPE {
            CAT, DOG, GENERIC
        };

        private TYPE type;
        private String name;
        private int age;
        private Boolean furry;
        private Boolean barking;
        private Boolean cuddly;

        public AnimalAdapted() {
        }

        public TYPE getType() {
            return type;
        }

        public void setType(TYPE type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Boolean getFurry() {
            return furry;
        }

        public void setFurry(Boolean furry) {
            this.furry = furry;
        }

        public Boolean getBarking() {
            return barking;
        }

        public void setBarking(Boolean barking) {
            this.barking = barking;
        }

        public Boolean getCuddly() {
            return cuddly;
        }

        public void setCuddly(Boolean cuddly) {
            this.cuddly = cuddly;
        }
    }

    @Override
    public AnimalAdapted adaptToJson(Animal animal) throws Exception {
        AnimalAdapted animalAdapted = new AnimalAdapted();
        if (Cat.class.isAssignableFrom(animal.getClass())) {
            animalAdapted.type = CAT;
            animalAdapted.cuddly = ((Cat) animal).isCuddly();
        } else if (Dog.class.isAssignableFrom(animal.getClass())) {
            animalAdapted.type = DOG;
            animalAdapted.barking = ((Dog) animal).isBarking();
        } else {
            animalAdapted.type = AnimalAdapted.TYPE.GENERIC;
        }
        animalAdapted.name = animal.getName();
        animalAdapted.age = animal.getAge();
        animalAdapted.furry = animal.isFurry();
        return animalAdapted;
    }

    @Override
    public Animal adaptFromJson(AnimalAdapted animalAdapted) throws Exception {
        Animal animal;
        switch (animalAdapted.type) {
            case CAT:
                animal = new Cat();
                ((Cat) animal).setCuddly(animalAdapted.cuddly);
                break;
            case DOG:
                animal = new Dog();
                ((Dog) animal).setBarking(animalAdapted.barking);
                break;
            default:
                animal = new Animal();
        }
        animal.setName(animalAdapted.name);
        animal.setAge(animalAdapted.age);
        animal.setFurry(animalAdapted.furry);
        return animal;
    }
}
