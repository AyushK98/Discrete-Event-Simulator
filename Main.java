import cs2030.simulator.Simulator;
import java.util.Scanner;

/**
 * Main Class that takes in ten variables, passes it to a simulator and 
 * then prints out the schedule of events and statistics.
 */
public class Main {

    /**
     * Main method that uses a Scanner class to obtain ten inputs.
     * @param args no arguments passed as a String array
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int baseSeed = sc.nextInt(); //for Random Generation
        int numServers = sc.nextInt(); 
        int selfcheckoutServers = sc.nextInt(); 
        int maxqlength = sc.nextInt();
        int numCustomers = sc.nextInt();
        double arrivalRate = sc.nextDouble(); //for Random Generation
        double serviceRate = sc.nextDouble(); //for Random Generation
        double restingRate = sc.nextDouble(); //for Random Generation
        double probabilityOfRest = sc.nextDouble();
        double probabilityOfGreedy = sc.nextDouble(); 

        Simulator simulator = new Simulator(baseSeed, numServers,
                selfcheckoutServers, maxqlength, numCustomers, arrivalRate,
                serviceRate, restingRate, probabilityOfRest, probabilityOfGreedy);

        simulator.startEvents();
        simulator.printStatistics();
        sc.close();
    }
}


