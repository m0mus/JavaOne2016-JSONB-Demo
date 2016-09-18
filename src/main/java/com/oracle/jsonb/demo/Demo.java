package com.oracle.jsonb.demo;

import com.oracle.jsonb.demo.adapter.AnimalAdapter;
import com.oracle.jsonb.demo.model.*;
import com.oracle.jsonb.demo.serializer.AnimalDeserializer;
import com.oracle.jsonb.demo.serializer.AnimalSerializer;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        new Demo().run();
    }

    private void run() {
        while (true) {
            printOptions();
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextLine()) {
                case "q":
                case "Q":
                    return;
                case "1":
                    runDefaultLoop();
                    break;
                case "2":
                    runAdapterLoop();
                    break;
                case "3":
                    runSerializerLoop();
                    break;
                case "4":
                    runGenericLoop();
            }
        }
    }

    private void printOptions() {
        System.out.println("----------------------------------- JSON-B Demo -----------------------------------\n");
        System.out.println("Choose a scenario to demonstrate or press Q to end the demonstration:\n");
        System.out.println("Serialization and deserialization of\n");
        System.out.println("1. List<Dog> using default settings");
        System.out.println("2. List<Animal> using adapter");
        System.out.println("3. List<Person> using serializer");
        System.out.println("4. List<Carrier<Animal>> using serializer\n");
        System.out.println("-----------------------------------------------------------------------------------");
    }

    private void runDefaultLoop() {
        // Create default JSONB engine
        Jsonb jsonb = JsonbBuilder.create();

        // Create list of dogs
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Falco", 4, false, false));
        dogs.add(new Dog("Cassidy", 6, false, true));
        Type dogsListType = new ArrayList<Dog>() {}.getClass().getGenericSuperclass();

        // Print list of dogs
        System.out.println("List of dogs:");
        dogs.forEach(person -> System.out.println("\t" + person));
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        // Serialize and print result
        System.out.println("\nAfter serialization:");
        final String defaultMarshalling = jsonb.toJson(dogs);
        System.out.println(defaultMarshalling);
        scanner.nextLine();ª

        // Deserialize and print result
        System.out.println("\nAfter deserialization:");
        List<Dog> personsUnmarshalled = jsonb.fromJson(defaultMarshalling, dogsListType);
        personsUnmarshalled.forEach(person -> System.out.println("\t" + person));
        System.out.println("\n");
        scanner.nextLine();
    }

    /**
     * This sample demonstrate serialization and deserialization of a generic list of objects with the same parent.
     */
    private void runAdapterLoop() {
        // Create configuration, attach adapters and create JSONB engine
        JsonbConfig config = new JsonbConfig()
                .withFormatting(true)
                .withAdapters(new AnimalAdapter());
        Jsonb jsonb = JsonbBuilder.create(config);

        // Create a list of animals
        List<Animal> animals = new ArrayList<>(3);
        animals.add(new Dog("Falco", 4, false, false));
        animals.add(new Dog("Cassidy", 6, false, true));
        animals.add(new Cat("Harris", 10, true, true));
        Type animalsListType = new ArrayList<Animal>() {}.getClass().getGenericSuperclass();

        // Print list of animals
        System.out.println("List of animals:");
        animals.forEach(animal -> System.out.println("\t" + animal));
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        // Serialize and print result
        System.out.println("\nAfter serialization:");
        final String adaptedMarshalling = jsonb.toJson(animals, animalsListType);
        System.out.println(adaptedMarshalling);
        scanner.nextLine();

        // Deserialize and print result
        System.out.println("\nAfter deserialization:");
        List<Animal> personsUnmarshalledAdapter = jsonb.fromJson(adaptedMarshalling, animalsListType);
        personsUnmarshalledAdapter.forEach(person -> System.out.println("\t" + person));
        System.out.println("\n");
        scanner.nextLine();
    }

    /**
     * This sample demonstrates serialization a list of objects having a property of an abstract type.
     */
    private void runSerializerLoop() {
        // Create configuration, attach (de)serializers and create JSONB engine
        JsonbConfig config = new JsonbConfig()
                .withFormatting(true)
                .withSerializers(new AnimalSerializer())
                .withDeserializers(new AnimalDeserializer());
        Jsonb jsonb = JsonbBuilder.create(config);

        // Create a list of persons
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Dmitry", new Dog("Falco", 3, false, false)));
        persons.add(new Person("Petros", new Cat("Harris", 10, true, true)));
        Type personListType = new ArrayList<Person>() {}.getClass().getGenericSuperclass();

        // Print list of persons
        System.out.println("List of persons:");
        persons.forEach(person -> System.out.println("\t" + person));
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        // Serialize and print the result
        System.out.println("\nAfter serialization:");
        final String customMarshalling = jsonb.toJson(persons, personListType);
        System.out.println(customMarshalling);
        scanner.nextLine();

        // Deserialize and print result
        System.out.println("\nAfter deserialization:");
        List<Person> personsUnmarshalledCustom = jsonb.fromJson(customMarshalling, personListType);
        personsUnmarshalledCustom.forEach(person -> System.out.println("\t" + person));
        System.out.println("\n");
        scanner.nextLine();
    }

    /**
     * This sample demonstrates serialization of a list of generic types.
     */
    private void runGenericLoop() {
        // Create configuration, attach (de)serializers and create JSONB engine
        JsonbConfig config = new JsonbConfig()
                .withFormatting(true)
                .withSerializers(new AnimalSerializer())
                .withDeserializers(new AnimalDeserializer());
        Jsonb jsonbCustom = JsonbBuilder.create(config);

        // Create a list of carrier objects
        List<Carrier<Animal>> carriers = new ArrayList<>();
        carriers.add(new Carrier<>(Carrier.TYPE.BAG, new Cat("Harris", 10, true, true)));
        carriers.add(new Carrier<>(Carrier.TYPE.CRATE, new Dog("Falco", 4, false, false)));
        Type carrierListType = new ArrayList<Carrier<Animal>>() {}.getClass().getGenericSuperclass();

        // Prints a list of carriers
        System.out.println("List of carriers:");
        carriers.forEach(carrier -> System.out.println("\t" + carrier));
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        // Serialize and print the result
        System.out.println("\nAfter serialization:");
        final String customMarshalling = jsonbCustom.toJson(carriers, carrierListType);
        System.out.println(customMarshalling);
        scanner.nextLine();

        // Deserialize and print result
        System.out.println("\nAfter deserialization:");
        List<Carrier<Animal>> carriersUnmarshalledCustom = jsonbCustom.fromJson(customMarshalling, carrierListType);
        carriersUnmarshalledCustom.forEach(carrier -> System.out.println("\t" + carrier));
        System.out.println("\n");
        scanner.nextLine();
    }
}
