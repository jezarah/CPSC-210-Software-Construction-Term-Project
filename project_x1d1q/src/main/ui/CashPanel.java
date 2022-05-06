package ui;

import model.CoffeeShop;

import javax.swing.*;
import java.awt.*;

// Based on the ScorePanel Class from SpaceInvaders
// https://github.students.cs.ubc.ca/CPSC210/SpaceInvadersRefactored.git

public class CashPanel extends JPanel {
    private static final String CASH_TXT = "Earnings: $ ";
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;

    private JLabel cashLbl;

    // Constructs a cash panel
    // EFFECTS: sets the background colour and draws the initial labels;
    //          updates this with the simulator whose earnings are to be displayed
    public CashPanel(CoffeeShop coffeeShop) {
        setBackground(Color.GREEN);
        cashLbl = new JLabel(CASH_TXT + coffeeShop.getCash());
        cashLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(cashLbl);
    }

    // Updates the cash panel
    // MODIFIES: this
    // EFFECTS:  updates the earnings to reflect current state of game
    public void update(CoffeeShop coffeeShop) {
        String cashStr = CASH_TXT + coffeeShop.getCash();
        cashLbl.setText(cashStr);
        repaint();
    }
}
