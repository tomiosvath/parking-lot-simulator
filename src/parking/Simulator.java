/**
 * @author Osvath Tamas
 * @version 1.0
 * @since 2012-12-01
 * The Simulator class implements a simulation for creating cars, entering them to queue, parking lot, exiting them, calculating the profit and so on
 */
package parking;

import gui.Updatable;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Simulator implements ActionListener, Serializable {
    //public class Simulator implements Serializable {
    private static String[] countyCode = {"AB", "HD", "AR", "IL", "AG", "IS", "BC", "IF", "BH", "MM", "BN", "MH", "BT", "MS", "BV", "NT", "BR", "OT", "BZ", "PH", "CS", "SM", "CL", "SJ", "CJ", "SB", "CT", "SV", "CV", "TR", "DB", "TM", "DJ", "TL", "GL", "VS", "GR", "VL", "GJ", "VN", "HR", "B"};
    private static String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Updatable updatable;
    private Timer timer;
    private Time time;// = new Time();
    private ParkingLot lot;// = new ParkingLot(time);
    private CarQueue q;// = new CarQueue(lot, time);
    File file;
    private int i = 0;

    /**
     * constructor
     */
    public Simulator() {
        file = new File("./cars.csv");
        this.Deserialize();
        this.updatable = new Updatable();
        //Simulate();

        this.timer = new javax.swing.Timer(10, this);
        this.timer.start();
    }

    /**
     * @return current time
     */
    public Time getTime() {
        return this.time;
    }

    /**
     * @return the parking lot
     */
    public ParkingLot getParkingLot() {
        return this.lot;
    }

    /**
     * @return the car queue
     */
    public CarQueue getCarQueue() {
        return this.q;
    }

    /**
     * Function for deserializing the data
     */
    private void Deserialize() {
        File simulatorSer = new File("./simulator.ser");
        boolean exists = simulatorSer.exists();
        this.time = null;
        this.lot = null;
        this.q = null;

        if (exists) {
            try {
                FileInputStream file = new FileInputStream("./simulator.ser");
                ObjectInputStream in = new ObjectInputStream(file);

                // Method for deserialization of object
                this.time = (Time) in.readObject();
                this.lot = (ParkingLot) in.readObject();
                this.q = (CarQueue) in.readObject();

                in.close();
                file.close();
                //System.out.println(simulator.time.getHour());
            } catch (IOException e) {
                System.out.println("IOException is caught");
                System.out.println(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            this.time = new Time();
            this.lot = new ParkingLot(this.time);
            this.q = new CarQueue(this.lot, this.time);
        }
    }

    /**
     * Function for serializing the data
     */
    private void Serialize() {
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("simulator.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this.time);
            out.writeObject(this.lot);
            out.writeObject(this.q);

            out.close();
            file.close();
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            System.out.println(ex);
        }
    }

    /**
     * @return a random license plate, in the format accepted in Romania
     */
    private String generateLicensePlate() {
        String license;

        //Get a random index from the county list
        int randCounty = (int) (Math.random() * (countyCode.length));

        //Get a random 3 letter string from the alphabet
        int[] randAlpha = {(int) (Math.random() * (alphabet.length)), (int) (Math.random() * (alphabet.length)), (int) (Math.random() * (alphabet.length))};

        //Get a random number from 1 to 99
        int randNr = (int) (Math.random() * 99) + 1;

        if (randNr > 9) {
            license = countyCode[randCounty] + Integer.toString(randNr) + alphabet[randAlpha[0]] + alphabet[randAlpha[1]] + alphabet[randAlpha[2]];
        } else {
            license = countyCode[randCounty] + "0" + Integer.toString(randNr) + alphabet[randAlpha[0]] + alphabet[randAlpha[1]] + alphabet[randAlpha[2]];
        }

        return license;
    }

    /**
     * Simulates the working mode
     */
    public void Simulate() {
        time.addMinutes(1);
        i++;
        int nbExit = (int) (Math.random() * 5) + 1;
        if (i == 5) {
            for (int i = 0; i < nbExit; i++) {
                Car car = lot.exitRandom();
                if (car != null) {
                    try (FileWriter writer = new FileWriter(this.file, true)) {
                        StringBuilder builder = new StringBuilder();

                        builder.append(car.getLicensePlate());
                        builder.append(',');
                        builder.append(car.getEntryString());
                        builder.append(',');
                        builder.append(car.getExitString());
                        builder.append('\n');

                        writer.write(builder.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ;
                }
            }
            Car[] cars = new Car[5];

            //get number of cars to put in the queue
            int nbCars = (int) (Math.random() * 5) + 1;

            //generate the cars and add them to the queue;
            for (int i = 0; i < nbCars; i++) {
                cars[i] = new Car(generateLicensePlate());
                q.addCar(cars[i]);
            }

            while (!q.isEmpty() && lot.isAvailableSpot()) {
                q.enterParking();
            }
            if (i == 5) i = 0;
        }
        if (time.isMidnight()) {
            //System.out.println("TES");
            lot.resetProfit();
        }
    }


    /**
     * @return the updatable object
     */
    public Updatable getUpdatable() {
        return this.updatable;
    }

    /**
     * Function for performing an action
     *
     * @param event invokes a component-defined action
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        this.Simulate();
        this.updatable.update();
        this.Serialize();
        this.timer.setDelay(30);
    }
}
