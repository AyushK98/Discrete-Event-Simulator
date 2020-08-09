package cs2030.simulator;

/*
 * An event for when a server comes back from a break.
 */ 
class ServerBack extends Event {

    /**
     * Constructor that creates a 'ServedBack' event.
     * @param eventTime time when server comes back.
     * @param server server that returns from break.
     */
    public ServerBack(double eventTime, Server server) {
        this.eventTime = eventTime;
        this.state = States.SERVER_IS_BACK;
        this.server = server;
    }

    /**
     * Method to print out the server who comes back from break.
     */
    @Override
    public String toString() {
        return String.format("%.3f server %d back", this.eventTime, this.server.getId());
    } 
}



