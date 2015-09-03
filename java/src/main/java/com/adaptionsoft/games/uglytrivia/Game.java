package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    public static final int MAXIMUM_AMOUNT_OF_PLAYERS = 6;
    public static final int NUMBER_OF_QUESTIONS = 50;
    private static final String CATEGORY_POP = "Pop";
    private static final String CATEGORY_SCIENCE = "Science";
    private static final String CATEGORY_SPORTS = "Sports";
    private static final String CATEGORY_ROCK = "Rock";
    private static final int MINIMUM_AMOUNT_OF_PLAYERS = 2;

    ArrayList players = new ArrayList();
    int[] places = new int[MAXIMUM_AMOUNT_OF_PLAYERS];
    int[] purses = new int[MAXIMUM_AMOUNT_OF_PLAYERS];
    boolean[] inPenaltyBox = new boolean[MAXIMUM_AMOUNT_OF_PLAYERS];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            popQuestions.addLast(CATEGORY_POP + " Question " + i);
            scienceQuestions.addLast((CATEGORY_SCIENCE + " Question " + i));
            sportsQuestions.addLast((CATEGORY_SPORTS + " Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return CATEGORY_ROCK + " Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= MINIMUM_AMOUNT_OF_PLAYERS);
    }

    public boolean add(String playerName) {


        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void executeMove(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % MINIMUM_AMOUNT_OF_PLAYERS != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                System.out.println(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        if (currentCategory() == CATEGORY_POP)
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == CATEGORY_SCIENCE)
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == CATEGORY_SPORTS)
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == CATEGORY_ROCK)
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return CATEGORY_POP;
        if (places[currentPlayer] == 4) return CATEGORY_POP;
        if (places[currentPlayer] == 8) return CATEGORY_POP;
        if (places[currentPlayer] == 1) return CATEGORY_SCIENCE;
        if (places[currentPlayer] == 5) return CATEGORY_SCIENCE;
        if (places[currentPlayer] == 9) return CATEGORY_SCIENCE;
        if (places[currentPlayer] == MINIMUM_AMOUNT_OF_PLAYERS) return CATEGORY_SPORTS;
        if (places[currentPlayer] == MAXIMUM_AMOUNT_OF_PLAYERS) return CATEGORY_SPORTS;
        if (places[currentPlayer] == 10) return CATEGORY_SPORTS;
        return CATEGORY_ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                purses[currentPlayer]++;
                System.out.println(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }


        } else {

            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == MAXIMUM_AMOUNT_OF_PLAYERS);
    }
}
