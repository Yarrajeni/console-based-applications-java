import java.util.*;

class User {
    String name;
    int age;
    String location;
    String occupation;

    List<User> friends = new ArrayList<>();
    List<User> requests = new ArrayList<>();
    List<Post> posts = new ArrayList<>();
    List<String> notifications = new ArrayList<>();

    User(String name, int age, String location, String occupation) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.occupation = occupation;
    }
}

class Post {
    String content;
    String author;
    int likes = 0;
    List<String> comments = new ArrayList<>();

    Post(String content, String author) {
        this.content = content;
        this.author = author;
    }
}

public class SocialMediaApp {

    static List<User> users = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static User currentUser = null;

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n--- SOCIAL MEDIA APP ---");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    signup();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    return;
            }
        }
    }

    static void signup() {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Location: ");
        String location = sc.nextLine();

        System.out.print("Enter Occupation: ");
        String occupation = sc.nextLine();

        User user = new User(name, age, location, occupation);
        users.add(user);

        System.out.println("Signup Successful!");
    }

    static void login() {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        for (User u : users) {
            if (u.name.equalsIgnoreCase(name)) {
                currentUser = u;
                userMenu();
                return;
            }
        }

        System.out.println("User not found.");
    }

    static void userMenu() {

        while (true) {

            System.out.println("\nWelcome " + currentUser.name);
            System.out.println("1. Friend Suggestions");
            System.out.println("2. View Friend Requests");
            System.out.println("3. View Friends");
            System.out.println("4. Create Post");
            System.out.println("5. News Feed");
            System.out.println("6. Notifications");
            System.out.println("7. Logout");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    suggestFriends();
                    break;

                case 2:
                    manageRequests();
                    break;

                case 3:
                    viewFriends();
                    break;

                case 4:
                    createPost();
                    break;

                case 5:
                    newsFeed();
                    break;

                case 6:
                    viewNotifications();
                    break;

                case 7:
                    return;
            }
        }
    }

    static void suggestFriends() {

        System.out.println("\n--- Friend Suggestions ---");

        for (User u : users) {

            if (u != currentUser && !currentUser.friends.contains(u)) {

                int match = 0;

                if (u.age == currentUser.age) match++;
                if (u.location.equalsIgnoreCase(currentUser.location)) match++;

                System.out.println(u.name + " | Match Score: " + match);
            }
        }

        System.out.print("Enter name to send request: ");
        String name = sc.nextLine();

        for (User u : users) {

            if (u.name.equalsIgnoreCase(name)) {

                u.requests.add(currentUser);
                u.notifications.add(currentUser.name + " sent you a friend request");

                System.out.println("Friend request sent!");
                return;
            }
        }

        System.out.println("User not found.");
    }

    static void manageRequests() {

        if (currentUser.requests.isEmpty()) {
            System.out.println("No requests.");
            return;
        }

        for (User u : currentUser.requests) {

            System.out.println(u.name + " sent request");
            System.out.println("1.Accept  2.Reject");

            int ch = sc.nextInt();

            if (ch == 1) {

                currentUser.friends.add(u);
                u.friends.add(currentUser);

                u.notifications.add(currentUser.name + " accepted your friend request");

                System.out.println("Friend added!");
            }
        }

        currentUser.requests.clear();
    }

    static void viewFriends() {

        System.out.println("\n--- Friends List ---");

        for (User u : currentUser.friends) {

            System.out.println(u.name + " | " + u.age + " | " + u.location + " | " + u.occupation);
        }
    }

    static void createPost() {

        System.out.print("Write Post: ");
        String content = sc.nextLine();

        Post p = new Post(content, currentUser.name);
        currentUser.posts.add(p);

        System.out.println("Post created!");
    }

    static void newsFeed() {

        List<Post> feed = new ArrayList<>();

        for (User f : currentUser.friends) {
            feed.addAll(f.posts);
        }

        Collections.reverse(feed);

        for (Post p : feed) {

            System.out.println("\nPost by: " + p.author);
            System.out.println(p.content);
            System.out.println("Likes: " + p.likes);

            for (String c : p.comments) {
                System.out.println("Comment: " + c);
            }

            System.out.println("1.Like  2.Comment  3.Next");
            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1) {
                p.likes++;
            }

            if (ch == 2) {

                System.out.print("Enter comment: ");
                String com = sc.nextLine();
                p.comments.add(currentUser.name + ": " + com);
            }
        }
    }

    static void viewNotifications() {

        System.out.println("\n--- Notifications ---");

        for (String n : currentUser.notifications) {
            System.out.println(n);
        }

        currentUser.notifications.clear();
    }
}