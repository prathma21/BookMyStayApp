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

    public void restore(String type) {
        availability.put(type, availability.get(type) + 1);
    }

    public int getAvailability(String type) {
        return availability.get(type);
    }
}

class Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;

    public Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }

    public String getRoomType() { return roomType; }
    public String getReservationId() { return reservationId; }
}

class CancellationService {
    private Stack<String> releasedRoomIds = new Stack<>();
    private Map<String, String> reservationRoomTypeMap = new HashMap<>();

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {
        String roomType = reservationRoomTypeMap.get(reservationId);
        if (roomType != null) {
            inventory.restore(roomType);
            releasedRoomIds.push(reservationId);
            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        }
    }

    public void showRollbackHistory() {
        System.out.println();
        System.out.println("Rollback History (Most Recent First):");
        while (!releasedRoomIds.isEmpty()) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.pop());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        String reservationId = "Single-1";
        String roomType = "Single";

        inventory.reduce(roomType);
        cancellationService.registerBooking(reservationId, roomType);

        cancellationService.cancelBooking(reservationId, inventory);
        cancellationService.showRollbackHistory();

        System.out.println();
        System.out.println("Updated Single Room Availability: " + inventory.getAvailability("Single"));
    }
}