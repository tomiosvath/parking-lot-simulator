/**
 * @author Osvath Tamas
 * @version 1.0
 * @since 2012-12-01n
 * The ParkingLot class implements a parking lot, with specific functions
 */

package parking;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class ParkingLot implements Serializable {
    private HashMap<Integer, Car> parkingLot = new HashMap<Integer, Car>(50);
    private int nbAvailable;
    private int hourFee = 1;
    private int income;
    private final int maxLots = 50;
    private Time time;

    /**
     * Constructor
     *
     * @param time - Time type of object for having the current time in the parking
     */
    public ParkingLot(Time time) {
        nbAvailable = maxLots;
        income = 0;
        this.time = time;
    }

    /**
     * Checks if there are available spots
     *
     * @return true if yes, false if no
     */
    public boolean isAvailableSpot() {
        if (nbAvailable > 0) return true;
        else return false;
    }


    /**
     * Parks a car to the first position to be free
     *
     * @param car - The Car object to be parked
     */
    public void parkCar(Car car) {
        while (true) {
            if (!isAvailableSpot()) break;
            int x = (int) (Math.random() * 50);
            if (!parkingLot.containsKey(x)) {
                parkingLot.put(x, car);
                nbAvailable--;
                break;
            }
        }
    }

    /**
     * Exits the car from the parking
     *
     * @param plate - The license plate of the car
     */
    public void exitCar(String plate) {
        for (int i = 0; i < maxLots; i++) {
            if (!parkingLot.containsKey(i)) continue;
            if (parkingLot.get(i).getLicensePlate().equals(plate)) {
                parkingLot.get(i).exit(time);
                int time = parkingLot.get(i).getSpentTime();
                income += (time * hourFee);
                parkingLot.remove(i);
            }
        }
        nbAvailable++;
    }

    /**
     * Function for exiting a random car
     *
     * @return the exited car
     */
    public Car exitRandom() {
        while (!parkingLot.isEmpty()) {
            int x = (int) (Math.random() * 50);
            if (parkingLot.containsKey(x)) {
                parkingLot.get(x).exit(time);
                int time = parkingLot.get(x).getSpentTime();
                income += (time * hourFee);
                Car car = parkingLot.get(x);
                parkingLot.remove(x);
                nbAvailable++;
                return car;
            }
        }
        return null;
    }

    /**
     * @return income of the parking
     */
    public int getIncome() {
        return income;
    }

    /**
     * Prints a dashboard with each parking slot
     */
    public void dashboard() {
        System.out.println("THE PARKING IS: ");
        for (int i = 0; i < maxLots; i++)
            if (!parkingLot.containsKey(i))
                System.out.println(i + " parking slot is empty");
            else
                System.out.println(i + " parking slot contains car " + parkingLot.get(i).getLicensePlate());
    }

    /**
     * The function for drawing the parking dashboard on the screen
     *
     * @param graphics it is the abstract base class for all graphics contexts
     */
    public void drawParking(Graphics graphics) {
        int x = 0;
        int y = 100;
        int w = 100;
        int h = 90;

        Graphics2D g2 = (Graphics2D) graphics;
        Font label = new Font(Font.MONOSPACED, Font.BOLD, 15);
        Font title = new Font(Font.MONOSPACED, Font.BOLD, 20);

        g2.setColor(Color.BLACK);
        graphics.setFont(title);
        graphics.drawString("Current Profit: " + this.getIncome(), 500, 20);
        graphics.drawString("Current Time: " + this.time.toStringTime(), 500, 40);

        int column = 0;
        for (int i = 0; i < maxLots; i++) {
            if (i != 0 && i % 5 == 0) column += 130;
            if (parkingLot.containsKey(i))
                graphics.setColor(Color.RED);
            else
                graphics.setColor(Color.GREEN);
            graphics.fillRect(x + 20 + column, ((i % 5) + 1) * y, w, h);
            graphics.setColor(Color.BLACK);
            if (parkingLot.containsKey(i)) {
                graphics.setFont(title);
                graphics.drawString(parkingLot.get(i).getLicensePlate(), x + 23 + column, ((i % 5) + 1) * y + 20);
                graphics.setFont(label);
                graphics.drawString("Entry time: ", x + 21 + column, ((i % 5) + 1) * y + 35);
                graphics.drawString(parkingLot.get(i).getTime(), x + 21 + column, ((i % 5) + 1) * y + 50);
            } else {
                graphics.setFont(title);
                graphics.drawString("EMPTY", x + 21 + column, ((i % 5) + 1) * y + 20);
            }
        }
    }


    public void resetProfit() {
        income = 0;
    }

    public static void main(String[] args) {
        Time time = new Time();
        ParkingLot park = new ParkingLot(time);
        CarQueue queue = new CarQueue(park, time);
        Car[] cars = new Car[10];
        cars[0] = new Car("SJ10DTO");
        cars[1] = new Car("SJ07AMO");
        cars[2] = new Car("SJ01KOK");
        cars[3] = new Car("SJ99BOB");

        for (int i = 0; i < 4; i++)
            queue.addCar(cars[i]);

        queue.enterParking();
        queue.enterParking();

        time.addHours(3);
        queue.enterParking();
        queue.enterParking();

        park.exitRandom();
        park.exitRandom();

        for (int i = 0; i < 4; i++)
            System.out.println(cars[i].getEntryTime() + " " + cars[i].getExitTime() + " " + cars[i].getSpentTime());

        System.out.println("Income is: " + park.getIncome());
    }
}
