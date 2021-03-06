package model;

import java.util.Calendar;
import java.util.Date;

// Represents a coffee shop event
// Class taken from Event Class in the AlarmSystem
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

public class Event {
    private static final int HASH_CONSTANT = 13;

    private Date dateLogged;
    private String description;


    // EFFECTS: Constructs an event with a given description and date/time stamp
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // EFFECTS: returns the date/time stamp of this event
    public Date getDate() {
        return dateLogged;
    }

    // EFFECTS: returns the description of this event
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;
        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
