package com.oracle.jsonb.demo.serializer;

import com.oracle.jsonb.demo.model.Animal;
import com.oracle.jsonb.demo.model.Cat;
import com.oracle.jsonb.demo.model.Dog;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AnimalsDeserializer implements JsonbDeserializer<Animal[]> {
    public Animal[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {
        List<Animal> animals = new ArrayList<>();

        if (jsonParser.hasNext()) {
            JsonParser.Event event = jsonParser.next();
            if (event == JsonParser.Event.KEY_NAME && jsonParser.getString().equals("animals")) {
                event = jsonParser.next();
                if (event == JsonParser.Event.START_ARRAY) {
                    Animal animal = null;
                    while (jsonParser.hasNext()) {
                        event = jsonParser.next();
                        if (event == JsonParser.Event.START_OBJECT) {
                            continue;
                        }
                        if (event == JsonParser.Event.END_OBJECT) {
                            if (animal != null) {
                                animals.add(animal);
                            }
                            animal = null;
                            continue;
                        }
                        if (event == JsonParser.Event.KEY_NAME) {
                            switch (jsonParser.getString()) {
                            case "type":
                                jsonParser.next();
                                switch (jsonParser.getString()) {
                                case "cat":
                                    animal = new Cat();
                                    break;
                                case "dog":
                                    animal = new Dog();
                                    break;
                                default:
                                    animal = new Animal();
                                }
                                break;
                            case "name":
                                jsonParser.next();
                                animal.setName(jsonParser.getString());
                                break;
                            case "age":
                                jsonParser.next();
                                animal.setAge(jsonParser.getInt());
                                break;
                            case "furry":
                                event = jsonParser.next();
                                animal.setFurry(event == JsonParser.Event.VALUE_TRUE);
                                break;
                            case "cuddly":
                                event = jsonParser.next();
                                ((Cat) animal).setCuddly(event == JsonParser.Event.VALUE_TRUE);
                                break;
                            case "barking":
                                event = jsonParser.next();
                                ((Dog) animal).setBarking(event == JsonParser.Event.VALUE_TRUE);
                            }
                        }
                    }
                }
            }
        }

        return animals.toArray(new Animal[0]);
    }
}
