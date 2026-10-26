package helpdesk;

/**
 * How urgent a ticket is.
 * Each value carries a rank number. A LOWER rank means MORE urgent,
 * so HIGH (1) gets processed before MEDIUM (2), which comes before LOW (3).
 * The PriorityQueue comparator and the Selection Sort both use getRank().
 *
 * This file is complete. No TODOs here.
 */
public enum Priority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private int rank;

    Priority(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
