import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class TestMe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(TestMe.class.getResourceAsStream("/data/network1.txt"));
        Network network = new Network(scanner);
        network.buildComputerNetwork();
        LinkedList<Integer[]> computerConnections = network.getComputerConnections();
        printConnections(computerConnections);
    }

    private static void printConnections(LinkedList<Integer[]> computerConnections) {
        for( Integer[] conn: computerConnections) {
            System.out.println(Arrays.toString(conn));
        }
    }
}
