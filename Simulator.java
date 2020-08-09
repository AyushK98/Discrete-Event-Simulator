package cs2030.simulator;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Discrete Event Simulator tasked with handling events.
 */
public class Simulator {

    private RandomGenerator generator;
    private int maxqlength;
    private double probabilityOfRest;
    private int numOfNormalServers;

    private double interArrivalTime = 0;
    private ArrayList<Server> listOfServers;
    private PriorityQueue<Event> listOfEvents;
    private double sumOfWaitingTime = 0.0; // Culminates time that all customers waited for
    private int leftWithoutServed = 0; // Number of customers who left without being served
    private int totalServed = 0; // Number of customers who were served


    /**
     * Constructor generates a list of servers and customers and a priority queue of events.
     * @param baseSeed for Random Generator
     * @param numServers total number of normal servers available
     * @param selfcheckoutServers total number of self-check out servers available
     * @param maxqlength maximum queue length for each normal server and first selfcheck out server
     * @param numCustomers total number of customers that will arrive
     * @param arrivalRate for Random Generator
     * @param serviceRate for Random Generator
     * @param restingRate for Random Generator
     * @param probabilityOfRest probability of a server needing to rest
     * @param probabilityOfGreedy probability of a customer being greedy
     */
    public Simulator(int baseSeed, int numServers, int selfcheckoutServers, int maxqlength, 
            int numCustomers, double arrivalRate, double serviceRate, double restingRate, 
            double probabilityOfRest, double probabilityOfGreedy) {

        // For random generation
        this.generator = new RandomGenerator(baseSeed, arrivalRate, serviceRate, restingRate);

        this.listOfServers = new ArrayList<>();
        this.maxqlength = maxqlength;
        this.probabilityOfRest = probabilityOfRest;
        this.numOfNormalServers = numServers;

        // Adding in human servers till k
        for (int i = 0; i < numServers; i++) {
            this.listOfServers.add(new HumanServer());
        }

        // Adding in self-check out servers from k + 1
        for (int i = 0; i < selfcheckoutServers; i++) {
            this.listOfServers.add(new SelfCheckOutServer());
        }

        // Creating a priority queue of events using EventComparator
        this.listOfEvents = new PriorityQueue<Event>(new EventComparator());

        // Creating the first arrival event
        if (generator.genCustomerType() >= probabilityOfGreedy) {
            this.listOfEvents.offer(new Arrives(0, new Customer(0, "normal")));
        } else {
            this.listOfEvents.offer(new Arrives(0, new Customer(0, "greedy")));
        }

        // Creating the rest of the arrival events in priority queue
        for (int i = 0; i < numCustomers - 1; i++) {
            interArrivalTime += generator.genInterArrivalTime();
            if (generator.genCustomerType() >= probabilityOfGreedy) {
                this.listOfEvents.offer(new Arrives(interArrivalTime, 
                            new Customer(interArrivalTime, "normal")));
            } else {
                this.listOfEvents.offer(new Arrives(interArrivalTime, 
                            new Customer(interArrivalTime, "greedy")));
            }
        }

        this.interArrivalTime = 0;
    }

    /**
     * Checks whether a customer can be served by any available server.
     * @param customer customer who want to be served
     * @return first server available
     */
    public Server whetherCanServe(Customer customer) {
        for (Server server : listOfServers) {
            if (server.canServe(customer)) {
                return server;
            }
        }
        return null;
    }

    /**
     * Checks which queue a typical or greedy customer should wait at.
     * @param customer customer who wants to be served
     * @return A typical customer will be added to the first server where queue has not exceeded 
     *         maximum length. A greedy customer will be added to the server with the shortest 
     *         queue.
     */
    public Server whichQueue(Customer customer) {
        if (customer.getType().equals("normal")) {
            for (Server server : listOfServers) {
                if (server.getQlength() < this.maxqlength && 
                        server.getId() <= numOfNormalServers + 1) {
                    server.addQ(customer);
                    return server;
                }
            }
        } else {
            Server minLength = listOfServers.get(0);
            for (Server server : listOfServers) {
                if (server.getQlength() < minLength.getQlength() && 
                        server.getId() <= numOfNormalServers + 1) {
                    minLength = server;
                }
            }
            if (minLength.getQlength() < this.maxqlength) {
                minLength.addQ(customer);
                return minLength;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Adds a new 'Served' event with the waiting customer.
     * @param event event for waiting customer who wants to be served.
     */
    public void getWaitingCustomer(Event event) {
        if (event.getServer().isHumanType()) {
            if (event.getServer().getQlength() > 0) {
                Customer next = event.getServer().popQ();
                this.sumOfWaitingTime += this.interArrivalTime - next.getArrivalTime();
                listOfEvents.add(new Served(event.getTime(), next, event.getServer()));
            }
        } else if (listOfServers.get(this.numOfNormalServers).getQlength() > 0) {
            Customer next = listOfServers.get(this.numOfNormalServers).popQ();
            this.sumOfWaitingTime += this.interArrivalTime - next.getArrivalTime();
            listOfEvents.add(new Served(event.getTime(), next, event.getServer()));
        }
    }           

    /**
     * Checks if a server is resting.
     * @param server server that is either resting or active.
     * @return true or false depending on whether the server is resting or not.
     */
    public boolean checkIfResting(Server server) {
        if (server.isHumanType()) {
            double random = generator.genRandomRest();
            if (random < this.probabilityOfRest) {
                return true;
            }
        }
        return false;
    }

    private Event getEvent() {
        return listOfEvents.poll();
    }

    /**
     * Method uses a list of servers to check whether a customer can be served or
     * not and then uses a Priority Queue to create and print a list of events which
     * allows the servers to serve multiple customers at a time and for every server
     * the next customers in line wait or leave.
     */
    public void startEvents() {
        
        double serviceTime;

        while (!listOfEvents.isEmpty()) {

            Event event = getEvent();

            // prints out the event if it is not a ServerRest or ServerBack event.
            if (!(event instanceof ServerRest) && !(event instanceof ServerBack)) {
                System.out.println(event);
            }   

            // Makes the server active. 
            if (event.getState() == States.SERVER_IS_BACK) {
                event.getServer().setToResting(false);
            }

            States eventState = event.getState();

            if (eventState == States.ARRIVES) {
                Server server = whetherCanServe(event.getCustomer());

                // Checks if there is a free server.
                if (server != null) {
                    this.interArrivalTime = event.getCustomer().getArrivalTime();
                    listOfEvents.add(new Served(interArrivalTime, event.getCustomer(), server));
                    continue;
                }

                // If there is no free server, looks for queue.
                server = whichQueue(event.getCustomer());
                if (server != null) {
                    this.interArrivalTime = event.getCustomer().getArrivalTime();
                    listOfEvents.add(new Waits(interArrivalTime, event.getCustomer(), server));
                    continue;
                }

                // If all queues have exceeded maximum length, customer leaves.
                this.leftWithoutServed++;
                listOfEvents.add(new Leaves(event.getCustomer().getArrivalTime(), 
                            event.getCustomer()));

            } else if (eventState == States.SERVED) {
                this.totalServed++;
                serviceTime = interArrivalTime;
                serviceTime += generator.genServiceTime();
                listOfEvents.add(new Done(serviceTime, event.getCustomer(),event.getServer()));
                event.getServer().setTime(serviceTime);

            } else if (eventState == States.DONE) {
                this.interArrivalTime = event.getTime();

                boolean serverRest = checkIfResting(event.getServer());
                if (serverRest) {
                    listOfEvents.add(new ServerRest(interArrivalTime, event.getServer()));
                    listOfEvents.add(new ServerBack(interArrivalTime + 
                                generator.genRestPeriod(), event.getServer()));
                    continue;
                } 
                getWaitingCustomer(event);

            } else if (eventState == States.SERVER_IS_BACK) {
                this.interArrivalTime = event.getTime();
                event.getServer().setTime(this.interArrivalTime);
                getWaitingCustomer(event);

            } else {
                continue;
            }

        }
    }

    /**
     * Method to print out statistics.    
     */
    public void printStatistics() {

        double averageWaitingTime = (this.sumOfWaitingTime == 0) ? 0 :
            this.sumOfWaitingTime / this.totalServed;

        String ans = String.format("[%.3f %d %d]", averageWaitingTime,
                this.totalServed, this.leftWithoutServed);

        System.out.println(ans);
    }
}


