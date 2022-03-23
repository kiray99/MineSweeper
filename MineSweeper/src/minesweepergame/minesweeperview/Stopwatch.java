package minesweepergame.minesweeperview;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Stopper óra osztály, méri az idõt másodpercekben, és megjeleníti azt,
 * az óra vezérléséhez szükséges metódusokat tartalmazza
 *
 */
public class Stopwatch extends JLabel implements ActionListener{
	/**
	 * Az órán eltelt idõ
	 */
	private int count;
	/**
	 * Timer ami az idõ mérését végzi
	 */
	private Timer tm;
	
	/**
	 * Az Osztály egy paraméteres konstruktora
	 * Létre hozza a szükséges elemeket és beállítja az óra idõzítését
	 * @param delay
	 * 		jelzések között eltelt idõ milisecundomban mérve
	 */
	public Stopwatch(int delay) {
		count = 0;
		tm = new Timer(delay, this);
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		this.setForeground(Color.red);
		this.setText(formatString_minsec());
	}
	
		/**
		 * Az óra által mért idõt a megfelelõ formátumba konvertálja
		 * @return
		 * 		szöveg megfelelõ formátumban
		 */
		private String formatString_minsec() {
		int min = count/60;
		int sec = count%60;
		return String.format("%02d:", min) + String.format("%02d", sec);
	}
		
	/**
	 * Az idõmérõ jelzésekor frissíti az óra állását
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		count++;
		this.setText(formatString_minsec());
	}
	
	/**
	 * Elindítja az idõmérõt és mejeleníti a kezdõ idõt
	 */
	public void startTimer() {
		this.setText(formatString_minsec());
		tm.start();
	}
	
	/**
	 * Lenullázza, elindítja az idõmérõt és mejeleníti a kezdõ idõt
	 */
	public void restartTimer() {
		count = 0;
		this.setText(formatString_minsec());
		tm.restart();
	}
	
	/**
	 * Megállítja az idõmérõt
	 */
	public void stopTimer() {
		tm.stop();
	}
	
	
	/**
	 * Beállítja az óra jelenlegi idejét a tetszõleges értékre
	 * @param n
	 * 		a beállítani kívánt érték
	 */
	public void setCount(int n) {
		count = n;
	}
	
	/**
	 * Visszatér az óra aktuális idejével
	 * @return count
	 * 		az óra ideje
	 */
	public int getCount() {
		return count;
	}
	


}
