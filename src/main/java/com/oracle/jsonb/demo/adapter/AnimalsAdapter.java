package com.oracle.jsonb.demo.adapter;

import com.oracle.jsonb.demo.model.Animal;
import com.oracle.jsonb.demo.model.Cat;
import com.oracle.jsonb.demo.model.Dog;

import javax.json.bind.adapter.JsonbAdapter;

public class AnimalsAdapter implements JsonbAdapter<Animal[], AnimalsAdapter.AnimalAdapted[]> {
    public static final class AnimalAdapted {
        private enum TYPE {
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
    public AnimalAdapted[] adaptToJson(Animal[] animals) throws Exception {
        AnimalAdapted[] animalsAdapted = new AnimalAdapted[animals.length];
        for (int i = 0; i < animals.length; i++) {
            Animal animal = animals[i];
            AnimalAdapted animalAdapted = new AnimalAdapted();
            if (Cat.class.isAssignableFrom(animal.getClass())) {
                animalAdapted.type = AnimalAdapted.TYPE.CAT;
                animalAdapted.cuddly = ((Cat) animal).isCuddly();
            } else if (Dog.class.isAssignableFrom(animal.getClass())) {
                animalAdapted.type = AnimalAdapted.TYPE.DOG;
                animalAdapted.barking = ((Dog) animal).isBarking();
            } else {
                animalAdapted.type = AnimalAdapted.TYPE.GENERIC;
            }
            animalAdapted.name = animal.getName();
            animalAdapted.age = animal.getAge();
            animalAdapted.furry = animal.isFurry();
            animalsAdapted[i] = animalAdapted;
        }
        return animalsAdapted;
    }

    @Override
    public Animal[] adaptFromJson(AnimalAdapted[] animalsAdapted) throws Exception {
        Animal[] animals = new Animal[animalsAdapted.length];
        for (int i = 0; i < animalsAdapted.length; i++) {
            AnimalAdapted animalAdapted = animalsAdapted[i];
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
            animals[i] = animal;
        }
        return animals;
    }
}
