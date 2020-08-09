package cs2030.simulator;

import java.util.Comparator;

/**
 * Implements a Comparator to override its compare method in order to sort multiple events.   
 */ 
public class EventComparator implements Comparator<Event> {

    /**
     * Takes in two events at a time to be sorted in ascending order using the
     * events' customers' arrival times and/or IDs. If the arrival time of the customer of the 
     * first event is lesser than the arrival time of the customer of the second event in queue, 
     * method returns -1 and first argument has priority.  If the opposite occurs, method returns 1 
     * and first event loses priority to second event, which has a lower value of arrival time. 
     * If the arrival times are equal, same process takes place with IDs. As above, the customer
     * with the lower attribute (ID number) has the priority. If both arrival times and both IDs
     * are equal, method returns 0 to display that there is no way of prioritising the events. 
     * @param event1 contains customer's ID and arrival time.
     * @param event2 contains customer's ID and arrival time.
     * @return A negative integer, zero, or a positive integer as the first argument is less
     *         than, equal to, or greater than the second. 
     */
    @Override
    public int compare(Event event1, Event event2) {
        if (event1.getTime() < event2.getTime()) { 
            return -1;
        } else if (event1.getTime() > event2.getTime()) { 
            return 1;
        } else {
            if (event1.getCustomer().getId() < event2.getCustomer().getId()) {
                return -1;
            } else if (event1.getCustomer().getId() > event2.getCustomer().getId()) {
                return 1;
            } else {
                return 0;
            }
        }

    }
}



