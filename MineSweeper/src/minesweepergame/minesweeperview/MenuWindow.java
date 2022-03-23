package minesweepergame.minesweeperview;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 * Osztály a játék menü megjelenítésére
 *
 */
public class MenuWindow extends JFrame{
	/**
	 * A ranglistát tartalmazó panel
	 */
	private JPanel rankpanel;
	/**
	 * A néz bekéréséhez szükséges elemek tartalmazó panel
	 */
	private JPanel nameinput;
	/**
	 * A fõ irányító gombokat tartalmazó panel
	 */
	private JPanel buttonpanel;
	/**
	 * Gomb új játék kezdéséhez
	 */
	private JToggleButton ujjatekButton;
	/**
	 * Gomb játék folytatásához
	 */
	private JButton folytatasButton;
	/**
	 * Gomb a ranglista megjelenítéséhez
	 */
	private JToggleButton ranglistaButton;
	/**
	 * Gomb a program bezárásához
	 */
	private JButton exitButton;
	/**
	 * Gomb amellyel véglelgesíthetõ a névbevitel
	 */
	private JButton nameOKButton;
	/**
	 * Név bevitelét szolgáló szöveges beviteli mezõ
	 */
	private JTextField textfield;
	/**
	 * A ranglistát tartalmazó lista
	 */
	private JList<Object> list;
	/**
	 * A listát magába foglaló gögethetõ felület
	 */
	private JScrollPane scrolllist;
	
	/**
	 * Paraméter nélküli konstruktor
	 * Létre hozza a megjelenítõ ablakot
	 * Meghívja az inicializáló [Init()] metódust
	 */
	public MenuWindow() {
		super("Kiray's Minesweeper");
		setSize(400, 400);
		setLocation(500, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Init();
	}
	
	/**
	 * Inicializáló metódus
	 * Konstruktorban hívódik meg
	 * Létrehozza és beállítja az ablakon megjelenõ elemeket
	 */
	private void Init() {
		ujjatekButton = new JToggleButton("Új Játék");
		ujjatekButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ujjatekButton.addActionListener(new UjJatekButtonListener());
		
		folytatasButton = new JButton("Játék Folytatása");
		folytatasButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		folytatasButton.addActionListener(new FolytatasButtonListener());
		
		ranglistaButton = new JToggleButton("Ranglista");
		ranglistaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ranglistaButton.addActionListener(new RanglistaButtonListener());
		
		exitButton = new JButton("Kilépés");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new KilepesButtonListener());
		
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.Y_AXIS));
		buttonpanel.add(Box.createVerticalGlue());
		buttonpanel.add(ujjatekButton);
		buttonpanel.add(Box.createVerticalGlue());
		buttonpanel.add(folytatasButton);
		buttonpanel.add(Box.createVerticalGlue());
		buttonpanel.add(ranglistaButton);
		buttonpanel.add(Box.createVerticalGlue());
		buttonpanel.add(exitButton);
		buttonpanel.add(Box.createVerticalGlue());
		super.add(buttonpanel, BorderLayout.CENTER);
		
		nameinput = new JPanel();
		nameinput.add(new JLabel("Írja be a játékos nevét!"));
		textfield = new JTextField();
		textfield.setPreferredSize(new Dimension(100, 20));;
		nameOKButton = new JButton("OK");
		nameOKButton.addActionListener(new OKButtonListener());
		nameinput.add(textfield);
		nameinput.add(nameOKButton);
		super.add(nameinput, BorderLayout.NORTH);
		nameinput.setVisible(false);
		
		rankpanel = new JPanel();
		super.add(rankpanel, BorderLayout.WEST);
		rankpanel.setVisible(false);
	}

	
	/**
	 * Új játék indítása
	 * menü elrejtése
	 * 
	 * @param load
	 * 		a játék mentett állás alapján töltõdik-e be
	 * @param name
	 * 		a játékot kezdõ játékos neve
	 */
	public void startNewGame(Boolean load, String name) {
		GameWindow game1 = new GameWindow(this);
		game1.startnewgame(load, name);
		this.setVisible(false);
	}
	
	/**
	 * Frissíti a ranglistát, megjeleníti illetve elrejti azt
	 */
	public void displayRanklist() {
		if(rankpanel.isVisible()) {
			scrolllist.remove(list);
			rankpanel.remove(scrolllist);
			rankpanel.setVisible(false);
		}
		else {
			list = new JList<Object>(readRanklist("rangsor.txt"));
			scrolllist = new JScrollPane(list);
			rankpanel.add(scrolllist);
			rankpanel.setVisible(true);
		}
	}
	
	/**
	 * 
	 * Fájlból beolvassa a ranglistát
	 * @param filename
	 * 		a beolvasandó fájl neve
	 * @return
	 * 		a beolvasott adatokból készült tömb
	 */
	public Object[] readRanklist(String filename) {
		ArrayList<String> ranklist = new ArrayList<>();
		InputStream in = getClass().getResourceAsStream("/rangsor.txt");
			Scanner sc = new Scanner(in);
			while(sc.hasNextLine())
				ranklist.add(sc.nextLine());
			sc.close();
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return ranklist.toArray();
	}
	
	/**
	 * Megjeleníti, illetve elrejti a névbevitelhez és játékkezdéshez szükséges panelt
	 */
	public void getName_startgame() {
		if(nameinput.isVisible())
			nameinput.setVisible(false);
		else
			nameinput.setVisible(true);
	}
	
	/**
	 * Listener osztály az "OK" gomb lenyomásának érzékelésére
	 * Gomb lenyomásakor új játék índítás a megadott névvel
	 *
	 */
	private class OKButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			startNewGame(false, textfield.getText());
		}
	}
	
	/**
	 * Listener osztály az "Új Játék" gomb lenyomásának érzékelésére
	 * Gomb lenyomásakor megjeleníti a játékkezdéshez szükséges névbeviteli mezõket
	 *
	 */
	private class UjJatekButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			getName_startgame();
		}
	}
	
	/**
	 * Listener osztály a "Játék Folyatatása" gomb lenyomásának érzékelésére
	 * Gomb lenyomásakor új játék indítása, a mentett adatok betöltésével
	 *
	 */
	private class FolytatasButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			startNewGame(true, null);
		}
	}
	
	/**
	 * Listener osztály a "Ranglista" gomb lenyomásának érzékelésére
	 * Gomb lenyomásakor ranglista megjelenítése
	 *
	 */
	private class RanglistaButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			displayRanklist();
		}
	}
	
	/**
	 * Listener osztály a "Kilépés" gomb lenyomásának érzékelésére
	 * Gomb lenyomásakor kilépés a programból
	 *
	 */
	private class KilepesButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
}
