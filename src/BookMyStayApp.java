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

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (Service s : getServices(reservationId)) {
            total += s.getCost();
        }
        return total;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Reservation reservation1 = new Reservation("Alice", "Single", "R001");
        Reservation reservation2 = new Reservation("Bob", "Suite", "R002");

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(reservation1.getRoomId(), new Service("Breakfast", 200));
        manager.addService(reservation1.getRoomId(), new Service("Airport Pickup", 500));
        manager.addService(reservation2.getRoomId(), new Service("Spa", 1000));

        System.out.println("Services for Reservation " + reservation1.getRoomId() + ":");
        for (Service s : manager.getServices(reservation1.getRoomId())) {
            System.out.println(s.getName() + " - " + s.getCost());
        }
        System.out.println("Total Additional Cost: " + manager.calculateTotalCost(reservation1.getRoomId()));

        System.out.println("Services for Reservation " + reservation2.getRoomId() + ":");
        for (Service s : manager.getServices(reservation2.getRoomId())) {
            System.out.println(s.getName() + " - " + s.getCost());
        }
        System.out.println("Total Additional Cost: " + manager.calculateTotalCost(reservation2.getRoomId()));
    }
}