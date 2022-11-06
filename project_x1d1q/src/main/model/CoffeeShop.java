package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Represents a Coffee Shop having a cash balance, menu and open status

public class CoffeeShop implements Writable {
    public static final int COST_OF_ONE_DRINK = 5;
    public static final int INITIAL_CASH_BALANCE = 0;

    private int cash;
    private List<Drink> menu;

    // Constructs a Coffee Shop
    // EFFECTS: constructs a coffee with a cash balance and menu
    public CoffeeShop(int cash) {
        this.cash = Math.max(cash, 0);
        menu = new ArrayList<>();
    }

    // getters
    public int getCash() {
        return cash;
    }

    // EFFECTS: returns an unmodifiable list of drinks in this coffeeshop
    public List<Drink> getMenu() {
        return Collections.unmodifiableList(menu);
    }

    // REQUIRES: drink != null
    // MODIFIES: this, theLog
    // EFFECTS: adds this drink to the end of the list of Drinks and logs the event
    public void addDrink(Drink drink) {
        menu.add(drink);
        String type = drink.getType();
        Event event = new Event("Added " + type + " to the menu.");
        EventLog.getInstance();
        EventLog.logEvent(event);
    }

    // MODIFIES: this, theLog
    // EFFECTS: returns true and removes a drink if it appears on the menu, false otherwise
    public void removeDrink(String type) {
        int size = sizeOfMenu();
        Drink checkDrink;
        int i = 0;
        boolean removed = false;
        while (i < size && !removed) {
            checkDrink = menu.get(i);
            if (Objects.equals(checkDrink.getType(), type)) {
                menu.remove(i);
                removed = true;
            }
            i++;
        }
        if (removed) {
            Event event = new Event("Removed " + type + " from the menu.");
            EventLog.getInstance();
            EventLog.logEvent(event);
        }
    }

    // EFFECTS: returns the number of items on the menu
    public int sizeOfMenu() {
        return menu.size();
    }

    // EFFECTS: given an index returns the Drink at that index in the Menu
    public Drink getDrinkAtIndex(int drinkIndex) {
        return menu.get(drinkIndex);
    }

    // MODIFIES: this
    // EFFECTS: adds the cost of one drink to the day's total amount
    public void addSale() {
        this.cash = cash + COST_OF_ONE_DRINK;
    }
    
    // modifies: this
    // effects: sets the shop's starting menu
    public void setInitialDrinkList() {
        menu.add(new Drink("Coffee"));
        menu.add(new Drink("Latte"));
        menu.add(new Drink("Americano"));
    }

    // Methods JSONObject and JSONArray from WorkRoom Class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cash", cash);
        json.put("menu", menuToJson());
        return json;
    }

    // EFFECTS: returns menu in coffee shop as a JSON array
    private JSONArray menuToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Drink d : menu) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}
