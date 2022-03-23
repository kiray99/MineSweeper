package minesweepergame.minesweeperview;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * A j�t�k k�zben megjelen� ablak, rajta minden elemmel
 *
 */
public class GameWindow extends JFrame{
	/**
	 * Az akt�v j�t�kos neve
	 */
	private String name;
	/**
	 * A stopper �r�t �s j�t�kosnevet tartalmaz� panel
	 */
	private JPanel watchlabel;
	/**
	 * A gombokat tartalmaz� panel
	 */
	private JPanel buttonpanel;
	/**
	 * A j�t�kot tartalmaz� panel
	 */
	private JPanel gamepanel;
	/**
	 * A j�t�kos nev�t megjelen�t� c�mke
	 */
	private JLabel nameLabel;
	/**
	 * Gomb �j j�t�k kezd�s�re
	 */
	private JButton ujjatekButton;
	/**
	 * Gomb a men�be val� vissza t�r�sre
	 */
	private JButton visszaButton;
	/**
	 * Gomb ment�sre �s vissza l�p�sre a f�men�be
	 */
	private JButton kilepesButton;
	/**
	 * Gratul�ci�t megjelen�t� c�mke
	 */
	private JLabel victoryLabel;
	/**
	 * Az az elem ahol a j�t�k megjelenik
	 */
	private DisplayMineField field;
	/**
	 * Az id� m�r�s�re szolg�l� elem
	 */
	private Stopwatch watch;
	/**
	 * A h�v� men�t jel�l� attrib�tum
	 */
	private MenuWindow menuwindow;
    
    /**
     * K�t param�teres konstruktor
     * L�tre hozza a megjelen�t� ablakot
	 * Megh�vja az inicializ�l� [Init()] met�dust
     * @param menuwindow
     * 		a h�v� oszt�ly referenci�ja 		
     */
    public GameWindow(MenuWindow menuwindow) {
		super("Kiray's Minesweeper");
		this.menuwindow = menuwindow;
		setSize(600, 600);
		setLocation(500, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Init();
		setVisible(true);
		watch.startTimer();
	}
    
	/**
	 * Inicializ�l� met�dus
	 * Konstruktorban h�v�dik meg
	 * L�trehozza �s be�ll�tja az ablakon megjelen� elemeket
	 */
	private void Init() {
		
		watch = new Stopwatch(1000);
		watchlabel = new JPanel();
		watchlabel.add(watch);
		victoryLabel = new JLabel("Gratul�lok!!");
		victoryLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		victoryLabel.setVisible(false);
		watchlabel.add(victoryLabel);
		super.add(watchlabel, BorderLayout.NORTH);
		
		ujjatekButton = new JButton("�j J�t�k");
		ujjatekButton.addActionListener(new UjJatekButtonListener());
		visszaButton = new JButton("Vissza a F�men�be");
		visszaButton.addActionListener(new VisszaButtonListener());
		kilepesButton = new JButton("Ment�s �s Kil�p�s");
		kilepesButton.addActionListener(new MentKilepButtonListener());
		
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.X_AXIS));
		buttonpanel.add(Box.createHorizontalGlue());
		buttonpanel.add(ujjatekButton);
		buttonpanel.add(Box.createHorizontalGlue());
		buttonpanel.add(visszaButton);
		buttonpanel.add(Box.createHorizontalGlue());
		buttonpanel.add(kilepesButton);
		buttonpanel.add(Box.createHorizontalGlue());
		super.add(buttonpanel, BorderLayout.SOUTH);
        
		gamepanel = new JPanel();
        super.add(gamepanel, BorderLayout.CENTER);
	}
	
	/**
	 * �j j�t�k ind�t�sa
	 * j�t�kosn�v �s j�t�kt�r megjeln�t�se
	 * Stopper �jraind�t�sa
	 * @param load
	 * 		a j�t�k mentett adatokb�l t�lt�dik-e be
	 * @param name
	 * 		a j�t�kot kezd� j�t�kos neve
	 */
	public void startnewgame(Boolean load, String name) {
		this.name = name;
		if(field != null) {
			field.setVisible(false);
			gamepanel.remove(field);
		}
		field = new DisplayMineField(this, load, name);
		gamepanel.add(field);
		if(nameLabel != null)
			watchlabel.remove(nameLabel);
		nameLabel = new JLabel(field.getGame().getPlayerName());
		nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		watchlabel.add(nameLabel);
		if(!load)
			watch.restartTimer();
	}
	
	/**
	 * A j�t�k megnyer�sekor megh�vand� met�dus
	 * meg�ll�tja a stoppert �s megjelen�ti a gratul�l� sz�veget
	 */
	public void gamefinished() {
		victoryLabel.setVisible(true);
		watch.stopTimer();
	}
	
	/**
	 * Visszaadja a stopper �ra pillanatnyi idej�t
	 * @return
	 * 		a stopper�ra ideje
	 */
	public int getTimeFromWatch() {
		return watch.getCount();
	}
	
	/**
	 * Visszat�r a tartalmazott DisplayMineField p�nd�ny�val
	 * @return field
	 * 		a tartalmazott DisplayMineField p�nd�nya
	 */
	public DisplayMineField getMineField() {
		return field;
	}
	
	/**
	 * Be�ll�tja a stopper�ra kezd� �rt�k�t
	 * @param n
	 * 		a kiindul�si id� m�sodpercben
	 */
	public void setTimeForWatch(int n) {
		watch.setCount(n);
	}
	
	/**
	 * Listener oszt�ly a "�j J�t�k" gomb lenyom�s�nak �rz�kel�s�re
	 * Gomb lenyom�sakor �j j�t�k ind�t�sa
	 */
	private class UjJatekButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			startnewgame(false, name);
		}
	}
	
	/**
	 * Listener oszt�ly a "Vissza a F�men�be" gomb lenyom�s�nak �rz�kel�s�re
	 * Gomb lenyom�sakor elrejti a j�t�k ablakot �s megjelen�ti a men�t
	 */
	private class VisszaButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.setVisible(false);
			menuwindow.setVisible(true);
		}
	}
	
	/**
	 * Listener oszt�ly a "Ment�s �s Kil�p�s" gomb lenyom�s�nak �rz�kel�s�re
	 * Gomb lenyom�sakor j�t�k�ll�s ment�se
	 * elrejti a j�t�k ablakot �s megjelen�ti a men�t
	 */
	private class MentKilepButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			field.saveGame();
			GameWindow.this.setVisible(false);
			menuwindow.setVisible(true);
		}
	}
	
}
