package uglytrivia;

import com.adaptionsoft.games.trivia.Printer;
import com.adaptionsoft.games.uglytrivia.Game;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class GameShould {
    private Game game;
    private List<String> printedLines;
    private Printer printer = new Printer() {
        @Override
        public void print(Object objectToPrint) {
            printedLines.add(objectToPrint.toString());
        }
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initialise() {
        printedLines = new ArrayList<>();
        game = new Game(printer);
    }

    @Test
    public void
    has_no_players_initially() {
        assertThat(game.numberOfPlayers(), is(0));
    }

    @Test
    @Parameters({"0", "1", "2", "3", "4", "5"})
    public void
    counts_added_Players(Integer numberOfPlayers) {
        addPlayers(numberOfPlayers);

        assertThat(game.numberOfPlayers(), is(numberOfPlayers));
    }

    @Test
    public void
    throws_an_exception_for_more_than_5_players() {
        addPlayers(5);

        thrown.expect(ArrayIndexOutOfBoundsException.class);

        game.add("oneToMuch");
    }


    @Test
    @Parameters({"0,false", "1,false", "2,true", "3,true", "4,true"})
    public void
    be_playable_with_at_least_two_players(Integer numberOfPlayers, boolean isPlayable) {
        addPlayers(numberOfPlayers);

        assertThat(game.isPlayable(), is(isPlayable));
    }

    @Test
    public void
    prints_added_players() {
        addPlayers(1);

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1"));
    }

    @Test
    @Parameters({"0,Pop", "1,Science", "2,Sports", "3,Rock"})
    public void
    prints_game_moves_and_asks_categorised_questions(int rolled, String category) {
        addPlayers(2);

        game.executeMove(rolled);

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1",
                "player2 was added",
                "They are player number 2",
                "player1 is the current player",
                "They have rolled a " + rolled,
                "player1's new location is " + rolled,
                "The category is " + category,
                category + " Question 0"
        ));
    }

    @Test
    public void
    awards_player_coins_after_correct_answer() {
        addPlayers(2);

        game.determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer();

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1",
                "player2 was added",
                "They are player number 2",
                "Answer was corrent!!!!",
                "player1 now has 1 Gold Coins."
        ));
    }

    @Test
    public void
    awards_player_no_coins_after_correct_answer_following_a_wrong_one() {
        addPlayers(2);

        game.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();
        game.determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer();
        game.determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer();

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1",
                "player2 was added",
                "They are player number 2",
                "Question was incorrectly answered",
                "player1 was sent to the penalty box",
                "Answer was corrent!!!!",
                "player2 now has 1 Gold Coins."
        ));
    }

    @Test
    public void
    moves_on_to_next_player_after_correct_answer() {
        addPlayers(2);

        game.determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer();
        game.determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer();

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1",
                "player2 was added",
                "They are player number 2",
                "Answer was corrent!!!!",
                "player1 now has 1 Gold Coins.",
                "Answer was corrent!!!!",
                "player2 now has 1 Gold Coins."
        ));
    }

    @Test
    @Parameters({"1,true", "2,true", "3,true", "4,true", "5,true", "6,false"})
    public void
    determines_if_player_won_after_six_correct_answers(int numberOfCorrectAnswersForEachPlayer, boolean noWinner) {
        addPlayers(2);


        Boolean noWinnerYet = true;
        for (int number = 0; number < numberOfCorrectAnswersForEachPlayer; number++) {
            noWinnerYet = game.determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer();
            game.determineIfPlayerWonAfterCorrectAnswerAndMoveOnToNextPlayer();
        }

        assertThat(noWinnerYet, is(noWinner));
    }

    @Test
    public void
    sends_player_to_penalty_box_after_wrong_answer() {
        addPlayers(2);

        game.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1",
                "player2 was added",
                "They are player number 2",
                "Question was incorrectly answered",
                "player1 was sent to the penalty box"
        ));
    }

    @Test
    public void
    moves_on_to_next_player_after_wrong_answer() {
        addPlayers(2);

        game.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();
        game.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1",
                "player2 was added",
                "They are player number 2",
                "Question was incorrectly answered",
                "player1 was sent to the penalty box",
                "Question was incorrectly answered",
                "player2 was sent to the penalty box"
        ));
    }

    @Test
    public void
    executes_no_move_if_in_penalty_box_and_even_roll() {
        addPlayers(2);

        game.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();
        game.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();
        game.executeMove(2);

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1",
                "player2 was added",
                "They are player number 2",
                "Question was incorrectly answered",
                "player1 was sent to the penalty box",
                "Question was incorrectly answered",
                "player2 was sent to the penalty box",
                "player1 is the current player",
                "They have rolled a 2",
                "player1 is not getting out of the penalty box"
        ));
    }

    @Test
    public void
    executes_move_if_in_penalty_box_and_odd_roll() {
        addPlayers(2);

        game.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();
        game.movePlayerInPenaltyBoxAfterWrongAnswerAndMoveOnToNextPlayer();
        game.executeMove(3);

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1",
                "player2 was added",
                "They are player number 2",
                "Question was incorrectly answered",
                "player1 was sent to the penalty box",
                "Question was incorrectly answered",
                "player2 was sent to the penalty box",
                "player1 is the current player",
                "They have rolled a 3",
                "player1 is getting out of the penalty box",
                "player1's new location is 3",
                "The category is Rock",
                "Rock Question 0"
        ));
    }

    private void addPlayers(int numberOfPlayersToAdd) {
        for (int playerNumber = 0; playerNumber < numberOfPlayersToAdd; playerNumber++) {
            game.add("player" + (playerNumber + 1));
        }
    }
}
