
package helpdesk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * The brain of the Help Desk application.
 * Stores users and tickets and handles the main system logic.
 */
public class HelpDeskSystem {

    private ArrayList<Ticket> allTickets;
    private HashMap<Integer, Ticket> ticketsByID;
    private PriorityQueue<Ticket> openQueue;
    private HashMap<Integer, User> users;

    private int nextTicketID;
    private int nextSubmissionOrder;

    // ================= CONSTRUCTOR =================

    public HelpDeskSystem() {

        allTickets = new ArrayList<>();
        ticketsByID = new HashMap<>();
        users = new HashMap<>();

        nextTicketID = 1;
        nextSubmissionOrder = 1;

        // HIGH first, then MEDIUM, then LOW.
        // Earlier submissions come first when priorities match.
        Comparator<Ticket> ticketComparator =
                Comparator.comparingInt(
                        (Ticket t) -> t.getPriority().ordinal())
                .thenComparingInt(Ticket::getSubmissionOrder);

        openQueue = new PriorityQueue<>(ticketComparator);
    }

    // ================= USERS =================

    public void addUser(User user) {

        users.put(user.getUserID(), user);
    }

    public User getUser(int userID) {

        return users.get(userID);
    }

    // ================= REQUESTER ACTIONS =================

    public Ticket submitTicket(User requester, String category,
                               String description, Priority priority) {

        if (requester == null) {
            throw new IllegalArgumentException(
                    "Requester cannot be null.");
        }

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ticket description cannot be empty.");
        }

        if (priority == null) {
            throw new IllegalArgumentException(
                    "Please select a valid priority.");
        }

        Ticket ticket = new Ticket(
                nextTicketID,
                category,
                description,
                priority,
                nextSubmissionOrder,
                requester
        );

        allTickets.add(ticket);

        ticketsByID.put(ticket.getTicketID(), ticket);

        openQueue.offer(ticket);

        nextTicketID++;
        nextSubmissionOrder++;

        return ticket;
    }

    public ArrayList<Ticket> getTicketsByUser(User requester) {

        ArrayList<Ticket> results = new ArrayList<>();

        if (requester == null) {
            return results;
        }

        for (Ticket ticket : allTickets) {

            if (ticket.getSubmittedBy().getUserID()
                    == requester.getUserID()) {

                results.add(ticket);
            }
        }

        return results;
    }

    // ================= SEARCHING =================

    public Ticket searchByID(int ticketID) {

        return ticketsByID.get(ticketID);
    }

    public ArrayList<Ticket> searchByCategory(String category) {

        ArrayList<Ticket> results = new ArrayList<>();

        if (category == null) {
            return results;
        }

        for (Ticket ticket : allTickets) {

            if (ticket.getCategory() != null
                    && ticket.getCategory().equalsIgnoreCase(category)) {

                results.add(ticket);
            }
        }

        return results;
    }

    // ================= SORTING =================

    public ArrayList<Ticket> sortByPriority() {

        ArrayList<Ticket> copy = new ArrayList<>(allTickets);

        // Selection Sort
        for (int i = 0; i < copy.size() - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < copy.size(); j++) {

                Ticket current = copy.get(j);
                Ticket minimum = copy.get(minIndex);

                int currentPriority = current.getPriority().ordinal();
                int minimumPriority = minimum.getPriority().ordinal();

                if (currentPriority < minimumPriority
                        || (currentPriority == minimumPriority
                        && current.getSubmissionOrder()
                        < minimum.getSubmissionOrder())) {

                    minIndex = j;
                }
            }

            Ticket temp = copy.get(i);
            copy.set(i, copy.get(minIndex));
            copy.set(minIndex, temp);
        }

        return copy;
    }

    // ================= STAFF ACTIONS =================

    public Ticket processNextTicket(User staff) {

        if (staff == null || !staff.isStaff()) {
            throw new IllegalArgumentException(
                    "Only staff can process tickets.");
        }

        Ticket next = openQueue.poll();

        // Skip tickets that are no longer OPEN.
        while (next != null && next.getStatus() != Status.OPEN) {
            next = openQueue.poll();
        }

        if (next == null) {
            return null;
        }

        next.setStatus(Status.IN_PROGRESS);
        next.setAssignedTo(staff);

        return next;
    }

    public void updateStatus(User staff, int ticketID,
                             Status newStatus) {

        if (staff == null || !staff.isStaff()) {
            throw new IllegalArgumentException(
                    "Only staff can update tickets.");
        }

        Ticket ticket = searchByID(ticketID);

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Ticket not found.");
        }

        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "Invalid ticket status.");
        }

        if (newStatus == Status.CLOSED) {
            throw new IllegalArgumentException(
                    "Use closeTicket() to close a ticket.");
        }

        Status oldStatus = ticket.getStatus();

        if (oldStatus == Status.CLOSED) {
            throw new IllegalArgumentException(
                    "Closed tickets cannot be updated.");
        }

        if (oldStatus == newStatus) {
            return;
        }

        if (newStatus == Status.IN_PROGRESS) {
            if (oldStatus != Status.OPEN) {
                throw new IllegalArgumentException(
                        "Ticket must be OPEN to start processing.");
            }

            // Remove this ticket from the waiting queue.
            openQueue.remove(ticket);
            ticket.setAssignedTo(staff);
        }

        if (newStatus == Status.OPEN) {
            ticket.setAssignedTo(null);
            openQueue.offer(ticket);
        }

        ticket.setStatus(newStatus);
    }

    public void closeTicket(User staff, int ticketID,
                            String resolutionNote) {

        if (staff == null || !staff.isStaff()) {
            throw new IllegalArgumentException(
                    "Only staff can close tickets.");
        }

        Ticket ticket = searchByID(ticketID);

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Ticket not found.");
        }

        if (ticket.getStatus() != Status.IN_PROGRESS) {
            throw new IllegalArgumentException(
                    "Ticket must be IN_PROGRESS before closing.");
        }

        if (resolutionNote == null
                || resolutionNote.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "A resolution note is required.");
        }

        ticket.setResolutionNote(resolutionNote.trim());
        ticket.setStatus(Status.CLOSED);
    }

    // ================= TICKET HISTORY =================

    public ArrayList<Ticket> getClosedTickets() {

        ArrayList<Ticket> results = new ArrayList<>();

        for (Ticket ticket : allTickets) {

            if (ticket.getStatus() == Status.CLOSED) {
                results.add(ticket);
            }
        }

        return results;
    }

    public ArrayList<Ticket> getAllTickets() {

        return new ArrayList<>(allTickets);
    }
}
