import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
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
}

class Inventory implements Serializable {
    private Map<String, Integer> availability;

    public Inventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public boolean allocateRoom(String roomType) {
        int count = availability.getOrDefault(roomType, 0);
        if (count > 0) {
            availability.put(roomType, count - 1);
            return true;
        }
        return false;
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAvailabilityMap() {
        return availability;
    }
}

class BookingHistory implements Serializable {
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

class PersistenceService {
    public void saveState(Inventory inventory, BookingHistory history, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(inventory);
            out.writeObject(history);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public Object[] loadState(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Inventory inventory = (Inventory) in.readObject();
            BookingHistory history = (BookingHistory) in.readObject();
            System.out.println("System state loaded successfully.");
            return new Object[]{inventory, history};
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state: " + e.getMessage());
            return new Object[]{new Inventory(), new BookingHistory()};
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        String filename = "system_state.dat";
        PersistenceService persistenceService = new PersistenceService();

        Object[] state = persistenceService.loadState(filename);
        Inventory inventory = (Inventory) state[0];
        BookingHistory history = (BookingHistory) state[1];

        Reservation r1 = new Reservation("Alice", "Single", "R001");
        if (inventory.allocateRoom(r1.getRoomType())) {
            history.addReservation(r1);
        }

        Reservation r2 = new Reservation("Bob", "Suite", "R002");
        if (inventory.allocateRoom(r2.getRoomType())) {
            history.addReservation(r2);
        }

        System.out.println("Current Booking History:");
        for (Reservation r : history.getHistory()) {
            System.out.println("Guest: " + r.getGuestName() + ", Room Type: " + r.getRoomType() + ", Room ID: " + r.getRoomId());
        }

        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getAvailabilityMap().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        persistenceService.saveState(inventory, history, filename);
    }
}