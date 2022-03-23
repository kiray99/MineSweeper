package minesweepergame.minesweepermodell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import minesweepergame.minesweeperview.DisplayMineField;
import minesweepergame.minesweeperview.GameWindow;
import minesweepergame.minesweeperview.MenuWindow;

public class GameTest {
	DisplayMineField field;
	Game game;
	Game game2;
	String name = "player";
	ArrayList<Field> fieldlist;
	@Before
	public void setup() {
		game = new Game(name);
		game.InitGame(10);
		fieldlist = new ArrayList<>();
		
		
	}
	@Test
	public void testInitGame() {
		int count = 0;
		assertEquals(false, game.getField(10).getBomb());
		for(int i = 0 ; i < 81 ; i++)
			fieldlist.add(game.getField(i));
		for(int i = 0 ; i < 81 ; i++)
			if(fieldlist.get(i).getBomb() == true)
				count++;
		assertEquals(10, count);
	}
	@Test
	public void testGameover() {
		game.gameover();
		for(int i = 0 ; i < 81 ; i++)
			fieldlist.add(game.getField(i));
		for(int i = 0 ; i < 81 ; i ++)
			assertEquals(true, fieldlist.get(i).getIsRevealed());	
	}
	@Test
	public void testReveal() {
		game.Reveal(-1, 0);
		assertEquals(0, game.getRevealedNumber());
		game.Reveal(0, 9);
		assertEquals(0, game.getRevealedNumber());
		game.Reveal(1, 1);
		assertEquals(true, game.getField(10).getIsRevealed());
	}
	@Test
	public void testSaveStatistics() {
		game.saveStatistics("testrangsor.txt");
		File file = new File("testrangsor.txt");
		Scanner sc;
		try {
			sc = new Scanner(file);
			assertEquals("0 sec player", sc.nextLine());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSave() {
		game.save("mentes.txt");
		field = new DisplayMineField(new GameWindow(new MenuWindow()), true, game.getPlayerName());
		assertEquals(game.getPlayerName(), field.getGame().getPlayerName());
		
	}
}
