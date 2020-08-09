package cs2030.simulator;

/**
 * An event for when customer has been served and is ready to leave.
 */
class Done extends Event {

    /**
     * Constructor that creates a 'Done' event.
     * @param eventTime time that the event takes place
     * @param customer customer who is ready to leave
     * @param server server who finished serving the customer 
     */
    public Done(double eventTime, Customer customer, Server server) {
        this.eventTime = eventTime;
        this.customer = customer;
        this.server = server;
        this.state = States.DONE;

    }

    /**
     * Method to print out type (typical or greedy) of customer who is done.
     */
    @Override
    public String toString() {
        String serverOut = server.toString();
        if (this.getCustomer().getType() == "normal") {
            return String.format("%.3f %d done serving by %s", this.eventTime,
                    this.customer.getId(), serverOut);
        } else {
            return String.format("%.3f %d(greedy) done serving by %s", this.eventTime,
                    this.customer.getId(), serverOut);
        }
    }
}

