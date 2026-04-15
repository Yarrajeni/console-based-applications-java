import java.util.ArrayList;
import java.util.Scanner;

class Question {
    private String questionText;
    private String[] options;
    private int correctAnswer; // 1 to 4

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public void displayQuestion() {
        System.out.println(questionText);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getCorrectOptionText() {
        return options[correctAnswer - 1];
    }
}

public class OnlineQuizApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question(
                "1. What is the capital of India?",
                new String[]{"Chennai", "Mumbai", "Delhi", "Kolkata"},
                3
        ));

        questions.add(new Question(
                "2. Which language is used for Android development?",
                new String[]{"Java", "Python", "C", "HTML"},
                1
        ));

        questions.add(new Question(
                "3. Which data structure uses FIFO?",
                new String[]{"Stack", "Queue", "Tree", "Array"},
                2
        ));

        questions.add(new Question(
                "4. Who is known as the father of Java?",
                new String[]{"James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Guido van Rossum"},
                1
        ));

        questions.add(new Question(
                "5. Which keyword is used to inherit a class in Java?",
                new String[]{"this", "super", "extends", "implements"},
                3
        ));

        int score = 0;
        int[] userAnswers = new int[questions.size()];

        System.out.println("===== Online Quiz Application =====");
        System.out.println("Answer each question by entering option number 1 to 4.");
        System.out.println();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            q.displayQuestion();

            int answer;
            while (true) {
                System.out.print("Enter your answer (1-4): ");
                if (sc.hasNextInt()) {
                    answer = sc.nextInt();
                    if (answer >= 1 && answer <= 4) {
                        break;
                    } else {
                        System.out.println("Please enter a number between 1 and 4.");
                    }
                } else {
                    System.out.println("Invalid input. Enter only numbers 1 to 4.");
                    sc.next();
                }
            }

            userAnswers[i] = answer;

            if (answer == q.getCorrectAnswer()) {
                score++;
            }

            System.out.println();
        }

        System.out.println("===== Quiz Completed =====");
        System.out.println("Your Score: " + score + " out of " + questions.size());
        System.out.println();

        System.out.println("===== Correct Answers =====");
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("Question " + (i + 1) + ": Correct Answer is Option "
                    + q.getCorrectAnswer() + " - " + q.getCorrectOptionText());
        }

        sc.close();
    }
}
