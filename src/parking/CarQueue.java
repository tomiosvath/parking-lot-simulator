/**
 * @author Osvath Tamas
 * @version 1.0
 * @since 2019-12-01
 * The CarQueue class implements the queue containing the cars, with specific functions, in order to gain access to the parking
 */

package parking;

import java.io.Serializable;
import java.util.ArrayList;

public class CarQueue implements Serializable {
    private ArrayList<Car> cars = new ArrayList<Car>();
    private ParkingLot lot;
    private Time time;

    /**
     * Constructor
     *
     * @param parking gets the ParkingLot type of object, the parking into which we want the cars to go in
     * @param time    gets the Time type of object, our current time
     */
    public CarQueue(ParkingLot parking, Time time) {
        this.lot = parking;
        this.time = time;
    }

    /**
     * Adds a car to the queue
     *
     * @param car added to the queue
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * The first car in the queue enters the parking, and it is removed from the queue
     */
    public void enterParking() {
        if (cars.size() == 0) {
            System.out.println("No cars to add to the parking lot");
            //int t = 0;
        } else if (!lot.isAvailableSpot()) {
            System.out.println("No available parking place");
            //int t = 0;
        } else {
            cars.get(0).enter(time);
            lot.parkCar(cars.get(0));
            cars.remove(0);
            //System.out.println(cars.size());
        }
    }

    /**
     * Prints the license plates of the cars which are in the queue
     */
    public void listCars() {
        for (int i = 0; i < cars.size(); i++)
            System.out.println(cars.get(i).getLicensePlate());
    }

    /**
     * Checks if the queue is empty or not
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (cars.size() == 0)
            return true;
        else
            return false;
    }
}
