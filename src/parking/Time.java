/**
 * @author Osvath Tamas
 * @version 1.0
 * @since 2019-12-01
 * The Time class implements some functions to manipulate the current time, in order to facilitate simulation for the parking lot
 * In real life using of the application, no manipulation of current time is needed, only the retrieval of it
 */
package parking;

import java.io.Serializable;
import java.time.LocalTime;

public class Time implements Serializable {
    LocalTime time;

    /**
     * constructor
     */
    public Time() {
        time = LocalTime.now();
    }

    public int getMinute() {
        return time.getMinute();
    }

    /**
     * returns the current time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * adds a certain amount of hours
     * @param hour Int-number of hours to add
     */
    public void addHours(int hour) {
        time = time.plusHours(hour);
    }

    /**
     * Adds minutes to the current time
     * @param min nb of mins
     */
    public void addMinutes(int min) {
        time = time.plusMinutes(min);
    }

    /**
     * This method returns the current hour
     * @return the hour
     */
    public int getHour() {
        return time.getHour();
    }

    /**
     * @return the time in a preferred format
     */
    public String toStringTime() {
        return time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
    }

    /**
     * Function for checking if the time is midnight
     * @return true in case it is midnight, otherwise false
     */
    public boolean isMidnight() {
        //Time mid = new Time(LocalTime.of(0,0,0));

        if (this.getHour() == 0 && this.getMinute() == 0) return true;
        else return false;
    }

    /**
     * Unit test for current class
     */
    public static void main(String[] args) {

        Time t = new Time();
        System.out.println(t.toStringTime());
        t.addHours(2);
        System.out.println(t.toStringTime());
        t.addMinutes(3);
        System.out.println(t.toStringTime());

    }
}
