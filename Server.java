package cs2030.simulator;

import java.util.ArrayList;

/**
 * Represents a system that provides a service to the customers.
 * This abstract class provides the foundation for normal and self check out servers.
 */ 
abstract class Server {

    protected double whenAvailable;
    protected int id;
    protected boolean currentlyServing;
    protected ArrayList<Customer> customers;
    protected static int numOfServers = 0;


    public double getWhenAvailable() {
        return whenAvailable;
    }

    public int getId() {
        return this.id;
    }

    public int getQlength() {
        return this.customers.size();
    }

    public void setCurrentlyServing(boolean value) {
        this.currentlyServing = value;
    }

    public void setTime(double time) {
        this.whenAvailable = time;
    }

    /** 
     * This method adds a customer to the queue for a particular server.
     * @param customer Customer who is added to the queue.
     */
    public void addQ(Customer customer) {
        this.customers.add(customer);
    }

    /** 
     * This method removes the next in line customer from the queue.
     * @return Customer that is removed.
     */
    public Customer popQ() {
        return this.customers.remove(0);
    }

    /** 
     * This abstract method checks if the server is normal(human) or not.
     * @return true of false depending on whether server is normal or not.
     */
    public abstract boolean isHumanType();

    /**
     * Checks whether the server can serve the customer or not.
     * @param customer customer to be served.
     * @return true of false depending on whether customer can be served.
     */
    public abstract boolean canServe(Customer customer);

    /**
     *  This abstract method sets the server to rest or active.
     */
    public abstract void setToResting(boolean value);

}

