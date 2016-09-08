package com.oracle.jsonb.demo.serializer;

import com.oracle.jsonb.demo.model.Animal;
import com.oracle.jsonb.demo.model.Cat;
import com.oracle.jsonb.demo.model.Dog;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

public class AnimalsSerializer implements JsonbSerializer<Animal[]> {
    public void serialize(Animal[] animals, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
        if (animals != null) {
            jsonGenerator.writeStartArray("animals");
            for (Animal animal : animals) {
                jsonGenerator.writeStartObject();
                if (Cat.class.isAssignableFrom(animal.getClass())) {
                    jsonGenerator.write("type", "cat");
                    jsonGenerator.write("cuddly", ((Cat) animal).isCuddly());
                } else if (Dog.class.isAssignableFrom(animal.getClass())) {
                    jsonGenerator.write("type", "dog");
                    jsonGenerator.write("barking", ((Dog) animal).isBarking());
                } else {
                    jsonGenerator.write("type", "animal");
                }
                jsonGenerator.write("name", animal.getName());
                jsonGenerator.write("age", animal.getAge());
                jsonGenerator.write("furry", animal.isFurry());
                jsonGenerator.writeEnd();
            }
            jsonGenerator.writeEnd();
        } else {
            serializationContext.serialize(null, jsonGenerator);
        }
    }
}
