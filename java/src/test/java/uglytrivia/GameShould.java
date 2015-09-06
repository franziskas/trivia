package uglytrivia;

import java.util.ArrayList;
import java.util.List;
import com.adaptionsoft.games.trivia.Printer;
import com.adaptionsoft.games.uglytrivia.Game;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

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
    public void
    prints_game_moves() {
        addPlayers(2);
        int rolled = 1;

        game.executeMove(rolled);

        assertThat(printedLines, contains(
                "player1 was added",
                "They are player number 1",
                "player2 was added",
                "They are player number 2",
                "player1 is the current player",
                "They have rolled a " + rolled,
                "player1's new location is " + rolled,
                "The category is Science",
                "Science Question 0"
        ));
    }

    private void addPlayers(int numberOfPlayersToAdd) {
        for (int playerNumber = 0; playerNumber < numberOfPlayersToAdd; playerNumber++) {
            game.add("player" + (playerNumber + 1));
        }
    }
}
