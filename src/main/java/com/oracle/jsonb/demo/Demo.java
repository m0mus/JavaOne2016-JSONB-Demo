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
        System.out.println("   Choose a scenario to demonstrate or press Q to end the demonstration:\n");
        System.out.println("1. Marshalling and unmarshalling using default settings");
        System.out.println("2. Marshalling and unmarshalling using custom JsonbAdapter");
        System.out.println("3. Marshalling and unmarshalling using custom JsonbSerializer and JsonbDeserializer");
        System.out.println("4. Marshalling and unmarshalling of generic type using custom serialization\n");
        System.out.println("-----------------------------------------------------------------------------------");
    }

    private void runDefaultLoop() {
        JsonbConfig jsonbDefaultConfig = new JsonbConfig();
        jsonbDefaultConfig.withFormatting(true);
        Jsonb jsonb = JsonbBuilder.create(jsonbDefaultConfig);

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Dmitry", new Dog("Falco", 3, false, false)));
        persons.add(new Person("Petros", new Cat("Bob", 10, true, true)));
        persons.add(new Person("Sylvester", new Animal("Tweety", 3, false)));
        Type personListType = new ArrayList<Person>(){}.getClass().getGenericSuperclass();

        System.out.println("List of persons:");
        persons.forEach(person -> System.out.println("\t" + person));
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        System.out.println("\nDefault marshalling to JSON:");
        final String defaultMarshalling = jsonb.toJson(persons);
        System.out.println(defaultMarshalling);
        scanner.nextLine();

        System.out.println("\nUnmarshalling JSON string produced using default serialization:");
        List<Person> personsUnmarshalled = jsonb.fromJson(defaultMarshalling, personListType);
        personsUnmarshalled.forEach(person -> System.out.println("\t" + person));
        System.out.println("\n");
        scanner.nextLine();
    }

    private void runAdapterLoop() {
        JsonbConfig jsonbAdaptersConfig = new JsonbConfig();
        jsonbAdaptersConfig.
                withFormatting(true).
                withAdapters(new AnimalAdapter());
        Jsonb jsonbAdapted = JsonbBuilder.create(jsonbAdaptersConfig);

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Dmitry", new Dog("Falco", 3, false, false)));
        persons.add(new Person("Petros", new Cat("Bob", 10, true, true)));
        persons.add(new Person("Sylvester", new Animal("Tweety", 3, false)));
        Type personListType = new ArrayList<Person>(){}.getClass().getGenericSuperclass();

        System.out.println("List of persons:");
        persons.forEach(person -> System.out.println("\t" + person));
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        System.out.println("\nAdapted marshalling to JSON:");
        final String adaptedMarshalling = jsonbAdapted.toJson(persons, personListType);
        System.out.println(adaptedMarshalling);
        scanner.nextLine();

        System.out.println("\nUnmarshalling JSON string produced using serialization with adapter:");
        List<Person> personsUnmarshalledAdapter = jsonbAdapted.fromJson(adaptedMarshalling, personListType);
        personsUnmarshalledAdapter.forEach(person -> System.out.println("\t" + person));
        System.out.println("\n");
        scanner.nextLine();
    }

    private void runSerializerLoop() {
        JsonbConfig jsonbSerializersConfig = new JsonbConfig();
        jsonbSerializersConfig.
                withFormatting(true).
                withSerializers(new AnimalSerializer()).
                withDeserializers(new AnimalDeserializer());
        Jsonb jsonbCustom = JsonbBuilder.create(jsonbSerializersConfig);

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Dmitry", new Dog("Falco", 3, false, false)));
        persons.add(new Person("Petros", new Cat("Bob", 10, true, true)));
        persons.add(new Person("Sylvester", new Animal("Tweety", 3, false)));
        Type personListType = new ArrayList<Person>(){}.getClass().getGenericSuperclass();

        System.out.println("List of persons:");
        persons.forEach(person -> System.out.println("\t" + person));
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        System.out.println("\nCustom marshalling to JSON:");
        final String customMarshalling = jsonbCustom.toJson(persons, personListType);
        System.out.println(customMarshalling);
        scanner.nextLine();

        System.out.println("\nUnmarshalling JSON string produced using custom serialization:");
        List<Person> personsUnmarshalledCustom = jsonbCustom.fromJson(customMarshalling, personListType);
        personsUnmarshalledCustom.forEach(person -> System.out.println("\t" + person));
        System.out.println("\n");
        scanner.nextLine();
    }

    private void runGenericLoop() {
        JsonbConfig jsonbSerializersConfig = new JsonbConfig();
        jsonbSerializersConfig.
                withFormatting(true).
                withSerializers(new AnimalSerializer()).
                withDeserializers(new AnimalDeserializer());
        Jsonb jsonbCustom = JsonbBuilder.create(jsonbSerializersConfig);

        List<Carrier<Animal>> carriers = new ArrayList<>();
        carriers.add(new Carrier<>(Carrier.TYPE.BAG, new Cat("Felix", 6, true, true)));
        carriers.add(new Carrier<>(Carrier.TYPE.CRATE, new Dog("Max", 7, true, false)));
        Type carrierListType = new ArrayList<Carrier<Animal>>(){}.getClass().getGenericSuperclass();

        System.out.println("List of carriers:");
        carriers.forEach(carrier -> System.out.println("\t" + carrier));
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        System.out.println("\nCustom marshalling to JSON:");
        final String customMarshalling = jsonbCustom.toJson(carriers, carrierListType);
        System.out.println(customMarshalling);
        scanner.nextLine();

        System.out.println("\nUnmarshalling JSON string produced using custom serialization:");
        List<Carrier<Animal>> carriersUnmarshalledCustom = jsonbCustom.fromJson(customMarshalling, carrierListType);
        carriersUnmarshalledCustom.forEach(carrier -> System.out.println("\t" + carrier));
        System.out.println("\n");
        scanner.nextLine();
    }
}
