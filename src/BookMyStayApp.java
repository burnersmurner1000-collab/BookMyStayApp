import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
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

class Inventory {
    private Map<String, Integer> availability;

    public Inventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public boolean isValidRoomType(String roomType) {
        return availability.containsKey(roomType);
    }

    public boolean isAvailable(String roomType) {
        return availability.getOrDefault(roomType, 0) > 0;
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {
        if (!isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        if (!isAvailable(roomType)) {
            throw new InvalidBookingException("Room type " + roomType + " is not available.");
        }
        int current = availability.get(roomType);
        if (current <= 0) {
            throw new InvalidBookingException("Cannot allocate room. Inventory invalid for " + roomType);
        }
        availability.put(roomType, current - 1);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        try {
            Reservation r1 = new Reservation("Alice", "Single", "R001");
            inventory.allocateRoom(r1.getRoomType());
            System.out.println("Reservation Confirmed: Guest " + r1.getGuestName() + ", Room Type: " + r1.getRoomType() + ", Room ID: " + r1.getRoomId());

            Reservation r2 = new Reservation("Bob", "Penthouse", "R002");
            inventory.allocateRoom(r2.getRoomType());
            System.out.println("Reservation Confirmed: Guest " + r2.getGuestName() + ", Room Type: " + r2.getRoomType() + ", Room ID: " + r2.getRoomId());
        } catch (InvalidBookingException e) {
            System.out.println("Reservation Failed: " + e.getMessage());
        }

        try {
            Reservation r3 = new Reservation("Charlie", "Suite", "R003");
            inventory.allocateRoom(r3.getRoomType());
            System.out.println("Reservation Confirmed: Guest " + r3.getGuestName() + ", Room Type: " + r3.getRoomType() + ", Room ID: " + r3.getRoomId());

            Reservation r4 = new Reservation("David", "Suite", "R004");
            inventory.allocateRoom(r4.getRoomType());
            System.out.println("Reservation Confirmed: Guest " + r4.getGuestName() + ", Room Type: " + r4.getRoomType() + ", Room ID: " + r4.getRoomId());

            Reservation r5 = new Reservation("Eve", "Suite", "R005");
            inventory.allocateRoom(r5.getRoomType());
            System.out.println("Reservation Confirmed: Guest " + r5.getGuestName() + ", Room Type: " + r5.getRoomType() + ", Room ID: " + r5.getRoomId());
        } catch (InvalidBookingException e) {
            System.out.println("Reservation Failed: " + e.getMessage());
        }
    }
}