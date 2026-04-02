import java.util.*;

abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String type, int count) {
        roomAvailability.put(type, count);
    }
}

class Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;

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

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationId() {
        return reservationId;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
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
    private Map<String, Integer> counters = new HashMap<>();

    public String allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.getOrDefault(type, 0) > 0) {
            int count = counters.getOrDefault(type, 0) + 1;
            counters.put(type, count);
            inventory.updateAvailability(type, availability.get(type) - 1);
            String roomId = type + "-" + count;
            reservation.setReservationId(roomId);
            System.out.println("Booking confirmed for " + reservation.getGuestName() + " : " + roomId);
            return roomId;
        } else {
            System.out.println("No rooms available for " + reservation.getGuestName());
            return null;
        }
    }
}

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        return servicesByReservation
                .getOrDefault(reservationId, Collections.emptyList())
                .stream()
                .mapToDouble(AddOnService::getCost)
                .sum();
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocator = new RoomAllocationService();
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        queue.addRequest(new Reservation("Abhi", "Single"));

        while (queue.hasRequests()) {
            Reservation r = queue.next();
            String reservationId = allocator.allocateRoom(r, inventory);

            if (reservationId != null) {
                serviceManager.addService(reservationId, new AddOnService("Breakfast", 500.0));
                serviceManager.addService(reservationId, new AddOnService("Airport Pickup", 1000.0));

                System.out.println("Add-On Service Selection");
                System.out.println("Reservation ID: " + reservationId);
                System.out.println("Total Add-On Cost: " +
                        serviceManager.calculateTotalServiceCost(reservationId));
            }
        }
    }
}