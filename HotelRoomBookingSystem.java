import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String category;
    private boolean isBooked;
    private String customerName;
    private String customerPhone;

    public Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
        this.customerName = "";
        this.customerPhone = "";
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void bookRoom(String customerName, String customerPhone) {
        isBooked = true;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public void cancelBooking() {
        isBooked = false;
        this.customerName = "";
        this.customerPhone = "";
    }

    public void displayRoom() {
        System.out.println("-----------------------------------");
        System.out.println("Room Number    : " + roomNumber);
        System.out.println("Category       : " + category);
        System.out.println("Status         : " + (isBooked ? "Booked" : "Available"));
        if (isBooked) {
            System.out.println("Customer Name  : " + customerName);
            System.out.println("Customer Phone : " + customerPhone);
        }
    }
}

public class HotelRoomBookingSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        addDefaultRooms();

        int choice;
        do {
            System.out.println("\n===== Hotel Room Booking System =====");
            System.out.println("1. Show All Rooms");
            System.out.println("2. Show Available Rooms");
            System.out.println("3. Book Room");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    showAllRooms();
                    break;
                case 2:
                    showAvailableRooms();
                    break;
                case 3:
                    bookRoom();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Enter between 1 and 5.");
            }
        } while (choice != 5);
    }

    public static void addDefaultRooms() {
        rooms.add(new Room(101, "AC"));
        rooms.add(new Room(102, "AC"));
        rooms.add(new Room(103, "AC"));
        rooms.add(new Room(201, "Non-AC"));
        rooms.add(new Room(202, "Non-AC"));
        rooms.add(new Room(203, "Non-AC"));
    }

    public static void showAllRooms() {
        System.out.println("\n===== All Rooms =====");
        for (Room room : rooms) {
            room.displayRoom();
        }
    }

    public static void showAvailableRooms() {
        System.out.println("\n===== Available Rooms =====");
        boolean found = false;

        for (Room room : rooms) {
            if (!room.isBooked()) {
                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    public static void bookRoom() {
        System.out.print("Enter Room Number to book: ");
        int roomNumber = getIntInput();

        Room room = findRoomByNumber(roomNumber);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        if (room.isBooked()) {
            System.out.println("Room already booked. Double booking not allowed.");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Customer Phone: ");
        String phone = sc.nextLine();

        room.bookRoom(name, phone);
        System.out.println("Room booked successfully.");
    }

    public static void cancelBooking() {
        System.out.print("Enter Room Number to cancel booking: ");
        int roomNumber = getIntInput();

        Room room = findRoomByNumber(roomNumber);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        if (!room.isBooked()) {
            System.out.println("This room is not currently booked.");
            return;
        }

        room.cancelBooking();
        System.out.println("Booking canceled successfully.");
    }

    public static Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
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
