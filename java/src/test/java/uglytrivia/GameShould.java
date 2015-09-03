package uglytrivia;

import java.util.ArrayList;
import java.util.List;
import com.adaptionsoft.games.trivia.Printer;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameShould {
    private Game game;
    private List<String> printedLines;
    private Printer printer = new Printer() {
        @Override
        public void print(Object objectToPrint) {
            printedLines.add(objectToPrint.toString());
        }
    };

    @Before
    public void initialise() {
        printedLines = new ArrayList<String>();
        game = new Game(printer);
    }

    @Test
    public void
    has_no_players_initially() {
        assertThat(game.numberOfPlayers(), is(0));
    }
}
