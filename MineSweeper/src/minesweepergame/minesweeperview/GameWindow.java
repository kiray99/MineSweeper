package minesweepergame.minesweeperview;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * A játék közben megjelenõ ablak, rajta minden elemmel
 *
 */
public class GameWindow extends JFrame{
	/**
	 * Az aktív játékos neve
	 */
	private String name;
	/**
	 * A stopper órát és játékosnevet tartalmazó panel
	 */
	private JPanel watchlabel;
	/**
	 * A gombokat tartalmazó panel
	 */
	private JPanel buttonpanel;
	/**
	 * A játékot tartalmazó panel
	 */
	private JPanel gamepanel;
	/**
	 * A játékos nevét megjelenítõ címke
	 */
	private JLabel nameLabel;
	/**
	 * Gomb új játék kezdésére
	 */
	private JButton ujjatekButton;
	/**
	 * Gomb a menübe való vissza térésre
	 */
	private JButton visszaButton;
	/**
	 * Gomb mentésre és vissza lépésre a fõmenübe
	 */
	private JButton kilepesButton;
	/**
	 * Gratulációt megjelenítõ címke
	 */
	private JLabel victoryLabel;
	/**
	 * Az az elem ahol a játék megjelenik
	 */
	private DisplayMineField field;
	/**
	 * Az idõ mérésére szolgáló elem
	 */
	private Stopwatch watch;
	/**
	 * A hívó menüt jelölõ attribútum
	 */
	private MenuWindow menuwindow;
    
    /**
     * Két paraméteres konstruktor
     * Létre hozza a megjelenítõ ablakot
	 * Meghívja az inicializáló [Init()] metódust
     * @param menuwindow
     * 		a hívó osztály referenciája 		
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
	 * Inicializáló metódus
	 * Konstruktorban hívódik meg
	 * Létrehozza és beállítja az ablakon megjelenõ elemeket
	 */
	private void Init() {
		
		watch = new Stopwatch(1000);
		watchlabel = new JPanel();
		watchlabel.add(watch);
		victoryLabel = new JLabel("Gratulálok!!");
		victoryLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		victoryLabel.setVisible(false);
		watchlabel.add(victoryLabel);
		super.add(watchlabel, BorderLayout.NORTH);
		
		ujjatekButton = new JButton("Új Játék");
		ujjatekButton.addActionListener(new UjJatekButtonListener());
		visszaButton = new JButton("Vissza a Fõmenübe");
		visszaButton.addActionListener(new VisszaButtonListener());
		kilepesButton = new JButton("Mentés és Kilépés");
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
	 * Új játék indítása
	 * játékosnév és játéktér megjelnítése
	 * Stopper újraindítása
	 * @param load
	 * 		a játék mentett adatokból töltõdik-e be
	 * @param name
	 * 		a játékot kezdõ játékos neve
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
	 * A játék megnyerésekor meghívandó metódus
	 * megállítja a stoppert és megjeleníti a gratuláló szöveget
	 */
	public void gamefinished() {
		victoryLabel.setVisible(true);
		watch.stopTimer();
	}
	
	/**
	 * Visszaadja a stopper óra pillanatnyi idejét
	 * @return
	 * 		a stopperóra ideje
	 */
	public int getTimeFromWatch() {
		return watch.getCount();
	}
	
	/**
	 * Visszatér a tartalmazott DisplayMineField péndányával
	 * @return field
	 * 		a tartalmazott DisplayMineField péndánya
	 */
	public DisplayMineField getMineField() {
		return field;
	}
	
	/**
	 * Beállítja a stopperóra kezdõ értékét
	 * @param n
	 * 		a kiindulási idõ másodpercben
	 */
	public void setTimeForWatch(int n) {
		watch.setCount(n);
	}
	
	/**
	 * Listener osztály a "Új Játék" gomb lenyomásának érzékelésére
	 * Gomb lenyomásakor új játék indítása
	 */
	private class UjJatekButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			startnewgame(false, name);
		}
	}
	
	/**
	 * Listener osztály a "Vissza a Fõmenübe" gomb lenyomásának érzékelésére
	 * Gomb lenyomásakor elrejti a játék ablakot és megjeleníti a menüt
	 */
	private class VisszaButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.setVisible(false);
			menuwindow.setVisible(true);
		}
	}
	
	/**
	 * Listener osztály a "Mentés és Kilépés" gomb lenyomásának érzékelésére
	 * Gomb lenyomásakor játékállás mentése
	 * elrejti a játék ablakot és megjeleníti a menüt
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
