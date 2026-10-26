# University IT Help Desk

CST 250, Group 2: Anthony, Reese, Henry, Audelina, Luke

Console Java app where students and faculty submit IT tickets and help desk staff work them in priority order (High, then Medium, then Low; first come first served on ties).

## How to open it in NetBeans

1. Clone this repo (Team > Git > Clone, or GitHub Desktop).
2. File > Open Project, pick the HelpDesk folder. NetBeans opens it as a Maven project.
3. Run with the green Run button. Main class is helpdesk.Main.

## Files

| File | What it does | Status |
| --- | --- | --- |
| Role.java | REQUESTER or STAFF | Done |
| Priority.java | HIGH(1), MEDIUM(2), LOW(3) | Done |
| Status.java | OPEN, IN_PROGRESS, CLOSED | Done |
| User.java | A person who logs in | TODO |
| Ticket.java | One help desk ticket | TODO |
| HelpDeskSystem.java | All the data structures and logic | TODO |
| Main.java | Menus and input only | TODO |

## Who is building what

| Person | Part |
| --- | --- |
| Anthony | |
| Reese | |
| Henry | |
| Audelina | |
| Luke | |

## Rules for working together

- Pull before you start working, every time.
- Only edit the file(s) you own unless the group agrees.
- Make sure the project still builds before you push.
- Small commits with clear messages, like "Ticket: constructor and getters".

## Suggested build order

1. User and Ticket (constructors, getters, setters, toString)
2. HelpDeskSystem constructor and submitTicket
3. processNextTicket and closeTicket
4. searchByID, searchByCategory, sortByPriority
5. Main menus
