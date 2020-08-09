package cs2030.simulator;

/**
 * An event for when a customer has to wait to be served.
 */
class Waits extends Event {

    /**
     * Constructor that creates a 'Waits' event.
     * @param eventTime time that the event takes place 
     * @param customer customer who waits
     * @param server server that the customer is waiting for
     */
    public Waits(double eventTime, Customer customer, Server server) {
        this.eventTime = eventTime;
        this.customer = customer;
        this.server = server;
        this.state = States.WAITS;
    }

    /**
     * Method to print out type (typical or greedy) of customer who is waiting.
     */
    @Override
    public String toString() {
        String serverOut = server.toString();
        if (this.getCustomer().getType() == "normal") {
            return String.format("%.3f %d waits to be served by %s", 
                    this.eventTime, this.customer.getId(), serverOut);
        }  else {
            return String.format("%.3f %d(greedy) waits to be served by %s",
                    this.eventTime, this.customer.getId(), serverOut);
        }
    }
}


