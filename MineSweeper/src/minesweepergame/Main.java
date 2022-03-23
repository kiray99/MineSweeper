package minesweepergame;

import minesweepergame.minesweeperview.MenuWindow;


/**
 * Main osztály, innen indul a program
 *
 */
public class Main {

	/**
	 * main metódus, a program belépési pontja
	 */
	public static void main(String[] args) {
		MenuWindow startwindow = new MenuWindow();
		startwindow.setVisible(true);
	}

}
