package uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.adaptionsoft.games.trivia.Printer;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class GameShould {
    private final int numberOfPlayers;
    private Game game;
    private List<String> printedLines;
    private Printer printer = new Printer() {
        @Override
        public void print(Object objectToPrint) {
            printedLines.add(objectToPrint.toString());
        }
    };

    @Parameters(name = "{index}: create {0} players")
    public static Iterable<Object[]> numbers_from_0_to_5() {
        return Arrays.asList(new Object[][]{
                {0}, {1}, {2}, {3}, {4}, {5}
        });
    }

    public GameShould(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    @Before
    public void initialise() {
        printedLines = new ArrayList<String>();
        game = new Game(printer);
    }

    @Test
    public void
    counts_added_Players() {
        for (int playerNumber = 0; playerNumber < numberOfPlayers; playerNumber++) {
            game.add("player" + playerNumber);
        }
        assertThat(game.numberOfPlayers(), is(numberOfPlayers));
    }
}
