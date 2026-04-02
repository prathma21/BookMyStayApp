import java.util.*;

abstract class Room {
    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000);
    }
}

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public boolean hasAvailability(String type) {
        return availability.getOrDefault(type, 0) > 0;
    }

    public void reduce(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

class Reservation {
    private String guestName;
    private String roomType;

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
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void add(Reservation r) {
        queue.offer(r);
    }

    public Reservation next() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class RoomAllocationService {
    public boolean allocate(Reservation r, RoomInventory inventory) {
        if (inventory.hasAvailability(r.getRoomType())) {
            inventory.reduce(r.getRoomType());
            return true;
        }
        return false;
    }
}

class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

class BookingReportService {
    public void generateReport(BookingHistory history) {
        System.out.println();
        System.out.println("Booking History Report");
        for (Reservation r : history.getConfirmedReservations()) {
            System.out.println(
                    "Guest: " + r.getGuestName() +
                            ", Room Type: " + r.getRoomType()
            );
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocator = new RoomAllocationService();
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        queue.add(new Reservation("Abhi", "Single"));
        queue.add(new Reservation("Subha", "Double"));
        queue.add(new Reservation("Vanmathi", "Suite"));

        while (queue.hasRequests()) {
            Reservation r = queue.next();
            if (allocator.allocate(r, inventory)) {
                history.addReservation(r);
            }
        }

        reportService.generateReport(history);
    }
}