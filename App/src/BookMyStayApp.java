import java.util.HashMap;
import java.util.Map;

// =====================================================
// ABSTRACT CLASS - Room
// =====================================================
abstract class Room {

    /** Number of beds available in the room. */
    protected int numberOfBeds;

    /** Total size of the room in square feet. */
    protected int squareFeet;

    /** Price charged per night for this room type. */
    protected double pricePerNight;

    /**
     * Constructor used by child classes to
     * initialize common room attributes.
     *
     * @param numberOfBeds number of beds in the room
     * @param squareFeet total room size
     * @param pricePerNight cost per night
     */
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

// =====================================================
// CLASS - SingleRoom
// =====================================================
class SingleRoom extends Room {
    /** Initializes a SingleRoom with predefined attributes. */
    public SingleRoom() { super(1, 250, 1500.0); }
}

// =====================================================
// CLASS - DoubleRoom
// =====================================================
class DoubleRoom extends Room {
    /** Initializes a DoubleRoom with predefined attributes. */
    public DoubleRoom() { super(2, 400, 2500.0); }
}

// =====================================================
// CLASS - SuiteRoom
// =====================================================
class SuiteRoom extends Room {
    /** Initializes a SuiteRoom with predefined attributes. */
    public SuiteRoom() { super(3, 750, 5000.0); }
}

// =====================================================
// CLASS - RoomInventory
// =====================================================
class RoomInventory {

    /**
     * Stores available room count for each room type.
     *
     * Key   -> Room type name
     * Value -> Available room count
     */
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes the inventory
     * with default availability values.
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes room availability data.
     *
     * This method centralizes inventory setup
     * instead of using scattered variables.
     */
    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    /**
     * Returns the current availability map.
     *
     * @return map of room type to available count
     */
    public Map<String, Integer> getRoomAvailability() { return roomAvailability; }

    /**
     * Updates availability for a specific room type.
     *
     * @param roomType the room type to update
     * @param count new availability count
     */
    public void updateAvailability(String roomType, int count) { roomAvailability.put(roomType, count); }
}

// =====================================================
// MAIN CLASS - BookMyStayApp
// =====================================================
/**
 * =====================================================
 * MAIN CLASS - BookMyStayApp
 * =====================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This class demonstrates how room availability
 * is managed using a centralized inventory.
 *
 * Room objects are used to retrieve pricing
 * and room characteristics.
 *
 * No booking or search logic is introduced here.
 *
 * @version 3.1
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        // Create room objects
        SingleRoom single = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        // Centralized inventory
        RoomInventory inventory = new RoomInventory();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Hotel Room Inventory Status");

        System.out.println("\nSingle Room:");
        single.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Single"));

        System.out.println("\nDouble Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Double"));

        System.out.println("\nSuite Room:");
        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Suite"));
    }
}