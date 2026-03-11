import java.util.*;

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

    public synchronized boolean allocateRoom(String roomType) {
        int count = availability.getOrDefault(roomType, 0);
        if (count > 0) {
            availability.put(roomType, count - 1);
            return true;
        }
        return false;
    }

    public synchronized int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

class BookingProcessor implements Runnable {
    private Inventory inventory;
    private Reservation reservation;

    public BookingProcessor(Inventory inventory, Reservation reservation) {
        this.inventory = inventory;
        this.reservation = reservation;
    }

    @Override
    public void run() {
        synchronized (inventory) {
            if (inventory.allocateRoom(reservation.getRoomType())) {
                System.out.println("Reservation Confirmed: Guest " + reservation.getGuestName() +
                        ", Room Type: " + reservation.getRoomType() +
                        ", Room ID: " + reservation.getRoomId());
            } else {
                System.out.println("Reservation Failed: Guest " + reservation.getGuestName() +
                        ", Room Type: " + reservation.getRoomType() + " not available.");
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        List<Reservation> reservations = Arrays.asList(
                new Reservation("Alice", "Single", "R001"),
                new Reservation("Bob", "Single", "R002"),
                new Reservation("Charlie", "Suite", "R003"),
                new Reservation("David", "Suite", "R004"),
                new Reservation("Eve", "Suite", "R005"),
                new Reservation("Frank", "Double", "R006"),
                new Reservation("Grace", "Double", "R007"),
                new Reservation("Hank", "Double", "R008"),
                new Reservation("Ivy", "Double", "R009")
        );

        List<Thread> threads = new ArrayList<>();
        for (Reservation r : reservations) {
            Thread t = new Thread(new BookingProcessor(inventory, r));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }

        System.out.println("Final Availability:");
        System.out.println("Single: " + inventory.getAvailability("Single"));
        System.out.println("Double: " + inventory.getAvailability("Double"));
        System.out.println("Suite: " + inventory.getAvailability("Suite"));
    }
}