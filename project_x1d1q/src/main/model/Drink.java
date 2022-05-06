package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a drink

public class Drink implements Writable {

    private String type;

    // EFFECTS: a drink is constructed with a type
    public Drink(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Methods JSONObject from Thingy Class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        return json;
    }
}
