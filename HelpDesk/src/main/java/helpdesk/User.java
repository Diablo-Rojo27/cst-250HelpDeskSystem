package helpdesk;

/**
 * A person who logs into the help desk.
 * Can be a REQUESTER (student or faculty) or STAFF.
 */
public class User {

    private int userID;
    private String name;
    private Role role;
    private String email;

    public User(int userID, String name, Role role, String email) {
        this.userID = userID;
        this.name = name;
        this.role = role;
        this.email = email;
    }

    // ---------- Getters ----------

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Quick check used for permission rules.
     * Example: only staff can process or close tickets.
     */
    public boolean isStaff() {
        return role == Role.STAFF;
    }

    @Override
    public String toString() {
        // Example: "#2 Jordan Lee (STAFF)"
        return "#" + userID + " " + name + " (" + role + ")";
    }
}
