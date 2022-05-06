package persistence;

// Tests taken from JsonWriterTest in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.CoffeeShop;
import model.Drink;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CoffeeShop cs = new CoffeeShop(100);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCoffeeshop() {
        try {
            CoffeeShop cs = new CoffeeShop(0);
            JsonWriter writer = new JsonWriter("./data/testWriterCoffeeShopEmpty.json");
            writer.open();
            writer.write(cs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCoffeeShopEmpty.json");
            cs = reader.read();
            assertEquals(0, cs.getCash());
            assertEquals(0, cs.sizeOfMenu());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCoffeeShop() {
        try {
            CoffeeShop cs = new CoffeeShop(10);
            cs.addDrink(new Drink("Coffee"));
            JsonWriter writer = new JsonWriter("./data/testWriterCoffeeShopGeneral.json");
            writer.open();
            writer.write(cs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCoffeeShopGeneral.json");
            cs = reader.read();
            assertEquals(10, cs.getCash());
            assertEquals(1, cs.sizeOfMenu());
            List<Drink> drinks = cs.getMenu();
            checkDrink("Coffee", drinks.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
