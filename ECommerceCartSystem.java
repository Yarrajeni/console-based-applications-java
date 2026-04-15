import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class EProduct {
    private int id;
    private String name;
    private double price;

    public EProduct(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void displayProduct() {
        System.out.println(id + " - " + name + " - Rs." + price);
    }
}

class ECartItem {
    private EProduct product;
    private int quantity;

    public ECartItem(EProduct product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public EProduct getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public void displayCartItem() {
        System.out.println(
            product.getId() + " - " + product.getName() +
            " | Price: Rs." + product.getPrice() +
            " | Qty: " + quantity +
            " | Total: Rs." + getTotalPrice()
        );
    }
}

public class ECommerceCartSystem {
    static ArrayList<EProduct> catalog = new ArrayList<>();
    static ArrayList<ECartItem> cart = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        addSampleProducts();

        int choice;

        do {
            System.out.println("\n===== E-Commerce Cart System =====");
            System.out.println("1. View Product Catalog");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Remove Product from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Calculate Total Bill");
            System.out.println("6. Checkout");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    viewCatalog();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    removeFromCart();
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                    showBill();
                    break;
                case 6:
                    checkout();
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Enter between 1 and 7.");
            }

        } while (choice != 7);

        sc.close();
    }

    public static void addSampleProducts() {
        catalog.add(new EProduct(1, "Laptop", 55000));
        catalog.add(new EProduct(2, "Mouse", 500));
        catalog.add(new EProduct(3, "Keyboard", 1200));
        catalog.add(new EProduct(4, "Headphones", 2000));
        catalog.add(new EProduct(5, "Mobile Phone", 25000));
    }

    public static void viewCatalog() {
        System.out.println("\n===== Product Catalog =====");
        for (EProduct p : catalog) {
            p.displayProduct();
        }
    }

    public static void addToCart() {
        System.out.print("Enter Product ID to add: ");
        int id = getIntInput();

        EProduct product = findProductById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = getIntInput();

        if (qty <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        ECartItem item = findCartItemByProductId(id);
        if (item != null) {
            item.setQuantity(item.getQuantity() + qty);
        } else {
            cart.add(new ECartItem(product, qty));
        }

        System.out.println("Product added to cart successfully.");
    }

    public static void removeFromCart() {
        System.out.print("Enter Product ID to remove: ");
        int id = getIntInput();

        ECartItem item = findCartItemByProductId(id);
        if (item == null) {
            System.out.println("Product not found in cart.");
            return;
        }

        cart.remove(item);
        System.out.println("Product removed from cart successfully.");
    }

    public static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n===== Your Cart =====");
        for (ECartItem item : cart) {
            item.displayCartItem();
        }
    }

    public static double calculateSubtotal() {
        double total = 0;
        for (ECartItem item : cart) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public static double calculateDiscount(double subtotal) {
        if (subtotal >= 50000) {
            return subtotal * 0.20;
        } else if (subtotal >= 20000) {
            return subtotal * 0.10;
        } else if (subtotal >= 5000) {
            return subtotal * 0.05;
        } else {
            return 0;
        }
    }

    public static void showBill() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        double subtotal = calculateSubtotal();
        double discount = calculateDiscount(subtotal);
        double finalAmount = subtotal - discount;

        System.out.println("\n===== Bill Details =====");
        System.out.println("Subtotal      : Rs." + subtotal);
        System.out.println("Discount      : Rs." + discount);
        System.out.println("Final Amount  : Rs." + finalAmount);
    }

    public static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Cannot checkout.");
            return;
        }

        viewCart();
        showBill();
        System.out.println("Checkout successful. Thank you for shopping.");
        cart.clear();
    }

    public static EProduct findProductById(int id) {
        for (EProduct p : catalog) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public static ECartItem findCartItemByProductId(int id) {
        for (ECartItem item : cart) {
            if (item.getProduct().getId() == id) {
                return item;
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
