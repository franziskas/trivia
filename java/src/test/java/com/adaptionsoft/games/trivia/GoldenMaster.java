package com.adaptionsoft.games.trivia;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;

public class GoldenMaster {
    @Test
    public void run_golden_master() throws FileNotFoundException {
        writeOutputTo("master.txt");

        runGameTimes(500);
    }

    private void writeOutputTo(String filename) throws FileNotFoundException {
        System.setOut(new PrintStream(new FileOutputStream(filename)));
    }

    private void runGameTimes(int numberOfGamesRun) {
        for (int seed = 0; seed < numberOfGamesRun; seed++) {
            Random rand = new Random(seed);
            runOneGame(rand);
        }
    }

    private void runOneGame(Random rand) {
        Game aGame = createGame();

        boolean notAWinner;
        do {
            aGame.executeMove(rand.nextInt(5) + 1);
            notAWinner = checkIfWinner(answerWasWrong(rand), aGame);

        } while (notAWinner);
    }

    private boolean checkIfWinner(boolean answerWasWrong, Game aGame) {
        if (answerWasWrong)
            return aGame.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();
        return aGame.determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer();
    }

    private boolean answerWasWrong(Random rand) {
        return rand.nextInt(9) == 7;
    }

    private Game createGame() {
        Game aGame = new Game();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        return aGame;
    }
}
