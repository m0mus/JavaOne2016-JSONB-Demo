package com.oracle.jsonb.demo.serializer;

import com.oracle.jsonb.demo.model.Animal;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

public class AnimalSerializer implements JsonbSerializer<Animal> {
    public void serialize(Animal animal, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
        if (animal != null) {
            serializationContext.serialize(animal.getClass().getName(), animal, jsonGenerator);
        } else {
            serializationContext.serialize(null, jsonGenerator);
        }
    }
}
