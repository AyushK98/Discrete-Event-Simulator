package cs2030.simulator;

/**
 * Represents customers who are queueing up for a service.
 */   
public class Customer {

    private int id;     
    private double arrivalTime;
    private static int numOfCust = 0; //Total number of customers 
    private String type; //Type of customers

    /**
     * A constructor that creates a customer instance. 
     * @param arrivalTime arrival time of specific customer.
     * @param type type of customer (typical or greedy).
     */ 
    public Customer(double arrivalTime, String type) {
        this.arrivalTime = arrivalTime;
        this.type = type;
        Customer.numOfCust++;
        this.id = numOfCust;
    }

    public int getId() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public String getType() {
        return this.type;
    }
}


