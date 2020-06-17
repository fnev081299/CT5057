import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SortingTest {
    private Sorting x = new Sorting();
    private ArrayList<Integer> testingArray = new ArrayList();

    @Test
    void mergeSort() {

        testingArray.add(12);   testingArray.add(11);   testingArray.add(10);
        testingArray.add(8);    testingArray.add(9);    testingArray.add(100);
        testingArray.add(1);    testingArray.add(2);    testingArray.add(81);

        //assume that the below array represents expected result
        Integer[] expectedOutput = {1,2,8,9,10,11,12,81,100};

        //to be tested.
        Integer[] methodOutput = x.mergeSort(testingArray).toArray(new Integer[0]);

        //outputted values
        System.out.println("Expected values: \n" + "[1, 2, 8, 9, 10, 11, 12, 81, 100]" +
                "\nResult: \n" + x.mergeSort(testingArray));
        assertArrayEquals(expectedOutput, methodOutput);

    }

}

class SearchingTest {
    private Searching x = new Searching();
    private ArrayList<Integer> testingArray = new ArrayList();

    @Test
    void binarySearchRecursive() {
        testingArray.add(1);        testingArray.add(2);        testingArray.add(4);
        testingArray.add(8);        testingArray.add(16);       testingArray.add(32);
        testingArray.add(64);       testingArray.add(128);      testingArray.add(256);

        //assume that the below array represents expected result = 128
        Integer expected = 128;

        //to be tested.
        boolean testing = x.binarySearchRecursive(testingArray, expected, 0, testingArray.size()-1);

        assertTrue(testing);

        System.out.println("Passing value is true:\n" + testing);

        //assume that the below array represents expected result = 128
        Integer expected2 = 122;

        //to be tested.
        boolean testing2 = x.binarySearchRecursive(testingArray, expected2, 0, testingArray.size()-1);

        assertFalse(testing2);

        System.out.println("Passing value is false:\n" + testing2);

    }
}