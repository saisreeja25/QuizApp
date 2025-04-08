import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Question {
	    String questionText;
	    String[] options;
	    int correctAnswer;
	    boolean fiftyFiftyUsed = false;
	    List<Integer> reducedOptionsIndexes;

	    Question(String questionText, String[] options, int correctAnswer) {
	        this.questionText = questionText;
	        this.options = options;
	        this.correctAnswer = correctAnswer;
	    }

	    void displayQuestion() {
	        System.out.println("\n " + questionText);
	        if (fiftyFiftyUsed) {
	            System.out.println(" 50-50 Applied! Choose from:");
	            for (int index : reducedOptionsIndexes) {
	                System.out.println((index + 1) + ". " + options[index]);
	            }
	        } else {
	            for (int i = 0; i < options.length; i++) {
	                System.out.println((i + 1) + ". " + options[i]);
	            }
	        }
	    }

	    void applyFiftyFifty() {
	        List<Integer> wrongOptions = new ArrayList<>();
	        for (int i = 0; i < options.length; i++) {
	            if (i + 1 != correctAnswer) wrongOptions.add(i);
	        }

	        Collections.shuffle(wrongOptions);
	        reducedOptionsIndexes = new ArrayList<>();
	        reducedOptionsIndexes.add(correctAnswer - 1);
	        reducedOptionsIndexes.add(wrongOptions.get(0));

	        fiftyFiftyUsed = true;
	    }

	    void audiencePoll() {
	        Random rand = new Random();
	        int correctPercentage = rand.nextInt(41) + 40; // Correct answer: 40-80%
	        int remaining = 100 - correctPercentage;

	        int[] wrongPercentages = new int[3];
	        wrongPercentages[0] = rand.nextInt(remaining / 2);
	        wrongPercentages[1] = rand.nextInt(remaining - wrongPercentages[0]);
	        wrongPercentages[2] = remaining - wrongPercentages[0] - wrongPercentages[1];

	        int[] pollResults = new int[4];
	        pollResults[correctAnswer - 1] = correctPercentage;

	        int wrongIndex = 0;
	        for (int i = 0; i < 4; i++) {
	            if (i != correctAnswer - 1) {
	                pollResults[i] = wrongPercentages[wrongIndex++];
	            }
	        }

	        System.out.println("\n Audience Poll Results:");
	        for (int i = 0; i < options.length; i++) {
	            System.out.println((i + 1) + ". " + options[i] + " - " + pollResults[i] + "%");
	        }
	    }
	
}
