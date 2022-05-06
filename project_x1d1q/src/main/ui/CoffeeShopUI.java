package ui;


import model.CoffeeShop;
import model.Drink;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Represents the main window in which the coffee shop simulator is played
// Methods based on the format found in the AlarmSystem
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

public class CoffeeShopUI extends JFrame {
    private static final String JSON_STORE = "./data/coffeeshop.json";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private JDesktopPane desktop;
    private JInternalFrame controlPanel;

    private CoffeeShop coffeeShop;
    private CashPanel cashPanel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Constructs main window
    // EFFECTS: sets up window in which Coffee Shop simulator will be played
    public CoffeeShopUI() throws FileNotFoundException {
        coffeeShop = new CoffeeShop(CoffeeShop.INITIAL_CASH_BALANCE);
        coffeeShop.setInitialDrinkList();
        desktop = new JDesktopPane();

        desktop.addMouseListener(new DesktopFocusAction());
        controlPanel = new JInternalFrame();
        controlPanel.setLayout(new BorderLayout());

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setContentPane(desktop);
        setTitle("Coffee Shop Simulator");
        setSize(WIDTH, HEIGHT);

        addSplashScreen();
        addMenu();
        addButtonPanel();
        addCashPanel();

        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setResizable(false);
        setVisible(true);
    }

    // EFFECTS: adds a splash screen when main is run
    private void addSplashScreen() {
        SplashScreen splash = new SplashScreen();
        splash.show(2000);
        splash.hide();
    }

    // EFFECTS: adds ButtonPanel that correspond to specific Actions
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.add(new JButton(new ServeCustomerAction()));
        buttonPanel.add(new JButton(new AddDrinkAction()));
        buttonPanel.add(new JButton(new RemoveDrinkAction()));
        buttonPanel.add(new JButton(new PrintMenuAction()));

        controlPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    // EFFECTS: sets up the cash balance panel on the control panel
    private void addCashPanel() {
        cashPanel = new CashPanel(coffeeShop);
        controlPanel.add(cashPanel, BorderLayout.NORTH);
    }

    // EFFECTS: Adds a menu to the main window
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu saveMenu = new JMenu("Options");
        saveMenu.setMnemonic('S');
        addMenuItem(saveMenu, new SaveGameAction(),
                KeyStroke.getKeyStroke("control S"));
        addMenuItem(saveMenu, new LoadGameAction(),
                KeyStroke.getKeyStroke("control L"));
        menuBar.add(saveMenu);

        setJMenuBar(menuBar);
    }

    // EFFECTS: adds an item to the JMenu with the above parameters
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }


    // MODIFIES: coffeeShop
    // EFFECTS: Opens a customer dialogue window, adds earnings, and updates the cash panel
    private class ServeCustomerAction extends AbstractAction {
        ServeCustomerAction() {
            super("Serve Customer");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DialoguePrinter dp;
            dp = new DialoguePrinter(CoffeeShopUI.this);
            desktop.add(dp);
            dp.printCustomerDialogue(coffeeShop);
            coffeeShop.addSale();
            cashPanel.update(coffeeShop);
        }
    }

    // MODIFIES: coffeeShop
    // EFFECTS: pops up a JOptionPane that prompts user input then adds the drink to menu
    private class AddDrinkAction extends AbstractAction {
        AddDrinkAction() {
            super("Add Drink");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String newDrink = JOptionPane.showInputDialog(null,
                    "Enter drink name:",
                    "New Menu Item",
                    JOptionPane.PLAIN_MESSAGE);

            coffeeShop.addDrink(new Drink(newDrink));
            JOptionPane.showMessageDialog(null,
                    "Added " + newDrink + " to the menu!");

        }
    }

    // MODIFIES: coffeeShop
    // EFFECTS: removes the drink that user inputs from the Coffee Shop Menu
    private class RemoveDrinkAction extends AbstractAction {
        RemoveDrinkAction() {
            super("Remove Drink");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            int s1;
            int s2;
            s1 = coffeeShop.sizeOfMenu();
            String type = JOptionPane.showInputDialog(null,
                    "Enter drink name: ",
                    "Remove Menu Item",
                    JOptionPane.PLAIN_MESSAGE);
            coffeeShop.removeDrink(type);
            s2 = coffeeShop.sizeOfMenu();
            if (s2 < s1) {
                JOptionPane.showMessageDialog(null,
                        "Removed " + type + " from the menu!");
            } else {
                JOptionPane.showMessageDialog(null,
                        "That isn't on your menu...");
            }
        }
    }

    // EFFECTS: opens a window and prints the current shop's menu
    private class PrintMenuAction extends AbstractAction {
        PrintMenuAction() {
            super("Print Menu");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            MenuPrinter mp;
            mp = new MenuPrinter(CoffeeShopUI.this);
            desktop.add(mp);

            mp.printMenu(coffeeShop.getMenu());
        }
    }

    // EFFECTS: saves the current coffeeshop to JSON_STORE
    private class SaveGameAction extends AbstractAction {
        SaveGameAction() {
            super("Save Progress");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(coffeeShop);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // MODIFIES: coffeeShop
    // EFFECTS: loads the previously saved coffeeshop from JSON_STORE and updates the cash panel
    private class LoadGameAction extends AbstractAction {
        LoadGameAction() {
            super("Load Progress");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                coffeeShop = jsonReader.read();
                cashPanel.update(coffeeShop);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // EFFECTS: Needed for key handling
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            CoffeeShopUI.this.requestFocusInWindow();
        }
    }
}
