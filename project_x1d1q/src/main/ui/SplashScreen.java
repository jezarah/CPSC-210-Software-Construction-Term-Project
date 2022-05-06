package ui;

import javax.swing.*;
import java.awt.*;

// Represents a splash screen
// https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui

public class SplashScreen {
    private final JWindow window;
    private long startTime;
    private int minimumMilliseconds;

    // EFFECTS: constructs a splash screen
    public SplashScreen() {
        window = new JWindow();
        ImageIcon image = new ImageIcon("./data/splashscreen.jpg");
        window.getContentPane().add(new JLabel("", image, SwingConstants.CENTER));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setBounds((int) ((screenSize.getWidth() - image.getIconWidth()) / 2),
                (int) ((screenSize.getHeight() - image.getIconHeight()) / 2),
                image.getIconWidth(), image.getIconHeight());
    }

    // EFFECTS: shows the splash screen image for at least a minimumMilliseconds time
    public void show(int minimumMilliseconds) {
        this.minimumMilliseconds = minimumMilliseconds;
        window.setVisible(true);
        startTime = System.currentTimeMillis();
    }

    // EFFECTS: sets the splash screen to not visible
    public void hide() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        try {
            Thread.sleep(Math.max(minimumMilliseconds - elapsedTime, 0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }
}
