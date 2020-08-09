package cs2030.simulator;

/** 
 * An event for when a server wants to rest.
 */
public class ServerRest extends Event {

    /**
     * Constructor that creates a 'ServerRest' event.
     * @param eventTime time that server starts resting
     * @param server server who is resting
     */
    public ServerRest(double eventTime, Server server) {
        this.eventTime = eventTime;
        this.state = States.SERVER_AT_REST;
        this.server = server;
        server.setToResting(true);

    }

    /**
     * Method to print out the server who is resting.
     */
    @Override
    public String toString() {
        return String.format("%.3f server %d rest", this.eventTime, this.server.getId());
    } 
}
