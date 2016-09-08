package com.oracle.jsonb.demo;

import com.oracle.jsonb.demo.adapter.AnimalsAdapter;
import com.oracle.jsonb.demo.model.Animal;
import com.oracle.jsonb.demo.model.Cat;
import com.oracle.jsonb.demo.model.Dog;
import com.oracle.jsonb.demo.serializer.AnimalsDeserializer;
import com.oracle.jsonb.demo.serializer.AnimalsSerializer;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo {
    private final Jsonb jsonb;
    private final Jsonb jsonbAdapted;
    private final Jsonb jsonbCustom;
    private final List<Animal> animals;
    private final Type animalListType;
    private final Scanner scanner;

    private Demo() {
        JsonbConfig jsonbDefaultConfig = new JsonbConfig();
        jsonbDefaultConfig.withFormatting(true);
        jsonb = JsonbBuilder.create(jsonbDefaultConfig);

        JsonbConfig jsonbAdaptersConfig = new JsonbConfig();
        jsonbAdaptersConfig.
                withFormatting(true).
                withAdapters(new AnimalsAdapter());
        jsonbAdapted = JsonbBuilder.create(jsonbAdaptersConfig);

        JsonbConfig jsonbSerializersConfig = new JsonbConfig();
        jsonbSerializersConfig.
                withFormatting(true).
                withSerializers(new AnimalsSerializer()).
                withDeserializers(new AnimalsDeserializer());
        jsonbCustom = JsonbBuilder.create(jsonbSerializersConfig);

        animals = new ArrayList<>();
        animals.add(new Cat("Fix", 5, true, true));
        animals.add(new Cat("Bob", 10, true, false));
        animals.add(new Dog("Milo", 1, true, true));
        animals.add(new Animal("Tweety", 3, false));
        animalListType = new ArrayList<Animal>() {
        }.getClass().getGenericSuperclass();

        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new Demo().run();
    }

    private void run() {
        while (true) {
            printOptions();
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
            }
        }
    }

    private void printOptions() {
        System.out.println("----------------------------------- JSON-B Demo -----------------------------------\n");
        System.out.println("   Choose a scenario to demonstrate or press Q to end the demonstration:\n");
        System.out.println("1. Marshalling and unmarshalling using default settings");
        System.out.println("2. Marshalling and unmarshalling using custom JsonbAdapter");
        System.out.println("3. Marshalling and unmarshalling using custom JsonbSerializer and JsonbDeserializer\n");
        System.out.println("-----------------------------------------------------------------------------------");
    }

    private void runDefaultLoop() {
        System.out.println("List of animals:");
        animals.forEach(animal -> System.out.println("\t" + animal));
        scanner.nextLine();

        System.out.println("\nDefault marshalling to JSON:");
        final String defaultMarshalling = jsonb.toJson(animals);
        System.out.println(defaultMarshalling);
        scanner.nextLine();

        System.out.println("\nUnmarshalling JSON string produced using default serialization:");
        List<Animal> animalsUnmarshalled = jsonb.fromJson(defaultMarshalling, animalListType);
        animalsUnmarshalled.forEach(animal -> System.out.println("\t" + animal));
        System.out.println("\n");
    }

    private void runAdapterLoop() {
        System.out.println("List of animals:");
        animals.forEach(animal -> System.out.println("\t" + animal));
        scanner.nextLine();

        System.out.println("\nAdapted marshalling to JSON:");
        final String adaptedMarshalling = jsonbAdapted.toJson(animals, animalListType);
        System.out.println(adaptedMarshalling);
        scanner.nextLine();

        System.out.println("\nUnmarshalling JSON string produced using serialization with adapter:");
        List<Animal> animalsUnmarshalledAdapter = jsonbAdapted.fromJson(adaptedMarshalling, animalListType);
        animalsUnmarshalledAdapter.forEach(animal -> System.out.println("\t" + animal));
        System.out.println("\n");
    }

    private void runSerializerLoop() {
        System.out.println("List of animals:");
        animals.forEach(animal -> System.out.println("\t" + animal));
        scanner.nextLine();

        System.out.println("\nCustom marshalling to JSON:");
        final String customMarshalling = jsonbCustom.toJson(animals, animalListType);
        System.out.println(customMarshalling);
        scanner.nextLine();

        System.out.println("\nUnmarshalling JSON string produced using custom serialization:");
        List<Animal> animalsUnmarshalledCustom = jsonbCustom.fromJson(customMarshalling, animalListType);
        animalsUnmarshalledCustom.forEach(animal -> System.out.println("\t" + animal));
        System.out.println("\n");
    }
}
