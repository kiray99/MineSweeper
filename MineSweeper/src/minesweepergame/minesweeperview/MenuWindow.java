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
 * Oszt�ly a j�t�k men� megjelen�t�s�re
 *
 */
public class MenuWindow extends JFrame{
	/**
	 * A ranglist�t tartalmaz� panel
	 */
	private JPanel rankpanel;
	/**
	 * A n�z bek�r�s�hez sz�ks�ges elemek tartalmaz� panel
	 */
	private JPanel nameinput;
	/**
	 * A f� ir�ny�t� gombokat tartalmaz� panel
	 */
	private JPanel buttonpanel;
	/**
	 * Gomb �j j�t�k kezd�s�hez
	 */
	private JToggleButton ujjatekButton;
	/**
	 * Gomb j�t�k folytat�s�hoz
	 */
	private JButton folytatasButton;
	/**
	 * Gomb a ranglista megjelen�t�s�hez
	 */
	private JToggleButton ranglistaButton;
	/**
	 * Gomb a program bez�r�s�hoz
	 */
	private JButton exitButton;
	/**
	 * Gomb amellyel v�glelges�thet� a n�vbevitel
	 */
	private JButton nameOKButton;
	/**
	 * N�v bevitel�t szolg�l� sz�veges beviteli mez�
	 */
	private JTextField textfield;
	/**
	 * A ranglist�t tartalmaz� lista
	 */
	private JList<Object> list;
	/**
	 * A list�t mag�ba foglal� g�gethet� fel�let
	 */
	private JScrollPane scrolllist;
	
	/**
	 * Param�ter n�lk�li konstruktor
	 * L�tre hozza a megjelen�t� ablakot
	 * Megh�vja az inicializ�l� [Init()] met�dust
	 */
	public MenuWindow() {
		super("Kiray's Minesweeper");
		setSize(400, 400);
		setLocation(500, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Init();
	}
	
	/**
	 * Inicializ�l� met�dus
	 * Konstruktorban h�v�dik meg
	 * L�trehozza �s be�ll�tja az ablakon megjelen� elemeket
	 */
	private void Init() {
		ujjatekButton = new JToggleButton("�j J�t�k");
		ujjatekButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ujjatekButton.addActionListener(new UjJatekButtonListener());
		
		folytatasButton = new JButton("J�t�k Folytat�sa");
		folytatasButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		folytatasButton.addActionListener(new FolytatasButtonListener());
		
		ranglistaButton = new JToggleButton("Ranglista");
		ranglistaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ranglistaButton.addActionListener(new RanglistaButtonListener());
		
		exitButton = new JButton("Kil�p�s");
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
		nameinput.add(new JLabel("�rja be a j�t�kos nev�t!"));
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
	 * �j j�t�k ind�t�sa
	 * men� elrejt�se
	 * 
	 * @param load
	 * 		a j�t�k mentett �ll�s alapj�n t�lt�dik-e be
	 * @param name
	 * 		a j�t�kot kezd� j�t�kos neve
	 */
	public void startNewGame(Boolean load, String name) {
		GameWindow game1 = new GameWindow(this);
		game1.startnewgame(load, name);
		this.setVisible(false);
	}
	
	/**
	 * Friss�ti a ranglist�t, megjelen�ti illetve elrejti azt
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
	 * F�jlb�l beolvassa a ranglist�t
	 * @param filename
	 * 		a beolvasand� f�jl neve
	 * @return
	 * 		a beolvasott adatokb�l k�sz�lt t�mb
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
	 * Megjelen�ti, illetve elrejti a n�vbevitelhez �s j�t�kkezd�shez sz�ks�ges panelt
	 */
	public void getName_startgame() {
		if(nameinput.isVisible())
			nameinput.setVisible(false);
		else
			nameinput.setVisible(true);
	}
	
	/**
	 * Listener oszt�ly az "OK" gomb lenyom�s�nak �rz�kel�s�re
	 * Gomb lenyom�sakor �j j�t�k �nd�t�s a megadott n�vvel
	 *
	 */
	private class OKButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			startNewGame(false, textfield.getText());
		}
	}
	
	/**
	 * Listener oszt�ly az "�j J�t�k" gomb lenyom�s�nak �rz�kel�s�re
	 * Gomb lenyom�sakor megjelen�ti a j�t�kkezd�shez sz�ks�ges n�vbeviteli mez�ket
	 *
	 */
	private class UjJatekButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			getName_startgame();
		}
	}
	
	/**
	 * Listener oszt�ly a "J�t�k Folyatat�sa" gomb lenyom�s�nak �rz�kel�s�re
	 * Gomb lenyom�sakor �j j�t�k ind�t�sa, a mentett adatok bet�lt�s�vel
	 *
	 */
	private class FolytatasButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			startNewGame(true, null);
		}
	}
	
	/**
	 * Listener oszt�ly a "Ranglista" gomb lenyom�s�nak �rz�kel�s�re
	 * Gomb lenyom�sakor ranglista megjelen�t�se
	 *
	 */
	private class RanglistaButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			displayRanklist();
		}
	}
	
	/**
	 * Listener oszt�ly a "Kil�p�s" gomb lenyom�s�nak �rz�kel�s�re
	 * Gomb lenyom�sakor kil�p�s a programb�l
	 *
	 */
	private class KilepesButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
}
