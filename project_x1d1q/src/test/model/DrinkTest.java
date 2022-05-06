package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import static java.awt.Event.TAB;
import static org.junit.jupiter.api.Assertions.*;

class DrinkTest {

    private Drink testDrink;

    @BeforeEach
    void runBefore() {
        testDrink = new Drink("London Fog");
    }

    @Test
    void testConstructor() {
        assertEquals("London Fog", testDrink.getType());
    }

    @Test
    void testToJson() throws IOException {
        JSONObject testJson = testDrink.toJson();
        JsonWriter writer = new JsonWriter("./data/testToJson.json");
        writer.open();
        JsonWriter.saveToFile(testJson.toString(TAB));
        writer.close();

        String testType = JsonReader.readFile("./data/testToJson.json");
        JSONObject jsonObject = new JSONObject(testType);
        String type = jsonObject.getString("type");
        assertEquals("London Fog", type);
    }
}