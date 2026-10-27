package helpdesk;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Console menus and user input ONLY.
 * Main does not hold tickets or do the logic itself; it calls HelpDeskSystem for that.
 *
 * Every number the user types goes through readInt(), which catches NumberFormatException.
 * Every call to HelpDeskSystem that can throw IllegalArgumentException is inside try/catch,
 * so bad input prints a message instead of crashing the program.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static HelpDeskSystem system = new HelpDeskSystem();

    public static void main(String[] args) {
        loadSampleUsers();

        System.out.println("=====================================");
        System.out.println("   University IT Help Desk");
        System.out.println("=====================================");

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("Sample users: 1 = Sam (student), 2 = Dr. Rivera (faculty), 3 = Jordan (staff), 4 = Casey (staff)");
            int id = readInt("Enter your user ID to log in (0 to quit): ");

            if (id == 0) {
                running = false;
            } else {
                User user = system.getUser(id);
                if (user == null) {
                    System.out.println("No user with that ID.");
                } else {
                    System.out.println("Welcome, " + user.getName() + "!");
                    if (user.isStaff()) {
                        staffMenu(user);
                    } else {
                        requesterMenu(user);
                    }
                }
            }
        }

        System.out.println("Goodbye!");
    }

    /**
     * Fake accounts so we can test without a real login system.
     */
    private static void loadSampleUsers() {
        system.addUser(new User(1, "Sam Carter", Role.REQUESTER, "scarter@roosevelt.edu"));
        system.addUser(new User(2, "Dr. Rivera", Role.REQUESTER, "rivera@roosevelt.edu"));
        system.addUser(new User(3, "Jordan Lee", Role.STAFF, "jlee@roosevelt.edu"));
        system.addUser(new User(4, "Casey Morgan", Role.STAFF, "cmorgan@roosevelt.edu"));
    }

    // ================= REQUESTER MENU =================

    private static void requesterMenu(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println();
            System.out.println("----- Requester Menu (" + user.getName() + ") -----");
            System.out.println("1. Submit a ticket");
            System.out.println("2. View my tickets");
            System.out.println("3. Search my tickets by ID");
            System.out.println("0. Logout");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    submitTicketFlow(user);
                    break;
                case 2:
                    printTickets(system.getTicketsByUser(user));
                    break;
                case 3:
                    int ticketID = readInt("Ticket ID: ");
                    Ticket ticket = system.searchByID(ticketID);
                    // Requesters can only see their own tickets
                    if (ticket == null || ticket.getSubmittedBy().getUserID() != user.getUserID()) {
                        System.out.println("You don't have a ticket with that ID.");
                    } else {
                        System.out.println(ticket);
                    }
                    break;
                case 0:
                    loggedIn = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void submitTicketFlow(User user) {
        System.out.print("Category (for example Network, Account, Hardware, Software): ");
        String category = scanner.nextLine();

        System.out.print("Describe the problem: ");
        String description = scanner.nextLine();

        Priority priority = readPriority();

        try {
            Ticket t = system.submitTicket(user, category, description, priority);
            System.out.println("Ticket #" + t.getTicketID() + " submitted. Status: " + t.getStatus());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= STAFF MENU =================

    private static void staffMenu(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println();
            System.out.println("----- Staff Menu (" + user.getName() + ") -----");
            System.out.println("Tickets waiting in queue: " + system.getOpenQueueSize());
            System.out.println("1. Process next ticket");
            System.out.println("2. Update ticket status");
            System.out.println("3. Close ticket");
            System.out.println("4. View all tickets");
            System.out.println("5. View tickets sorted by priority");
            System.out.println("6. Search by ticket ID");
            System.out.println("7. Search by category");
            System.out.println("8. View closed ticket history");
            System.out.println("0. Logout");
            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        Ticket next = system.processNextTicket(user);
                        if (next == null) {
                            System.out.println("Queue is empty. No tickets to process.");
                        } else {
                            System.out.println("Now working on: " + next);
                        }
                        break;
                    case 2:
                        int updateID = readInt("Ticket ID: ");
                        Status newStatus = readStatus();
                        system.updateStatus(user, updateID, newStatus);
                        System.out.println("Ticket #" + updateID + " updated to " + newStatus + ".");
                        break;
                    case 3:
                        int closeID = readInt("Ticket ID: ");
                        System.out.print("Resolution note: ");
                        String note = scanner.nextLine();
                        system.closeTicket(user, closeID, note);
                        System.out.println("Ticket #" + closeID + " closed.");
                        break;
                    case 4:
                        printTickets(system.getAllTickets());
                        break;
                    case 5:
                        printTickets(system.sortByPriority());
                        break;
                    case 6:
                        int searchID = readInt("Ticket ID: ");
                        Ticket found = system.searchByID(searchID);
                        if (found == null) {
                            System.out.println("No ticket with that ID.");
                        } else {
                            System.out.println(found);
                        }
                        break;
                    case 7:
                        System.out.print("Category: ");
                        String category = scanner.nextLine();
                        printTickets(system.searchByCategory(category));
                        break;
                    case 8:
                        printTickets(system.getClosedTickets());
                        break;
                    case 0:
                        loggedIn = false;
                        System.out.println("Logged out.");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ================= INPUT HELPERS =================

    /**
     * Reads a whole line and turns it into an int.
     * Keeps asking until the user types a real number.
     */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    /**
     * 1 -> HIGH, 2 -> MEDIUM, 3 -> LOW, anything else -> null (invalid).
     * HelpDeskSystem rejects null with an error message.
     */
    private static Priority readPriority() {
        int choice = readInt("Priority (1 = High, 2 = Medium, 3 = Low): ");
        switch (choice) {
            case 1:
                return Priority.HIGH;
            case 2:
                return Priority.MEDIUM;
            case 3:
                return Priority.LOW;
            default:
                return null;
        }
    }

    /**
     * 1 -> OPEN, 2 -> IN_PROGRESS, anything else -> null (invalid).
     * CLOSED is not offered here because closing goes through closeTicket.
     */
    private static Status readStatus() {
        int choice = readInt("New status (1 = Open, 2 = In Progress): ");
        switch (choice) {
            case 1:
                return Status.OPEN;
            case 2:
                return Status.IN_PROGRESS;
            default:
                return null;
        }
    }

    /**
     * Prints a list of tickets, or a message if the list is empty.
     */
    private static void printTickets(ArrayList<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
        } else {
            for (Ticket t : tickets) {
                System.out.println(t);
            }
        }
    }
}
