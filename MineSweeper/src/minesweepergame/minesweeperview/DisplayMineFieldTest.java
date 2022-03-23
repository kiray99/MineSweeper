package minesweepergame.minesweeperview;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import minesweepergame.minesweepermodell.Game;

public class DisplayMineFieldTest {
	Game game;
	DisplayMineField field;
	String playername;
	@Before
	public void setup() {
		playername = "player";
		game = new Game(playername);
		game.save("mentes.txt");
	}
	@Test
	public void testLoadGame() {
		field = new DisplayMineField(new GameWindow(new MenuWindow()),true, playername);
		assertEquals(game.getPlayerName(), field.getGame().getPlayerName());
	}
}
