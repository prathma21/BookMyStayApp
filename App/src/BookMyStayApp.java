import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    /** Displays room details. */
    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

        class SingleRoom extends Room {
            /** Initializes a SingleRoom with predefined attributes. */
            public SingleRoom() { super(1, 250, 1500.0); }
        }

        class DoubleRoom extends Room {
            /** Initializes a DoubleRoom with predefined attributes. */
            public DoubleRoom() { super(2, 400, 2500.0); }
        }

        class SuiteRoom extends Room {
            /** Initializes a SuiteRoom with predefined attributes. */
            public SuiteRoom() { super(3, 750, 5000.0); }
        }

        class RoomInventory {

            private Map<String, Integer> roomAvailability;

            public RoomInventory() {
                roomAvailability = new HashMap<>();
                initializeInventory();
            }

            private void initializeInventory() {
                roomAvailability.put("Single", 5);
                roomAvailability.put("Double", 3);
                roomAvailability.put("Suite", 2);
            }
            public Map<String, Integer> getRoomAvailability() { return roomAvailability; }

            public void updateAvailability(String roomType, int count) { roomAvailability.put(roomType, count); }
        }

        class RoomSearchService {

            public void searchAvailableRooms(
                    RoomInventory inventory,
                    Room singleRoom,
                    Room doubleRoom,
                    Room suiteRoom) {

                Map<String, Integer> availability = inventory.getRoomAvailability();

                // Check and display Single Room availability
                if (availability.get("Single") > 0) {
                    System.out.println("\nSingle Room:");
                    singleRoom.displayRoomDetails();
                    System.out.println("Available: " + availability.get("Single"));
                }

                // Check and display Double Room availability
                if (availability.get("Double") > 0) {
                    System.out.println("\nDouble Room:");
                    doubleRoom.displayRoomDetails();
                    System.out.println("Available: " + availability.get("Double"));
                }

                // Check and display Suite Room availability
                if (availability.get("Suite") > 0) {
                    System.out.println("\nSuite Room:");
                    suiteRoom.displayRoomDetails();
                    System.out.println("Available: " + availability.get("Suite"));
                }
            }
        }
        class Reservation {

            /** Name of the guest making the booking. */
            private String guestName;

            /** Requested room type. */
            private String roomType;

            /**
             * Creates a new booking request.
             *
             * @param guestName name of the guest
             * @param roomType  requested room type
             */
            public Reservation(String guestName, String roomType) {
                this.guestName = guestName;
                this.roomType = roomType;
            }
            public String getGuestName() { return guestName; }

            public String getRoomType() { return roomType; }
        }

        class BookingRequestQueue {

            private Queue<Reservation> requestQueue;
            public BookingRequestQueue() { requestQueue = new LinkedList<>(); }

            public void addRequest(Reservation reservation) { requestQueue.offer(reservation); }

            public Reservation getNextRequest() { return requestQueue.poll(); }
            public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
        }

        public class BookMyStayApp {
            public static void main(String[] args) {

                // Display application header
                System.out.println("Booking Request Queue");

                // Initialize booking queue
                BookingRequestQueue bookingQueue = new BookingRequestQueue();

                // Create booking requests
                Reservation r1 = new Reservation("Abhi", "Single");
                Reservation r2 = new Reservation("Subha", "Double");
                Reservation r3 = new Reservation("Vanmathi", "Suite");

                // Add requests to the queue
                bookingQueue.addRequest(r1);
                bookingQueue.addRequest(r2);
                bookingQueue.addRequest(r3);

                // Display queued booking requests in FIFO order
                while (bookingQueue.hasPendingRequests()) {
                    Reservation next = bookingQueue.getNextRequest();
                    System.out.println("Processing booking for Guest: "
                            + next.getGuestName()
                            + ", Room Type: "
                            + next.getRoomType());
                }
            }
        }