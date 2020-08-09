package cs2030.simulator;

/**
 * An event for when a customer leaves without being served.
 */
class Leaves extends Event {

    /**
     * Constructor that creates a 'Leaves' event.
     * @param eventTime time that the event takes place
     * @param customer customer who leaves
     */
    public Leaves(double eventTime, Customer customer) {
        this.eventTime = eventTime;
        this.customer = customer;
        this.state = States.LEAVES;
    }

    /**
     * Method to print out type (typical or greedy) of customer who leaves.
     */
    @Override
    public String toString() {
        if (this.getCustomer().getType() == "normal") {
            return String.format("%.3f %d leaves", this.eventTime, this.customer.getId());
        } else {
            return String.format("%.3f %d(greedy) leaves", this.eventTime, this.customer.getId());
        }
    }    
}

