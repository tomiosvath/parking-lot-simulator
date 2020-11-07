/**
 * @author Osvath Tamas
 * @version 1.0
 * @since 06.01.2020
 * The UserInterface class displays the graphics on the screen by using Runnable class's method run
 */
package gui;

import parking.*;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class UserInterface implements Runnable, Serializable {
    private Simulator simulator;
    private JFrame frame;
    private DrawingBoard board;
    private int width;
    private int height;

    public UserInterface (Simulator simulator, int width, int height) {
        this.simulator = simulator;
        this.width = width;
        this.height = height;
        this.frame = new JFrame("Parking Lot");
        this.board = new DrawingBoard(this.simulator);
    }

    /**
     * @return the simulator
     */
    public Simulator getSimulator() {
        return this.simulator;
    }

    /**
     * Function for creating the components and adding them to the container
     * @param container
     */
    private void createComponents (Container container) {
        container.add(board);

        this.simulator.getUpdatable().addComponent(board);
    }

    /**
     * Implicit function for running user interface
     */
    @Override
    public void run() {
        this.frame.setPreferredSize(new Dimension(this.width, this.height));
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.createComponents(this.frame.getContentPane());

        this.frame.pack();
        this.frame.setVisible(true);
    }
}
