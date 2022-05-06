package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.awt.Event.TAB;
import static model.CoffeeShop.COST_OF_ONE_DRINK;
import static org.junit.jupiter.api.Assertions.*;

public class CoffeeShopTest {

    private CoffeeShop testCoffeeShop;

    @BeforeEach
    void runBefore() {
        testCoffeeShop = new CoffeeShop(100);
    }

    @Test
    void testConstructorEmptyMenu() {
        assertEquals(100, testCoffeeShop.getCash());
        assertEquals(0, testCoffeeShop.sizeOfMenu());
    }

    @Test
    void testGivenNegativeCashBalance() {
        testCoffeeShop = new CoffeeShop(-100);
        assertEquals(0, testCoffeeShop.getCash());
    }

    @Test
    void testGetMenu() {
        Drink testCoffee = new Drink("Coffee");
        Drink testLatte = new Drink("Latte");
        testCoffeeShop.addDrink(testCoffee);
        testCoffeeShop.addDrink(testLatte);
        List<Drink> drinkList = testCoffeeShop.getMenu();
        assertEquals(testCoffee, drinkList.get(0));
        assertEquals(testLatte, drinkList.get(1));
    }

    @Test
    void testAddToMenu() {
        Drink testLondonFog = new Drink("London Fog");
        Drink testCaffeMisto = new Drink("Caffe Misto");
        Drink testCappuccino = new Drink("Cappuccino");
        testCoffeeShop.addDrink(testLondonFog);
        testCoffeeShop.addDrink(testCaffeMisto);
        testCoffeeShop.addDrink(testCappuccino);
        assertEquals(testLondonFog, testCoffeeShop.getDrinkAtIndex(0));
        assertEquals(testCaffeMisto, testCoffeeShop.getDrinkAtIndex(1));
        assertEquals(testCappuccino, testCoffeeShop.getDrinkAtIndex(2));
        assertEquals(3, testCoffeeShop.sizeOfMenu());
    }

    @Test
    void testSetInitialDrinkList() {
        testCoffeeShop.setInitialDrinkList();
        Drink testDrink1 = testCoffeeShop.getDrinkAtIndex(0);
        Drink testDrink2 = testCoffeeShop.getDrinkAtIndex(1);
        Drink testDrink3 = testCoffeeShop.getDrinkAtIndex(2);
        assertEquals("Coffee", testDrink1.getType());
        assertEquals("Latte", testDrink2.getType());
        assertEquals("Americano", testDrink3.getType());
    }

    @Test
    void testRemoveDrinkFromMenu() {
        Drink testLondonFog = new Drink("London Fog");
        Drink testCaffeMisto = new Drink("Caffe Misto");
        Drink testCappuccino = new Drink("Cappuccino");
        testCoffeeShop.addDrink(testLondonFog);
        testCoffeeShop.addDrink(testCaffeMisto);
        testCoffeeShop.addDrink(testCappuccino);
        assertEquals(3, testCoffeeShop.sizeOfMenu());
        testCoffeeShop.removeDrink("London Fog");
        assertEquals(2,testCoffeeShop.sizeOfMenu());
        testCoffeeShop.removeDrink("Caffe Misto");
        assertEquals(1,testCoffeeShop.sizeOfMenu());
        assertEquals(testCappuccino, testCoffeeShop.getDrinkAtIndex(0));
    }

    @Test
    void testRemoveLastAddedToMenu() {
        Drink testLondonFog = new Drink("London Fog");
        Drink testCaffeMisto = new Drink("Caffe Misto");
        Drink testCappuccino = new Drink("Cappuccino");
        testCoffeeShop.addDrink(testLondonFog);
        testCoffeeShop.addDrink(testCaffeMisto);
        testCoffeeShop.addDrink(testCappuccino);
        assertEquals(3, testCoffeeShop.sizeOfMenu());
        testCoffeeShop.removeDrink("Cappuccino");
        assertEquals(testLondonFog, testCoffeeShop.getDrinkAtIndex(0));
        assertEquals(testCaffeMisto, testCoffeeShop.getDrinkAtIndex(1));
        assertEquals(2, testCoffeeShop.sizeOfMenu());
    }

    @Test
    void testAddSale() {
        testCoffeeShop.addSale();
        assertEquals(100 + COST_OF_ONE_DRINK, testCoffeeShop.getCash());
    }

    @Test
    void testToJsonAndMenuToJson() throws IOException {
        testCoffeeShop.addDrink(new Drink("Coffee"));
        JSONObject testJson = testCoffeeShop.toJson();
        JsonWriter writer = new JsonWriter("./data/testToJson.json");
        writer.open();
        JsonWriter.saveToFile(testJson.toString(TAB));
        writer.close();

        JsonReader reader = new JsonReader("./data/testToJson.json");
        testCoffeeShop = reader.read();
        assertEquals(100, testCoffeeShop.getCash());
        assertEquals(1, testCoffeeShop.sizeOfMenu());
    }


}
