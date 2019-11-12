import java.util.*;

/**
 * A simple Network class to build a network
 *
 * @author Vaastav Arora, arora74@purdue.edu
 */
public class Network {
    /**
     * computerConnections represents list of all inter-computer edges
     * Each edge is an Integer[] of size 3
     * edge[0] = source computer index ( Not IP, it's the Index !)
     * edge[1] = destination computer index ( Not IP, it's the Index !)
     * edge[2] = latency/edge weight
     */
    private LinkedList<Integer[]> computerConnections;
    /**
     * Adjacency List representing computer graph
     */
    private LinkedList<LinkedList<Integer>> computerGraph;
    /**
     * LinkedList of clusters where each cluster is represented as a LinkedList of computer IP addresses
     */
    private LinkedList<LinkedList<Integer>> cluster;
    /**
     * Adjacency List representing router graph
     */
    private LinkedList<LinkedList<Integer[]>> routerGraph;

    Scanner s; // Scanner to read Stdin input

    //Add your own field variables as required

    private int indexPool;

    private Map<Integer, Computer> computerByIp;

    private class Computer {
        private int ip;
        private int index;
        private Set<Computer> connections;

        Computer(int ip) {
            this.ip = ip;
            this.index = indexPool++;
            connections = new HashSet<>();
        }

        int getIp() {
            return ip;
        }

        int getIndex() {
            return index;
        }

        Set<Computer> getConnections() {
            return connections;
        }

        void addConnection(Computer connection) {
            connections.add(connection);
        }
    }

    /**
     * Default Network constructor, initializes data structures
     *
     * @param s Provided Scanner to be used throughout program
     */
    public Network(Scanner s) {
        this.s = s;
        computerConnections = new LinkedList<>();
        computerByIp = new HashMap<>();
    }

    /**
     * Method to parse Stdin input and generate inter-computer edges
     * Edges are stored within computerConnections
     * <p>
     * First line of input => Number of edges
     * All subsequent lines => [IP address of comp 1] [IP address of comp 2] [latency of connection]
     */
    public void buildComputerNetwork() {
        int rows = s.nextInt();
        for (int i = 0; i < rows; i++) {
            int ip = s.nextInt();
            int otherIp = s.nextInt();
            int latency = s.nextInt();

            Computer computer = getOrCreateComputer(ip);
            Computer otherComputer = getOrCreateComputer(otherIp);
            computer.addConnection(otherComputer);
            computerConnections.add(new Integer[] {computer.getIndex(), otherComputer.getIndex(), latency});
        }
    }

    /**
     * Method to generate clusters from computer graph
     * Throws Exception when cannot create required clusters
     *
     * @param k number of clusters to be created
     */
    public void buildCluster(int k) throws Exception {

        //TODO

    }

    /**
     * Method to parse Stdin input and generate inter-router edges
     * Graph is stored within routerGraph as an adjacency list
     * <p>
     * First line of input => Number of edges
     * All subsequent lines => [IP address of Router 1] [IP address of Router 2] [latency of connection]
     */
    public void connectCluster() {

        //TODO
    }

    /**
     * Method to take a traversal request and find the shortest path for that traversal
     * Traversal request passed in through parameter test
     * Format of Request => [IP address of Source Router].[IP address of Source Computer] [IP address of Destination Router].[IP address of Destination Computer]
     * Eg. 123.456 128.192
     * 123 = IP address of Source Router
     * 456 = IP address of Source Computer
     * 128 = IP address of Destination Router
     * 192 = IP address of Destination Computer
     *
     * @param test String containing traversal input
     * @return Shortest traversal distance between Source and Destination Computer
     */
    public int traversNetwork(String test) {

        //TODO
        return -1;
    }

    //Add your own methods as required

    Computer getOrCreateComputer(int ip) {
        Computer computer;
        if (computerByIp.containsKey(ip)) {
            computer = computerByIp.get(ip);
        } else {
            computer = new Computer(ip);
            computerByIp.put(ip, computer);
        }
        return computer;
    }

    private void printGraph(LinkedList<LinkedList<Integer[]>> graph) {
        for (var i : graph) {
            for (var j : i) {
                System.out.print(j[0] + " " + j[1]);
            }
            System.out.println();
        }
    }

    public LinkedList<Integer[]> getComputerConnections() {
        return computerConnections;
    }

    public LinkedList<LinkedList<Integer>> getComputerGraph() {
        return computerGraph;
    }

    public LinkedList<LinkedList<Integer>> getCluster() {
        return cluster;
    }

    public LinkedList<LinkedList<Integer[]>> getRouterGraph() {
        return routerGraph;
    }

}
