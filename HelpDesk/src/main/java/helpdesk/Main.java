package helpdesk;

import java.util.Scanner;
hello
/**
 * Console menus and user input ONLY.
 * Main should not hold tickets or do the logic itself; it calls HelpDeskSystem for that.
 *
 * Every number the user types goes through readInt(), which catches NumberFormatException.
 * Every call to HelpDeskSystem that can throw IllegalArgumentException should be inside try/catch,
 * so bad input prints a message instead of crashing the program.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static HelpDeskSystem system = new HelpDeskSystem();

    public static void main(String[] args) {
        loadSampleUsers();

        // TODO: main loop
        //   print "Enter your user ID to log in (0 to quit):"
        //   read it with readInt()
        //   0 -> exit the loop
        //   look the user up with system.getUser(...)
        //   null -> print "No user with that ID" and loop again
        //   if user.isStaff() -> staffMenu(user), else -> requesterMenu(user)
        //   when that menu returns (logout), loop back to login
    }

    /**
     * Fake accounts so we can test without a real login system.
     * Add more if you want. Keep at least one REQUESTER and one STAFF.
     */
    private static void loadSampleUsers() {
        // TODO: system.addUser(new User(1, "...", Role.REQUESTER, "...@roosevelt.edu"));
        // TODO: add a second requester (faculty)
        // TODO: add at least one STAFF user
    }

    // ================= REQUESTER MENU =================

    private static void requesterMenu(User user) {
        // TODO: loop until the user picks Logout
        //   1. Submit a ticket       -> submitTicketFlow(user)
        //   2. View my tickets       -> print system.getTicketsByUser(user)
        //   3. Search ticket by ID   -> only show it if it belongs to this user
        //   0. Logout
    }

    private static void submitTicketFlow(User user) {
        // TODO: ask for category (String)
        // TODO: ask for description (String)
        // TODO: ask for priority: 1 = High, 2 = Medium, 3 = Low
        //       turn the number into a Priority with readPriority()
        // TODO: inside try/catch(IllegalArgumentException e):
        //       Ticket t = system.submitTicket(user, category, description, priority);
        //       print "Ticket #__ submitted"
        //       catch: print e.getMessage()
    }

    // ================= STAFF MENU =================

    private static void staffMenu(User user) {
        // TODO: loop until the user picks Logout
        //   1. Process next ticket       -> system.processNextTicket(user), print it or "Queue is empty"
        //   2. Update ticket status      -> ask for ID and new status, call system.updateStatus(...)
        //   3. Close ticket              -> ask for ID and resolution note, call system.closeTicket(...)
        //   4. View all tickets          -> system.getAllTickets()
        //   5. View sorted by priority   -> system.sortByPriority()
        //   6. Search by ID              -> system.searchByID(...)
        //   7. Search by category        -> system.searchByCategory(...)
        //   8. View closed history       -> system.getClosedTickets()
        //   0. Logout
        // Wrap the calls that can throw in try/catch(IllegalArgumentException e)
    }

    // ================= INPUT HELPERS =================

    /**
     * Reads a whole line and turns it into an int.
     * Keeps asking until the user types a real number.
     */
    private static int readInt(String prompt) {
        // TODO: loop forever:
        //   print prompt
        //   String line = scanner.nextLine();
        //   try: return Integer.parseInt(line.trim());
        //   catch (NumberFormatException e): print "Please enter a number."
        return 0;
    }

    /**
     * 1 -> HIGH, 2 -> MEDIUM, 3 -> LOW, anything else -> null (invalid).
     */
    private static Priority readPriority() {
        // TODO: int choice = readInt("Priority (1 = High, 2 = Medium, 3 = Low): ");
        // TODO: use if/else or a switch to return the matching Priority, or null if invalid
        return null;
    }

    /**
     * 1 -> OPEN, 2 -> IN_PROGRESS, anything else -> null (invalid).
     * CLOSED is not offered here because closing goes through closeTicket.
     */
    private static Status readStatus() {
        // TODO: same idea as readPriority
        return null;
    }

    /**
     * Prints a list of tickets, or a message if the list is empty.
     */
    private static void printTickets(java.util.ArrayList<Ticket> tickets) {
        // TODO: if empty, print "No tickets found."
        // TODO: otherwise loop and System.out.println(each ticket)
    }
}
