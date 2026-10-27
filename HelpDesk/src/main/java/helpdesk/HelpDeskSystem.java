package helpdesk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * The "brain" of the app. Holds every ticket and user, and does all the work.
 * Main only handles menus and input; it calls these methods to get things done.
 *
 * Data structures and what each one is for:
 *   allTickets   ArrayList      every ticket ever made, in submission order (used for linear search and sorting)
 *   ticketsByID  HashMap        ticket ID -> ticket, for fast lookup by ID
 *   openQueue    PriorityQueue  only OPEN tickets; poll() always hands back the most urgent one
 *   users        HashMap        user ID -> user, for simulated login
 *
 * Any rule that gets broken (blank description, unknown ID, non-staff doing staff work)
 * throws an IllegalArgumentException with a message. Main catches it and prints the message.
 */
public class HelpDeskSystem {

    private ArrayList<Ticket> allTickets;
    private HashMap<Integer, Ticket> ticketsByID;
    private PriorityQueue<Ticket> openQueue;
    private HashMap<Integer, User> users;

    private int nextTicketID;        // starts at 1, goes up by 1 for each new ticket
    private int nextSubmissionOrder; // starts at 1, goes up by 1 for each new ticket

    public HelpDeskSystem() {
        allTickets = new ArrayList<>();
        ticketsByID = new HashMap<>();
        users = new HashMap<>();

        nextTicketID = 1;
        nextSubmissionOrder = 1;

        // The comparator tells the PriorityQueue how to order tickets, so Java does the ordering for us.
        //   Rule 1: lower priority rank comes first (HIGH = 1, MEDIUM = 2, LOW = 3)
        //   Rule 2: if the ranks tie, lower submissionOrder comes first (first come, first served)
        Comparator<Ticket> urgencyOrder =
                Comparator.comparingInt((Ticket t) -> t.getPriority().getRank())
                          .thenComparingInt(t -> t.getSubmissionOrder());

        openQueue = new PriorityQueue<>(urgencyOrder);
    }

    // ================= USERS =================

    public void addUser(User user) {
        users.put(user.getUserID(), user);
    }

    public User getUser(int userID) {
        // HashMap.get returns null if no user has that ID
        return users.get(userID);
    }

    // ================= REQUESTER ACTIONS =================

    /**
     * Creates a new ticket and adds it to all three ticket structures.
     * Returns the new ticket so Main can print the ID back to the user.
     */
    public Ticket submitTicket(User requester, String category, String description, Priority priority) {
        // ----- Validation -----
        if (requester == null) {
            throw new IllegalArgumentException("You must be logged in to submit a ticket.");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (priority == null) {
            throw new IllegalArgumentException("Invalid priority. Choose High, Medium, or Low.");
        }

        // ----- Build the ticket -----
        Ticket ticket = new
