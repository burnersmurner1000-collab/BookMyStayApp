import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        this.active = false;
    }
}

class Inventory {
    private Map<String, Integer> availability;

    public Inventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public void allocateRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }

    public void restoreRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) + 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getHistory() {
        return new ArrayList<>(history);
    }
}

class CancellationService {
    private Inventory inventory;
    private BookingHistory bookingHistory;
    private Stack<String> rollbackStack;

    public CancellationService(Inventory inventory, BookingHistory bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
        rollbackStack = new Stack<>();
    }

    public void cancelReservation(String roomId) {
        for (Reservation r : bookingHistory.getHistory()) {
            if (r.getRoomId().equals(roomId) && r.isActive()) {
                r.cancel();
                rollbackStack.push(roomId);
                inventory.restoreRoom(r.getRoomType());
                System.out.println("Cancellation Confirmed: Guest " + r.getGuestName() + ", Room Type: " + r.getRoomType() + ", Room ID: " + r.getRoomId());
                return;
            }
        }
        System.out.println("Cancellation Failed: Reservation with Room ID " + roomId + " not found or already cancelled.");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("Alice", "Single", "R001");
        Reservation r2 = new Reservation("Bob", "Suite", "R002");
        Reservation r3 = new Reservation("Charlie", "Double", "R003");

        inventory.allocateRoom(r1.getRoomType());
        inventory.allocateRoom(r2.getRoomType());
        inventory.allocateRoom(r3.getRoomType());

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        CancellationService cancellationService = new CancellationService(inventory, history);

        cancellationService.cancelReservation("R002");
        cancellationService.cancelReservation("R004");

        System.out.println("Availability after cancellations:");
        System.out.println("Single: " + inventory.getAvailability("Single"));
        System.out.println("Double: " + inventory.getAvailability("Double"));
        System.out.println("Suite: " + inventory.getAvailability("Suite"));
    }
}