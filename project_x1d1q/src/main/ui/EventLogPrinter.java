package ui;

import model.Event;
import model.EventLog;

// Represents a printer that prints the EventLog to the Console
public class EventLogPrinter {

    public EventLogPrinter() {
        printLog();
    }

    // EFFECTS: prints the event log
    public void printLog() {
        EventLog el = EventLog.getInstance();
        System.out.println("Event Log: ");
        for (Event next : el) {
            System.out.println(next.getDate());
            System.out.println(next.getDescription());
        }
    }
}
