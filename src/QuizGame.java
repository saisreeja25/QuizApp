import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class QuizGame {
    Question[] mainQuestions;
    Question[] swapQuestions;
    Player player;
    Scanner scanner;
    int prizeMoney = 1000;
    int currentQuestionIndex = 0;
    Random rand = new Random();

    QuizGame(Player player) {
        this.player = player;
        this.scanner = new Scanner(System.in);
        this.mainQuestions = new Question[10];
        this.swapQuestions = new Question[5];
        loadQuestions();
    }

    void loadQuestions() {
        // Java Questions
        mainQuestions[0] = new Question("Who invented Java?", 
                                        new String[]{"James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Guido van Rossum"}, 1);
        mainQuestions[1] = new Question("Which keyword is used to inherit a class in Java?", 
                                        new String[]{"implements", "extends", "inherit", "super"}, 2);
        mainQuestions[2] = new Question("Which data structure follows LIFO?", 
                                        new String[]{"Queue", "Stack", "Linked List", "Array"}, 2);
        mainQuestions[3] = new Question("What does JVM stand for?", 
                                        new String[]{"Java Virtual Machine", "Java Visual Machine", "Java Variable Memory", "Java Verified Mode"}, 1);
        mainQuestions[4] = new Question("Which collection allows storing unique elements only?", 
                                        new String[]{"List", "Queue", "Set", "Map"}, 3);

        // General Knowledge Questions
        mainQuestions[5] = new Question("Who was the first President of India?", 
                                        new String[]{"Dr. B.R. Ambedkar", "Dr. Rajendra Prasad", "Jawaharlal Nehru", "Sardar Patel"}, 2);
        mainQuestions[6] = new Question("Which is the largest ocean on Earth?", 
                                        new String[]{"Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Arctic Ocean"}, 3);
        mainQuestions[7] = new Question("What is the national animal of India?", 
                                        new String[]{"Lion", "Tiger", "Elephant", "Peacock"}, 2);
        mainQuestions[8] = new Question("Who invented the telephone?", 
                                        new String[]{"Thomas Edison", "Alexander Graham Bell", "Nikola Tesla", "Albert Einstein"}, 2);
        mainQuestions[9] = new Question("Which is the longest river in the world?", 
                                        new String[]{"Amazon River", "Nile River", "Ganges River", "Mississippi River"}, 2);
    }
    void displayRules() {
        try {
            System.out.println("\n🎉 Welcome to the Quiz Game, " + player.name + "!");
            Thread.sleep(2000);
            System.out.println("\n📜 Rules:");
            Thread.sleep(1000);
            System.out.println("1️⃣ Answer the multiple-choice questions.");
            Thread.sleep(1000);
            System.out.println("2️⃣ You have 3 lifelines: 50-50, Swap Question, and Audience Poll.");
            Thread.sleep(1000);
            System.out.println("3️⃣ Lifelines can be used only once.");
            Thread.sleep(1000);
            System.out.println("4️⃣ If you answer incorrectly, the game ends, and you keep your earned prize.");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("An error occurred while displaying rules.");
        }
    }

    void useLifeline() {
        System.out.println("\n🎯 Choose a Lifeline:");
        System.out.println("1️⃣ 50-50");
        System.out.println("2️⃣ Swap Question");
        System.out.println("3️⃣ Audience Poll");
        System.out.println("4️⃣ Cancel");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                if (!player.lifelines[0]) {
                    System.out.println("⚠️ You have already used 50-50!");
                } else {
                    useFiftyFifty();
                }
                break;
            case 2:
                if (!player.lifelines[1]) {
                    System.out.println("⚠️ You have already used Swap Question!");
                } else {
                    useSwap();
                }
                break;
            case 3:
                if (!player.lifelines[2]) {
                    System.out.println("⚠️ You have already used Audience Poll!");
                } else {
                    useAudiencePoll();
                }
                break;
            case 4:
                System.out.println("❌ Lifeline canceled.");
                break;
            default:
                System.out.println("❌ Invalid choice.");
        }
    }

    void useFiftyFifty() {
        player.lifelines[0] = false;
        Question q = mainQuestions[currentQuestionIndex];
        List<Integer> incorrectOptions = new ArrayList<>();
        for (int i = 0; i < q.options.length; i++) {
            if (i + 1 != q.correctAnswer) {
                incorrectOptions.add(i + 1);
            }
        }

        Collections.shuffle(incorrectOptions);
        int removedOption = incorrectOptions.get(0);

        System.out.println("\n📌 50-50 Lifeline Used! Remaining options:");
        System.out.println(q.correctAnswer + ". " + q.options[q.correctAnswer - 1]);
        System.out.println(removedOption + ". " + q.options[removedOption - 1]);
    }

    void useSwap() {
        player.lifelines[1] = false;
        System.out.println("🔄 Swapping Question...");
        currentQuestionIndex++;
    }

    void useAudiencePoll() {
        player.lifelines[2] = false;
        Question q = mainQuestions[currentQuestionIndex];

        int correctPercentage = rand.nextInt(21) + 60;
        int remainingPercentage = 100 - correctPercentage;
        
        List<Integer> wrongOptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i + 1 != q.correctAnswer) {
                wrongOptions.add(i);
            }
        }
        
        Collections.shuffle(wrongOptions);
        int option2 = rand.nextInt(remainingPercentage - 10);
        int option3 = rand.nextInt(remainingPercentage - option2);
        int option4 = remainingPercentage - option2 - option3;

        int[] pollResults = new int[4];
        pollResults[q.correctAnswer - 1] = correctPercentage;
        pollResults[wrongOptions.get(0)] = option2;
        pollResults[wrongOptions.get(1)] = option3;
        pollResults[wrongOptions.get(2)] = option4;

        System.out.println("\n📊 Audience Poll Results:");
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ". " + q.options[i] + " - " + pollResults[i] + "%");
        }
    }

    void start() {
    	displayRules();
        System.out.println("\n🎉 Welcome to the Quiz Game, " + player.name + "!");
        try {
            while (currentQuestionIndex < mainQuestions.length) {
                Question q = mainQuestions[currentQuestionIndex];
                q.displayQuestion();

                System.out.println("\n📌 Options: ");
                System.out.println("1-4: Answer");
                System.out.println("5: Use Lifeline");
                System.out.println("6: Quit");

                int choice = scanner.nextInt();

                if (choice == 5) {
                    useLifeline();
                    continue;
                } else if (choice == 6) {
                    System.out.println("💰 You won ₹" + player.cashPrize);
                    return;
                } else if (choice == q.correctAnswer) {
                    player.cashPrize = prizeMoney;
                    System.out.println("✅ Correct! You won ₹" + prizeMoney);
                    prizeMoney *= 2;
                    currentQuestionIndex++;
                } else {
                    System.out.println("❌ Wrong! Game Over.");
                    System.out.println("💰 You won ₹" + player.cashPrize);
                    return;
                }
            }
        } finally {
            scanner.close();
            System.out.println("\n🔚 Game Over. Thanks for playing!");
        }
    }
}
