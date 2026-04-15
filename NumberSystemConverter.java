import java.util.Scanner;

public class NumberSystemConverter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Number System Converter =====");
            System.out.println("1. Binary to Decimal");
            System.out.println("2. Decimal to Binary");
            System.out.println("3. Octal to Decimal");
            System.out.println("4. Decimal to Octal");
            System.out.println("5. Hexadecimal to Decimal");
            System.out.println("6. Decimal to Hexadecimal");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter binary number: ");
                    String binary = sc.nextLine();
                    if (binary.matches("[01]+")) {
                        System.out.println("Decimal value: " + Integer.parseInt(binary, 2));
                    } else {
                        System.out.println("Invalid binary number.");
                    }
                    break;

                case 2:
                    System.out.print("Enter decimal number: ");
                    if (sc.hasNextInt()) {
                        int decimal = sc.nextInt();
                        System.out.println("Binary value: " + Integer.toBinaryString(decimal));
                    } else {
                        System.out.println("Invalid input.");
                        sc.next();
                    }
                    break;

                case 3:
                    System.out.print("Enter octal number: ");
                    String octal = sc.nextLine();
                    if (octal.matches("[0-7]+")) {
                        System.out.println("Decimal value: " + Integer.parseInt(octal, 8));
                    } else {
                        System.out.println("Invalid octal number.");
                    }
                    break;

                case 4:
                    System.out.print("Enter decimal number: ");
                    if (sc.hasNextInt()) {
                        int decimal = sc.nextInt();
                        System.out.println("Octal value: " + Integer.toOctalString(decimal));
                    } else {
                        System.out.println("Invalid input.");
                        sc.next();
                    }
                    break;

                case 5:
                    System.out.print("Enter hexadecimal number: ");
                    String hex = sc.nextLine();
                    if (hex.matches("[0-9A-Fa-f]+")) {
                        System.out.println("Decimal value: " + Integer.parseInt(hex, 16));
                    } else {
                        System.out.println("Invalid hexadecimal number.");
                    }
                    break;

                case 6:
                    System.out.print("Enter decimal number: ");
                    if (sc.hasNextInt()) {
                        int decimal = sc.nextInt();
                        System.out.println("Hexadecimal value: " + Integer.toHexString(decimal).toUpperCase());
                    } else {
                        System.out.println("Invalid input.");
                        sc.next();
                    }
                    break;

                case 7:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 7);

        sc.close();
    }
}
