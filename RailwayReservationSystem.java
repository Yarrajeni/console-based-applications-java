import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Train {
    private int trainNumber;
    private String trainName;
    private int totalSeats;
    private int availableSeats;

    public Train(int trainNumber, String trainName, int totalSeats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    public void cancelSeat() {
        if (availableSeats < totalSeats) {
            availableSeats++;
        }
    }

    public void displayTrain() {
        System.out.println("-----------------------------------");
        System.out.println("Train Number    : " + trainNumber);
        System.out.println("Train Name      : " + trainName);
        System.out.println("Total Seats     : " + totalSeats);
        System.out.println("Available Seats : " + availableSeats);
    }
}

class Ticket {
    private static int counter = 1001;

    private int ticketId;
    private String passengerName;
    private int age;
    private Train train;
    private boolean active;

    public Ticket(String passengerName, int age, Train train) {
        this.ticketId = counter++;
        this.passengerName = passengerName;
        this.age = age;
        this.train = train;
        this.active = true;
    }

    public int getTicketId() {
        return ticketId;
    }

    public Train getTrain() {
        return train;
    }

    public boolean isActive() {
        return active;
    }

    public void cancelTicket() {
        active = false;
    }

    public void displayTicket() {
        System.out.println("\n========== Ticket ==========");
        System.out.println("Ticket ID      : " + ticketId);
        System.out.println("Passenger Name : " + passengerName);
        System.out.println("Age            : " + age);
        System.out.println("Train Number   : " + train.getTrainNumber());
        System.out.println("Train Name     : " + train.getTrainName());
        System.out.println("Status         : " + (active ? "Booked" : "Cancelled"));
        System.out.println("============================");
    }
}

public class RailwayReservationSystem {
    static ArrayList<Train> trains = new ArrayList<>();
    static ArrayList<Ticket> tickets = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        addSampleTrains();

        int choice;
        do {
            System.out.println("\n===== Railway Reservation System =====");
            System.out.println("1. View Trains and Seat Availability");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. View Ticket by Ticket ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    showTrains();
                    break;
                case 2:
                    bookTicket();
                    break;
                case 3:
                    cancelTicket();
                    break;
                case 4:
                    viewTicket();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Enter between 1 and 5.");
            }
        } while (choice != 5);

        sc.close();
    }

    public static void addSampleTrains() {
        trains.add(new Train(101, "Chennai Express", 5));
        trains.add(new Train(102, "Coimbatore Express", 3));
        trains.add(new Train(103, "Madurai Superfast", 4));
    }

    public static void showTrains() {
        System.out.println("\n===== Train Details =====");
        for (Train train : trains) {
            train.displayTrain();
        }
    }

    public static void bookTicket() {
        showTrains();

        System.out.print("\nEnter Train Number: ");
        int trainNumber = getIntInput();

        Train train = findTrainByNumber(trainNumber);
        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        if (train.getAvailableSeats() == 0) {
            System.out.println("No seats available in this train.");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = getIntInput();

        if (age <= 0) {
            System.out.println("Invalid age.");
            return;
        }

        boolean booked = train.bookSeat();
        if (booked) {
            Ticket ticket = new Ticket(name, age, train);
            tickets.add(ticket);
            System.out.println("Ticket booked successfully.");
            ticket.displayTicket();
        } else {
            System.out.println("Booking failed.");
        }
    }

    public static void cancelTicket() {
        System.out.print("Enter Ticket ID to cancel: ");
        int ticketId = getIntInput();

        Ticket ticket = findTicketById(ticketId);
        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        if (!ticket.isActive()) {
            System.out.println("This ticket is already cancelled.");
            return;
        }

        ticket.cancelTicket();
        ticket.getTrain().cancelSeat();
        System.out.println("Ticket cancelled successfully.");
    }

    public static void viewTicket() {
        System.out.print("Enter Ticket ID: ");
        int ticketId = getIntInput();

        Ticket ticket = findTicketById(ticketId);
        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        ticket.displayTicket();
    }

    public static Train findTrainByNumber(int trainNumber) {
        for (Train train : trains) {
            if (train.getTrainNumber() == trainNumber) {
                return train;
            }
        }
        return null;
    }

    public static Ticket findTicketById(int ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                return ticket;
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
