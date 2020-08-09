package cs2030.simulator;

import java.util.ArrayList;

/** 
 *  Models the self-check out server.
 */

class SelfCheckOutServer extends Server {

    /**
     * A constructor that creates a self check out server instance. 
     */ 
    public SelfCheckOutServer() {
        this.whenAvailable = 0;
        Server.numOfServers++;
        this.id = Server.numOfServers;
        this.currentlyServing = false;
        this.customers = new ArrayList<>();
    }

    /**
     * Checks whether the server can serve the customer or not.  Returns true if the customer's 
     * arrival time is greater than or equal to the server's time of availability and thus customer 
     * can be served instantly. Otherwise, method returns false because at that time server is not 
     * free and customer has to either wait for server to be free or leave.
     * @param customer Instance of Customer class who wishes to be served.
     * @return True or False depending on whether the server is free to serve customer or not.     
     */ 
    public boolean canServe(Customer customer) {
        if (customer.getArrivalTime() >= this.whenAvailable) {
            this.currentlyServing = true;
            return true;
        }
        return false;
    }

    /**
     * This method checks if server is normal or not.
     * @return False because server is self check out.
     */
    public boolean isHumanType() {
        return false;
    }

    public void setToResting(boolean value) {
        // Self Check out servers never rest
    }

    @Override
    public String toString() {
        return String.format("self-check %d", this.id);
    }
}

