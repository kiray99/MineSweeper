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
 * A j�t�kt�bl�t megjelen�t� �s kezel�s�t ir�ny�t� oszt�ly
 *
 */
public class DisplayMineField extends JButton{
	/**
	 * Az els� kattint�s indexe a t�bl�n
	 */
	private int firstindex;
	/**
	 * Az els� l�p�s k�vetkezik-e
	 */
	private Boolean firstmove;
	/**
	 * A j�t�k egy p�ld�nya
	 */
	private Game game;
	/**
	 * A h�v� ablak referenci�ja
	 */
	private GameWindow window;
	/**
	 * A megjelen�t�shez sz�ks�ges k�pek list�ja
	 */
	private ArrayList<BufferedImage> images;
	/**
	 * MouseListener az eg�r bevitel kezel�s�hez
	 */
	MouseEventListener listener;

	/**
	 * Az Oszt�ly h�rom param�teres konstruktora
	 * l�trehozza a j�t�k oszt�ly egy p�d�ny�t
	 * beolvassa a megjelen�t�shez sz�ks�ges f�jlokat
	 * @param window
	 * 		A h�v� oszt�ly referenci�ja
	 * @param load
	 * 		A j�t�k mentett adatokkal folytat�djon-e
	 * @param playername
	 * 		A j�t�kos neve
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
	 * �j j�t�k kezd�se, a j�t�kt�r felt�lt�se
	 */
	public void Newgame(){
		game.InitGame(firstindex);
	}
	
	/**
	 * A j�t�kid� friss�t�se, a j�t�k�ll�s ment�se
	 */
	public void saveGame() {
		game.setTime(window.getTimeFromWatch());
		game.save("mentes.txt");
	}
	
	/**
	 * J�t�k bet�lt�se ment�si f�jlb�l
	 * Stopper �ra �ll�s friss�t�se a beolvasott adatok alapj�n
	 * @param filename
	 * 		a f�jl neve ahonnan az adatot olvassuk
	 * @return
	 * 		visszat�r�si �rt�ke megadja a f�jlbeolvas�s sikeress�g�t
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
	 * Az oszt�lyt megjelen�t� met�dus
	 * kirajzolja a j�t�kteret az aktu�lis �llapotoknak megfelel�en
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
	 * A J�t�k ir�ny�t�s�t vez�rl� oszt�ly
	 * Figyeli az eg�r pozici�j�t �s az eg�rkattint�sokat
	 * A kattint�s hely�nek �s az eg�rgombnak megfelel� met�dust h�vja meg
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
	 * Visszaadja a tartalmazott j�t�k oszt�lyt
	 * @return game
	 * 		a j�t�k oszt�ly referenci�ja
	 */
	public Game getGame() {
		return game;
	}
}




