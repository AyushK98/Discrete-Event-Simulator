package cs2030.simulator;

/**
 * An event for when a customer is served. 
 */
class Served extends Event {

    /**
     * Constructor that creates a 'Served' event.
     * @param eventTime time that the event takes place 
     * @param customer customer who arrives
     * @param server server who serves the customer 
     */
    public Served(double eventTime, Customer customer, Server server) {
        this.eventTime = eventTime;
        this.customer = customer;
        this.server = server;
        this.state = States.SERVED;
    }

    /**
     * Method to print out type (typical or greedy) of customer who is being served.
     */
    @Override
    public String toString() {
        String serverOut = server.toString();
        if (this.getCustomer().getType() == "normal") {
            return String.format("%.3f %d served by %s", this.eventTime,
                    this.customer.getId(), serverOut);
        } else {
            return String.format("%.3f %d(greedy) served by %s", this.eventTime,
                    this.customer.getId(), serverOut);
        }
    }

}

