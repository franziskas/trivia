package com.adaptionsoft.games.trivia;

import java.util.Random;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;

public class GoldenMaster {
    @Test
    public void run_golden_master() {
        for (int seed = 0; seed < 500; seed++) {
            Random rand = new Random(seed);
            runOneGame(rand);
        }
    }

    private void runOneGame(Random rand) {
        Game aGame = new Game();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");


        boolean notAWinner;
        do {

            aGame.executeMove(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();
            } else {
                notAWinner = aGame.determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer();
            }


        } while (notAWinner);
    }
}
