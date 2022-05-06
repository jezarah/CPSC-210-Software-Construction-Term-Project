package ui;

import model.CoffeeShop;
import model.Drink;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

// Represents a Printer for printing customer Dialogue based on ScreenPrinter Class in AlarmSystem
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

public class DialoguePrinter extends JInternalFrame {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private JTextArea dialogueArea;


    // EFFECTS: constructs a dialogue printer
    public DialoguePrinter(Component parent) {
        super("Serving next customer:", false, true, false, false);
        dialogueArea = new JTextArea();
        dialogueArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(dialogueArea);
        add(scrollPane);
        setSize(WIDTH, HEIGHT);
        setPosition(parent);
        setVisible(true);
    }

    // EFFECTS: Prints customer dialogue onto a window
    public void printCustomerDialogue(CoffeeShop cs) {
        int menuSize = cs.sizeOfMenu();
        int r = randomNumber(menuSize);
        Drink d = cs.getDrinkAtIndex(r);
        dialogueArea.setText(dialogueArea.getText() + orderDialogue(d.getType()) + "\n\n");
        dialogueArea.setText(dialogueArea.getText() + "Make and Serve Drink." + "\n\n");

        repaint();
    }

    // EFFECTS: prints one of three customer dialogue based on generated random number
    public String orderDialogue(String drinkName) {
        int number = randomNumber(2);
        if (number == 0) {
            return ("Hey there! I'll have a " + drinkName);
        }
        if (number == 1) {
            return (drinkName + ", please?");
        } else {
            return ("Hello, can I order a " + drinkName + " ?");
        }
    }

    // effects: returns a random number from 0 to the passed number - 1
    public int randomNumber(int passed) {
        Random rand = new Random();
        return rand.nextInt(passed);
    }

    /**
     * Sets the position of window in which log will be printed relative to
     * parent
     * @param parent  the parent component
     */
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth() - 20,
                parent.getHeight() - getHeight() - 20);
    }
}
