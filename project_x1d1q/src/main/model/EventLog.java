package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


// Represents a log of coffee shop events. Utilizing Singleton Design
// Pattern to ensure that there is only a single instance of EventLog
// Class taken from EventLog Class in AlarmSystem
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

public class EventLog implements Iterable<Event> {

    // the only EventLog (Singleton Design Pattern)
    private static EventLog theLog;
    private static Collection<Event> events;


    // Private Constructor to prevent external construction
    // EFFECTS: Constructs the EventLog
    private EventLog() {
        events = new ArrayList<>();
    }


    // EFFECTS: returns instance of existing EventLog or creates it and returns it yet to exist
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds an event to the coffee shop EventLog
    public static void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: clears the previous events in the log and logs this event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}

