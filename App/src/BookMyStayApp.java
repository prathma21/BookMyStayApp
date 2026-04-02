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
// MAIN CLASS - BookMyStayApp
// =====================================================
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

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable  = 2;

        System.out.println("Hotel Room Initialization");

        System.out.println("\nSingle Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println("\nDouble Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println("\nSuite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}