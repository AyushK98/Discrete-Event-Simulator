package cs2030.simulator;

/**
 * An event to characterise a customer arriving.
 */
class Arrives extends Event {

    /**
     * Constructor that creates an 'Arrives' event.
     * @param eventTime time of arrival of customer
     * @param customer customer who arrives
     */
    public Arrives(double eventTime, Customer customer) {
        this.eventTime = eventTime;
        this.customer = customer;
        this.state = States.ARRIVES;
    }

    /**
     * Method to print out type (typical or greedy) of customer that has arrived.
     */
    @Override
    public String toString() {
        if (this.getCustomer().getType() == "normal") {
            return String.format("%.3f %d arrives", this.eventTime, this.customer.getId());
        } else {
            return String.format("%.3f %d(greedy) arrives", this.eventTime, this.customer.getId());
        }
    }
}

