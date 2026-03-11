import java.util.*;

class Room {
    private String type;
    private double price;
    private int availability;

    public Room(String type, double price, int availability) {
        this.type = type;
        this.price = price;
        this.availability = availability;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailability() {
        return availability;
    }
}

class Inventory {
    private List<Room> rooms;

    public Inventory() {
        rooms = new ArrayList<>();
        rooms.add(new Room("Single", 1000, 5));
        rooms.add(new Room("Double", 2000, 0));
        rooms.add(new Room("Suite", 5000, 2));
    }

    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getAvailability() > 0) {
                available.add(room);
            }
        }
        return available;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        List<Room> availableRooms = inventory.getAvailableRooms();
        System.out.println("Available Rooms:");
        for (Room room : availableRooms) {
            System.out.println("Type: " + room.getType() + ", Price: " + room.getPrice() + ", Availability: " + room.getAvailability());
        }
    }
}