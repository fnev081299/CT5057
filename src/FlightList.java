import java.io.*;
import java.util.*;

/**
 * Main flight information and functionality alongside
 * file reading and writing and updating etc.
 */
public class FlightList {
    // showing the different flights to the employee for booking assistance
    private HashMap<Integer, Flight> flightListInfo;
    private ArrayList<Flight> flights;
    private ArrayList<String> information = new ArrayList<String>();
    private ArrayList<String> info = new ArrayList<String>();

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates an instance of the Flights list
     */
    public FlightList() {
        this.flightListInfo = new HashMap<Integer, Flight>();
        this.flights = new ArrayList<Flight>();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return The information of one flight from the .txt file
     */
    public HashMap getFlightListInfo() {
        return flightListInfo;
    }

    /**
     * Printing the information
     */
    public void printFlightInfo(){
        for(Integer flight: flightListInfo.keySet()){
            String key = flight.toString();
            String value = flightListInfo.get(flight).returnFlightDepAndArr();

            String seatingsF = flightListInfo.get(flight).getFClass().toString();
            String seatingsB = flightListInfo.get(flight).getBClass().toString();
            String seatingsE = flightListInfo.get(flight).getEClass().toString();

            System.out.println("Flight number: " + key +  "\nInformation: " + value
                    + "\nFirst class:" + seatingsF + "\nBusiness class:" + seatingsB
                    + "\nEconomy class:" + seatingsE );
        }
    }

    /**
     * The information of flights from the .txt file
     * converted into objects
     */
    public void createFlightListInfo() {
        try{
            BufferedReader flightsFile = new BufferedReader(new FileReader("flights.txt"));
            do {
                information.add(flightsFile.readLine());
                information.add(flightsFile.readLine());
                information.add(flightsFile.readLine());
                information.add(flightsFile.readLine());
                information.add(flightsFile.readLine());
                information.add(flightsFile.readLine());
                information.add(flightsFile.readLine());
            } while (flightsFile.readLine() !=null);
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("Error........");
            System.exit(0);
        }
        for(int i = 0; i != information.size(); i = i + 7){
            int flightNum = Integer.parseInt(information.get(i));
            Airport depAirport = new Airport(information.get(i+1));
            String depDate = information.get(i+2);
            Airport arrAirport = new Airport(information.get(i+3));
            String[] firstClass = information.get(i+4).split(" ");
            String[] businessClass = information.get(i+5).split(" ");
            String[] economyClass = information.get(i+6).split(" ");

            // makes the flight list info and flights from the file
            flightListInfo.put(flightNum, new Flight(flightNum, depAirport, arrAirport,
                    depDate, firstClass, businessClass, economyClass));
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @param Key Flight number
     * @return Flight
     */
    public Flight getFromKey(int Key) {
        return flightListInfo.get(Key);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return Flights
     */
    public ArrayList<Flight> getFlights() {
        return flights;
    }

    /**
     * Sets the flight
     */
    public void setFlights() {
        for(Integer flight: flightListInfo.keySet()){
            flightListInfo.get(flight);
        }
    }

    /**
     * Search flights
     * @param flightNum Flight number
     */
    public Boolean searchFlights(Integer flightNum){
        boolean inlist = false;

        // checks if its in list
        for(Integer flight: flightListInfo.keySet()){
            if (flightNum.equals(flight)){
                inlist = true;
            } else{
                inlist = false;
            }
        }

        return inlist;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Sets up the flight file - used for updating etc.
     * @throws IOException Exception
     */
    public void setupFlights(Integer flightNum) throws IOException {
            // String[] list;
//            System.out.println(getFromKey(flightNum).getFClass());

            FileWriter fw = new FileWriter("Flights/" + flightNum + ".txt");
            PrintWriter pw = new PrintWriter(fw);

            fw.write(getFromKey(flightNum).getFlightNumber() + "\n");
            fw.write(getFromKey(flightNum).getDepAirport() + "\n");
            fw.write(getFromKey(flightNum).getArrAirport() + "\n");
            fw.write(getFromKey(flightNum).getDepDate() + "\n");

            // prints booking and waiting lists in seprarate lines
            for(Integer str: getFromKey(flightNum).getFClass()) {
                fw.write(str + " ");
            }
            pw.println("");
            for(Integer str: getFromKey(flightNum).getBClass()) {
                fw.write(str + " ");
            }
            pw.println("");
            for(Integer str: getFromKey(flightNum).getEClass()) {
                fw.write(str + " ");
            }

            pw.println("");
            if(getFromKey(flightNum).getWaitingListFirst() != null) {
                for (Integer str : getFromKey(flightNum).getWaitingListFirst()) {
                    fw.write(str + " ");
                }
            }
            pw.println("");

            if(!getFromKey(flightNum).getWaitingListBusiness().isEmpty()) {
                for (Integer str : getFromKey(flightNum).getWaitingListBusiness()) {
                    fw.write(str + " ");
                }
            }

            pw.println("");

            if(!getFromKey(flightNum).getWaitingListEconomy().isEmpty()) {
                for (Integer str : getFromKey(flightNum).getWaitingListEconomy()) {
                    // System.out.println(getFromKey(key).getWaitingListEconomy());
                    fw.write(str + " ");
                }
            }
            // System.out.println(value.getWaitingListFirst());
            fw.close();
            pw.close();

            // removes null values
            checkingFile(flightNum);
    }

    /**
     * Adding the first list from the flight file
     * @param flightNum Flight number
     * @return First list
     * @throws IOException  Exception
     */
    public ArrayList<Integer> reAddFirst(Integer flightNum) throws IOException {
        String file = "Flights/" + flightNum + ".txt";
        FileReader readf = new FileReader(file);
        BufferedReader readb = new BufferedReader(readf);

        String line = null;
        int counter = 0;

        // reads and adds from line 5
        while((line = readb.readLine()) != null) {
            counter++;
            if(counter == 4) {
                String line5 = readb.readLine();
                if (line5 != null && !line5.equals("")) {
                    List<String> fClass = new ArrayList<>(Arrays.asList(line5.split(" ")));
                    for (String myInt : fClass) {
                        if(getFromKey(flightNum).getFirstClass().length >= getFromKey(flightNum).getFClass().size()){
                            // adds to the booked list
                            getFromKey(flightNum).addFClass(Integer.parseInt(myInt));
                        }
                    }
//                    getFromKey(flightNum).printFClass();
                }
            }
        }
        return getFromKey(flightNum).getFClass();
    }

    /**
     * Adding the business list from the flight file
     * @param flightNum Flight number
     * @return Business list
     * @throws IOException  Exception
     */
    public ArrayList<Integer> reAddBusiness(Integer flightNum) throws IOException {
        String file = "Flights/" + flightNum + ".txt";
        FileReader readf = new FileReader(file);
        BufferedReader readb = new BufferedReader(readf);

        String line = null;
        int counter = 0;

        // reads and adds from line 6
        while((line = readb.readLine()) != null) {
            counter++;
            if (counter == 5) {
                String line6 = readb.readLine();
                if (line6 != null && !line6.equals("")) {
                    List<String> bClass = new ArrayList<>(Arrays.asList(line6.split(" ")));
                    for (String myIntB : bClass) {
                        if (getFromKey(flightNum).getBusinessClass().length >= getFromKey(flightNum).getBClass().size()) {
                            // adds to booked list
                            getFromKey(flightNum).addBClass(Integer.parseInt(myIntB));
                        }
                    }
                }
            }
        }
        return getFromKey(flightNum).getBClass();
    }

    /**
     * Adding the economy list from the flight file
     * @param flightNum Flight number
     * @return Economy list
     * @throws IOException  Exception
     */
    public ArrayList<Integer> reAddEconomy(Integer flightNum) throws IOException {
        String file = "Flights/" + flightNum + ".txt";
        FileReader readf = new FileReader(file);
        BufferedReader readb = new BufferedReader(readf);

        String line = null;
        int counter = 0;

        // adds from the 7th line
        while((line = readb.readLine()) != null) {
            counter++;
            if (counter == 6) {
                String line7 = readb.readLine();
                if (line7 != null && !line7.equals("")) {
                    List<String> eClass = new ArrayList<>(Arrays.asList(line7.split(" ")));
                    for (String myIntE : eClass) {
                        if (getFromKey(flightNum).getEconomyClass().length >= getFromKey(flightNum).getEClass().size()) {
                            // adds to the booked list
                            getFromKey(flightNum).addEClass(Integer.parseInt(myIntE));
                        }
                    }
                }
            }
        }
        return getFromKey(flightNum).getEClass();
    }

    /**
     * Adding the first waiting list from the flight file
     * @param flightNum Flight number
     * @throws IOException  Exception
     */
    public void reAddFirstWaiting(Integer flightNum) throws IOException {
        String file = "Flights/" + flightNum + ".txt";
        FileReader readf = new FileReader(file);
        BufferedReader readb = new BufferedReader(readf);

        String line = null;
        int counter = 0;

        // reads and adds from the 8th line
        while((line = readb.readLine()) != null) {
            counter++;
            if(counter == 7) {
                String line8 = readb.readLine();
                if (line8 != null && !line8.equals("")) {
                    List<String> fClass = new ArrayList<>(Arrays.asList(line8.split(" ")));
                    for (String myInt : fClass) {
                        if(myInt.equals("null") || myInt == null){
                            return;
                        } else {
                            Integer res = Integer.valueOf(myInt);
                            // adds to the waiting list
                            getFromKey(flightNum).addToWaitingListFirst(res);
                        }
                    }
                    // getFromKey(flightNum).printFClass();
                }
            }
        }
    }

    /**
     * Adding the business waiting list from the flight file
     * @param flightNum Flight number
     * @throws IOException  Exception
     */
    public void reAddBusinessWaiting(Integer flightNum) throws IOException {
        String file = "Flights/" + flightNum + ".txt";
        FileReader readf = new FileReader(file);
        BufferedReader readb = new BufferedReader(readf);

        String line = null;
        int counter = 0;

        // reads and adds from the 6th line
        while((line = readb.readLine()) != null) {
            counter++;
            if (counter == 8) {
                String line6 = readb.readLine();
                if (line6 != null && !line6.equals("")) {
                    List<String> bClass = new ArrayList<>(Arrays.asList(line6.split(" ")));
                    for (String myIntB : bClass) {
                        if(myIntB.equals("null") || myIntB == null){
                            return;
                        } else {
                            Integer res = Integer.valueOf(myIntB);
                            // adds to the waiting list
                            getFromKey(flightNum).addToWaitingListBusiness(res);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adding the economy waiting list from the flight file
     * @param flightNum Flight number
     * @throws IOException  Exception
     */
    public void reAddEconomyWaiting(Integer flightNum) throws IOException {
        String file = "Flights/" + flightNum + ".txt";
        FileReader readf = new FileReader(file);
        BufferedReader readb = new BufferedReader(readf);

        String line = null;
        int counter = 0;

        // reads the 10th lina and adds id numbers to the list
        while((line = readb.readLine()) != null) {
            counter++;
            if (counter == 9) {
                String line10 = readb.readLine();
                if (line10 != null && !line10.equals("")) {
                    List<String> eClass = new ArrayList<>(Arrays.asList(line10.split(" ")));
                    for (String myIntE : eClass) {
                        if(myIntE.equals("null") || myIntE == null){
                            return;
                        } else {
                            Integer res = Integer.valueOf(myIntE);
                            // adds to economy waiting list
                            getFromKey(flightNum).addToWaitingListEconomy(res);
                        }
                    }
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Adding to the Flight
     * @param flightNum Flight number
     * @param classType Class type for seat
     * @param passengerID Passenger id number
     * @throws IOException Exception
     */
    public void addToFlight(Integer flightNum, String classType, Integer passengerID) throws IOException {

        Sorting mergeSort = new Sorting();
        Searching search = new Searching();

        // re-adding thr booked and waiting lists
        reAddFirst(flightNum);
        reAddBusiness(flightNum);
        reAddEconomy(flightNum);

        reAddFirstWaiting(flightNum);
        reAddBusinessWaiting(flightNum);
        reAddEconomyWaiting(flightNum);

        // for the type of seat do it based on it
        switch (classType){
            // first class commented but the same occurs for the other two respectively
            case "first":

                String[] firstSeats = getFromKey(flightNum).getFirstClass();
                ArrayList<Integer> firstAdded = getFromKey(flightNum).getFClass();

                // check if there are seats available and adds the passenger
                if (firstSeats.length-1 >= firstAdded.size()) {
                    ArrayList<Integer> unsortedFList = new ArrayList<>();
                    unsortedFList.addAll(getFromKey(flightNum).getFClass());
                    unsortedFList.addAll(getFromKey(flightNum).getBClass());
                    unsortedFList.addAll(getFromKey(flightNum).getEClass());
                    ArrayList<Integer> sortedFList = new ArrayList<>();

                    // check if passenger is in flight already
                    sortedFList = mergeSort.mergeSort(unsortedFList);
                    boolean x;
                    x = search.binarySearchRecursive(sortedFList, passengerID, 0, sortedFList.size()-1);

                    // System.out.println(sortedFList);
                    // System.out.println(x);

                    if (x){
                        System.out.println("Sorry passenger already on flight");
                        return;
                    }
                    // adds the passenger to the specific class
                    getFromKey(flightNum).addFClass(passengerID);
                    setupFlights(getFromKey(flightNum).getFlightNumber());
                    return;
                } else {
                    // asks if they want to be added to the waiting list
                    System.out.println("There are no first class seats available!");
                    System.out.println("Add to waiting list?");

                    System.out.println("Yes or No: ");
                    String ans;
                    Scanner answer = new Scanner(System.in);
                    ans = answer.nextLine().toLowerCase();

                    switch(ans) {
                        case "yes":
                            System.out.println("Added to the wait list for First class!");
                            // adds them
                            addToList(flightNum, classType, passengerID);
                            return;
                        case "no":
                            // doesnt add them
                            return;
                    }
                }

                // update flight file
                setupFlights(getFromKey(flightNum).getFlightNumber());
                break;
            case "business":

                String[] businessSeats = getFromKey(flightNum).getBusinessClass();
                ArrayList<Integer> businessAdded = getFromKey(flightNum).getBClass();

                if (businessSeats.length-1 >= businessAdded.size()) {
                    ArrayList<Integer> unsortedBList = new ArrayList<>();
                    unsortedBList.addAll(getFromKey(flightNum).getFClass());
                    unsortedBList.addAll(getFromKey(flightNum).getBClass());
                    unsortedBList.addAll(getFromKey(flightNum).getEClass());
                    ArrayList<Integer> sortedBList = new ArrayList<>();
                    sortedBList = mergeSort.mergeSort(unsortedBList);
                    boolean x;

                    x = search.binarySearchRecursive(sortedBList, passengerID, 0, sortedBList.size()-1);

                    // System.out.println(sortedBList);
                    // System.out.println(x);

                    if (x){
                        System.out.println("Sorry passenger already on flight");
                        return;
                    }
                    getFromKey(flightNum).addBClass(passengerID);
                    setupFlights(getFromKey(flightNum).getFlightNumber());
                    return;
                } else {
                    System.out.println("There are no business class seats available!");
                    System.out.println("Add to waiting list?");

                    System.out.println("Yes or No: ");
                    String ans;
                    Scanner answer = new Scanner(System.in);
                    ans = answer.nextLine().toLowerCase();

                    switch(ans) {
                        case "yes":
                            System.out.println("Added to the wait list for Business class!");
                            addToList(flightNum, classType, passengerID);
                            setupFlights(getFromKey(flightNum).getFlightNumber());
                            return;
                        case "no":
                            return;
                    }
                }
                break;
            case "economy":

                String[] economySeats = getFromKey(flightNum).getEconomyClass();
                ArrayList<Integer> economyAdded = getFromKey(flightNum).getEClass();

                if (economySeats.length-1 >= economyAdded.size()) {
                    ArrayList<Integer> unsortedEList = new ArrayList<>();
                    unsortedEList.addAll(getFromKey(flightNum).getFClass());
                    unsortedEList.addAll(getFromKey(flightNum).getBClass());
                    unsortedEList.addAll(getFromKey(flightNum).getEClass());
                    ArrayList<Integer> sortedEList = new ArrayList<>();
                    sortedEList = mergeSort.mergeSort(unsortedEList);
                    boolean x;
                    x = search.binarySearchRecursive(sortedEList, passengerID, 0, sortedEList.size()-1);

                    // System.out.println(sortedBList);
                    // System.out.println(x);

                    if (x){
                        System.out.println("Sorry passenger already on flight");
                        return;
                    }
                    getFromKey(flightNum).addEClass(passengerID);
                } else {
                    System.out.println("There are no economy seats available!");
                    System.out.println("Add to waiting list?");

                    System.out.println("Yes or No: ");
                    String ans;
                    Scanner answer = new Scanner(System.in);
                    ans = answer.nextLine().toLowerCase();

                    switch(ans) {
                        case "yes":
                            System.out.println("Added to the wait list for Economy class!");
                            addToList(flightNum, classType, passengerID);
                            return;
                        case "no":
                            return;
                    }
                }

                setupFlights(getFromKey(flightNum).getFlightNumber());
                break;
        }
    }

    /**
     * Removing from a flight
     * @param flightNum Flight number
     * @param passengerID Passenger id number
     * @throws IOException Exception
     */
    public void removeFromFlight(Integer flightNum, Integer passengerID) throws IOException {
        Sorting mergeSort = new Sorting();
        Searching binarySearch = new Searching();

        // re-adding the bookings and waiting lists
        reAddFirst(flightNum);
        reAddBusiness(flightNum);
        reAddEconomy(flightNum);

        reAddFirstWaiting(flightNum);
        reAddBusinessWaiting(flightNum);
        reAddEconomyWaiting(flightNum);

        String classType = null;
        boolean f;
        boolean b;
        boolean e;

        // checking the lists for the passenger id
        mergeSort.mergeSort(getFromKey(flightNum).getFClass());
        f = binarySearch.binarySearchRecursive(getFromKey(flightNum).getFClass(), passengerID, 0,
                getFromKey(flightNum).getFClass().size()-1);
        mergeSort.mergeSort(getFromKey(flightNum).getBClass());
        b = binarySearch.binarySearchRecursive(getFromKey(flightNum).getBClass(), passengerID, 0,
                getFromKey(flightNum).getBClass().size()-1);
        mergeSort.mergeSort(getFromKey(flightNum).getEClass());
        e = binarySearch.binarySearchRecursive(getFromKey(flightNum).getEClass(), passengerID, 0,
                getFromKey(flightNum).getEClass().size()-1);

        // removes the correct passenger and then asks to add new passenger in replacement form 5the waiting list
        if (f && !b && !e){
            // System.out.println("first class " + f);
            classType = "first";
            getFromKey(flightNum).removeFClass(passengerID);

            System.out.println("Would you like to add someone from the wait list to the flight? " +
                    "\nYes or No: ");
            String ans;
            Scanner answer = new Scanner(System.in);
            ans = answer.nextLine().toLowerCase();

            switch(ans) {
                case "yes":
                    System.out.println("Added to the wait list for First class!");
                    removeAndAddListToFlight(flightNum, classType, classType);
                    return;
                case "no":
                    setupFlights(getFromKey(flightNum).getFlightNumber());
                    return;
            }

        } else if (!f && b && !e){
            // System.out.println("business class " + b);
            classType = "business";
            getFromKey(flightNum).removeBClass(passengerID);

            System.out.println("Would you like to add someone from the wait list to the flight? " +
                    "\nYes or No: ");
            String ans;
            Scanner answer = new Scanner(System.in);
            ans = answer.nextLine().toLowerCase();

            switch(ans) {
                case "yes":
                    System.out.println("Added to the wait list for Business class!");
                    removeAndAddListToFlight(flightNum, classType, classType);
                    return;
                case "no":
                    setupFlights(getFromKey(flightNum).getFlightNumber());
                    return;
            }

        } else if (!f && !b && e) {
            // System.out.println("economy class " + e);
            classType = "economy";
            getFromKey(flightNum).removeEClass(passengerID);

            System.out.println("Would you like to add someone from the wait list to the flight? " +
                    "\nYes or No: ");
            String ans;
            Scanner answer = new Scanner(System.in);
            ans = answer.nextLine().toLowerCase();

            switch(ans) {
                case "yes":
                    System.out.println("Added to the wait list for Economy class!");
                    removeAndAddListToFlight(flightNum, classType, classType);
                    return;
                case "no":
                    setupFlights(getFromKey(flightNum).getFlightNumber());
                    return;
            }

        } else{
            // no passenger
            System.out.println("-- No passenger --");
        }

        // updates file
        setupFlights(getFromKey(flightNum).getFlightNumber());
        return;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Add to the specific wait list
     * @param flightNum Flight number
     * @param classType Seating class in waiting
     * @param passengerID Passenger id number
     * @throws IOException Exception
     */
    public void addToList(Integer flightNum, String classType, Integer passengerID) throws IOException {
        Sorting mergeSort = new Sorting();
        Searching search = new Searching();

        // based on the type of seat they are added to the wait list
        switch (classType){
            case "first":
                getFromKey(flightNum).addToWaitingListFirst(passengerID);
                setupFlights(getFromKey(flightNum).getFlightNumber());
                break;
            case "business":
                getFromKey(flightNum).addToWaitingListBusiness(passengerID);
                setupFlights(getFromKey(flightNum).getFlightNumber());
                break;
            case "economy":
                getFromKey(flightNum).addToWaitingListEconomy(passengerID);
                setupFlights(getFromKey(flightNum).getFlightNumber());
                break;
        }
    }

    /**
     * Removes from waiting list to add to the booking list
     * @param flightNum Flight number
     * @param classType Seating class in waiting
     * @param classTypeAdding Seating class added to in bookings
     * @throws IOException Exception
     */
    public void removeAndAddListToFlight(Integer flightNum, String classType, String classTypeAdding)
            throws IOException {
//        reAddFirst(flightNum);
//        reAddBusiness(flightNum);
//        reAddEconomy(flightNum);
//
//        reAddFirstWaiting(flightNum);
//        reAddBusinessWaiting(flightNum);
//        reAddEconomyWaiting(flightNum);

        // checks the type of seat
        switch (classType){
            // same occurs for other switch case sections respectively
            case "first":
                // checks the seat type being added to
                switch(classTypeAdding) {
                    case "first":
                        // removes from the waiting list and adds them to the booked list
                        getFromKey(flightNum).addFClass(getFromKey(flightNum).getStartFromFirst());
                        getFromKey(flightNum).removeFromWaitingListFirst();
                        // System.out.println(getFromKey(flightNum).getWaitingListFirst());
                        break;
                    case "business":
                        // removes from the waiting list and adds them to the booked list
                        getFromKey(flightNum).addBClass(getFromKey(flightNum).getStartFromFirst());
                        getFromKey(flightNum).removeFromWaitingListFirst();
                        // System.out.println(getFromKey(flightNum).getWaitingListFirst());
                        break;
                    case "economy":
                        // removes from the waiting list and adds them to the booked list
                        getFromKey(flightNum).addEClass(getFromKey(flightNum).getStartFromFirst());
                        getFromKey(flightNum).removeFromWaitingListFirst();
                        // System.out.println(getFromKey(flightNum).getWaitingListFirst());
                        break;
                    case "none":
                        //removes from the flight waiting list but doesnt add to the bookings
                        getFromKey(flightNum).removeFromWaitingListFirst();
                        // System.out.println(getFromKey(flightNum).getWaitingListFirst());
                        break;
                }
                break;
            case "business":
                switch(classTypeAdding) {
                    case "first":
                        getFromKey(flightNum).addFClass(getFromKey(flightNum).getStartFromBusiness());
                        getFromKey(flightNum).removeFromWaitingListBusiness();
                        // System.out.println(getFromKey(flightNum).getWaitingListBusiness());
                        break;
                    case "business":
                        getFromKey(flightNum).addBClass(getFromKey(flightNum).getStartFromBusiness());
                        getFromKey(flightNum).removeFromWaitingListBusiness();
                        // System.out.println(getFromKey(flightNum).getWaitingListBusiness());
                        break;
                    case "economy":
                        getFromKey(flightNum).addEClass(getFromKey(flightNum).getStartFromBusiness());
                        getFromKey(flightNum).removeFromWaitingListBusiness();
                        // System.out.println(getFromKey(flightNum).getWaitingListBusiness());
                        break;
                    case "none":
                        getFromKey(flightNum).removeFromWaitingListBusiness();
                        // System.out.println(getFromKey(flightNum).getWaitingListBusiness());
                        break;
                }
                break;
            case "economy":
                switch(classTypeAdding) {
                    case "first":
                        getFromKey(flightNum).addFClass(getFromKey(flightNum).getStartFromEconomy());
                        getFromKey(flightNum).removeFromWaitingListEconomy();
                        // System.out.println(getFromKey(flightNum).getWaitingListEconomy());
                        break;
                    case "business":
                        getFromKey(flightNum).addBClass(getFromKey(flightNum).getStartFromEconomy());
                        getFromKey(flightNum).removeFromWaitingListEconomy();
                        // System.out.println(getFromKey(flightNum).getWaitingListEconomy());
                        break;
                    case "economy":
                        getFromKey(flightNum).addEClass(getFromKey(flightNum).getStartFromEconomy());
                        getFromKey(flightNum).removeFromWaitingListEconomy();
                        // System.out.println(getFromKey(flightNum).getWaitingListEconomy());
                        break;
                    case "none":
                        getFromKey(flightNum).removeFromWaitingListEconomy();
                        // System.out.println(getFromKey(flightNum).getWaitingListEconomy());
                        break;
                }
        }

        // update the file
        setupFlights(getFromKey(flightNum).getFlightNumber());
    }

    /**
     * Removes the specific passenger from the wait list
     * @param flightNum Flight number
     * @param passengerID Passenger id number
     * @param classType Seat class
     * @return Class type
     * @throws IOException Exception
     */
    public String removeFromListSpecific(Integer flightNum, Integer passengerID, String classType)
            throws IOException {
        Sorting mergeSort = new Sorting();
        Searching binarySearch = new Searching();

        // re-adding the booked and waiting lists
        reAddFirst(flightNum);
        reAddBusiness(flightNum);
        reAddEconomy(flightNum);
        reAddFirstWaiting(flightNum);
        reAddBusinessWaiting(flightNum);
        reAddEconomyWaiting(flightNum);

        ArrayList<Integer> wFirst = new ArrayList<>();
        wFirst.addAll(getFromKey(flightNum).getWaitingListFirst());
        wFirst.removeAll(Collections.singleton(null)); // Removes Nulls

        ArrayList<Integer> wBusiness = new ArrayList<>();
        wBusiness.addAll(getFromKey(flightNum).getWaitingListBusiness());
        wBusiness.removeAll(Collections.singleton(null)); // Removes Nulls

        ArrayList<Integer> wEconomy = new ArrayList<>();
        wEconomy.addAll(getFromKey(flightNum).getWaitingListEconomy());
        wEconomy.removeAll(Collections.singleton(null)); // Removes Nulls

        boolean found = false;
        ArrayList<Integer> sorted = new ArrayList<>();

        // check class for class and removes the specific passenger
        switch(classType){
            // commented in first bus the smae occurs in the other two sections respectively
            case "first":
                sorted = mergeSort.mergeSort(wFirst);
                found = binarySearch.binarySearchRecursive(sorted, passengerID, 0, sorted.size()-1);
                if(found){
                    // System.out.println("First");
                    wFirst.remove(passengerID); // remove the passenger

                    // make the size 3 to work
                    do{
                        getFromKey(flightNum).addToWaitingListFirst(0);
                    }
                    while(getFromKey(flightNum).getWaitingListFirst().size() <= 3);

                    getFromKey(flightNum).removeAllFromWaitingListFirst();

                    // re adds
                    for (Integer myIntE : wFirst) {
                        Integer res = Integer.valueOf(myIntE);
                        getFromKey(flightNum).addToWaitingListFirst(res);
                    }

                    // adds all
                    getFromKey(flightNum).getWaitingListFirst().addAll(wFirst);
                }
                break;
            case"business":
                sorted = mergeSort.mergeSort(wBusiness);
                found = binarySearch.binarySearchRecursive(sorted, passengerID, 0, sorted.size()-1);
                if(found){
                    // System.out.println("Business");
                    wBusiness.remove(passengerID);

                    do{
                        getFromKey(flightNum).addToWaitingListBusiness(0);
                    }
                    while(getFromKey(flightNum).getWaitingListBusiness().size() < 3);

                    getFromKey(flightNum).removeAllFromWaitingListBusiness();

                    for (Integer myIntB : wBusiness) {
                        Integer res = Integer.valueOf(myIntB);
                        getFromKey(flightNum).addToWaitingListBusiness(res);
                    }

                    getFromKey(flightNum).getWaitingListBusiness().addAll(wBusiness);
                }
                break;
            case"economy":
                sorted = mergeSort.mergeSort(wEconomy);
                found = binarySearch.binarySearchRecursive(sorted, passengerID, 0, sorted.size()-1);
                if(found){
                    // System.out.println("Economy");
                    wEconomy.remove(passengerID);
                    // System.out.print(wEconomy);

                    do{
                        getFromKey(flightNum).addToWaitingListEconomy(0);
                    }
                    while(getFromKey(flightNum).getWaitingListEconomy().size() < 3);

                    getFromKey(flightNum).removeAllFromWaitingListEconomy();

                    for (Integer myIntE : wEconomy) {
                        Integer res = Integer.valueOf(myIntE);
                        getFromKey(flightNum).addToWaitingListEconomy(res);
                    }

                    getFromKey(flightNum).getWaitingListEconomy().addAll(wEconomy);
                }
                break;
        }

        setupFlights(getFromKey(flightNum).getFlightNumber());
        return classType;
    }
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Cleans the file from nulls
     * @param flightNum Flight number
     * @throws IOException Exception
     */
    public void checkingFile(Integer flightNum) throws IOException {
        File fileToBeModified = new File("Flights/" + flightNum + ".txt");
        File temp = File.createTempFile("file", ".txt", fileToBeModified.getParentFile());

        String charset = "UTF-8";
        String delete = "null";

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(fileToBeModified), charset));

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));

        // checks for nulls in line
        for (String line; (line = reader.readLine()) != null;) {
            line = line.replace(delete, "");
            writer.println(line);
        }

        reader.close();
        writer.close();

        fileToBeModified.delete();
        temp.renameTo(fileToBeModified);
    }

    /**
     * Creating the flight passengers for adding their names to the flight info when being returned
     * @param flightNum Flight number
     * @param classType  Seating class
     * @return list of all passenger names
     * @throws IOException Exception
     */
    public ArrayList<String> createFlightPassengers(Integer flightNum, String classType) throws IOException {

        // readding the booked lists
        reAddFirst(flightNum);
        reAddBusiness(flightNum);
        reAddEconomy(flightNum);

        // readding the waiting lists
        reAddFirstWaiting(flightNum);
        reAddBusinessWaiting(flightNum);
        reAddEconomyWaiting(flightNum);

        ArrayList<Passenger> results = new ArrayList<>();
        File[] files = new File("Passengers").listFiles();

        // reads files for the passenger information
        for (File file : files) {
            if (file.isFile()) {
                String x = file.getName().replace(".txt", "");
                String f = "Passengers/" + x + ".txt";
                FileReader readf = new FileReader(f);
                BufferedReader readb = new BufferedReader(readf);

                String line = null;
                int counter = 0;

                while((line = readb.readLine()) != null) {
                    counter++;
                    if(counter == 1) {
                        String line2 = readb.readLine();
                        Passenger y = new Passenger(line2, Integer.parseInt(x));
                        results.add(y);
                    }
                }
            }
        }
        ArrayList<String> results2 = new ArrayList<>();

        // gets the passenger name and adds it to the results2
        for (Passenger passenger : results) {
            if(getFromKey(flightNum).getFClass().contains(passenger.getPassengerID()) &&
                    classType.equals("first")){
                results2.add(passenger.getName());
            }else if(getFromKey(flightNum).getBClass().contains(passenger.getPassengerID()) &&
                    classType.equals("business")){
                results2.add(passenger.getName());
            }else if(getFromKey(flightNum).getEClass().contains(passenger.getPassengerID()) &&
                    classType.equals("economy")){
                results2.add(passenger.getName());
            }else if(getFromKey(flightNum).getWaitingListFirst().contains(passenger.getPassengerID()) &&
                    classType.equals("wfirst")){
                results2.add(passenger.getName());
            }else if(getFromKey(flightNum).getWaitingListBusiness().contains(passenger.getPassengerID()) &&
                    classType.equals("wbusiness")){
                results2.add(passenger.getName());
            }else if(getFromKey(flightNum).getWaitingListEconomy().contains(passenger.getPassengerID()) &&
                    classType.equals("weconomy")){
                results2.add(passenger.getName());
            }
        }
        //  reverses the order ot the list
        Collections.reverse(results2);
        return results2;
    }

    /**
     * Print the flight information with passenger names
     * @param flightNum Flight number
     * @throws IOException Exception
     */
    public void printFlight(Integer flightNum) throws IOException {

        // readding the booked lists
        reAddFirst(flightNum);
        reAddBusiness(flightNum);
        reAddEconomy(flightNum);

        // readding the waiting lists
        reAddFirstWaiting(flightNum);
        reAddBusinessWaiting(flightNum);
        reAddEconomyWaiting(flightNum);

        // printing
        System.out.println("Flight:");
        System.out.println(getFromKey(flightNum).getFlightNumber());
        System.out.println("Date:");
        System.out.println(getFromKey(flightNum).getDepDate());
        System.out.println("\nDeparting:");
        System.out.println(getFromKey(flightNum).getDepAirport());
        System.out.println("Destination:");
        System.out.println(getFromKey(flightNum).getArrAirport());
        System.out.println("\nBooked Passengers:");

        System.out.println("First:");
        System.out.println("First Class Seats:");
        System.out.println("    " + Arrays.toString(getFromKey(flightNum).getFirstClass()));
        System.out.println("    " + createFlightPassengers(flightNum, "first"));
        // System.out.println(getFromKey(flightNum).getFClass());

        System.out.println("Business:");
        System.out.println("Business Class Seats:");
        System.out.println("    " + Arrays.toString(getFromKey(flightNum).getBusinessClass()));
        System.out.println("    " + createFlightPassengers(flightNum, "business"));
        // System.out.println(getFromKey(flightNum).getBClass());

        System.out.println("Economy:");
        System.out.println("Economy Class Seats:");
        System.out.println("    " + Arrays.toString(getFromKey(flightNum).getEconomyClass()));
        System.out.println("    " + createFlightPassengers(flightNum, "economy"));
        // System.out.println(getFromKey(flightNum).getEClass());

        System.out.println("\nWaiting Passengers:");

        System.out.println("First:");
        System.out.println("    " + createFlightPassengers(flightNum, "wfirst"));
        // System.out.println(getFromKey(flightNum).getFClass());

        System.out.println("Business:");
        System.out.println("    " + createFlightPassengers(flightNum, "wbusiness"));
        // System.out.println(getFromKey(flightNum).getBClass());

        System.out.println("Economy:");
        System.out.println("    " + createFlightPassengers(flightNum, "weconomy"));
        // System.out.println(getFromKey(flightNum).getEClass());
    }

    // Testing
    public static void main(String[] args) throws IOException {

        FlightList x = new FlightList();

        x.createFlightListInfo();
//        System.out.println(x.createFlightPassengers(101, "first"));
//        x.printFlight(101);
//        x.removeAndAddListToFlight(101, "business", "economy");
//        x.addToFlight(501, "first", 999);
        x.removeFromFlight(101, 5);
        x.setupFlights(101);
//        x.removeFromListSpecific(101, 77, "first");
//        System.out.println(x.getFromKey(101).getWaitingListFirst());
//        x.printFlight(101);
    }
}

/**
 * Storing the flight information of a single flight
 */
class Flight {
    private Integer flightNumber;
    private String depDate;

    private Airport depAirport;
    private Airport arrAirport;

    private WaitingList waitingListFirst;
    private WaitingList waitingListBusiness;
    private WaitingList waitingListEconomy;

    private String[] firstClass;
    private String[] businessClass;
    private String[] economyClass;

    private ArrayList<Integer> fClass;
    private ArrayList<Integer> bClass;
    private ArrayList<Integer> eClass;

    private HashMap<Passenger, String> passengers;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Constructor
     * @param flightNumber Flight number
     * @param depAirport Departure airport
     * @param arrAirport Arrival airport
     * @param depDate Departure time
     */
    public Flight(Integer flightNumber, Airport depAirport, Airport arrAirport, String depDate,
                  String[] firstClass, String[] businessClass, String[] economyClass) {

        this.flightNumber = flightNumber;

        this.depAirport = depAirport;
        this.depDate = depDate;
        this.arrAirport = arrAirport;

        this.waitingListFirst = new WaitingList();
        this.waitingListBusiness = new WaitingList();
        this.waitingListEconomy = new WaitingList();

        this.firstClass = firstClass;
        this.businessClass = businessClass;
        this.economyClass = economyClass;

        this.fClass = new ArrayList<Integer>();
        this.bClass = new ArrayList<Integer>();
        this.eClass = new ArrayList<Integer>();

        this.passengers = new HashMap<Passenger, String>();
    }

    /**
     *
     * @return Flight number
     */
    public Integer getFlightNumber() {
        return flightNumber;
    }

    /**
     *
     * @param flightNumber Flight number
     */
    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return Departure airport
     */
    public String getDepAirport() {
        return depAirport.getAirportAbbreviation();
    }

    /**
     *
     * @param depAirport Departure airport
     */
    public void setDepAirport(Airport depAirport) {
        this.depAirport = depAirport;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return Departure date
     */
    public String getDepDate() {
        return depDate;
    }

    /**
     *
     * @param depDate Departure date
     */
    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return Arrival airport
     */
    public String getArrAirport() {
        return arrAirport.getAirportAbbreviation();
    }

    /**
     *
     * @param arrAirport Arrival airport
     */
    public void setArrAirport(Airport arrAirport) {
        this.arrAirport = arrAirport;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return List of passengers on flight
     */
    public HashMap<Passenger, String> getPassengers() {
        return passengers;
    }

    /**
     *
     * @param person Passenger
     * @param classType Class of seat
     */
    public void addPassengers(Passenger person, String classType) {
        this.passengers.put(person, classType);
    }

    /**
     * Prints all passenger info for flight
     */
    public void printPassengers(){
        System.out.println("Booked Seats:");
        System.out.println("First Class:");
        printFClass();
        System.out.println("Business Class:");
        printBClass();
        System.out.println("Economy Class:");
        printEClass();
        System.out.println("\nWaiting List:");
        System.out.println("First Class:");
        System.out.println(getWaitingListFirst());
        System.out.println("Business Class:");
        System.out.println(waitingListBusiness.getQueue());
        System.out.println("Economy Class:");
        System.out.println(waitingListEconomy.getQueue());
    }

    /**
     *
     * @param person Passenger
     * @param classType Class of seat
     */
    public void removePassengers(Passenger person, String classType) {
        this.passengers.remove(person, classType);
    }

    /**
     *
     * @return Flight information
     */
    public Flight getFlight(){
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return First class passengers
     */
    public String[] getFirstClass() {
        return firstClass;
    }

    /**
     *
     * @return Business passengers
     */
    public String[] getBusinessClass() {
        return businessClass;
    }

    /**
     *
     * @return Economy class passengers
     */
    public String[] getEconomyClass() {
        return economyClass;
    }

    /**
     *
     * @return String of the flight departure and arrival information
     */
    public String returnFlightDepAndArr(){
        String x;
        x = ("\n   Departure Airport: " + depAirport.getAirportAbbreviation()
                + "\n   Arrival Airport: " + arrAirport.getAirportAbbreviation()
                + "\n   Date: " + depDate + "\n");
        return x;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return List of first class bookings
     */
    public ArrayList<Integer> getFClass() {
        return fClass;
    }

    /**
     * Prints first class bookings
     */
    public void printFClass() {
        System.out.println(fClass);
    }

    /**
     * Adds to first class bookings
     * @param passengerID Passenger ID number
     */
    public void addFClass(Integer passengerID) {
        this.fClass.add(passengerID);
    }

    /**
     *  Removes from first class bookings
     * @param passengerID Passenger ID number
     */
    public void removeFClass(Integer passengerID) {
        this.fClass.remove(passengerID);
    }

    /**
     *
     * @return List of business bookings
     */
    public ArrayList<Integer> getBClass() {
        return bClass;
    }

    /**
     * Printing the business bookings
     */
    public void printBClass() {
        System.out.println(bClass);
    }

    /**
     * Adding to the business bookings
     * @param passengerID Passenger ID number
     */
    public void addBClass(Integer passengerID) {
        bClass.add(passengerID);
    }

    /**
     * Removing passenger form business bookings
     * @param passengerID Passenger ID number
     */
    public void removeBClass(Integer passengerID) {
        bClass.remove(passengerID);
    }

    /**
     *
     * @return Economy booking list
     */
    public ArrayList<Integer> getEClass() {
        return eClass;
    }

    /**
     * Printing the economy booked list
     */
    public void printEClass() {
        System.out.println(eClass);
    }

    /**
     * Adding passenger to economy bookings
     * @param passengerID Passenger ID number
     */
    public void addEClass(Integer passengerID) {
        eClass.add(passengerID);
    }

    /**
     * Removing passenger from economy bookings
     * @param passengerID Passenger ID number
     */
    public void removeEClass(Integer passengerID) {
        eClass.remove(passengerID);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return First class waiting list
     */
    public ArrayList<Integer> getWaitingListFirst() {
        return waitingListFirst.getQueue();
        //System.out.println(waitingListFirst.getQueue());
    }

    /**
     *
     * @param addWaiting Adding to first class waiting queue
     */
    public void addToWaitingListFirst(Integer addWaiting) {
        waitingListFirst.addToWaiting(addWaiting);
    }

    /**
     * Removing from first class waiting list
     */
    public void removeFromWaitingListFirst() {
        waitingListFirst.removeFromWaiting();
    }

    /**
     * Removing all from the first class waiting list
     */
    public void removeAllFromWaitingListFirst() {
        waitingListFirst.emptyQueue();
    }

    /**
     *
     * @return The first passenger in the first class waiting list
     */
    public Integer getStartFromFirst(){
        return waitingListFirst.getStart();
    }

    /**
     * printing the first class waiting list
     */
    public void printWaitingListFirst(){
        waitingListFirst.printWaitingList();
    }

    /**
     *
     * @return Business waiting list
     */
    public ArrayList<Integer> getWaitingListBusiness() {
        return waitingListBusiness.getQueue();
    }

    /**
     *
     * @param addWaiting adding to the business waiting list queue
     */
    public void addToWaitingListBusiness(Integer addWaiting) {
        waitingListBusiness.addToWaiting(addWaiting);
    }

    /**
     * Removing from business waiting list
     */
    public void removeFromWaitingListBusiness() {
        waitingListBusiness.removeFromWaiting();
    }

    /**
     * Removing all from the business waiting list
     */
    public void removeAllFromWaitingListBusiness() {
        waitingListBusiness.emptyQueue();
    }

    /**
     *
     * @return First person on business waiting list
     */
    public Integer getStartFromBusiness(){
        return waitingListBusiness.getStart();
    }

    /**
     * Printing the business waiting list
     */
    public void printWaitingListBusiness(){
        waitingListBusiness.printWaitingList();
    }

    /**
     * Getting the economy waiting list
     * @return Economy waiting list
     */
    public ArrayList<Integer> getWaitingListEconomy() {
        return waitingListEconomy.getQueue();
    }

    /**
     *
     * @param addWaiting Adding to economy waiting list
     */
    public void addToWaitingListEconomy(Integer addWaiting) {
        waitingListEconomy.addToWaiting(addWaiting);
    }

    /**
     * Removing from economy waiting list
     */
    public void removeFromWaitingListEconomy() {
        waitingListEconomy.removeFromWaiting();
    }

    /**
     * Removing all the economy passengers
     */
    public void removeAllFromWaitingListEconomy() {
        waitingListEconomy.emptyQueue();
    }

    /**
     *
     * @return  First person on economy waiting list
     */
    public Integer getStartFromEconomy(){
        return waitingListEconomy.getStart();
    }

    /**
     * Printing out the Economy waiting list
     */
    public void printWaitingListEconomy(){
        waitingListEconomy.printWaitingList();
    }

    //testing
    public static void main(String[] args) throws IOException {
        FlightList x = new FlightList();
//        x.setupFlights();
//
//        x.createFlightListInfo();
//
//        x.getFromKey(101).addToWaitingListFirst(123);
//        x.getFromKey(101).getWaitingListFirst();
//        System.out.println(x.getFromKey(101).returnFlightDepAndArr());
//        x.getFromKey(101).printPassengers();
    }
}

/**
 * All passenger information is stored within this object
 */
class Passenger {
    private String name;
    private Integer passengerID;
    private ArrayList<Integer> bookings;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Constructor
     * The booking list will be used to store the user's personal bookings
     * and stating whether their are waiting or on flight
     * @param name The name given to the passenger
     * @param passengerID Passenger Id number
     */
    public Passenger(String name, Integer passengerID) {
        this.name = name;
        this.passengerID  = passengerID;
        this.bookings = new ArrayList<Integer>();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @return The name of passenger
     */
    public String getName() {
        return name;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returning the passenger id number
     * @return Passenger ID
     */
    public Integer getPassengerID() {
        return passengerID;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Getting the booked flights
     * @return Bookings
     */
    public ArrayList<Integer> getBookings() {
        return bookings;
    }

    /**
     * Add to flights booked
     * @param flightNum Flight number
     */
    public void addBookings(Integer flightNum) {
        bookings.add(flightNum);
    }

    /**
     * Removing a flight from the booked flights
     * @param flightNum Flight number
     */
    public void removeBookings(Integer flightNum) {
        bookings.remove(flightNum);
    }

    /**
     * Printing the passenger bookings
     * @param bookingList List of booked flight ids
     */
    public void printBookings(ArrayList<Integer> bookingList){
        for(int i = 0; i < bookingList.size(); i++) {
            System.out.print(bookingList.get(i) + " \n");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Printing the passenger information
     */
    public void printPassenger(){
        System.out.println("Passenger ID:   "+ passengerID);
        System.out.println("Passenger Name: "+ name);
        System.out.println("Bookings:");
        printBookings(bookings);
    }

    /**
     * Converts the array to integer array
     * @param stringArray String array
     * @return Integer array
     */
    public ArrayList<Integer> getIntegerArray(ArrayList<String> stringArray) {
        // converts the array to integer array
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(String stringValue : stringArray) {
            try {
                // Convert String to Integer, and store it into integer array list.
                result.add(Integer.parseInt(stringValue));
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
        }
        return result;
    }

    /**
     * Checking the passenger is already created
     * @param passengerID ID number of passenger
     * @return If on flight
     */
    public Boolean isIDAlready(Integer passengerID){
        Sorting sort = new Sorting();
        Searching search = new Searching();
        boolean x;

        ArrayList<String> results = new ArrayList<String>();

        File[] files = new File("Passengers").listFiles();

        // getting the passenger ids from the passenger file
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName().replace(".txt", ""));
                // System.out.println(results);
            }
        }

        // A collection of Strings
        ArrayList<Integer> resultList = getIntegerArray(results);

        // sort and search for the passenger
        x = search.binarySearchRecursive(sort.mergeSort(resultList), passengerID, 0, resultList.size()-1);

        // System.out.println(x);

        return x;
    }

    /**
     * Setting up/writing passenger objects to text file
     * @throws IOException exception
     */
    public void setupPassenger() throws IOException {
        Passenger NewPassenger = new Passenger(name, passengerID);

        // If true then there is already a passenger with that id
        // System.out.println(passengerID + " exists");

        FileWriter fw = new FileWriter("Passengers/" + passengerID.toString()+".txt");
        PrintWriter pw = new PrintWriter(fw);

        fw.write(NewPassenger.getPassengerID().toString() + "\n");
        fw.write(NewPassenger.getName() + "\n");

        for(Integer str: getBookings()) {
            fw.write(str + " ");
        }

        fw.close();
    }

    /**
     * Adds the flights back in to the passenger bookings form file
     * @param passengerID ID number of passenger
     * @return Bookings
     * @throws IOException exception
     */
    public ArrayList<Integer> reAdd(Integer passengerID) throws IOException {
        String file = "Passengers/" + passengerID + ".txt";
        FileReader readf = new FileReader(file);
        BufferedReader readb = new BufferedReader(readf);

        String line = null;
        int counter = 0;

        // read line 3
        while((line = readb.readLine()) != null) {
            counter++;
            if(counter == 2) {
                String line3 = readb.readLine();
                if (line3 != null && !line3.equals("")) {
                    List<String> fClass = new ArrayList<>(Arrays.asList(line3.split(" ")));
                    for (String myInt : fClass) {
                        // add the flights back int the bookings
                        addBookings(Integer.parseInt(myInt));
                    }
                    // getFromKey(flightNum).printFClass();
                }
            }
        }
        System.out.print(getBookings());
        return getBookings();
    }

    /**
     * Alternative function to add passenger bookings
     * @param flightNum
     * @throws IOException
     */
    public void addToBookings(Integer flightNum) throws IOException {
        Sorting mergeSort = new Sorting();
        Searching search = new Searching();

        reAdd(passengerID);

        ArrayList<Integer> unsortedFList = new ArrayList<>();

        unsortedFList.addAll(getBookings());

        ArrayList<Integer> sortedFList = new ArrayList<>();

        // check if passenger is already in the flight
        sortedFList = mergeSort.mergeSort(unsortedFList);
        boolean fn;
        fn = search.binarySearchRecursive(sortedFList, flightNum, 0, sortedFList.size()-1);

        // System.out.println(sortedFList);
        // System.out.println(x);

        if (fn){
            System.out.println("Flight already in list");
            return;
        }
        addBookings(flightNum);
        setupPassenger();
        return;

    }

    /**
     *   Removes the flight from the passenger personal bookings
     * @param flightNum Flight number
     * @throws IOException exception
     */
    public void removeFromBookings(Integer flightNum) throws IOException {
        Sorting mergeSort = new Sorting();
        Searching binarySearch = new Searching();

        // adds the bookings back
        reAdd(passengerID);
        boolean x;

        // search for booking to remove
        mergeSort.mergeSort(getBookings());
        x = binarySearch.binarySearchRecursive(getBookings(), passengerID, 0, getBookings().size()-1);

        // remove the booking and setup passenger
        removeBookings(flightNum);
        setupPassenger();
    }

    /**
     * Gets the name form the file of the passenger
     * @param passengerID Id number of passenger
     * @return the passenger name
     * @throws IOException exception
     */
    public String getPassengerFromFile(Integer passengerID) throws IOException {
        String name = "";

        FileReader readfile = new FileReader("Passengers/" + passengerID + ".txt");
        BufferedReader readbuffer = new BufferedReader(readfile);
        int lineNumber;

        // reads the passenger name to be returned
        for (lineNumber = 1; lineNumber < 34; lineNumber++) {
            if (lineNumber == 2) {
                name = readbuffer.readLine();
                // System.out.println(name);
            } else
                readbuffer.readLine();
        }

        return name;
    }

    // Testing
    public static void main(String[] args) throws IOException {
        Passenger x = new Passenger("Bobby", 1);

//        x.isIDAlready(269);
//        x.printBookings(x.bookings);
        x.addToBookings(199);
//        x.setupPassenger();
//        x.printPassenger();
//        x.removeFromBookings(101);
//        System.out.println(x.getPassengerFromFile(269));
    }
}

/**
 * The airport location and information to be used within the flight information
 */
class Airport{
    private String airportName;
    private String country;
    private String airportAbbreviation;

    /**
     *
     * @param airportAbbreviation Abbreviation
     */
    public Airport(String airportAbbreviation) {
        this.airportAbbreviation = airportAbbreviation;
    }

    /**
     *
     * @return Name of airport
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     *
     * @param airportName Name of airport
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     *
     * @return Location
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country Location
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return Abbreviation of the airport
     */
    public String getAirportAbbreviation() {
        return airportAbbreviation;
    }

    /**
     *
     * @param airportAbbreviation Abbreviation of the airport
     */
    public void setAirportAbbreviation(String airportAbbreviation) {
        this.airportAbbreviation = airportAbbreviation;
    }
}

/**
 * Class to make a queue for the passengers waiting list
 */
class WaitingList {
    private Integer start;
    private Integer [] queue;
    private int rear;
    private int counter = 0;
    private ArrayList<Integer> intQueue = new ArrayList<>();

    /**
     * Constructor for the waiting list
     * Making the start, rear and queue for the waiting list
     */
    public WaitingList(){
        start = -1;
        rear = 0;
        queue = new Integer[1];
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Size of the queue
     * @return Size of the list
     */
    private int size(){return counter;}

    /**
     * Make empty
     * @return empty
     */
    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     * Clearing the queue
     */
    public void emptyQueue(){
        Iterator<Integer> it = getQueue().iterator();
        // while still populated
        while (it.hasNext()) {
            Integer passenger = it.next();
            it.remove(); //removing the next value
            removeFromWaiting();
        }
    }

    /**
     * Altering the size of the queue
     * @param newSize Reformatted size of queue
     */
    public void altSize(int newSize){
        Integer [] new_array = new Integer[newSize];
        int prevSize = queue.length;
        int i = start;
        int limit = (start < rear) ? rear : prevSize;

        for (i = start; i < limit; i++) {
            new_array[i - start] = queue[i];
        }

        if (rear < start) {
            for (int j = 0; j < rear; j++) {
                new_array[i] = queue[j];
                i++;
            }
        }

        start = 0;
        queue = new_array;
    }

    /**
     * Adding to queue
     * @param value Enqueue of the additional flights
     */
    public void addToWaiting(Integer value){
        // check size
        if (size() == queue.length){
            rear = queue.length;
            altSize(2 * queue.length);
        }
        //check empty
        if (isEmpty())
            start = rear;
        queue[rear] = value; // add to rear
        rear = (rear + 1) % queue.length;
        counter ++;
    }

    /**
     * Removing from the queue first input FIFO
     * @return Popping from the queue
     */
    public Integer removeFromWaiting(){
        assert (start >=0);
        Integer selectedItem = queue[start];
        counter--;
        queue[start] = null; // set the selected start to null
        start = (start + 1) % queue.length;

        if (counter == queue.length / 4){
            System.out.println("");
            try {
                altSize(queue.length / 2);
            } catch(ArrayIndexOutOfBoundsException x){

            }
            rear = queue.length /2;
        }
        return selectedItem;
    }

    /**
     * Printing out the queue
     */
    public void printWaitingList() {
        for (Integer set: queue) {
            System.out.println(set);
        }
    }

    /**
     * Making the queue an array list and removing nulls
     * @return Array list of the queue
     */
    public ArrayList<Integer> getQueue() {
        ArrayList<Integer> x = new ArrayList<>();
        x.addAll(Arrays.asList(queue));

        ArrayList<Integer> newList = new ArrayList<>();
        // for the elements in the queue add tot he list
        for (Integer element : x) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        x.clear();
        x.addAll(newList);

        ArrayList<Integer> removedNull = new ArrayList<>();
        // removing null values from queue
        for (Integer str : x)
            if (str != null)
                removedNull.add(str);
        removedNull.toArray(new Integer[0]);

        return removedNull;
    }

    /**
     * Returns the first person on the queue
     * @return Start of the queue
     */
    public Integer getStart(){
        Integer startItem;
        startItem = getQueue().get(0);
        return startItem;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // Testing
    public static void main(String[] args){
        WaitingList waiting = new WaitingList();

        System.out.println("Waiting Flight numbers:");
        waiting.addToWaiting(null);
        waiting.addToWaiting(101);
        waiting.addToWaiting(202);
        waiting.addToWaiting(303);
        waiting.addToWaiting(404);

//        waiting.printWaitingList();

//        System.out.println();
//        System.out.println("Adding one more item to the list:");
//        waiting.addToWaiting(909);
//        waiting.printWaitingList();
//
//        System.out.println();
//        System.out.println("Removing one from the list:");
//        waiting.removeFromWaiting();
//        waiting.printWaitingList();
//
//        System.out.println();
//        System.out.println("Size:");
//        System.out.println(waiting.size());

//        waiting.emptyQueue();

//        System.out.println(waiting.getStart());
        System.out.println(waiting.getQueue());
    }
}

/**
 * Searching algorithm
 */
class Searching {

    /**
     * Constructor
     */
    public Searching() {
    }

    // -----------------------------------------------------------------------------------------------------------------
    /**
     * Binary search
     * @param array Array searched
     * @param key Looking for key e.g. the ID
     * @param start Start of the Search
     * @param end End of the Search
     * @return True or False based on the search finding something
     */
    public boolean binarySearchRecursive(ArrayList<Integer> array, Integer key, int start, int end) {
        // mid value
        int middle = (start + end) / 2;

        //checking the start and end values
        if (end < start) {
            return false;
        }

        // searching using the binary chop method  - cutting in half etc.
        if (key < array.get(middle)) {
            return binarySearchRecursive(array, key, start, middle - 1);
        } else if (key > array.get(middle)) {
            return binarySearchRecursive(array, key, middle + 1, end);
        } else if (key.equals(array.get(middle))) {
            return true;

        }

        return false;
    }

    // testing the performance
    public ArrayList genRandomArr(ArrayList<Integer> array){
        for(int i = 0; i < array.size(); i++){
            array.set(i, (int) (Math.random() * 1000) + 10);
        }

        int itemsInArr;
        itemsInArr = array.size() -1;

        return array;
    }

    // testing
    public static void main(String[] args) throws IOException {
//        Searching x = new Searching();
//        ArrayList<Integer> y = new ArrayList<>();
//        y.add(123);
//        y.add(124);
//        y.add(126);
//        y.add(127);
//        y.add(128);
//
//        System.out.println(x.binarySearchRecursive(y, 127, 0, y.size() -1));

        Searching x = new Searching();

        ArrayList<Integer> y = new ArrayList<>(200000);
        long startT = System.nanoTime();
        x.binarySearchRecursive(x.genRandomArr(y), 20, 0, x.genRandomArr(y).size()-1);
        long stopT = System.nanoTime();
        long elapsedT = stopT - startT;
        System.out.println("Test1: 200000 items - " + elapsedT + " nano time");

        long startT1 = System.nanoTime();
        ArrayList<Integer> z = new ArrayList<>(400000);
        x.binarySearchRecursive(x.genRandomArr(z), 20, 0, x.genRandomArr(z).size()-1);
        long stopT1 = System.nanoTime();
        long elapsedT1 = stopT1 - startT1;
        System.out.println("Test2: 400000 items - " + elapsedT1 + " nano time");

        long startT2 = System.nanoTime();
        ArrayList<Integer> c = new ArrayList<>(600000);
        x.binarySearchRecursive(x.genRandomArr(c), 20, 0, x.genRandomArr(c).size()-1);
        long stopT2 = System.nanoTime();
        long elapsedT2 = stopT2 - startT2;
        System.out.println("Test3: 600000 items - " + elapsedT2 + " nano time");

        long startT3 = System.nanoTime();
        ArrayList<Integer> q = new ArrayList<>(800000);
        x.binarySearchRecursive(x.genRandomArr(q), 20, 0, x.genRandomArr(q).size()-1);
        long stopT3 = System.nanoTime();
        long elapsedT3 = stopT3 - startT3;
        System.out.println("Test4: 800000 items - " + elapsedT3 + " nano time");

        long startT4 = System.nanoTime();
        ArrayList<Integer> w = new ArrayList<>(1000000);
        x.binarySearchRecursive(x.genRandomArr(w), 20, 0, x.genRandomArr(w).size()-1);
        long stopT4 = System.nanoTime();
        long elapsedT4 = stopT4 - startT4;
        System.out.println("Test5: 1000000 items - " + elapsedT4 + " nano time");
    }
}

/**
 * Sorting algorithm
 */
class Sorting {
    // testing
    long stopT;
    /**
     * Constructor
     */
    public Sorting() {
    }

    /**
     * New merge Sort replacing insertion sort
     * @param unsortedList List of unsorted integers
     * @return Sorted list
     */
    public ArrayList<Integer> mergeSort(ArrayList<Integer> unsortedList){
        // checking for basic/ smaller lists
        if(unsortedList.size() <= 1)
        {
            return unsortedList;
        }

        // making left and right lists including the sorted list
        ArrayList<Integer> sortedList = new ArrayList<Integer>();
        ArrayList<Integer> left = new ArrayList<Integer>();
        ArrayList<Integer> right = new ArrayList<Integer>();

        int middle = unsortedList.size()/2;

        //splits the array into unsortedList size lists of size one
        for(int i = 0; i < unsortedList.size(); i++)
        {
            if(i < middle)
            {
                left.add(unsortedList.get(i));
            }
            else
            {
                right.add(unsortedList.get(i));
            }
        }

        // merging tool used
        left = mergeSort(left);
        right = mergeSort(right);

        //combines the lists
        sortedList = merge(left, right);
        return sortedList;
    }

    /**
     * Merging tool for the merge sort
     * @param left Left list
     * @param right Right list
     * @return Merged list
     */
    public ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right){
        ArrayList<Integer> mergedList = new ArrayList<Integer>();

        // while there are elements merge
        while(left.size() > 0 || right.size() > 0)
        {
            if(left.size() > 0 && right.size() > 0)
            {
                // merging
                if(left.get(0) < right.get(0))
                {
                    mergedList.add(left.get(0));
                    left.remove(0);
                } else {
                    // merging
                    mergedList.add(right.get(0));
                    right.remove(0);
                }
            } else if(left.size() > 0) {
                // merging
                mergedList.add(left.get(0));
                left.remove(0);
            } else if(right.size() > 0) {
                // merging
                mergedList.add(right.get(0));
                right.remove(0);
            }
        }
        return mergedList;
    }

    // -----------------------------------------------------------------------------------------------------------------
    /**
     * Functioning insertion sort - to be replaced by merge sort
     * @param listSorting Insertion sort
     * @return Sorted List
     */
    public ArrayList<Integer> sortList(ArrayList<Integer> listSorting){
        // key: comparing
        // j: index of another value
        int i, j, key, temp;

        for (i = 1; i < listSorting.size(); i++) {
            // Get the value at index
            key = listSorting.get(i);
            // Get index at previous value
            j = i - 1;

            // compare with j index to key
            while (j >= 0 && key < listSorting.get(j)) {
                temp = listSorting.get(j);
                listSorting.set(j, listSorting.get(j + 1));
                listSorting.set(j + 1, temp);

                // Move to previous index
                j--;
            }
        }
        // Return the sorted list
        return listSorting;
    }

    // testing the performance
    public ArrayList genRandomArr(ArrayList<Integer> array){
        for(int i = 0; i < array.size(); i++){
            array.set(i, (int) (Math.random() * 1000) + 10);
        }

        int itemsInArr;
        itemsInArr = array.size() -1;

        return array;
    }

    // testing
    public static void main(String[] args) throws IOException {
//        ArrayList<Integer> List = new ArrayList<Integer>();
//        ArrayList<Integer> uList = new ArrayList<Integer>();
//        Sorting merge = new Sorting();
//
//        uList.add(123);
//        uList.add(3);
//        uList.add(10);
//        uList.add(1000);
//
//        List = merge.mergeSort(uList);
//
//        System.out.println("unSorted");
//        System.out.println(uList);
//
//        System.out.println("Sorted");
//        System.out.println(List);
        Sorting x = new Sorting();

        ArrayList<Integer> y = new ArrayList<>(200000);
        long startT = System.nanoTime();
        x.mergeSort(x.genRandomArr(y));
        x.stopT = System.nanoTime();
        long elapsedT = (x.stopT - startT);
        System.out.println("Test1: 200000 items - " + elapsedT + " nano time");

        long startT1 = System.nanoTime();
        ArrayList<Integer> z = new ArrayList<>(400000);
        x.mergeSort(x.genRandomArr(z));
        x.stopT = System.nanoTime();
        long elapsedT1 = x.stopT - startT1;
        System.out.println("Test2: 400000 items - " + elapsedT1 + " nano time");

        long startT2 = System.nanoTime();
        ArrayList<Integer> c = new ArrayList<>(600000);
        x.mergeSort(x.genRandomArr(c));
        x.stopT = System.nanoTime();
        long elapsedT2 = x.stopT - startT2;
        System.out.println("Test3: 600000 items - " + elapsedT2 + " nano time");

        long startT3 = System.nanoTime();
        ArrayList<Integer> q = new ArrayList<>(800000);
        x.mergeSort(x.genRandomArr(q));
        long stopT3 = System.nanoTime();
        long elapsedT3 = stopT3 - startT3;
        System.out.println("Test4: 800000 items - " + elapsedT3 + " nano time");

        long startT4 = System.nanoTime();
        ArrayList<Integer> w = new ArrayList<>(1000000);
        x.mergeSort(x.genRandomArr(w));
        long stopT4 = System.nanoTime();
        long elapsedT4 = stopT4 - startT4;
        System.out.println("Test5: 1000000 items - " + elapsedT4 + " nano time");
    }

}
