import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> playersNames = new ArrayList<>();
        Map<String, Integer> playerScores = new HashMap<>();

        StringBuilder hiddenWord = new StringBuilder();

        Random random = new Random();

        String[] words = {"elephant", "sunflower", "adventure", "technology", "giraffe", "mysterious", "bicycle", "pizza", "symphony", "penguin"};
        String[] hints = {"Hint: Largest land mammal.", "Hint: Yellow flower with a large central disk.", " Hint: Exciting experience or journey.", "Hint: Application of scientific knowledge for practical purposes.", "Hint: Tallest living terrestrial animal.", "Hint: Difficult to understand or explain.", "Hint: Two-wheeled vehicle powered by pedals.", "Hint: Italian dish with a flattened, round base of leavened wheat-based dough.", "Hint: Elaborate musical composition for a full orchestra.", "Hint: Flightless bird that lives in the Southern Hemisphere."};


        System.out.print("\033[1;34m" + "Welcome to the game! \nPlease write the number of players: " + "\033[0m");

        int playersNumber = scanner.nextInt();
        System.out.println("\033[1;34m" + "Thank you. Now please write your names: " + "\033[0m");


        String name = "";
        for (int i = 0; i < playersNumber; i++) {
            System.out.print(i + 1 + ".");
            name = scanner.next();
            playersNames.add(name);
            playerScores.put(name, 0);
        }
        clean();
        Collections.shuffle(playersNames);
        System.out.println("\033[1;34m" + "This is the list of your turns. Please, keep it in your mind. " + "\033[0m");

        for (int i = 0; i < playersNumber; i++) {
            System.out.println((i + 1) + ". " + playersNames.get(i));
        }
        Thread.sleep(5000);
        clean();
        System.out.println("\033[1;34m" + "Are you ready to start?" + "\033[0m");
        String readyAnswer = scanner.next();
        clean();
        int randomNumber = random.nextInt(10);

        String hiddenToString = "";
        for (int i = 0; i < words[randomNumber].length(); i++) {
            hiddenWord.append("⬛");
            hiddenToString = hiddenWord.toString();
        }


        String winnerFromWholeWord = "";
        while (hiddenToString.contains("⬛")&& playersNumber > 0) {
            for (int i = 0; i < playersNumber; i++) {

                int consoleWidth = 130;
                int padding = Math.max(0, (consoleWidth - hints[randomNumber].length()) / 2);
                System.out.print(" ".repeat(padding));
                System.out.println("\033[1;34m" + hints[randomNumber] + "\033[0m");



                int consoleWidth2 = 120;
                int padding2 = Math.max(0, (consoleWidth - hiddenToString.length()) / 2);
                System.out.print(" ".repeat(padding2));

                System.out.println(hiddenToString);

                System.out.println(playersNames.get(i) + " - it is your turn to guess the word.");

                String letter = scanner.next();
                if (letter.length() == 1) {
                    if (words[randomNumber].contains(letter)) {
                        if (!hiddenWord.toString().contains(letter)) {
                            int index = words[randomNumber].indexOf(letter);
                            while (index != -1) {
                                hiddenWord.setCharAt(index, letter.charAt(0));
                                index = words[randomNumber].indexOf(letter, index + 1);
                                int currentScore = playerScores.get(playersNames.get(i));
                                playerScores.put(playersNames.get(i), currentScore + 100);
                            }
                            hiddenToString = hiddenWord.toString();

                            printHead(hints[randomNumber], hiddenToString);
                            System.out.println("\033[32m" + "Super!!! " + playersNames.get(i) + ", you have found correctly letter " + letter + "!!! Your points - " + playerScores.get(playersNames.get(i)) + "\033[0m");



                            Thread.sleep(2000);
                            clean();

                            if (!hiddenToString.contains("⬛")) {
                                break;
                            }
                            i--;
                        } else {
                            printHead(hints[randomNumber], hiddenToString);
                            System.out.println("\033[31m" + "This letter has already been found. Turn is going to the next player" + "\033[0m");
                            Thread.sleep(3000);
                            clean();
                        }
                    } else {
                        printHead(hints[randomNumber], hiddenToString);
                        System.out.println("\033[31m" + "Unfortunately, this word does not contain this letter.Turn is going to the next player" + "\033[0m");
                        Thread.sleep(3000);
                        clean();
                    }
                } else {
                    if (!letter.equals(words[randomNumber])) {
                        printHead(hints[randomNumber], hiddenToString);
                        System.out.println("\033[31m" + playersNames.get(i) + ", you tried to guess the whole word. But it was wrong." + "\033[0m");



                        Thread.sleep(3000);
                        clean();

                        boolean moreThanHalf = true;
                        if(playerScores.get(playersNames.get(i))<= words[randomNumber].length()*100/2){
                            moreThanHalf = false;

                        }


                        if (moreThanHalf) {
                            playersNames.remove(playersNames.get(i));
                            playersNumber--;

                            for (int in = 0; in < playersNumber; in++) {

                                System.out.println(hints[randomNumber]);
                                System.out.println(hiddenToString);
                                System.out.println(playersNames.get(in) + " - it is your turn to guess the word.You need to write whole word");

                                letter = scanner.next();
                                if (letter.equals(words[randomNumber])) {
                                    int currentScore = playerScores.get(playersNames.get(in));
                                    int additionalPoints = 0;

                                    for (int ind = 0; ind < hiddenToString.length(); ind++) {
                                        if (hiddenToString.charAt(ind) == '⬛') {
                                            additionalPoints += 100;
                                        }
                                    }
                                    hiddenWord.setLength(0);
                                    hiddenWord.append(words[randomNumber]);
                                    hiddenToString = hiddenWord.toString();
                                    printHead(hints[randomNumber], hiddenToString);

                                    playerScores.put(playersNames.get(i), currentScore + additionalPoints);
                                    System.out.println("\033[32m" + playersNames.get(i) + ", you tried to guess the whole word. And you found. Congratulations!!!" + "\033[0m");

                                    winnerFromWholeWord = playersNames.get(i);



                                    Thread.sleep(3000);
                                    clean();
                                    i = playersNumber;
                                    break;
                                } else {
                                    printHead(hints[randomNumber], hiddenToString);
                                    System.out.println("\033[31m" + playersNames.get(i) + ", you tried to guess the whole word. But it was wrong.You lose" + "\033[0m");

                                    i++;
                                    Thread.sleep(3000);
                                    clean();

                                }


                            }
                            playersNumber = 0;
                            break;

                        }
                        else {
                            playersNames.remove(playersNames.get(i));

                            playersNumber--;

                        }
                    } else {
                        int currentScore = playerScores.get(playersNames.get(i));
                        int additionalPoints = 0;

                        for(int in = 0; in < hiddenToString.length(); in++){
                            if(hiddenToString.charAt(in) == '⬛'){
                                additionalPoints+=100;
                            }
                        }
                        hiddenWord.setLength(0);
                        hiddenWord.append(words[randomNumber]);
                        hiddenToString = hiddenWord.toString();
                        printHead(hints[randomNumber], hiddenToString);


                        playerScores.put(playersNames.get(i), currentScore + additionalPoints);

                        System.out.println("\033[32m" + playersNames.get(i) + ", you tried to guess the whole word. And you found. Congratulations!!!" + "\033[0m");

                        winnerFromWholeWord = playersNames.get(i);
                        Thread.sleep(3000);
                        clean();
                        i = playersNumber;
                        break;



                    }
                }
            }
        }
        displayScores(playerScores, winnerFromWholeWord);
        System.out.println("\033[32m" + "Game over!" + "\033[0m");


    }

    public static void clean() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printHead(String hints, String hiddenToString) {
        clean();

        int consoleWidth = 130;
        int padding = Math.max(0, (consoleWidth - hints.length()) / 2);
        System.out.print(" ".repeat(padding));
        System.out.println("\033[1;34m" + hints + "\033[0m");


        int consoleWidth2 = 120;
        int padding2 = Math.max(0, (consoleWidth - hiddenToString.length()) / 2);
        System.out.print(" ".repeat(padding2));


        System.out.println(hiddenToString);
    }

    public static void displayScores(Map<String, Integer> playerScores, String winnerFromWholeWord) {
        System.out.println("\033[32m" + "Final Scores:" + "\033[0m");
        int maxScore = -1;
        String winner = "";

        for (String playerName : playerScores.keySet()) {
            int score = playerScores.get(playerName);
            System.out.println(playerName + ": " + score + " points");

            if (score > maxScore) {
                maxScore = score;
                winner = playerName;
            }
        }
        if(winnerFromWholeWord.length() > 0){
            System.out.println("\033[32m" + "Winner: " + winnerFromWholeWord + " , because this player found whole word" + "\033[0m");
        }else{
            System.out.println("\033[32m" + "Winner: " + winner + " with " + maxScore + " points" + "\033[0m");

        }


    }


}
