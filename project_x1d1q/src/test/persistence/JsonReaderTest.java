package persistence;

import model.CoffeeShop;
import model.Drink;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests taken from JsonReaderTest in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest  extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CoffeeShop cs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyCoffeeShop() {
        JsonReader reader = new JsonReader("./data/testReaderCoffeeShopEmpty.json");
        try {
            CoffeeShop cs = reader.read();
            assertEquals(0, cs.getCash());
            assertEquals(0, cs.sizeOfMenu());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCoffeeShop() {
        JsonReader reader = new JsonReader("./data/testReaderCoffeeShopGeneral.json");
        try {
            CoffeeShop cs = reader.read();
            List<Drink> drinkList = cs.getMenu();
            assertEquals(4, drinkList.size());
            checkDrink("Coffee", drinkList.get(0));
            checkDrink("Latte", drinkList.get(1));
            checkDrink("Americano", drinkList.get(2));
            checkDrink("Matcha Latte", drinkList.get(3));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
