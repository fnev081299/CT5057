import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightListTest {
    private FlightList testFlightList = new FlightList();

    private Airport JFK = new Airport("JFK");
    private Airport PAR = new Airport("PAR");
    private Airport LAX = new Airport("LAX");

    private String[] first;
    private String[] business;
    private String[] economy;

    private Flight testFlight = new Flight(123, JFK, PAR, "12/01/2020",first, business, economy);

    @Test
    void getFlightListInfo() {
        assertNotNull(testFlightList.getFlightListInfo());
    }

    @Test
    void printFlightInfo() throws IOException {
        testFlightList.createFlightListInfo();
        testFlightList.printFlightInfo();
        assertNotNull(testFlightList.getFlightListInfo());
    }

    @Test
    void createFlightListInfo() {
        testFlightList.createFlightListInfo();
        testFlightList.printFlightInfo();
        assertNotNull(testFlightList.getFlightListInfo());
    }

    @Test
    void getFromKey() {
        testFlightList.createFlightListInfo();
        Integer x = testFlightList.getFromKey(101).getFlightNumber();
        assertEquals(101, x);
    }

    @Test
    void getFlights() {
        testFlightList.createFlightListInfo();
        ArrayList<Flight> x = testFlightList.getFlights();

        assertNotNull(x);
    }

    @Test
    void searchFlights() {
        boolean x = testFlightList.searchFlights(101);

        assertEquals(false, x);
    }

    @Test
    void addToFlight() throws IOException {
        // to run this test you have to remove 11 from flight 101
        testFlightList.createFlightListInfo();

        testFlightList.reAddFirst(101);
        testFlightList.reAddBusiness(101);
        testFlightList.reAddEconomy(101);

        testFlightList.reAddFirstWaiting(101);
        testFlightList.reAddBusinessWaiting(101);
        testFlightList.reAddEconomyWaiting(101);

        if (testFlightList.reAddBusiness(101).contains(11)){
            testFlightList.removeFromFlight(101,11);
        }

        testFlightList.addToFlight(101,"business", 11);

        ArrayList<Integer> x = new ArrayList<>();
        x.add(11);

        assertEquals(x, testFlightList.getFromKey(101).getBClass());

    }

    @Test
    void removeFromFlight() throws IOException {
        // to run this test you have to remove 11 from flight 101
        testFlightList.createFlightListInfo();

        testFlightList.reAddFirst(101);
        testFlightList.reAddBusiness(101);
        testFlightList.reAddEconomy(101);

        testFlightList.reAddFirstWaiting(101);
        testFlightList.reAddBusinessWaiting(101);
        testFlightList.reAddEconomyWaiting(101);

        testFlightList.removeFromFlight(101, 11);

        ArrayList<Integer> x = new ArrayList<>();

        assertEquals(x, testFlightList.getFromKey(101).getBClass());

        // works upto asking to add someone from waiting list due to the junit not
        // allowing you to enter anything in terminal

    }

    @Test
    void addToList() throws IOException {
        testFlightList.createFlightListInfo();

        testFlightList.reAddFirst(101);
        testFlightList.reAddBusiness(101);
        testFlightList.reAddEconomy(101);

        testFlightList.reAddFirstWaiting(101);
        testFlightList.reAddBusinessWaiting(101);
        testFlightList.reAddEconomyWaiting(101);

        testFlightList.addToList(101, "business", 333);

        assertNotNull(testFlightList.getFromKey(101).getWaitingListBusiness());
    }

    @Test
    void removeAndAddListToFlight() throws IOException {
        testFlightList.createFlightListInfo();

        testFlightList.reAddFirst(101);
        testFlightList.reAddBusiness(101);
        testFlightList.reAddEconomy(101);

        testFlightList.reAddFirstWaiting(101);
        testFlightList.reAddBusinessWaiting(101);
        testFlightList.reAddEconomyWaiting(101);

        testFlightList.removeAndAddListToFlight(101,"business", "economy");

        assertNotNull(testFlightList.getFromKey(101).getEClass());
    }

    @Test
    void removeFromListSpecific() throws IOException {
        testFlightList.createFlightListInfo();

        testFlightList.reAddFirst(101);
        testFlightList.reAddBusiness(101);
        testFlightList.reAddEconomy(101);

        testFlightList.reAddFirstWaiting(101);
        testFlightList.reAddBusinessWaiting(101);
        testFlightList.reAddEconomyWaiting(101);

        testFlightList.removeFromListSpecific(101, 1, "first");
        // removes value from the first class waiting list
    }
}

class FlightTest {
    private Airport JFK = new Airport("JFK");
    private Airport PAR = new Airport("PAR");
    private Airport LAX = new Airport("LAX");
    private String[] first;
    private String[] business;
    private String[] economy;

    private Flight testFlight = new Flight(123, JFK, PAR, "12/01/2020",first, business, economy);

    @Test
    void getFlightNumber() {
        Integer x = testFlight.getFlightNumber();
        Integer act = 123;

        assertEquals(act,x);
        System.out.println("Actual:\n123\nGiven:\n" + x);
    }

    @Test
    void setFlightNumber() {
        testFlight.setFlightNumber(111);
        Integer x = testFlight.getFlightNumber();

        assertEquals(111,x);

        System.out.println("New Actual:\n111 \nGiven: \n" + x);
    }

    @Test
    void getDepAirport() {
        String x = testFlight.getDepAirport();

        assertEquals("JFK", x);
        System.out.println("Actual:\nJFK \nGiven: \n" + x);
    }

    @Test
    void setDepAirport() {
        testFlight.setDepAirport(LAX);
        String x = testFlight.getDepAirport();

        assertEquals("LAX", x);
        System.out.println("Actual:\nLAX \nGiven: \n" + x);
    }

    @Test
    void getDepDate() {
        String x = testFlight.getDepDate();

        assertEquals("12/01/2020", x);
        System.out.println("Actual:\n12/01/2020 \nGiven: \n" + x);
    }

    @Test
    void setDepDate() {
        testFlight.setDepDate("12/12/2021");
        String x = testFlight.getDepDate();

        assertEquals("12/12/2021", x);
        System.out.println("Actual:\n12/12/2021 \nGiven: \n" + x);
    }

    @Test
    void getArrAirport() {
        String x = testFlight.getArrAirport();

        assertEquals("PAR", x);
        System.out.println("Actual:\nPAR \nGiven: \n" + x);
    }

    @Test
    void setArrAirport() {
        testFlight.setArrAirport(LAX);
        String x = testFlight.getArrAirport();

        assertEquals("LAX", x);
        System.out.println("Actual:\nLAx \nGiven: \n" + x);
    }

    @Test
    void printPassengers() {
        testFlight.printPassengers();

        assertNotNull(testFlight.getPassengers());
    }

    @Test
    void getFlight() {
        assertNotNull(testFlight.getFlight());
    }

    @Test
    void returnFlightDepAndArr() {
        System.out.println(testFlight.returnFlightDepAndArr());

        assertNotNull(testFlight.returnFlightDepAndArr());
    }

    @Test
    void getFClass() {
        testFlight.addFClass(123);
        ArrayList<Integer> x = testFlight.getFClass();
        ArrayList<Integer> y = new ArrayList<>();

        y.add(123);

        assertEquals(y,x);
    }

    @Test
    void printFClass() {
        testFlight.addFClass(123);
        ArrayList<Integer> x = testFlight.getFClass();

        testFlight.printFClass();

        assertNotNull(x);
    }

    @Test
    void addFClass() {
        testFlight.addFClass(123);
        ArrayList<Integer> x = testFlight.getFClass();

        assertNotNull(x);
    }

    @Test
    void removeFClass() {
        testFlight.addFClass(123);
        testFlight.removeFClass(123);
        ArrayList<Integer> x = testFlight.getFClass();
        ArrayList<Integer> y = new ArrayList<>();

        assertEquals(y,x);
    }

    @Test
    void getBClass() {
        testFlight.addBClass(123);
        ArrayList<Integer> x = testFlight.getBClass();
        ArrayList<Integer> y = new ArrayList<>();

        y.add(123);

        assertEquals(y,x);
    }

    @Test
    void printBClass() {
        testFlight.addBClass(123);
        ArrayList<Integer> x = testFlight.getBClass();

        testFlight.printBClass();

        assertNotNull(x);
    }

    @Test
    void addBClass() {
        testFlight.addBClass(123);
        ArrayList<Integer> x = testFlight.getBClass();

        assertNotNull(x);
    }

    @Test
    void removeBClass() {
        testFlight.addBClass(123);
        testFlight.removeBClass(123);
        ArrayList<Integer> x = testFlight.getBClass();
        ArrayList<Integer> y = new ArrayList<>();

        assertEquals(y,x);
    }

    @Test
    void getEClass() {
        testFlight.addEClass(123);
        ArrayList<Integer> x = testFlight.getEClass();
        ArrayList<Integer> y = new ArrayList<>();

        y.add(123);

        assertEquals(y,x);
    }

    @Test
    void printEClass() {
        testFlight.addEClass(123);
        ArrayList<Integer> x = testFlight.getEClass();

        testFlight.printEClass();

        assertNotNull(x);
    }

    @Test
    void addEClass() {
        testFlight.addEClass(123);
        ArrayList<Integer> x = testFlight.getEClass();

        assertNotNull(x);
    }

    @Test
    void removeEClass() {
        testFlight.addEClass(123);
        testFlight.removeEClass(123);
        ArrayList<Integer> x = testFlight.getEClass();
        ArrayList<Integer> y = new ArrayList<>();

        assertEquals(y,x);
    }

    @Test
    void getWaitingListFirst() {
        assertNotNull(testFlight.getWaitingListFirst());
    }

    @Test
    void addToWaitingListFirst() {
        testFlight.addToWaitingListFirst(1);
        ArrayList<Integer> x = new ArrayList<>();
        x.add(1);
        assertEquals(x, testFlight.getWaitingListFirst());
    }

    @Test
    void removeFromWaitingListFirst() {
        testFlight.addToWaitingListFirst(1);
        testFlight.addToWaitingListFirst(2);
        testFlight.removeFromWaitingListFirst();
        ArrayList<Integer> x = new ArrayList<>();
        x.add(1);
        x.add(2);
        // queues remove the first entry
        x.remove(0);

        assertEquals(x, testFlight.getWaitingListFirst());
    }

    @Test
    void removeAllFromWaitingListFirst() {
        testFlight.addToWaitingListFirst(1);
        testFlight.addToWaitingListFirst(2);
        testFlight.removeAllFromWaitingListFirst();

        assertNotNull(testFlight.getWaitingListFirst());
    }

    @Test
    void getStartFromFirst() {
        testFlight.addToWaitingListFirst(1);
        testFlight.addToWaitingListFirst(2);

        assertEquals(1, testFlight.getStartFromFirst());
    }

    @Test
    void printWaitingListFirst() {
        testFlight.addToWaitingListFirst(1);
        testFlight.addToWaitingListFirst(2);

        testFlight.printWaitingListFirst();
        assertNotNull(testFlight.getWaitingListFirst());
    }

    @Test
    void getWaitingListBusiness() {
        assertNotNull(testFlight.getWaitingListBusiness());
    }

    @Test
    void addToWaitingListBusiness() {
        testFlight.addToWaitingListBusiness(1);
        ArrayList<Integer> x = new ArrayList<>();
        x.add(1);
        assertEquals(x, testFlight.getWaitingListBusiness());
    }

    @Test
    void removeFromWaitingListBusiness() {
        testFlight.addToWaitingListBusiness(1);
        testFlight.addToWaitingListBusiness(2);
        testFlight.removeFromWaitingListBusiness();
        ArrayList<Integer> x = new ArrayList<>();
        x.add(1);
        x.add(2);
        // queues remove the first entry
        x.remove(0);

        assertEquals(x, testFlight.getWaitingListBusiness());
    }

    @Test
    void removeAllFromWaitingListBusiness() {
        testFlight.addToWaitingListBusiness(1);
        testFlight.addToWaitingListBusiness(2);
        testFlight.removeAllFromWaitingListBusiness();

        assertNotNull(testFlight.getWaitingListBusiness());
    }

    @Test
    void getStartFromBusiness() {
        testFlight.addToWaitingListBusiness(1);
        testFlight.addToWaitingListBusiness(2);

        assertEquals(1, testFlight.getStartFromBusiness());
    }

    @Test
    void printWaitingListBusiness() {
        testFlight.addToWaitingListBusiness(1);
        testFlight.addToWaitingListBusiness(2);

        testFlight.printWaitingListBusiness();
        assertNotNull(testFlight.getWaitingListBusiness());
    }

    @Test
    void getWaitingListEconomy() {
        assertNotNull(testFlight.getWaitingListEconomy());
    }

    @Test
    void addToWaitingListEconomy() {
        testFlight.addToWaitingListEconomy(1);
        ArrayList<Integer> x = new ArrayList<>();
        x.add(1);
        assertEquals(x, testFlight.getWaitingListEconomy());
    }

    @Test
    void removeFromWaitingListEconomy() {
        testFlight.addToWaitingListEconomy(1);
        testFlight.addToWaitingListEconomy(2);
        testFlight.removeFromWaitingListEconomy();
        ArrayList<Integer> x = new ArrayList<>();
        x.add(1);
        x.add(2);
        // queues remove the first entry
        x.remove(0);

        assertEquals(x, testFlight.getWaitingListEconomy());
    }

    @Test
    void removeAllFromWaitingListEconomy() {
        testFlight.addToWaitingListEconomy(1);
        testFlight.addToWaitingListEconomy(2);
        testFlight.removeAllFromWaitingListEconomy();

        assertNotNull(testFlight.getWaitingListEconomy());
    }

    @Test
    void getStartFromEconomy() {
        testFlight.addToWaitingListEconomy(1);
        testFlight.addToWaitingListEconomy(2);

        assertEquals(1, testFlight.getStartFromEconomy());
    }

    @Test
    void printWaitingListEconomy() {
        testFlight.addToWaitingListEconomy(1);
        testFlight.addToWaitingListEconomy(2);

        testFlight.printWaitingListEconomy();
        assertNotNull(testFlight.getWaitingListEconomy());
    }
}

class PassengerTest {
    private Passenger testP = new Passenger("test", 1);

    @Test
    void getName() {
        String x = testP.getName();
        String actualVal = "test";

        assertEquals("test", x);
        System.out.println("Test 1:\nExpected:\ntest\nActual:\n" + x);
    }

    @Test
    void getPassengerID() {
        Integer x = testP.getPassengerID();
        Integer actualVal = 1;

        assertEquals(1, x);
        System.out.println("Test 1:\nExpected:\n1\nActual:\n" + x);
    }

    @Test
    void getBookings() {
        ArrayList<Integer> x = new ArrayList<>();
        x.add(122);
        testP.addBookings(122);

        assertEquals(x, testP.getBookings());
        System.out.println("Test 1:\nExpected:\n[122]\nActual:\n" + testP.getBookings());
    }

    @Test
    void addBookings() {
        ArrayList<Integer> x = new ArrayList<>();
        x.add(122);
        x.add(123);

        testP.addBookings(122);
        testP.addBookings(123);

        assertEquals(x, testP.getBookings());
        System.out.println("Test 1:\nExpected:\n[122, 123]\nActual:\n" + testP.getBookings());
    }

    @Test
    void removeBookings() {
        ArrayList<Integer> x = new ArrayList<>();
        x.add(122);
        x.add(123);

        testP.addBookings(122);
        testP.addBookings(123);
        testP.addBookings(124);
        System.out.println("Original:\n" + testP.getBookings() + "\n");

        testP.removeBookings(124);


        assertEquals(x, testP.getBookings());
        System.out.println("Test 1:\nExpected:\n[122, 123]\nActual:\n" + testP.getBookings());
    }

    @Test
    void printBookings() {
        ArrayList<Integer> x = new ArrayList<>();
        x.add(122);
        x.add(123);
        x.add(124);

        testP.addBookings(122);
        testP.addBookings(123);
        testP.addBookings(124);
        System.out.println("Original:\n" + testP.getBookings() + "\n");

        testP.getBookings();


        assertEquals(x, testP.getBookings());
        System.out.println("Test 1:\nExpected:\n[122, 123, 124]\nActual:\n" + testP.getBookings());
    }

    @Test
    void printPassenger() {
        ArrayList<Integer> x = new ArrayList<>();

        assertEquals(1, testP.getPassengerID());
        assertEquals("test", testP.getName());
        assertEquals(x, testP.getBookings());

        System.out.println("Expecting:\n" +
                "Passenger ID:   1\n" +
                "Passenger Name: test\n" +
                "Bookings:");
        System.out.println("Actual");
        testP.printPassenger();
    }

    @Test
    void setupPassenger() throws IOException {
        // sets up passenger only done once as the file is sett up
        // testP.setupPassenger();

        Sorting sort = new Sorting();
        Searching search = new Searching();
        boolean x;

        ArrayList<String> results = new ArrayList<>();

        File[] files = new File("Passengers").listFiles();

        // getting the passenger ids from the passenger file
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName().replace(".txt", ""));
                //System.out.println(results);
            }
        }

        // A collection of Strings
        ArrayList<Integer> resultList = testP.getIntegerArray(results);

        // sort and search for the passenger
        sort.mergeSort(resultList);

        x = search.binarySearchRecursive(sort.mergeSort(resultList), testP.getPassengerID(), 0,
                resultList.size()-1);

        assertEquals(true, x);

        System.out.println("Expected:\n" +
                "true\n" +
                "Result:\n" +
                x);

    }

}
