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
    private final Map<String, Integer> availability = new HashMap<>();

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

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public Set<String> getTypes() {
        return availability.keySet();
    }
}

class Reservation {
    private final String guestName;
    private final String roomType;

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
    private final Queue<Reservation> queue = new LinkedList<>();

    public void add(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation poll() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomAllocationService {
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();
        if (inventory.hasAvailability(type)) {
            inventory.reduce(type);
            System.out.println(
                    "Booking confirmed for Guest: " +
                            reservation.getGuestName() +
                            ", Room ID: " + type + "-" + UUID.randomUUID()
            );
        } else {
            System.out.println(
                    "Booking failed for Guest: " +
                            reservation.getGuestName() +
                            ", Room Type: " + type
            );
        }
    }
}

class ConcurrentBookingProcessor implements Runnable {
    private final BookingRequestQueue bookingQueue;
    private final RoomInventory inventory;
    private final RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation;
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) break;
                reservation = bookingQueue.poll();
            }
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.add(new Reservation("Abhi", "Single"));
        bookingQueue.add(new Reservation("Vanmathi", "Double"));
        bookingQueue.add(new Reservation("Kural", "Suite"));
        bookingQueue.add(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(
                bookingQueue, inventory, allocationService
        ));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(
                bookingQueue, inventory, allocationService
        ));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println();
        System.out.println("Remaining Inventory:");
        for (String type : inventory.getTypes()) {
            System.out.println(type + ": " + inventory.getAvailability(type));
        }
    }
}