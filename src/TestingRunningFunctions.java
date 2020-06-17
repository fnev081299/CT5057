import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {
    private Scheduler test = new Scheduler();

    @Test
    void setFlightSchedule() throws IOException {
        test.setFlightSchedule("test", 1,101, "economy");
        //in flight 101 there will be a new passenger as value 1
    }

    @Test
    void setFlightCancellation() throws IOException {
        test.setFlightCancellation("test", 1,101,true, "economy");
        //in flight 101 passenger 1 will be cancelled
    }

    @Test
    void getPassengerStatus() throws IOException {
        test.getPassengerStatus("test", 2);
        // prints info
    }

    @Test
    void getFlightStatus() throws IOException {
        test.getFlightStatus(101);
        // prints info
    }

    @Test
    void getRoute() {
        test.getRoute("JFK", "LAX");
        // returns route
    }
}
