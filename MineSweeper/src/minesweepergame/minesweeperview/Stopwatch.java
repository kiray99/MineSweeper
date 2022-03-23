package minesweepergame.minesweeperview;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Stopper �ra oszt�ly, m�ri az id�t m�sodpercekben, �s megjelen�ti azt,
 * az �ra vez�rl�s�hez sz�ks�ges met�dusokat tartalmazza
 *
 */
public class Stopwatch extends JLabel implements ActionListener{
	/**
	 * Az �r�n eltelt id�
	 */
	private int count;
	/**
	 * Timer ami az id� m�r�s�t v�gzi
	 */
	private Timer tm;
	
	/**
	 * Az Oszt�ly egy param�teres konstruktora
	 * L�tre hozza a sz�ks�ges elemeket �s be�ll�tja az �ra id�z�t�s�t
	 * @param delay
	 * 		jelz�sek k�z�tt eltelt id� milisecundomban m�rve
	 */
	public Stopwatch(int delay) {
		count = 0;
		tm = new Timer(delay, this);
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		this.setForeground(Color.red);
		this.setText(formatString_minsec());
	}
	
		/**
		 * Az �ra �ltal m�rt id�t a megfelel� form�tumba konvert�lja
		 * @return
		 * 		sz�veg megfelel� form�tumban
		 */
		private String formatString_minsec() {
		int min = count/60;
		int sec = count%60;
		return String.format("%02d:", min) + String.format("%02d", sec);
	}
		
	/**
	 * Az id�m�r� jelz�sekor friss�ti az �ra �ll�s�t
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		count++;
		this.setText(formatString_minsec());
	}
	
	/**
	 * Elind�tja az id�m�r�t �s mejelen�ti a kezd� id�t
	 */
	public void startTimer() {
		this.setText(formatString_minsec());
		tm.start();
	}
	
	/**
	 * Lenull�zza, elind�tja az id�m�r�t �s mejelen�ti a kezd� id�t
	 */
	public void restartTimer() {
		count = 0;
		this.setText(formatString_minsec());
		tm.restart();
	}
	
	/**
	 * Meg�ll�tja az id�m�r�t
	 */
	public void stopTimer() {
		tm.stop();
	}
	
	
	/**
	 * Be�ll�tja az �ra jelenlegi idej�t a tetsz�leges �rt�kre
	 * @param n
	 * 		a be�ll�tani k�v�nt �rt�k
	 */
	public void setCount(int n) {
		count = n;
	}
	
	/**
	 * Visszat�r az �ra aktu�lis idej�vel
	 * @return count
	 * 		az �ra ideje
	 */
	public int getCount() {
		return count;
	}
	


}
