/**
 * @author Osvath Tamas
 * @version 1.0
 * @since 06.01.2020
 * The DrawingBoard class implements functions for displaying the graphics made in the classes
 */
package gui;

import parking.*;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class DrawingBoard extends JPanel implements Serializable {

    private Simulator simulator;

    public DrawingBoard (Simulator simulator) {
        this.simulator = simulator;
    }

    /**
     * Function for displaying the graphics
     * @param g graphics
     */
    @Override
    protected  void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.simulator.getParkingLot().drawParking(g);
    }
}
