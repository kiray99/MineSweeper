package minesweepergame.minesweepercontroller;

import minesweepergame.minesweeperview.DisplayMineField;
import minesweepergame.minesweeperview.GameWindow;
import minesweepergame.minesweeperview.MenuWindow;

public class StartScreen {
	MenuWindow startwindow;
	GameWindow gamewindow;
	DisplayMineField mindefilddisplay;


	public StartScreen() {
		startwindow = new MenuWindow();
		startwindow.setVisible(true);
	}

}
