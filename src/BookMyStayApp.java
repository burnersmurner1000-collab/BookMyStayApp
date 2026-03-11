abstract class Room {
    private String name;
    private int beds;
    private double price;

    public Room(String name, int beds, double price) {
        this.name = name;
        this.beds = beds;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 1800.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 3500.0);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("Room: " + single.getName() + ", Beds: " + single.getBeds() + ", Price: " + single.getPrice() + ", Available: " + singleAvailability);
        System.out.println("Room: " + doubleRoom.getName() + ", Beds: " + doubleRoom.getBeds() + ", Price: " + doubleRoom.getPrice() + ", Available: " + doubleAvailability);
        System.out.println("Room: " + suite.getName() + ", Beds: " + suite.getBeds() + ", Price: " + suite.getPrice() + ", Available: " + suiteAvailability);
    }
}