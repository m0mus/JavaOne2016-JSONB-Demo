package com.oracle.jsonb.demo.adapter;

import com.oracle.jsonb.demo.model.Animal;
import com.oracle.jsonb.demo.model.Cat;
import com.oracle.jsonb.demo.model.Dog;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.adapter.JsonbAdapter;

public class AnimalAdapter implements JsonbAdapter<Animal, JsonObject> {
    private static final String CAT = "CAT";
    private static final String DOG = "DOG";

    @Override
    public JsonObject adaptToJson(Animal animal) throws Exception {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        if (Cat.class.isAssignableFrom(animal.getClass())) {
            jsonObjectBuilder.add("type", CAT);
            jsonObjectBuilder.add("cuddly", ((Cat) animal).isCuddly());
        } else if (Dog.class.isAssignableFrom(animal.getClass())) {
            jsonObjectBuilder.add("type", DOG);
            jsonObjectBuilder.add("barking", ((Dog) animal).isBarking());
        } else {
            throw new Exception("Unknown animal type");
        }
        return jsonObjectBuilder.
                add("name", animal.getName()).
                add("age", animal.getAge()).
                add("furry", animal.isFurry()).
                build();
    }

    @Override
    public Animal adaptFromJson(JsonObject animalAdapted) throws Exception {
        Animal animal;
        switch (animalAdapted.getString("type")) {
            case CAT:
                animal = new Cat();
                ((Cat) animal).setCuddly(animalAdapted.getBoolean("cuddly"));
                break;
            case DOG:
                animal = new Dog();
                ((Dog) animal).setBarking(animalAdapted.getBoolean("barking"));
                break;
            default:
                throw new Exception("Unknown animal type");
        }
        animal.setName(animalAdapted.getString("name"));
        animal.setAge(animalAdapted.getInt("age"));
        animal.setFurry(animalAdapted.getBoolean("furry"));
        return animal;
    }
}
