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
        // TODO: store each parameter in its matching field (use "this.")
    }

    // ---------- Getters ----------

    public int getUserID() {
        // TODO: return the userID field
        return 0;
    }

    public String getName() {
        // TODO: return the name field
        return null;
    }

    public Role getRole() {
        // TODO: return the role field
        return null;
    }

    public String getEmail() {
        // TODO: return the email field
        return null;
    }

    /**
     * Quick check used for permission rules.
     * Example: only staff can process or close tickets.
     */
    public boolean isStaff() {
        // TODO: return true if role is Role.STAFF, otherwise false
        return false;
    }

    @Override
    public String toString() {
        // TODO: return a readable line, for example: "#2 Jordan Lee (STAFF)"
        return "";
    }
}
