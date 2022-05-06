package persistence;

import model.CoffeeShop;
import model.Drink;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Methods readFile, parseCoffeeShop, addMenu and addDrink taken from and based on JsonReader class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads coffee shop from JSON data stored in file
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads coffee shop from file and returns it
    // throws IOException if an error occurs reading data from file
    public CoffeeShop read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCoffeeShop(jsonObject);
    }


    // EFFECTS: reads source file as string and returns it
    public static String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses coffee shop from JSON object and returns it
    private CoffeeShop parseCoffeeShop(JSONObject jsonObject) {
        int cash = jsonObject.getInt("cash");
        CoffeeShop cs = new CoffeeShop(cash);
        addMenu(cs, jsonObject);
        return cs;
    }

    // MODIFIES: cs
    // EFFECTS: parses menu from JSON object and adds them to coffeeshop
    private void addMenu(CoffeeShop cs, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("menu");
        for (Object json : jsonArray) {
            JSONObject nextDrink = (JSONObject) json;
            addDrink(cs, nextDrink);
        }
    }

    // MODIFIES: cs
    // EFFECTS: parses drink from JSON object and adds it to coffeeshop
    private void addDrink(CoffeeShop cs, JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        Drink drink = new Drink(type);
        cs.addDrink(drink);
    }
}
