import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean isIssued;
    private String issuedTo;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isIssued = false;
        this.issuedTo = "";
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void issueBook(String userName) {
        isIssued = true;
        issuedTo = userName;
    }

    public void returnBook() {
        isIssued = false;
        issuedTo = "";
    }

    public void displayBook() {
        System.out.println("----------------------------------");
        System.out.println("Book ID      : " + bookId);
        System.out.println("Title        : " + title);
        System.out.println("Author       : " + author);
        System.out.println("Issue Status : " + (isIssued ? "Issued to " + issuedTo : "Available"));
    }
}

public class LibraryManagementSystem {
    static ArrayList<Book> books = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Display Available Books");
            System.out.println("5. Search by Title");
            System.out.println("6. Search by Author");
            System.out.println("7. Display All Books");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    issueBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    displayAvailableBooks();
                    break;
                case 5:
                    searchByTitle();
                    break;
                case 6:
                    searchByAuthor();
                    break;
                case 7:
                    displayAllBooks();
                    break;
                case 8:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter between 1 and 8.");
            }

        } while (choice != 8);
    }

    public static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = getIntInput();

        if (findBookById(id) != null) {
            System.out.println("Book with this ID already exists.");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        books.add(new Book(id, title, author));
        System.out.println("Book added successfully.");
    }

    public static void issueBook() {
        System.out.print("Enter Book ID to issue: ");
        int id = getIntInput();

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.isIssued()) {
            System.out.println("Book is already issued to " + book.getIssuedTo());
            return;
        }

        sc.nextLine();
        System.out.print("Enter User Name: ");
        String userName = sc.nextLine();

        book.issueBook(userName);
        System.out.println("Book issued successfully to " + userName);
    }

    public static void returnBook() {
        System.out.print("Enter Book ID to return: ");
        int id = getIntInput();

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!book.isIssued()) {
            System.out.println("This book is already available in the library.");
            return;
        }

        book.returnBook();
        System.out.println("Book returned successfully.");
    }

    public static void displayAvailableBooks() {
        boolean found = false;

        System.out.println("\n===== Available Books =====");
        for (Book book : books) {
            if (!book.isIssued()) {
                book.displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available books found.");
        }
    }

    public static void searchByTitle() {
        sc.nextLine();
        System.out.print("Enter title to search: ");
        String title = sc.nextLine().toLowerCase();

        boolean found = false;

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title)) {
                book.displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No book found with this title.");
        }
    }

    public static void searchByAuthor() {
        sc.nextLine();
        System.out.print("Enter author to search: ");
        String author = sc.nextLine().toLowerCase();

        boolean found = false;

        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author)) {
                book.displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No book found by this author.");
        }
    }

    public static void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }

        System.out.println("\n===== All Books =====");
        for (Book book : books) {
            book.displayBook();
        }
    }

    public static Book findBookById(int id) {
        for (Book book : books) {
            if (book.getBookId() == id) {
                return book;
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
