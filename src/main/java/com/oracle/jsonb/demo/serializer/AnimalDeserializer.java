package com.oracle.jsonb.demo.serializer;

import com.oracle.jsonb.demo.model.Animal;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;

public class AnimalDeserializer implements JsonbDeserializer<Animal> {
    public Animal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {
        Animal animal = null;
        while (jsonParser.hasNext()) {
            JsonParser.Event event = jsonParser.next();
            if (event == JsonParser.Event.KEY_NAME) {
                String className = jsonParser.getString();
                jsonParser.next();
                try {
                    animal = deserializationContext.deserialize(Class.forName(className).asSubclass(Animal.class), jsonParser);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return animal;
    }
}
