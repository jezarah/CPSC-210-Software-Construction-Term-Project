package persistence;

import model.CoffeeShop;
import model.Drink;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Taken from JsonTest in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkDrink(String type, Drink drink) {
        assertEquals(type, drink.getType());
    }
}
