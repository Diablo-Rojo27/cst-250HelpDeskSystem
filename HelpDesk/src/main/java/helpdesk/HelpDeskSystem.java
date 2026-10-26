package helpdesk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * The "brain" of the app. Holds every ticket and user, and does all the work.
 * Main only handles menus and input; it calls these methods to get things done.
 *
 * Data structures and what each one is for:
 *   allTickets   ArrayList   every ticket ever made, in submission order (used for linear search and sorting)
 *   ticketsByID  HashMap     ticket ID -> ticket, for fast lookup by ID
 *   openQueue    PriorityQueue  only OPEN tickets; poll() always hands back the most urgent one
 *   users        HashMap     user ID -> user, for simulated login
 */
public class HelpDeskSystem {

    private ArrayList<Ticket> allTickets;
    private HashMap<Integer, Ticket> ticketsByID;
    private PriorityQueue<Ticket> openQueue;
    private HashMap<Integer, User> users;

    private int nextTicketID;        // start at 1, go up by 1 for each new ticket
    private int nextSubmissionOrder; // start at 1, go up by 1 for each new ticket

    public HelpDeskSystem() {
        // TODO: create the ArrayList and both HashMaps
        // TODO: set nextTicketID and nextSubmissionOrder to 1
        //
        // TODO: create the PriorityQueue WITH a comparator so Java does the ordering for us.
        //   Rule 1: compare priority rank (HIGH=1 comes first)
        //   Rule 2: if ranks tie, compare submissionOrder (lower = submitted first)
        //   Look up: Comparator.comparingInt(...) and .thenComparingInt(...)
        //   You will need: import java.util.Comparator;
    }

    // ================= USERS =================

    public void addUser(User user) {
        // TODO: put the user into the users HashMap, using their ID as the key
    }

    public User getUser(int userID) {
        // TODO: return the user with this ID, or null if there isn't one
        return null;
    }

    // ================= REQUESTER ACTIONS =================

    /**
     * Creates a new ticket and adds it to all three ticket structures.
     * Returns the new ticket so Main can print the ID back to the user.
     */
    public Ticket submitTicket(User requester, String category, String description, Priority priority) {
        // TODO: VALIDATION
        //   if description is null or blank (after trim), throw new IllegalArgumentException("...")
        //   if priority is null, throw new IllegalArgumentException("...")
        //
        // TODO: build a new Ticket using nextTicketID and nextSubmissionOrder
        // TODO: add it to allTickets
        // TODO: put it in ticketsByID
        // TODO: offer() it to openQueue   (do NOT sort anything, the queue places it)
        // TODO: increase nextTicketID and nextSubmissionOrder by 1
        // TODO: return the new ticket
        return null;
    }

    /**
     * Returns only the tickets this requester submitted.
     */
    public ArrayList<Ticket> getTicketsByUser(User requester) {
        // TODO: loop through allTickets, keep the ones where getSubmittedBy() matches this user
        return new ArrayList<>();
    }

    // ================= SEARCHING =================

    /**
     * Fast lookup using the HashMap.
     * Returns null if no ticket has that ID.
     */
    public Ticket searchByID(int ticketID) {
        // TODO: use ticketsByID.get(...)
        return null;
    }

    /**
     * LINEAR SEARCH: check every ticket one by one.
     * Case-insensitive, so "network", "Network", and "NETWORK" all match.
     */
    public ArrayList<Ticket> searchByCategory(String category) {
        // TODO: make an empty results list
        // TODO: loop through allTickets
        //       if the ticket's category equalsIgnoreCase(category), add it to results
        // TODO: return results
        return new ArrayList<>();
    }

    // ================= SORTING =================

    /**
     * SELECTION SORT by priority (HIGH first), tiebreak by submissionOrder.
     * Works on a COPY so the real allTickets order is never changed.
     * This is for DISPLAYING the list. The PriorityQueue is what actually picks the next ticket.
     */
    public ArrayList<Ticket> sortByPriority() {
        // TODO: make a copy:  ArrayList<Ticket> copy = new ArrayList<>(allTickets);
        //
        // TODO: Selection Sort on the copy
        //   outer loop i from 0 to size - 1
        //     assume position i holds the smallest (most urgent), save minIndex = i
        //     inner loop j from i + 1 to size
        //       if ticket at j is more urgent than ticket at minIndex, minIndex = j
        //       ("more urgent" = lower rank, or same rank and lower submissionOrder)
        //     swap the tickets at i and minIndex
        //
        // TODO: return the copy
        return new ArrayList<>();
    }

    // ================= STAFF ACTIONS =================

    /**
     * Takes the most urgent OPEN ticket off the queue and assigns it to this staff member.
     * Returns null if the queue is empty.
     */
    public Ticket processNextTicket(User staff) {
        // TODO: VALIDATION: if staff is not staff, throw new IllegalArgumentException("...")
        // TODO: Ticket next = openQueue.poll();
        // TODO: if next is null, return null (nothing to process)
        // TODO: set its status to IN_PROGRESS and assignedTo to staff
        // TODO: return it
        return null;
    }

    /**
     * Lets staff change a ticket's status.
     * Closing should go through closeTicket() instead, because closing needs a resolution note.
     */
    public void updateStatus(User staff, int ticketID, Status newStatus) {
        // TODO: VALIDATION
        //   staff must be staff
        //   ticket must exist (use searchByID), otherwise throw
        //   newStatus cannot be null
        //   if newStatus is CLOSED, throw and tell them to use closeTicket
        //
        // TODO: set the new status
        // TODO: IMPORTANT: if newStatus is OPEN, offer() the ticket back into openQueue
        //       or it will never get processed again
    }

    /**
     * Closes a ticket. A resolution note is REQUIRED.
     */
    public void closeTicket(User staff, int ticketID, String resolutionNote) {
        // TODO: VALIDATION
        //   staff must be staff
        //   ticket must exist
        //   ticket must be IN_PROGRESS (can't close something nobody worked on)
        //   resolutionNote cannot be null or blank
        //
        // TODO: set resolutionNote and set status to CLOSED
    }

    /**
     * Returns every ticket with status CLOSED (history view).
     */
    public ArrayList<Ticket> getClosedTickets() {
        // TODO: loop allTickets, keep the CLOSED ones
        return new ArrayList<>();
    }

    /**
     * Returns every ticket (staff "view all").
     */
    public ArrayList<Ticket> getAllTickets() {
        // TODO: return allTickets
        return new ArrayList<>();
    }
}
