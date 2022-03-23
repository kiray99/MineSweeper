package minesweepergame.minesweeperview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import minesweepergame.minesweepermodell.Game;

/**
 * A játéktáblát megjelenítõ és kezelését irányító osztály
 *
 */
public class DisplayMineField extends JButton{
	/**
	 * Az elsõ kattintás indexe a táblán
	 */
	private int firstindex;
	/**
	 * Az elsõ lépés következik-e
	 */
	private Boolean firstmove;
	/**
	 * A játék egy példánya
	 */
	private Game game;
	/**
	 * A hívó ablak referenciája
	 */
	private GameWindow window;
	/**
	 * A megjelenítéshez szükséges képek listája
	 */
	private ArrayList<BufferedImage> images;
	/**
	 * MouseListener az egér bevitel kezeléséhez
	 */
	MouseEventListener listener;

	/**
	 * Az Osztály három paraméteres konstruktora
	 * létrehozza a játék osztály egy pédányát
	 * beolvassa a megjelenítéshez szükséges fájlokat
	 * @param window
	 * 		A hívó osztály referenciája
	 * @param load
	 * 		A játék mentett adatokkal folytatódjon-e
	 * @param playername
	 * 		A játékos neve
	 */
	public DisplayMineField(GameWindow window, boolean load, String playername) {
		this.window = window;
		if(load) {
			firstmove = false;
			if(loadGame("mentes.txt") == false) {
				firstmove = true;
				this.game = new Game(playername);
			}	
		}
		else {
			firstmove = true;
			this.game = new Game(playername);
		}
		this.setPreferredSize(new Dimension(450, 450));
		listener = new MouseEventListener();
		this.addMouseListener(listener);
		
		images = new ArrayList<>();
		try {
			images.add(ImageIO.read(ClassLoader.getSystemResource("bomb.png"))); //0
			images.add(ImageIO.read(ClassLoader.getSystemResource("one.png"))); //1
			images.add(ImageIO.read(ClassLoader.getSystemResource("two.png"))); //2
			images.add(ImageIO.read(ClassLoader.getSystemResource("three.png"))); //3
			images.add(ImageIO.read(ClassLoader.getSystemResource("four.png"))); //4
			images.add(ImageIO.read(ClassLoader.getSystemResource("five.png"))); //5 
			images.add(ImageIO.read(ClassLoader.getSystemResource("six.png"))); //6
			images.add(ImageIO.read(ClassLoader.getSystemResource("seven.png"))); //7
			images.add(ImageIO.read(ClassLoader.getSystemResource("eight.png"))); //8 
			images.add(ImageIO.read(ClassLoader.getSystemResource("tile1.png"))); //9
			images.add(ImageIO.read(ClassLoader.getSystemResource("tile2.png"))); //10
			images.add(ImageIO.read(ClassLoader.getSystemResource("flag.png"))); //11 
			images.add(ImageIO.read(ClassLoader.getSystemResource("questionmark.png"))); //12
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Új játék kezdése, a játéktér feltöltése
	 */
	public void Newgame(){
		game.InitGame(firstindex);
	}
	
	/**
	 * A játékidõ frissítése, a játékállás mentése
	 */
	public void saveGame() {
		game.setTime(window.getTimeFromWatch());
		game.save("mentes.txt");
	}
	
	/**
	 * Játék betöltése mentési fájlból
	 * Stopper óra állás frissítése a beolvasott adatok alapján
	 * @param filename
	 * 		a fájl neve ahonnan az adatot olvassuk
	 * @return
	 * 		visszatérési értéke megadja a fájlbeolvasás sikerességét
	 */
	public boolean loadGame(String filename) {
		try {
			File file = new File(filename);
			if(!file.exists())
				return false;
			FileInputStream f = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(f);
			game = (Game)in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		window.setTimeForWatch(game.getTime());
		return true;
	}
	
	/**
	 * Az osztályt megjelenítõ metódus
	 * kirajzolja a játékteret az aktuális állapotoknak megfelelõen
	 */
	public void paint(Graphics g) {    
		
        for(int i = 0 ; i < 81 ; i++){
        	int x = (i/9)*50;
        	int y = (i%9)*50;
        	g.drawImage(images.get(9), x, y, null);
        	if(!firstmove) {
        		if(game.getField(i).getIsRevealed() == false) {
        			switch(game.getField(i).getfieldstatus()) {
        			case FLAGGED:
						g.drawImage(images.get(11), x, y, null);	break;
					case MARKED:
						g.drawImage(images.get(12), x, y, null);	break;
					default:	
						break;
        			}
        		} else {
        			g.drawImage(images.get(10), x, y, null);
        			if(game.getField(i).getBomb())
        				g.drawImage(images.get(0), x, y, null);
        			else if(game.getField(i).getMinesNear() != 0)
	        			g.drawImage(images.get(game.getField(i).getMinesNear()), x, y, null);
        		}
        	}    
        }
	}
	
	/**
	 * A Játék irányítását vezérlõ osztály
	 * Figyeli az egér pozicióját és az egérkattintásokat
	 * A kattintás helyének és az egérgombnak megfelelõ metódust hívja meg
	 */
	public class MouseEventListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			Point pos = new Point((int)e.getX()/50, (int)e.getY()/50);
			if(firstmove) {
				firstindex = pos.x*9 + pos.y;
				Newgame();
				firstmove = false;
			}
			if(!firstmove) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					game.Reveal(pos.x, pos.y);
					if(game.getgameover())
						game.gameover();
					if(game.getvictory()) {
						game.setTime(window.getTimeFromWatch());
						game.saveStatistics("rangsor.txt");
						window.gamefinished();
					}
				}
				if(e.getButton() == MouseEvent.BUTTON3)
					game.getField(pos.x*9 + pos.y).setfieldstatus();
			}
			updateUI();
		}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	/**
	 * Visszaadja a tartalmazott játék osztályt
	 * @return game
	 * 		a játék osztály referenciája
	 */
	public Game getGame() {
		return game;
	}
}




