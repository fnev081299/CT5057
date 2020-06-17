import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WaitingListTest {
    private WaitingList x = new WaitingList();

    @Test
    void addToWaiting() {
        x.addToWaiting(123);
        //assume that the below array represents expected result
        Integer[] expectedOutput = {123};

        //to be tested.
        Integer[] methodOutput = x.getQueue().toArray(new Integer[0]);

        //outputted values
        System.out.println("\nTest 1:\nExpected values: \n" + "[123]" +
                "\nResult: \n" + x.getQueue());

        assertArrayEquals(expectedOutput, methodOutput);

        // ------------------------------------------------------------
        x.addToWaiting(321);
        //assume that the below array represents expected result
        Integer[] expectedOutput1 = {123, 321};

        //to be tested.
        Integer[] methodOutput1 = x.getQueue().toArray(new Integer[0]);

        //outputted values
        System.out.println("\nTest 2:\nExpected values: \n" + "[123, 321]" +
                "\nResult: \n" + x.getQueue());
        assertArrayEquals(expectedOutput1, methodOutput1);
    }

    @Test
    void removeFromWaiting() {
        // Values in test are 123, 321
        // when popping the first value is removed leaving 321

        x.addToWaiting(123);
        x.addToWaiting(321);

        // print original
        System.out.println("Original: \n" + x.getQueue());

        x.removeFromWaiting();
        //assume that the below array represents expected result
        Integer[] expectedOutput = {321};

        //to be tested.
        Integer[] methodOutput = x.getQueue().toArray(new Integer[0]);

        //outputted values
        System.out.println("\nTest 1:\nExpected values (Popping 123): \n" + "[321]" +
                "\nResult: \n" + x.getQueue());

        assertArrayEquals(expectedOutput, methodOutput);
    }

    @Test
    void getQueue() {
        // adding values to queue first
        x.addToWaiting(123);
        x.addToWaiting(321);
        x.addToWaiting(456);
        x.addToWaiting(654);

        //assume that the below array represents expected result
        Integer[] expectedOutput = {123, 321, 456, 654};

        //to be tested.
        Integer[] methodOutput = x.getQueue().toArray(new Integer[0]);

        assertArrayEquals(expectedOutput, methodOutput);

        System.out.println("Test1:\n Expecting: \n[123, 321, 456, 654]" + "\nValues OutPut: \n" + x.getQueue());
    }

    @Test
    void getStart() {
        //get the first value
        // adding values to queue first
        x.addToWaiting(123);
        x.addToWaiting(321);
        x.addToWaiting(456);
        x.addToWaiting(654);

        assertEquals(123, x.getStart());

        System.out.println("Test1:\n Expecting: \n123" + "\nValues OutPut: \n" + x.getStart());
    }
}
