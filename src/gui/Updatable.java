/**
 * @author Osvath Tamas
 * @version 1.0
 * @since 06.01.2020
 * The Updatable class implements functions for updating components when running the gui
 */
package gui;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Updatable implements Serializable {

    private  ArrayList<Component> comps;

    public Updatable() {
        this.comps = new ArrayList<Component>();
    }

    /**
     * Adds a component to the class
     * @param component component to be added
     */
    public void addComponent (Component component) {
        this.comps.add(component);
    }

    /**
     * Updates the components in order to "refresh" the dashboard
     */
    public void update() {
        for (Component component : this.comps) {
            component.repaint();
        }
    }
}
