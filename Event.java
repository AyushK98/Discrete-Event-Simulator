package cs2030.simulator;

/**
 * Represents Events where Customers and Servers interact.
 */ 
public class Event {

    protected double eventTime; 
    protected States state; 
    protected Customer customer;
    protected Server server;

    public double getTime() {
        return this.eventTime;
    }

    public States getState() {
        return this.state;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Server getServer() {
        return this.server;
    }
}

