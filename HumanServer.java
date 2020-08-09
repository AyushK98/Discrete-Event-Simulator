package cs2030.simulator;

import java.util.ArrayList;

/** 
 * Models the normal server.
 */
class HumanServer extends Server {

    private boolean isResting;

    /**
     * A constructor that creates a normal server instance. 
     */ 
    public HumanServer() {
        this.whenAvailable = 0;
        Server.numOfServers++;
        this.id = Server.numOfServers;
        this.currentlyServing = false;
        this.customers = new ArrayList<>();
        this.isResting = false;
    }

    /**
     * Checks whether the server can serve the customer or not.  Returns true if the customer's 
     * arrival time is greater than or equal to the server's time of availability and 
     * server is not resting and thus customer can be served instantly. Otherwise, method returns
     * false because at that time server is not free and customer has to either wait for server 
     * to be free or leave.
     * @param customer Instance of Customer class who wishes to be served.
     * @return True or False depending on whether the server is free to serve customer or not.     
     */ 
    public boolean canServe(Customer customer) {
        if (customer.getArrivalTime() >= this.whenAvailable && !this.isResting) {
            this.currentlyServing = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if server is resting or not.
     * @return True of False depending on whether server is resting or not.
     */ 
    public boolean get_isResting() {
        return this.isResting;
    }

    /**
     * This method sets server to rest or active.
     * @param status Status to set the server to rest or active.
     */  
    public void setToResting(boolean status) {
        this.isResting = status;
    }

    /**
     * This method checks if server is normal or not.
     * @return True because server is normal.
     */
    public boolean isHumanType() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("server %d", this.id);
    }
} 

