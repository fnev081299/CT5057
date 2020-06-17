import java.util.*;
import java.lang.Math;

/**
 * The route of the flight through connected flights
 * Runs the other classes in this section for the A* implementation
 */
public class FlightRoute {

    /**
     * Constructor
     */
    public FlightRoute() {
    }

    /**
     * Searching for route
     * @param start Start location
     * @param destination Destination location
     */
    void searchingForRoute(String start, String destination) {
        Map<String, Map<String, Double>> heuristic = new HashMap<String, Map<String, Double>>();

        // map for LHR
        Map<String, Double> LHR = new HashMap<String, Double>();
        LHR.put("LHR", 0.0);
        LHR.put("AAL", 10.0);
        LHR.put("PAR", 15.0);
        LHR.put("CPT", 115.0);
        LHR.put("JFK", 100.0);
        LHR.put("EZE", 110.0);
        LHR.put("YXX", 120.0);
        LHR.put("LAX", 140.0);
        LHR.put("BJS", 240.0);

        // map for AAL
        Map<String, Double> AAL = new HashMap<String, Double>();
        AAL.put("LHR", 10.0);
        AAL.put("AAL", 0.0);
        AAL.put("PAR", 10.0);
        AAL.put("CPT", 110.0);
        AAL.put("JFK", 110.0);
        AAL.put("EZE", 120.0);
        AAL.put("YXX", 130.0);
        AAL.put("LAX", 150.0);
        AAL.put("BJS", 250.0);

        // map for PAR
        Map<String, Double> PAR = new HashMap<String, Double>();
        PAR.put("LHR", 15.0);
        PAR.put("AAL", 10.0);
        PAR.put("PAR", 0.0);
        PAR.put("CPT", 100.0);
        PAR.put("JFK", 115.0);
        PAR.put("EZE", 125.0);
        PAR.put("YXX", 135.0);
        PAR.put("LAX", 155.0);
        PAR.put("BJS", 255.0);

        // map for CPT
        Map<String, Double> CPT = new HashMap<String, Double>();
        CPT.put("LHR", 115.0);
        CPT.put("AAL", 110.0);
        CPT.put("PAR", 100.0);
        CPT.put("CPT", 0.0);
        CPT.put("JFK", 215.0);
        CPT.put("EZE", 225.0);
        CPT.put("YXX", 235.0);
        CPT.put("LAX", 255.0);
        CPT.put("BJS", 355.0);

        // map for JFK
        Map<String, Double> JFK = new HashMap<String, Double>();
        JFK.put("LHR", 100.0);
        JFK.put("AAL", 110.0);
        JFK.put("PAR", 115.0);
        JFK.put("CPT", 215.0);
        JFK.put("JFK", 0.0);
        JFK.put("EZE", 100.0);
        JFK.put("YXX", 20.0);
        JFK.put("LAX", 40.0);
        JFK.put("BJS", 140.0);

        // map for EZE
        Map<String, Double> EZE = new HashMap<String, Double>();
        EZE.put("LHR", 110.0);
        EZE.put("AAL", 120.0);
        EZE.put("PAR", 125.0);
        EZE.put("CPT", 225.0);
        EZE.put("JFK", 100.0);
        EZE.put("EZE", 0.0);
        EZE.put("YXX", 120.0);
        EZE.put("LAX", 110.0);
        EZE.put("BJS", 210.0);

        // map for YXX
        Map<String, Double> YXX = new HashMap<String, Double>();
        YXX.put("LHR", 120.0);
        YXX.put("AAL", 130.0);
        YXX.put("PAR", 135.0);
        YXX.put("CPT", 235.0);
        YXX.put("JFK", 20.0);
        YXX.put("EZE", 120.0);
        YXX.put("YXX", 0.0);
        YXX.put("LAX", 30.0);
        YXX.put("BJS", 130.0);

        // map for LA
        Map<String, Double> LAX = new HashMap<String, Double>();
        LAX.put("LHR", 140.0);
        LAX.put("AAL", 250.0);
        LAX.put("PAR", 155.0);
        LAX.put("CPT", 255.0);
        LAX.put("JFK", 40.0);
        LAX.put("EZE", 110.0);
        LAX.put("YXX", 30.0);
        LAX.put("LAX", 0.0);
        LAX.put("BJS", 100.0);

        // map for BJS
        Map<String, Double> BJS = new HashMap<String, Double>();
        BJS.put("LHR", 240.0);
        BJS.put("AAL", 250.0);
        BJS.put("PAR", 255.0);
        BJS.put("CPT", 355.0);
        BJS.put("JFK", 140.0);
        BJS.put("EZE", 240.0);
        BJS.put("YXX", 130.0);
        BJS.put("LAX", 100.0);
        BJS.put("BJS", 0.0);

        heuristic.put("LHR", LHR);
        heuristic.put("AAL", AAL);
        heuristic.put("PAR", PAR);
        heuristic.put("CPT", CPT);
        heuristic.put("JFK", JFK);
        heuristic.put("EZE", EZE);
        heuristic.put("YXX", YXX);
        heuristic.put("LAX", LAX);
        heuristic.put("BJS", BJS);

        // making the heuristic graph node ids
        AStarGraph<String> graph = new AStarGraph<String>(heuristic);
        graph.nodeAdd("LHR");
        graph.nodeAdd("AAL");
        graph.nodeAdd("PAR");
        graph.nodeAdd("CPT");
        graph.nodeAdd("JFK");
        graph.nodeAdd("YXX");
        graph.nodeAdd("EZE");
        graph.nodeAdd("LAX");
        graph.nodeAdd("BJS");

        // adding edges
        graph.addEdge("LHR", "AAL", 10);
        graph.addEdge("LHR", "PAR", 15);
        graph.addEdge("LHR", "JFK", 100);
        graph.addEdge("LHR", "EZE", 110);

        graph.addEdge("AAL", "PAR", 10);

        graph.addEdge("PAR", "CPT", 100);

        graph.addEdge("JFK", "YXX", 20);
        graph.addEdge("JFK", "EZE", 100);
        graph.addEdge("JFK", "LAX", 40);

        graph.addEdge("EZE", "LAX", 110);

        graph.addEdge("YXX", "LAX", 30);

        graph.addEdge("LAX", "BJS", 100);

        // a star graph
        AStar<String> aStar = new AStar<String>(graph);

        // working out shortest path
        for (String path : aStar.AStarAglo(start, destination)) {
            System.out.println(path); // prints the airports being used
        }
    }

    // testing
    public static void main(String[] args) {
        FlightRoute x = new FlightRoute();

        long startT = System.nanoTime();
        x.searchingForRoute("LHR", "PAR");
        long stopT = System.nanoTime();
        long elapsedT = (stopT - startT);
        System.out.println("Test1: Shortest possible distance - " + elapsedT + " nano time \n");

        long startT1 = System.nanoTime();
        x.searchingForRoute("LAX", "JFK");
        long stopT1 = System.nanoTime();
        long elapsedT1 = (stopT1 - startT1);
        System.out.println("Test2: mid-ranged distance - " + elapsedT1 + " nano time \n");

        long startT2 = System.nanoTime();
        x.searchingForRoute("LAX", "AAL");
        long stopT2 = System.nanoTime();
        long elapsedT2 = (stopT2 - startT2);
        System.out.println("Test3: mid-ranged distance - " + elapsedT2 + " nano time \n");

        long startT3 = System.nanoTime();
        x.searchingForRoute("AAL", "BJS");
        long stopT3 = System.nanoTime();
        long elapsedT3 = (stopT3 - startT3);
        System.out.println("Test4: longest possible distance - " + elapsedT3 + " nano time");

    }
}

/**
 * Node object creation
 * @param <T> A generic class and can work on any type of data
 */
final class Nodes<T> {
    private final T nodeID;
    private final Map<T, Double> heuristic;
    private double distanceFromSource;  // distanceFromSource = distance from the startN
    private double heuristFromDest; // heuristFromDest = the heuristic of destN.
    private double finding; // finding = distanceFromSource + heuristFromDest

    /**
     * Constructor
     * @param nodeID Node ID
     * @param heuristic Heuristic map
     */
    public Nodes (T nodeID, Map<T, Double> heuristic) {
        this.nodeID = nodeID;
        this.distanceFromSource = Double.MAX_VALUE;
        this.heuristic = heuristic;
    }

    /**
     *
     * @return Node ID
     */
    public T getNodeID() {
        return nodeID;
    }

    /**
     * Gets the Graph distance from source
     * @return Distance from Source
     */
    public double getGraph() {
        return distanceFromSource;
    }

    /**
     *
     * @param distanceFromSource Distance form the source
     */
    public void setGraph(double distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

    /**
     * Calculating the finding
     * @param destN Destination Node
     */
    public void calculatingFinding(T destN) {
        this.heuristFromDest = heuristic.get(destN);
        this.finding = distanceFromSource + heuristFromDest;
    }

    /**
     *
     * @return Heuristic from Destination
     */
    public double getHeuristics() {
        return heuristFromDest;
    }

    /**
     *
     * @return Finding
     */
    public double getFinding() {
        return finding;
    }
}

/**
 * Graph representing an undirected graph
 */
final class AStarGraph<T> implements Iterable<T> {
    // Map from the nodeID to outgoing edges
    private final Map<T, Map<Nodes<T>, Double>> graph; // Outgoing edges are represented as a tuples of Nodes and the edge l
    private final Map<T, Map<T, Double>> mapHeur; // Map of heuristic from a node to each other node in the graph
    private final Map<T, Nodes<T>> nodesIDn; // Map between nodeID and node data

    /**
     * Constructor
     * @param mapHeur Heuristic map
     */
    public AStarGraph(Map<T, Map<T, Double>> mapHeur) {
        if (mapHeur == null) throw new NullPointerException("H - map should not be null");
        graph = new HashMap<T, Map<Nodes<T>, Double>>();
        nodesIDn = new HashMap<T, Nodes<T>>();
        this.mapHeur = mapHeur;
    }

    // added for junit testing
    public Map<T, Nodes<T>> getNodesIDn() {
        return nodesIDn;
    }
    // added for junit testing
    public Map<T, Map<Nodes<T>, Double>> getGraph() {
        return graph;
    }

    /**
     * Adds a new node to graph
     * Creates the Data to populate the heuristic maps
     * @param nodeID Node ID
     */
    public void nodeAdd(T nodeID) {
        if (nodeID == null)
            throw new NullPointerException("The node can't be empty[null]");
        if (!mapHeur.containsKey(nodeID))
            throw new NoSuchElementException("This node is not a part of h - map");

        graph.put(nodeID, new HashMap<Nodes<T>, Double>());
        nodesIDn.put(nodeID, new Nodes<T>(nodeID, mapHeur.get(nodeID)));
    }

    /**
     * Adds edges from startN node to destN node
     * Only one single edge from startN to node
     * @param firstNode The first node to be in the edge
     * @param secondNode The second node to be second node in the edge
     * @param l The length of the edge.
     */
    public void addEdge(T firstNode, T secondNode, double l) {
        if (firstNode == null || secondNode == null)
            throw new NullPointerException("Null");

        if (!mapHeur.containsKey(firstNode) || !mapHeur.containsKey(secondNode)) {
            throw new NoSuchElementException("Source and Destination should be in h - map");
        }
        if (!graph.containsKey(firstNode) || !graph.containsKey(secondNode)) {
            throw new NoSuchElementException("Source and Destination should be in graph");
        }

        graph.get(firstNode).put(nodesIDn.get(secondNode), l);
        graph.get(secondNode).put(nodesIDn.get(firstNode), l);
    }

    /**
     *
     * @param nodeID The nodeID outgoing edge to be returned
     * @return View of the edges leaving that node
     */
    public Map<Nodes<T>, Double> edges (T nodeID) {
        if (nodeID == null)
            throw new NullPointerException("Node shouldn't be null.");

        if (!mapHeur.containsKey(nodeID))
            throw new NoSuchElementException("Node not part of h - map");

        if (!graph.containsKey(nodeID))
            throw new NoSuchElementException("Node shouldn't be null.");

        return Collections.unmodifiableMap(graph.get(nodeID));
    }

    /**
     *
     * @param nodeID Node ID
     * @return Data node
     */
    public Nodes<T> getNodes (T nodeID) {
        if (nodeID == null) {
            throw new NullPointerException("Node id shouldn't be empty");
        }
        if (!nodesIDn.containsKey(nodeID))  {
            throw new NoSuchElementException("nodeID doesn't exist");
        }
        return nodesIDn.get(nodeID);
    }

    /**
     * Iterator to traverse the nodes of the graph
     * @return Iterator.
     */
    @Override public Iterator<T> iterator() {
        return graph.keySet().iterator();
    }
}

/**
 * A Star implementation
 */
class AStar<T> {

    private final AStarGraph<T> graph;

    /**
     * Constructor
     * @param graphAStar Graph used
     */
    public AStar(AStarGraph<T> graphAStar) {
        this.graph = graphAStar;
    }

    /**
     * Extending comparing function
     */
    public class compNode implements Comparator<Nodes<T>> {

        /**
         * Comparing
         * @param nodeFirst First
         * @param nodeSecond Second
         * @return 0
         */
        public int compare(Nodes<T> nodeFirst, Nodes<T> nodeSecond) {
            // checking first node and second node
            if (nodeFirst.getFinding() > nodeSecond.getFinding()) {
                return 1;
            }
            if (nodeSecond.getFinding() > nodeFirst.getFinding()) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * A-star algorithm path from start to destination
     *
     * @param startN start node ID
     * @param destN  destination node ID
     * @return path from start to destination
     */
    public List<T> AStarAglo(T startN, T destN) {
        final Queue<Nodes<T>> oQueue = new PriorityQueue<Nodes<T>>(11, new compNode());
        Nodes<T> StartN = graph.getNodes(startN);

        StartN.setGraph(0);
        // calculation to destination
        StartN.calculatingFinding(destN);

        // add the starting node
        oQueue.add(StartN);

        final Map<T, T> path = new HashMap<T, T>();
        final Set<Nodes<T>> cList = new HashSet<Nodes<T>>();

        // while the queue isnt empty
        while (!oQueue.isEmpty()) {
            // removes the first entry in container
            final Nodes<T> nodeData = oQueue.poll();
            // check for destination
            if (nodeData.getNodeID().equals(destN)) {
                return path(path, destN);
            }

            // add location to list
            cList.add(nodeData);

            // for all graph edges entries do this
            for (Map.Entry<Nodes<T>, Double> adjNodeEnt : graph.edges(nodeData.getNodeID()).entrySet()) {
                Nodes<T> adjNeighbor = adjNodeEnt.getKey();
                // continue if there are neighbours
                if (cList.contains(adjNeighbor)) continue;
                double distBetweenNodes = adjNodeEnt.getValue();
                double testGraph = distBetweenNodes + nodeData.getGraph();

                // adding to path
                if (testGraph < adjNeighbor.getGraph()) {
                    adjNeighbor.setGraph(testGraph);
                    adjNeighbor.calculatingFinding(destN);

                    path.put(adjNeighbor.getNodeID(), nodeData.getNodeID());

                    // check neighbors
                    if (!oQueue.contains(adjNeighbor)) {
                        oQueue.add(adjNeighbor);
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param path Path
     * @param destN Destination
     * @return Path
     */
    private List<T> path(Map<T, T> path, T destN) {
        assert path != null;
        assert destN != null;

        final List<T> pList = new ArrayList<T>();
        pList.add(destN);
        while (path.containsKey(destN)) {
            destN = path.get(destN);
            pList.add(destN);
        }
        Collections.reverse(pList);
        return pList;
    }

}