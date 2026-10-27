
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
    private int submissionOrder;
    private User submittedBy;
    private User assignedTo;
    private String resolutionNote;

    // ---------- Constructor ----------

    public Ticket(int ticketID, String category, String description,
                  Priority priority, int submissionOrder, User submittedBy) {

        this.ticketID = ticketID;
        this.category = category;
        this.description = description;
        this.priority = priority;
        this.submissionOrder = submissionOrder;
        this.submittedBy = submittedBy;

        this.status = Status.OPEN;
        this.assignedTo = null;
        this.resolutionNote = null;
    }

    // ---------- Getters ----------

    public int getTicketID() {
        return ticketID;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public int getSubmissionOrder() {
        return submissionOrder;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public String getResolutionNote() {
        return resolutionNote;
    }

    // ---------- Setters ----------

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setResolutionNote(String resolutionNote) {
        this.resolutionNote = resolutionNote;
    }

    // ---------- toString ----------

    @Override
    public String toString() {

        String result = "#" + ticketID
                + " [" + priority + "] "
                + category + " - " + description
                + " | Status: " + status
                + " | By: " + submittedBy.getName();

        if (assignedTo != null) {
            result += " | Assigned to: " + assignedTo.getName();
        }

        return result;
    }
}
