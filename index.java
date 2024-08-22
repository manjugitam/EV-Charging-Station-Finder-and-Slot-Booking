import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class to represent a charging station
class ChargingStation {
    private String name;
    private String location;
    private int availableSlots;
    private String chargingType; // e.g., Fast, Normal

    public ChargingStation(String name, String location, int availableSlots, String chargingType) {
        this.name = name;
        this.location = location;
        this.availableSlots = availableSlots;
        this.chargingType = chargingType;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public String getChargingType() {
        return chargingType;
    }

    public boolean bookSlot() {
        if (availableSlots > 0) {
            availableSlots--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Location: " + location + ", Available Slots: " + availableSlots + ", Charging Type: " + chargingType;
    }
}

// Class to represent the EV charging system
class EVChargingSystem {
    private List<ChargingStation> stations;

    public EVChargingSystem() {
        stations = new ArrayList<>();
        populateStations();
    }

    private void populateStations() {
        // Sample charging stations
        stations.add(new ChargingStation("Station A", "Downtown", 5, "Fast"));
        stations.add(new ChargingStation("Station B", "Suburb", 10, "Normal"));
        stations.add(new ChargingStation("Station C", "City Center", 2, "Fast"));
        stations.add(new ChargingStation("Station D", "Mall", 8, "Normal"));
    }

    public List<ChargingStation> filterStations(String location, String chargingType) {
        List<ChargingStation> filteredStations = new ArrayList<>();
        for (ChargingStation station : stations) {
            if ((location == null || station.getLocation().equalsIgnoreCase(location)) &&
                (chargingType == null || station.getChargingType().equalsIgnoreCase(chargingType))) {
                filteredStations.add(station);
            }
        }
        return filteredStations;
    }

    public boolean bookChargingSlot(String stationName) {
        for (ChargingStation station : stations) {
            if (station.getName().equalsIgnoreCase(stationName)) {
                return station.bookSlot();
            }
        }
        return false; // Station not found
    }
}

// Main class to interact with the EV charging system
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EVChargingSystem evSystem = new EVChargingSystem();

        System.out.println("Welcome to the EV Charging Station Finder!");

        while (true) {
            System.out.println("\n1. Find Charging Stations");
            System.out.println("\n2. Book a Charging Slot");
            System.out.println("\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter location filter (or press Enter to skip): ");
                    String location = scanner.nextLine();
                    System.out.print("Enter charging type filter (Fast/Normal or press Enter to skip): ");
                    String chargingType = scanner.nextLine();

                    List<ChargingStation> filteredStations = evSystem.filterStations(
                        location.isEmpty() ? null : location,
                        chargingType.isEmpty() ? null : chargingType
                    );

                    System.out.println("\nAvailable Charging Stations:");
                    for (ChargingStation station : filteredStations) {
                        System.out.println(station);
                    }
                    break;

                case 2:
                    System.out.print("Enter the name of the station to book a slot: ");
                    String stationName = scanner.nextLine();

                    if (evSystem.bookChargingSlot(stationName)) {
                        System.out.println("Slot booked successfully at " + stationName + "!");
                    } else {
                        System.out.println("Failed to book a slot. The station might be full or doesn't exist.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
