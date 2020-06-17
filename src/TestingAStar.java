import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AStarTest {
    private FlightRoute x = new FlightRoute();

    @Test
    void AStarAglo() {
        System.out.println("Expected Result:" +
                "\nLHR" +
                "\nJFK" +
                "\nLAX" +
                "\nBJS\n");

        System.out.println("Actual Result:");
        x.searchingForRoute("LHR", "BJS");
    }
}

class AStarGraphTest {
    private Map<String, Map<String, Double>> heuristic = new HashMap<String, Map<String, Double>>();
    private Map<String, Double> LHR = new HashMap<String, Double>();
    private Map<String, Double> AAL = new HashMap<String, Double>();
    private Map<String, Double> PAR = new HashMap<String, Double>();
    private AStarGraph<String> graph = new AStarGraph<String>(heuristic);

    @Test
    void nodeAdd() {
        LHR.put("LHR", 0.0);
        LHR.put("AAL", 10.0);
        LHR.put("PAR", 15.0);

        AAL.put("LHR", 10.0);
        AAL.put("AAL", 0.0);
        AAL.put("PAR", 10.0);

        PAR.put("LHR", 15.0);
        PAR.put("AAL", 10.0);
        PAR.put("PAR", 0.0);

        heuristic.put("LHR", LHR);
        heuristic.put("AAL", AAL);
        heuristic.put("PAR", PAR);

        graph.nodeAdd("LHR");
        graph.nodeAdd("AAL");
        graph.nodeAdd("PAR");

        System.out.println(graph.getNodesIDn());

        // check if the map has values entered
        assertNotNull(graph.getNodesIDn());

    }

    @Test
    void addEdge() {
        LHR.put("LHR", 0.0);
        LHR.put("AAL", 10.0);
        LHR.put("PAR", 15.0);

        AAL.put("LHR", 10.0);
        AAL.put("AAL", 0.0);
        AAL.put("PAR", 10.0);

        PAR.put("LHR", 15.0);
        PAR.put("AAL", 10.0);
        PAR.put("PAR", 0.0);

        heuristic.put("LHR", LHR);
        heuristic.put("AAL", AAL);
        heuristic.put("PAR", PAR);

        graph.nodeAdd("LHR");
        graph.nodeAdd("AAL");
        graph.nodeAdd("PAR");

        graph.addEdge("LHR", "AAL", 10);
        graph.addEdge("LHR", "PAR", 15);
        graph.addEdge("AAL", "PAR", 10);

        System.out.println(graph.getGraph());

        // check if the graph is not empty
        assertNotNull(graph.getGraph());
    }

    @Test
    void edges() {
        LHR.put("LHR", 0.0);
        LHR.put("AAL", 10.0);
        LHR.put("PAR", 15.0);

        AAL.put("LHR", 10.0);
        AAL.put("AAL", 0.0);
        AAL.put("PAR", 10.0);

        PAR.put("LHR", 15.0);
        PAR.put("AAL", 10.0);
        PAR.put("PAR", 0.0);

        heuristic.put("LHR", LHR);
        heuristic.put("AAL", AAL);
        heuristic.put("PAR", PAR);

        graph.nodeAdd("LHR");
        graph.nodeAdd("AAL");
        graph.nodeAdd("PAR");

        graph.addEdge("LHR", "AAL", 10);
        graph.addEdge("LHR", "PAR", 15);
        graph.addEdge("AAL", "PAR", 10);

        System.out.println(graph.edges("AAL"));

        // check if the point has edges
        assertNotNull(graph.edges("AAL"));
    }

    @Test
    void getNodes() {
        LHR.put("LHR", 0.0);
        LHR.put("AAL", 10.0);
        LHR.put("PAR", 15.0);

        AAL.put("LHR", 10.0);
        AAL.put("AAL", 0.0);
        AAL.put("PAR", 10.0);

        PAR.put("LHR", 15.0);
        PAR.put("AAL", 10.0);
        PAR.put("PAR", 0.0);

        heuristic.put("LHR", LHR);
        heuristic.put("AAL", AAL);
        heuristic.put("PAR", PAR);

        graph.nodeAdd("LHR");
        graph.nodeAdd("AAL");
        graph.nodeAdd("PAR");

        graph.addEdge("LHR", "AAL", 10);
        graph.addEdge("LHR", "PAR", 15);
        graph.addEdge("AAL", "PAR", 10);

        System.out.println(graph.getNodes("AAL"));

        // check if the node exists
        assertNotNull(graph.getNodes("AAL"));
    }

    @Test
    void iterator() {
        LHR.put("LHR", 0.0);
        LHR.put("AAL", 10.0);
        LHR.put("PAR", 15.0);

        AAL.put("LHR", 10.0);
        AAL.put("AAL", 0.0);
        AAL.put("PAR", 10.0);

        PAR.put("LHR", 15.0);
        PAR.put("AAL", 10.0);
        PAR.put("PAR", 0.0);

        heuristic.put("LHR", LHR);
        heuristic.put("AAL", AAL);
        heuristic.put("PAR", PAR);

        graph.nodeAdd("LHR");
        graph.nodeAdd("AAL");
        graph.nodeAdd("PAR");

        graph.addEdge("LHR", "AAL", 10);
        graph.addEdge("LHR", "PAR", 15);
        graph.addEdge("AAL", "PAR", 10);

        System.out.println(graph.iterator());

        // check if the iterator is created
        assertNotNull(graph.iterator());
    }
}
