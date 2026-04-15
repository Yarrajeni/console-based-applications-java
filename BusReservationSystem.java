import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Bus {
    private int busId;
    private String busName;
    private boolean[] seats;
    private String[] passengerNames;

    public Bus(int busId, String busName, int totalSeats) {
        this.busId = busId;
        this.busName = busName;
        this.seats = new boolean[totalSeats];
        this.passengerNames = new String[totalSeats];
    }

    public int getBusId() {
        return busId;
    }

    public String getBusName() {
        return busName;
    }

    public int getTotalSeats() {
        return seats.length;
    }

    public void displaySeatStatus() {
        System.out.println("\nSeat Status for Bus: " + busName);
        for (int i = 0; i < seats.length; i++) {
            System.out.println("Seat " + (i + 1) + " : " + (seats[i] ? "Booked by " + passengerNames[i] : "Available"));
        }
    }

    public boolean bookSeat(int seatNumber, String passengerName) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            return false;
        }
        if (seats[seatNumber - 1]) {
            return false;
        }
        seats[seatNumber - 1] = true;
        passengerNames[seatNumber - 1] = passengerName;
        return true;
    }

    public boolean cancelReservation(int seatNumber) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            return false;
        }
        if (!seats[seatNumber - 1]) {
            return false;
        }
        seats[seatNumber - 1] = false;
        passengerNames[seatNumber - 1] = null;
        return true;
    }
}

public class BusReservationSystem {
    static ArrayList<Bus> buses = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        addSampleBuses();

        int choice;
        do {
            System.out.println("\n===== Bus Reservation System =====");
            System.out.println("1. Display All Buses");
            System.out.println("2. Display Seat Status");
            System.out.println("3. Book Seat");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    displayAllBuses();
                    break;
                case 2:
                    displaySeatStatus();
                    break;
                case 3:
                    bookSeat();
                    break;
                case 4:
                    cancelReservation();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter between 1 and 5.");
            }
        } while (choice != 5);

        sc.close();
    }

    public static void addSampleBuses() {
        buses.add(new Bus(101, "Chennai Express", 10));
        buses.add(new Bus(102, "Bangalore Deluxe", 8));
        buses.add(new Bus(103, "Hyderabad Super", 12));
    }

    public static void displayAllBuses() {
        System.out.println("\n===== Available Buses =====");
        for (Bus bus : buses) {
            System.out.println("Bus ID: " + bus.getBusId() +
                               ", Bus Name: " + bus.getBusName() +
                               ", Total Seats: " + bus.getTotalSeats());
        }
    }

    public static void displaySeatStatus() {
        System.out.print("Enter Bus ID: ");
        int busId = getIntInput();

        Bus bus = findBusById(busId);
        if (bus == null) {
            System.out.println("Bus not found.");
            return;
        }

        bus.displaySeatStatus();
    }

    public static void bookSeat() {
        System.out.print("Enter Bus ID: ");
        int busId = getIntInput();

        Bus bus = findBusById(busId);
        if (bus == null) {
            System.out.println("Bus not found.");
            return;
        }

        System.out.print("Enter Seat Number: ");
        int seatNumber = getIntInput();

        sc.nextLine();
        System.out.print("Enter Passenger Name: ");
        String passengerName = sc.nextLine();

        boolean success = bus.bookSeat(seatNumber, passengerName);

        if (success) {
            System.out.println("Seat booked successfully.");
        } else {
            System.out.println("Booking failed. Seat may already be booked or invalid.");
        }
    }

    public static void cancelReservation() {
        System.out.print("Enter Bus ID: ");
        int busId = getIntInput();

        Bus bus = findBusById(busId);
        if (bus == null) {
            System.out.println("Bus not found.");
            return;
        }

        System.out.print("Enter Seat Number to cancel: ");
        int seatNumber = getIntInput();

        boolean success = bus.cancelReservation(seatNumber);

        if (success) {
            System.out.println("Reservation cancelled successfully.");
        } else {
            System.out.println("Cancellation failed. Seat may be invalid or not booked.");
        }
    }

    public static Bus findBusById(int busId) {
        for (Bus bus : buses) {
            if (bus.getBusId() == busId) {
                return bus;
            }
        }
        return null;
    }

    public static int getIntInput() {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter an integer: ");
                sc.next();
            }
        }
    }
}
