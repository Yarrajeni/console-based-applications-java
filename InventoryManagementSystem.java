import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalValue() {
        return quantity * price;
    }

    public boolean isLowStock() {
        return quantity < 5;
    }

    public void displayProduct() {
        System.out.println("-----------------------------------");
        System.out.println("Product ID     : " + id);
        System.out.println("Product Name   : " + name);
        System.out.println("Quantity       : " + quantity);
        System.out.println("Price          : " + price);
        System.out.println("Total Value    : " + getTotalValue());
        if (isLowStock()) {
            System.out.println("Alert          : Low Stock");
        }
    }
}

public class InventoryManagementSystem {
    static ArrayList<Product> products = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== Inventory Management System =====");
            System.out.println("1. Add Product");
            System.out.println("2. Update Stock");
            System.out.println("3. Remove Product");
            System.out.println("4. Display Inventory");
            System.out.println("5. Calculate Total Inventory Value");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateStock();
                    break;
                case 3:
                    removeProduct();
                    break;
                case 4:
                    displayInventory();
                    break;
                case 5:
                    calculateInventoryValue();
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Enter between 1 and 6.");
            }

        } while (choice != 6);
    }

    public static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = getIntInput();

        if (findProductById(id) != null) {
            System.out.println("Product with this ID already exists.");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = getIntInput();

        if (quantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }

        System.out.print("Enter Price: ");
        double price = getDoubleInput();

        if (price < 0) {
            System.out.println("Price cannot be negative.");
            return;
        }

        products.add(new Product(id, name, quantity, price));
        System.out.println("Product added successfully.");
    }

    public static void updateStock() {
        System.out.print("Enter Product ID to update stock: ");
        int id = getIntInput();

        Product product = findProductById(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter new quantity: ");
        int newQuantity = getIntInput();

        if (newQuantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }

        product.setQuantity(newQuantity);
        System.out.println("Stock updated successfully.");
    }

    public static void removeProduct() {
        System.out.print("Enter Product ID to remove: ");
        int id = getIntInput();

        Product product = findProductById(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        products.remove(product);
        System.out.println("Product removed successfully.");
    }

    public static void displayInventory() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println("\n===== Inventory Details =====");
        for (Product product : products) {
            product.displayProduct();
        }
    }

    public static void calculateInventoryValue() {
        double total = 0;

        for (Product product : products) {
            total += product.getTotalValue();
        }

        System.out.println("Total Inventory Value: " + total);
    }

    public static Product findProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
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

    public static double getDoubleInput() {
        while (true) {
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter a valid number: ");
                sc.next();
            }
        }
    }
}
