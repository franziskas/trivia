package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.trivia.Printer;

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
    private final Printer printer;

    ArrayList players = new ArrayList();
    int[] places = new int[MAXIMUM_AMOUNT_OF_PLAYERS];
    int[] numberOfGoldCoins = new int[MAXIMUM_AMOUNT_OF_PLAYERS];
    boolean[] inPenaltyBox = new boolean[MAXIMUM_AMOUNT_OF_PLAYERS];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game(Printer printer) {
        this.printer = printer;
        for (int index = 0; index < NUMBER_OF_QUESTIONS; index++) {
            popQuestions.addLast(CATEGORY_POP + " Question " + index);
            scienceQuestions.addLast((CATEGORY_SCIENCE + " Question " + index));
            sportsQuestions.addLast((CATEGORY_SPORTS + " Question " + index));
            rockQuestions.addLast(createRockQuestion(index));
        }
    }

    public String createRockQuestion(int index) {
        return CATEGORY_ROCK + " Question " + index;
    }

    public boolean isPlayable() {
        return (numberOfPlayers() >= MINIMUM_AMOUNT_OF_PLAYERS);
    }

    public boolean add(String playerName) {


        players.add(playerName);
        places[numberOfPlayers()] = 0;
        numberOfGoldCoins[numberOfPlayers()] = 0;
        inPenaltyBox[numberOfPlayers()] = false;

        print(playerName + " was added");
        print("They are player number " + players.size());
        return true;
    }

    public int numberOfPlayers() {
        return players.size();
    }

    public void executeMove(int roll) {
        String output = players.get(currentPlayer) + " is the current player";
        print(output);
        print("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            boolean rollIsOdd = roll % MINIMUM_AMOUNT_OF_PLAYERS != 0;

            if (rollIsOdd) {
                playerGetsOutOfPenaltyBox();

                moveThePlayerAndAskTheNextQuestion(roll);
            } else {
                playerDoesNotGetOutOfPenaltyBox();
            }

        } else {
            moveThePlayerAndAskTheNextQuestion(roll);
        }

    }

    private void moveThePlayerAndAskTheNextQuestion(int roll) {
        moveTheCurrentPlayer(roll);

        print("The category is " + currentCategory());
        askQuestion();
    }

    private void moveTheCurrentPlayer(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        boolean endOfTheBoardIsReached = places[currentPlayer] > 11;
        if (endOfTheBoardIsReached) places[currentPlayer] = places[currentPlayer] - 12;

        print(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
    }

    private void playerDoesNotGetOutOfPenaltyBox() {
        print(players.get(currentPlayer) + " is not getting out of the penalty box");
        isGettingOutOfPenaltyBox = false;
    }

    private void playerGetsOutOfPenaltyBox() {
        isGettingOutOfPenaltyBox = true;
        print(players.get(currentPlayer) + " is getting out of the penalty box");
    }

    private void askQuestion() {
        if (currentCategory() == CATEGORY_POP)
            print(popQuestions.removeFirst());
        if (currentCategory() == CATEGORY_SCIENCE)
            print(scienceQuestions.removeFirst());
        if (currentCategory() == CATEGORY_SPORTS)
            print(sportsQuestions.removeFirst());
        if (currentCategory() == CATEGORY_ROCK)
            print(rockQuestions.removeFirst());
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

    public boolean determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                print("Answer was correct!!!!");
                numberOfGoldCoins[currentPlayer]++;
                print(players.get(currentPlayer)
                        + " now has "
                        + numberOfGoldCoins[currentPlayer]
                        + " Gold Coins.");

                boolean winner = noWinnerYet();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }


        } else {

            print("Answer was corrent!!!!");
            numberOfGoldCoins[currentPlayer]++;
            print(players.get(currentPlayer)
                    + " now has "
                    + numberOfGoldCoins[currentPlayer]
                    + " Gold Coins.");

            boolean noWinnerYet = noWinnerYet();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return noWinnerYet;
        }
    }

    public boolean movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer() {
        print("Question was incorrectly answered");
        print(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean noWinnerYet() {
        return !(numberOfGoldCoins[currentPlayer] == MAXIMUM_AMOUNT_OF_PLAYERS);
    }

    private void print(Object output) {
        printer.print(output);
    }
}
