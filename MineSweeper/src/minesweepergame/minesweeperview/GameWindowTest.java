package minesweepergame.minesweeperview;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class GameWindowTest {
	GameWindow window;
	@Before
	public void setup() {
		window = new GameWindow(new MenuWindow());
	}
	@Test
	public void testStartNewGame() {
		String expectedname = "Test one";
		window.startnewgame(false, expectedname);
		String actualname = window.getMineField().getGame().getPlayerName();
		assertEquals(expectedname, actualname);
	}
}
