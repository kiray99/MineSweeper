package minesweepergame.minesweepermodell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * Egy játékot reprezentáló osztály
 *
 */
public class Game implements Serializable{
	/**
	 * A felfedett mezõk száma
	 */
	private int revealednumber = 0;
	/**
	 * Mezõket tartalmazõ ArrayList
	 */
	private ArrayList<Field> fields;
	/**
	 * A Játék véget ért-e, bomba felfedés miatt
	 */
	private boolean gameover = false;
	/**
	 * A játék véget ért-e, minden szabad mezõ felfedése miatt
	 */
	private boolean victory = false;
	/**
	 * A játékot játszó játékos neve
	 */
	private String playername;
	/**
	 * Az adott játékkal eltöltött idõ másodpercben
	 */
	private int time;
	
	/**
	 * Egy paraméteres konstruktor, amely beállítja a játékos nevét
	 * és az idõt alap (0) értékre állítja
	 * 
	 * @param playername
	 * 			A játékos neve
	 */
	public Game(String playername) {
		this.playername = playername;
		time = 0;
	}
	
	/**
	 * Új játék kezdetekor felállítja a táblát, létre hozza a bombákat
	 * és a mezõk értékeit kezdéshez megfelelõen beállítja
	 * Az elsõ kattintás helyén, bomba nem generálódhat
	 * 
	 * @param index
	 * 			az a mezõ index ahol az elsõ kattintás történt
	 */
	public void InitGame(int index) {
		fields = new ArrayList<Field>();
		for(int i = 0 ; i < 81 ; i++)
			fields.add(new Field());
		Generatebomb(index);
		Generatenumbers();
		
	}
	
	/**
	 * Bomba generálását és a mezõkön elhelyezését végzõ metódus
	 * Random osztály segítségével véletlenszerûen elhelyezi a megfelelõ számú bombát
	 * 
	 * @param nobomb
	 * 			Az az index ahova bomba nem tehetõ
	 */
	private void Generatebomb(int nobomb) {
		Random r = new Random();
		int rand;
		for(int i = 0 ; i < 10 ; i++)
		{
			rand = r.nextInt(81);
			if(fields.get(rand).getBomb() == false && rand != nobomb)
				fields.get(rand).setBomb(true);
			else
				i--;
		}
	}
	
	/**
	 * Beállítja a mezõk körül található bombák számát
	 * Minden bomba körüli mezõ értékét megnöveli
	 * 
	 */
	private void Generatenumbers() {
		int x;
		int y;
		for(int i = 0 ; i < 81 ; i++){
			if(fields.get(i).getBomb()) {
				x = i/9 -1;
				y = i%9 -1;
				if((x >= 0 && x < 9) && (y >= 0 && y < 9))
					fields.get(x*9 + y).IncreaseMinesNear();	//BF
				
				x = i/9 -1;
				y = i%9 ;
				if((x >= 0 && x < 9) && (y >= 0 && y < 9))
					fields.get(x*9 + y).IncreaseMinesNear();	//F
				
				x = i/9 -1;
				y = i%9 +1;
				if((x >= 0 && x < 9) && (y >= 0 && y < 9))
					fields.get(x*9 + y).IncreaseMinesNear();	//JF
				
				x = i/9 ;
				y = i%9 -1;
				if((x >= 0 && x < 9) && (y >= 0 && y < 9))
					fields.get(x*9 + y).IncreaseMinesNear();	//B
				
				x = i/9 ;
				y = i%9 +1;
				if((x >= 0 && x < 9) && (y >= 0 && y < 9))
					fields.get(x*9 + y).IncreaseMinesNear();	//J
				
				x = i/9 +1;
				y = i%9 -1;
				if((x >= 0 && x < 9) && (y >= 0 && y < 9))
					fields.get(x*9 + y).IncreaseMinesNear();	//BA
				
				x = i/9 +1;
				y = i%9 ;
				if((x >= 0 && x < 9) && (y >= 0 && y < 9))
					fields.get(x*9 + y).IncreaseMinesNear();	//A
				
				x = i/9 +1;
				y = i%9 +1;
				if((x >= 0 && x < 9) && (y >= 0 && y < 9))
					fields.get(x*9 + y).IncreaseMinesNear();	//JA
			}
		}
	}
	
	/**
	 * Rekurzivan felderíti és felfedi az erre alkalmas mezõket
	 * Vizsgálja hogy bombát próbáltunk-e felfedni
	 * Vizsgálja hogy minden szabad mezõt felfedtünk-e
	 * 
	 * @param x
	 * 			felfedendõ mezõ X értéke
	 * @param y
	 * 			felfedendõ mezõ Y értéke
	 */
	public void Reveal(int x, int y) {
		if(x < 0 || x > 8 || y < 0 || y > 8)
			return;
		int index = x*9 + y;
		if(fields.get(index).getBomb()) {
			gameover = true;
			return;
		}
		if(fields.get(index).getIsRevealed())
			return;
		fields.get(index).Reveal();
		revealednumber++;
		if(fields.get(index).getMinesNear() == 0 &&
			fields.get(index).getBomb() == false) {
			this.Reveal(x-1, y-1);	//BF
			this.Reveal(x-1, y);	//F
			this.Reveal(x-1, y+1);	//JF
			this.Reveal(x, y-1);	//B
			this.Reveal(x, y+1);	//J
			this.Reveal(x+1, y-1);	//BA
			this.Reveal(x+1, y);	//A
			this.Reveal(x+1, y+1);	//JA
		}
		if (revealednumber == 71) {
			victory = true;
		}
		return;
	}
	
	/**
	 * A Game osztály mentése fájlba késõbbi visszaolvasás céljából
	 * Szerializálás alkalmazásával
	 * @param filename
	 * 		a beolvasandó fájl neve
	 */
	public void save(String filename) {
		try {
			File file = new File(filename);
			if(!file.exists())
				file.createNewFile();
			FileOutputStream f = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(this);
			out.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Az osztály statisztikailag fontos adatait menti
	 * Ezek az attribútumok: 
	 * 		playername
	 * 		time
	 * Mentés elõtt beolvassa a fájlt, ha már létezik, majd hozzá fûzi az új értéket
	 * Idõ szerinti rednezés után szöveges fájlba írja
	 * @param filename
	 * 		a fájl neve ahova mentünk
	 */
	public void saveStatistics(String filename) {
		ArrayList<String> ranklist = new ArrayList<>();
		try {
			File rangsorfile = new File(filename);
			if(!rangsorfile.exists())
				rangsorfile.createNewFile();
			Scanner sc = new Scanner(rangsorfile);
			while(sc.hasNextLine())
				ranklist.add(sc.nextLine());
			sc.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ranklist.add(time + " sec " + playername);
		Collections.sort(ranklist);
		Iterator<String> iter = ranklist.iterator();

		try {
			FileWriter fw = new FileWriter(filename);
			PrintWriter pw = new PrintWriter(fw);
			while(iter.hasNext()) {
				pw.println(iter.next());
			}
				
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Vissza adja az adott indexü mezõt
	 * 
	 * @param index
	 * 		a mezõ indexe
	 * @return fields
	 * 		mezõket tartalmazó lista
	 */
	public Field getField(int index){
		return fields.get(index);
	}
	
	/**
	 * Vissza adja, hogy a játék véget ért-e
	 * 
	 * @return gameover
	 * 
	 */
	public boolean getgameover() {
		return gameover;
	}
	
	/**
	 * Vissza adja, hogy a játékot megnyerték-e
	 * 
	 * @return victory
	 */
	public boolean getvictory() {
		return victory;
	}
	
	/**
	 * Felfed minden mezõt a játéba
	 */
	public void gameover() {
		for(int i = 0 ; i < 81 ; i++) {
			fields.get(i).Reveal();
		}
	}
	
	/**
	 * A kapott paraméter alapján beállítja a játék idejét
	 * 
	 * @param time
	 * 		a beállítani kívánt érték
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	/**
	 * Vissza adja a játékos nevét
	 * 
	 * @return playername
	 */
	public String getPlayerName() {
		return playername;
	}
	
	/**
	 * Vissza adja a játék aktuális idejét
	 * 
	 * @return time
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Visszatér a felfedett mezõk számával
	 * @return revealednumber
	 * 		a felfedett mezõk száma
	 */
	public int getRevealedNumber() {
		return revealednumber;
	}
	

}
