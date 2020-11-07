/**
 * @author Osvath Tamas
 * @version 1.0
 * @since 2019-12-01
 * The Car class implements the car object
 */

package parking;

import java.io.Serializable;
import java.time.LocalTime;

public class Car implements Serializable {
    private String licensePlate;
    private LocalTime entryTime, exitTime;
    ;
    private boolean inParking;

    /**
     * constructor
     *
     * @param licensePlate the license plate of the car
     */
    public Car(String licensePlate) {
        this.licensePlate = licensePlate;
        this.entryTime = LocalTime.now();
        this.exitTime = LocalTime.now();
        inParking = false;
    }

    /**
     * Function for entering a parking
     *
     * @param time - the time when the car entered the parking
     */
    public void enter(Time time) {
        entryTime = LocalTime.from(time.getTime());
        inParking = true;
    }

    /**
     * @return the time in a preferred format
     */
    public String getTime() {
        return entryTime.getHour() + ":" + entryTime.getMinute() + ":" + entryTime.getSecond();
    }

    /**
     * Function for exiting a parking
     *
     * @param time - the time when the car exited the parking
     */
    public void exit(Time time) {
        exitTime = LocalTime.from(time.getTime());
        inParking = false;
    }

    /**
     * Function for calculating the spent time in the parking
     *
     * @return spent time
     */
    public int getSpentTime() {
        if (exitTime.getHour() >= entryTime.getHour())
            return (exitTime.getHour() - entryTime.getHour()); //hours
        else
            return (exitTime.getHour() + 24 - entryTime.getHour());
    }

    /**
     * @return license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * @return entry time hour
     */
    public int getEntryTime() {
        return entryTime.getHour();
    }

    /**
     * @return exit time hour
     */
    public int getExitTime() {
        return exitTime.getHour();
    }

    /**
     * Checks if the car is in the parking
     *
     * @return true if the car is in the parking, false otherwise
     */
    public boolean isInParking() {
        if (inParking)
            return true;
        else
            return false;
    }

    /**
     * @return a string in format HH:MM:SS for the entry time
     */
    public String getEntryString() {
        return entryTime.getHour() + ":" + entryTime.getMinute() + ":" + entryTime.getSecond();
    }

    /**
     * @return a string in format HH:MM:SS for the exit time
     */
    public String getExitString() {
        return exitTime.getHour() + ":" + exitTime.getMinute() + ":" + exitTime.getSecond();
    }
}
