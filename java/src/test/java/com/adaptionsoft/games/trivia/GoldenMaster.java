package com.adaptionsoft.games.trivia;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Random;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;

import static java.nio.file.Files.readAllBytes;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GoldenMaster {

    private static final String TESTRUN_FILE_NAME = "testrun.txt";
    private static final String MASTER_FILE_NAME = "master.txt";

    @Test
    public void run_golden_master() throws IOException {
        writeOutputTo(TESTRUN_FILE_NAME);

        runGameTimes(500);

        assertThat(readFile(TESTRUN_FILE_NAME), is(readFile(MASTER_FILE_NAME)));
    }

    private String readFile(String filename) throws IOException {
        return new String(readAllBytes(Paths.get(filename)));
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
        Game aGame = new Game(new ConsolePrinter());

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        return aGame;
    }
}
