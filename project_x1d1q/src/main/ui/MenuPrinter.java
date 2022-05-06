package ui;

import model.Drink;

import java.awt.Component;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Represents a printer for printing the current coffee shop menu based on ScreenPrinter Class in AlarmSystem
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

public class MenuPrinter extends JInternalFrame {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 300;
    private JTextArea logArea;

    // EFFECTS: constructs a window in which menu will be printed on screen
    public MenuPrinter(Component parent) {
        super("Menu:", false, true, false, false);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane);
        setSize(WIDTH, HEIGHT);
        setPosition(parent);
        setVisible(true);
    }

    // EFFECTS: prints each drink in the menu onto the menu printer
    public void printMenu(List<Drink> menu) {
        for (Drink d : menu) {
            logArea.setText(logArea.getText() + d.getType() + "\n\n");
        }
        repaint();
    }

    // EFFECTS: sets the position of window relative to parent
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth() - 20,
                parent.getHeight() - getHeight() - 20);
    }
}
