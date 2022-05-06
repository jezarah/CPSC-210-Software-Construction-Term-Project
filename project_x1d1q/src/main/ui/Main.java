package ui;

import java.io.FileNotFoundException;

// The ShutdownHook lines taken from
// https://www.baeldung.com/jvm-shutdown-hooks

public class Main {
    public static void main(String[] args) {
        try {
            new CoffeeShopUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
        Thread printingHook = new Thread(() -> new EventLogPrinter());
        Runtime.getRuntime().addShutdownHook(printingHook);
    }
}
