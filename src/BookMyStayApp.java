import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
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

    public boolean isAvailable(String roomType) {
        return availability.getOrDefault(roomType, 0) > 0;
    }

    public void allocateRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
}

class BookingService {
    private Queue<Reservation> requestQueue;
    private Map<String, Set<String>> allocatedRooms;
    private Inventory inventory;

    public BookingService(Inventory inventory) {
        this.inventory = inventory;
        requestQueue = new LinkedList<>();
        allocatedRooms = new HashMap<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
    }

    public void processRequests() {
        while (!requestQueue.isEmpty()) {
            Reservation reservation = requestQueue.poll();
            if (inventory.isAvailable(reservation.getRoomType())) {
                String roomId = UUID.randomUUID().toString();
                reservation.setRoomId(roomId);
                allocatedRooms.putIfAbsent(reservation.getRoomType(), new HashSet<>());
                allocatedRooms.get(reservation.getRoomType()).add(roomId);
                inventory.allocateRoom(reservation.getRoomType());
                System.out.println("Reservation Confirmed: Guest " + reservation.getGuestName() + ", Room Type: " + reservation.getRoomType() + ", Room ID: " + reservation.getRoomId());
            } else {
                System.out.println("Reservation Failed: Guest " + reservation.getGuestName() + ", Room Type: " + reservation.getRoomType() + " not available.");
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        BookingService bookingService = new BookingService(inventory);

        bookingService.addRequest(new Reservation("Alice", "Single"));
        bookingService.addRequest(new Reservation("Bob", "Double"));
        bookingService.addRequest(new Reservation("Charlie", "Suite"));
        bookingService.addRequest(new Reservation("David", "Suite"));
        bookingService.addRequest(new Reservation("Eve", "Suite"));

        bookingService.processRequests();
    }
}