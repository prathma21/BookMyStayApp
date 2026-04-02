import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class ReservationValidator {
    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty())
            throw new InvalidBookingException("Guest name cannot be empty.");

        if (!roomType.equals("Single") &&
                !roomType.equals("Double") &&
                !roomType.equals("Suite"))
            throw new InvalidBookingException("Invalid room type selected.");

        if (!inventory.hasAvailability(roomType))
            throw new InvalidBookingException("No rooms available for selected type.");
    }
}

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
    public SingleRoom() { super(1, 250, 1500); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 400, 2500); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(3, 750, 5000); }
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
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();
    public void add(Reservation r) { queue.offer(r); }
    public Reservation next() { return queue.poll(); }
    public boolean hasRequests() { return !queue.isEmpty(); }
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

public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocator = new RoomAllocationService();

        try {
            System.out.print("Enter guest name: ");
            String guest = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String type = scanner.nextLine();

            validator.validate(guest, type, inventory);

            Reservation r = new Reservation(guest, type);
            queue.add(r);

            while (queue.hasRequests()) {
                Reservation next = queue.next();
                allocator.allocate(next, inventory);
            }

            System.out.println("Booking successful.");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}