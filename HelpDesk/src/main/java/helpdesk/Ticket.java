package helpdesk;

/**
 * One help desk ticket.
 *
 * Set when the ticket is created (by HelpDeskSystem.submitTicket):
 *   ticketID, category, description, priority, submissionOrder, submittedBy
 *   status always starts as Status.OPEN
 *
 * Set later by staff:
 *   assignedTo      (when staff processes it)
 *   status          (IN_PROGRESS, then CLOSED)
 *   resolutionNote  (required when closing)
 */
public class Ticket {

    private int ticketID;
    private String category;
    private String description;
    private Priority priority;
    private Status status;
    private int submissionOrder;   // tiebreaker: lower number = submitted earlier
    private User submittedBy;
    private User assignedTo;       // null until staff picks it up
    private String resolutionNote; // null until closed

    public Ticket(int ticketID, String category, String description,
                  Priority priority, int submissionOrder, User submittedBy) {
        // TODO: store each parameter in its field
        // TODO: set status to Status.OPEN
        // TODO: leave assignedTo and resolutionNote as null
    }

    // ---------- Getters ----------

    public int getTicketID() {
        // TODO
        return 0;
    }

    public String getCategory() {
        // TODO
        return null;
    }

    public String getDescription() {
        // TODO
        return null;
    }

    public Priority getPriority() {
        // TODO
        return null;
    }

    public Status getStatus() {
        // TODO
        return null;
    }

    public int getSubmissionOrder() {
        // TODO
        return 0;
    }

    public User getSubmittedBy() {
        // TODO
        return null;
    }

    public User getAssignedTo() {
        // TODO
        return null;
    }

    public String getResolutionNote() {
        // TODO
        return null;
    }

    // ---------- Setters (only for fields that change after creation) ----------

    public void setStatus(Status status) {
        // TODO
    }

    public void setAssignedTo(User assignedTo) {
        // TODO
    }

    public void setResolutionNote(String resolutionNote) {
        // TODO
    }

    @Override
    public String toString() {
        // TODO: return one readable line, for example:
        // "#3 [HIGH] Network - Wifi down in library | Status: OPEN | By: Sam"
        // Tip: if assignedTo is not null, add who it is assigned to.
        return "";
    }
}
