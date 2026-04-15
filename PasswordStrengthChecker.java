import java.util.Scanner;

public class PasswordStrengthChecker {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        checkStrength(password);

        sc.close();
    }

    public static void checkStrength(String password) {
        int score = 0;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        // Length check
        if (password.length() >= 8) {
            score++;
        }

        // Check characters
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        if (hasUpper) score++;
        if (hasLower) score++;
        if (hasDigit) score++;
        if (hasSpecial) score++;

        // Strength evaluation
        System.out.println("\n===== Password Strength Result =====");

        if (score <= 2) {
            System.out.println("Strength: WEAK");
        } else if (score <= 4) {
            System.out.println("Strength: MEDIUM");
        } else {
            System.out.println("Strength: STRONG");
        }

        // Suggestions
        System.out.println("\nSuggestions to improve:");

        if (password.length() < 8) {
            System.out.println("- Use at least 8 characters");
        }
        if (!hasUpper) {
            System.out.println("- Add uppercase letters (A-Z)");
        }
        if (!hasLower) {
            System.out.println("- Add lowercase letters (a-z)");
        }
        if (!hasDigit) {
            System.out.println("- Add digits (0-9)");
        }
        if (!hasSpecial) {
            System.out.println("- Add special characters (!@#$%^&*)");
        }

        if (score == 5) {
            System.out.println("- Your password is strong 👍");
        }
    }
}
