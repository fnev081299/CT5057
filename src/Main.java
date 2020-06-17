import java.util.*;
import java.io.*;


/**
 *  Running the program
 */
public class Main {

    /**
     * Runs the main program
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // scheduler object
        Scheduler schedule = new Scheduler();

        String again = null;
        String chosenFunct;

        // helpful info
        Scanner s = new Scanner(System.in);
        System.out.println("<--- Scheduler System --->");
        System.out.println("<----- S = Schedule ----->");
        System.out.println("<------ C = Cancel ------>");
        System.out.println("<-- P = Customer Info --->");
        System.out.println("<--- F = Flight Info ---->");
        System.out.println("<-- R = Flight Routes --->");
        System.out.println("<------- Q = Quit ------->");
        System.out.println("<------- H = Help ------->");
        System.out.println("----> Enter Command Below:");
        chosenFunct = s.nextLine().toLowerCase();

        // flight list object
        FlightList f = new FlightList();

        // chosen function
        switch (chosenFunct) {
            case "s": // scheduling
                System.out.println("-- Scheduling --");

                String name;
                int passengerID;
                String seatClass;
                int flightNum;

                System.out.println("Scheduling Passenger: ");

                System.out.println("Name: ");
                Scanner y1 = new Scanner(System.in);
                name = y1.nextLine().toLowerCase();

                System.out.println("Passenger ID Number: ");
                Scanner y2 = new Scanner(System.in);
                passengerID = y2.nextInt();

                System.out.println("Flight Number: ");
                Scanner y3 = new Scanner(System.in);
                flightNum = y3.nextInt();

                System.out.println("Seat Class: ");
                Scanner y4 = new Scanner(System.in);
                seatClass = y4.nextLine().toLowerCase();

                schedule.setFlightSchedule(name, passengerID, flightNum, seatClass);
                System.out.println("Scheduled");

                break;

            case "c": // cancelling
                String type;

                System.out.println("From waiting list or booked passengers? \nb = Booked \nw = Waiting list");
                Scanner checkType = new Scanner(System.in);
                type = checkType.nextLine().toLowerCase();

                int flight;
                int ID;
                String classSeat;
                String name1;

                System.out.println("Flight: ");
                Scanner checkFlight1 = new Scanner(System.in);
                flight = checkFlight1.nextInt();

                System.out.println("Type of seat: ");
                Scanner checkSeat1 = new Scanner(System.in);
                classSeat = checkSeat1.nextLine().toLowerCase();

                System.out.println("Passenger ID Number: ");
                Scanner checkPassenger1 = new Scanner(System.in);
                ID = checkPassenger1.nextInt();

                System.out.println("Passenger Name: ");
                Scanner checkPassengerName1 = new Scanner(System.in);
                name1 = checkPassengerName1.nextLine().toLowerCase();

                switch (type) {
                    case "b": // booking cancellation
                        System.out.println("Cancelling");
                        schedule.setFlightCancellation(name1, ID, flight, true, classSeat);

                        break;
                    case "w": // wait list cancellation
                        System.out.println("Removing from waiting list");
                        schedule.setFlightCancellation(name1, ID, flight, false, classSeat);

                        break;
                }

                break;

            case "p": // passenger info
                String namePassenger;
                Integer pID;

                System.out.println("Passenger Information: "); // flight number, class and name

                System.out.println("Name: ");
                Scanner e1 = new Scanner(System.in);
                namePassenger = e1.nextLine().toLowerCase();

                System.out.println("Passenger ID: ");
                Scanner e2 = new Scanner(System.in);
                pID = e2.nextInt();

                schedule.getPassengerStatus(namePassenger, pID);

                break;

            case "f": // flight info
                int flightNum1;

                System.out.println("Flight Information: "); // flight number, class and name
                System.out.println("Flight Number: ");

                Scanner s1 = new Scanner(System.in);
                flightNum1 = s1.nextInt();

                schedule.getFlightStatus(flightNum1);

                break;

            case "r": // route info
                String dep;
                String dest;

                System.out.println("Departing from: ");
                Scanner departureLocation = new Scanner(System.in);
                dep = departureLocation.nextLine().toUpperCase();

                System.out.println("Departing from: ");
                Scanner destinationLocation = new Scanner(System.in);
                dest = destinationLocation.nextLine().toUpperCase();

                schedule.getRoute(dep, dest);

                break;

            case "q": // quiting
                System.exit(0);
                break;

            case "h": // help
                System.out.println("Help");
                System.out.println("S = Scheduling a passenger on a flight" +
                        "\nC = Cancel a passenger from a flight" +
                        "\nP = Prints the passenger information" +
                        "\nF = Prints the flight information" +
                        "\nQ = Quitting the system" +
                        "\nH = Help");
                break;
        }
    }
}

/**
 * Scheduler = main commands used
 */
class Scheduler {
    FlightList flightList;

    /**
     * Constructor
     */
    public Scheduler() {

    }

    /**
     * Scheduling the passenger on a flight
     * @param name Passenger name
     * @param passengerID Passenger id number
     * @param flightNum Flight number
     * @param seatClass Seat class first, business or economy
     * @throws IOException Exception
     */
    public void setFlightSchedule(String name, Integer passengerID, Integer flightNum, String seatClass)
            throws IOException {
        Passenger NewPassenger = new Passenger(name, passengerID);

        // If true then there is already a passenger with that id
        if(NewPassenger.isIDAlready(passengerID)){

            flightList = new FlightList();
            flightList.createFlightListInfo();

            // Passenger Exists
            try {
                flightList.searchFlights(flightNum); // Check flight list for flight

                flightList.getFromKey(flightNum).addPassengers(NewPassenger, seatClass);

                // Adds passenger into the chosen class
                flightList.addToFlight(flightNum, seatClass, passengerID);
                NewPassenger.addToBookings(flightNum);

                System.out.println("--- Passengers on flight info ---");

                // flight information when booked
                getFlightStatus(flightNum);

                return;

            } catch (Exception e) {
                System.out.println("\n--- Flight Scheduling Error ---");
                // flightList.printFlightInfo(); overwrites the lists
            }
        } else {
            // Passenger doesnt exist
            flightList = new FlightList();
            flightList.createFlightListInfo();

            try {
                flightList.searchFlights(flightNum); // Check flight list for flight
                flightList.getFromKey(flightNum).addPassengers(NewPassenger, seatClass);

                // adds passenger into the class
                flightList.addToFlight(flightNum, seatClass, passengerID);
                NewPassenger.addBookings(flightNum);
//              System.out.println(NewPassenger.getBookings());

                System.out.println("--- Passengers on flight info ---");
                getFlightStatus(flightNum);

            } catch (Exception e) {
                System.out.println("\n--- Flight Scheduling Error ---");
            }
            NewPassenger.setupPassenger();
        }

    }

    /**
     * Cancelling a flight for a passenger
     * @param name Name of passenger
     * @param passengerID Passenger Id number
     * @param flightNum Flight number
     * @param onFlight Check if they are on flight booked or waiting
     * @param seatClass Check the class of seat they have
     * @throws IOException Exception
     */
    public void setFlightCancellation(String name, Integer passengerID, Integer flightNum, boolean onFlight, String seatClass)
            throws IOException {
        Passenger NewPassenger = new Passenger(name, passengerID);

        flightList = new FlightList();
        flightList.createFlightListInfo();

        flightList.searchFlights(flightNum); // Check flight list for flight

        if(onFlight) {
            NewPassenger.removeFromBookings(flightNum); // remove from passenger's info
            flightList.removeFromFlight(flightNum, passengerID); // remove from flight
            flightList.setupFlights(flightNum);
        } else{
            NewPassenger.removeFromBookings(flightNum); // remove from passenger's info
            flightList.removeFromListSpecific(flightNum, passengerID, seatClass); // remove from waiting list
            flightList.setupFlights(flightNum);
        }
    }

    /**
     * Gets and prints the passenger status
     * @param name Passenger name
     * @param passengerID Passenger ID number
     * @throws IOException Exception
     */
    public void getPassengerStatus(String name, Integer passengerID) throws IOException {
        Passenger newPassenger = new Passenger(name, passengerID);

        // setting up and printing the passenger
        newPassenger.reAdd(passengerID);
        newPassenger.setupPassenger();
        newPassenger.printPassenger();

        // printing the relevant information for the passenger
        // i.e. the flights they are on a wait or booked on
        scanFiles.scanFiles(new File("Flights").getAbsolutePath(), passengerID.toString());

    }

    /**
     * Prints the flight information for the user
     * @param flightNum Flight number
     * @throws IOException Exception
     */
    public void getFlightStatus(Integer flightNum) throws IOException {
        flightList = new FlightList();
        flightList.createFlightListInfo();
        flightList.printFlight(flightNum);
    }

    /**
     * Gets the shortest route from location to destination
     * @param dep Departure location
     * @param dest Destination location
     */
    public void getRoute(String dep, String dest){
        FlightRoute x = new FlightRoute();
        x.searchingForRoute(dep, dest);
    }

}

/**
 *  Reading and finding specifics from files
 */
class scanFiles {
    /**
     * Constructor to scan through files in directory
     */
    public scanFiles() {
    }

    /**
     * Main file scanner function
     * @param folderPath                Path of the folder
     * @param searchString              Search word
     * @throws FileNotFoundException    Not found
     * @throws IOException              Input error
     */
    static void scanFiles(String folderPath, String searchString) throws FileNotFoundException, IOException {
        // folder made using path and flight list made
        File folder = new File(folderPath);
        FlightList x = new FlightList();
        x.createFlightListInfo();

        // checks for the folder in directory
        if (folder.isDirectory()) {
            // for files in the directory read and look for searchString
            for (File file : folder.listFiles()) {
                if (!file.isDirectory()) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String content = "";
                    try {
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();

                        // read all lines
                        while (line != null) {
                            sb.append(line);
                            sb.append(System.lineSeparator());
                            line = br.readLine();
                        }
                        content = sb.toString();

                    } finally {
                        br.close();
                    }

                    // check for the searchString
                    if (content.contains(searchString)) {
                        String y = file.getName().replace(".txt", "");

                        // print the flight number and search string
                        System.out.println("Flight " + file.getName().replace(".txt", ""));
                        System.out.println("-------------------------------------------------------------------");

                        // print flight
                        x.printFlight(Integer.parseInt(y));
                        System.out.println("-------------------------------------------------------------------");
                    }

                }
            }
        } else {
            System.out.println("Not a Directory!");
        }
    }

    // testing
    public static void main(String args[]) throws FileNotFoundException, IOException{
        scanFiles(new File("Flights").getAbsolutePath(),"111");
    }
}