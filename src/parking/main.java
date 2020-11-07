package parking;

import gui.*;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();

        UserInterface ui = new UserInterface(simulator, 1380, 900);
        SwingUtilities.invokeLater(ui);
    }
}
