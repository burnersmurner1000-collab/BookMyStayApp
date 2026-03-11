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

class BookingReportService {
    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public void generateReport() {
        System.out.println("Booking History Report:");
        for (Reservation r : bookingHistory.getHistory()) {
            System.out.println("Guest: " + r.getGuestName() + ", Room Type: " + r.getRoomType() + ", Room ID: " + r.getRoomId());
        }
        System.out.println("Total Reservations: " + bookingHistory.getHistory().size());
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("Alice", "Single", "R001");
        Reservation r2 = new Reservation("Bob", "Double", "R002");
        Reservation r3 = new Reservation("Charlie", "Suite", "R003");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        BookingReportService reportService = new BookingReportService(history);
        reportService.generateReport();
    }
}