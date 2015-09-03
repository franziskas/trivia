package com.adaptionsoft.games.trivia;

import java.util.Random;
import com.adaptionsoft.games.uglytrivia.Game;
import junit.framework.TestCase;
import org.junit.Test;

public class GoldenMaster {
    @Test
    public void execute_one_game() {
        Game aGame = new Game();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = new Random();

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
