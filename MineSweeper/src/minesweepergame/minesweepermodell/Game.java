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
 * Egy j�t�kot reprezent�l� oszt�ly
 *
 */
public class Game implements Serializable{
	/**
	 * A felfedett mez�k sz�ma
	 */
	private int revealednumber = 0;
	/**
	 * Mez�ket tartalmaz� ArrayList
	 */
	private ArrayList<Field> fields;
	/**
	 * A J�t�k v�get �rt-e, bomba felfed�s miatt
	 */
	private boolean gameover = false;
	/**
	 * A j�t�k v�get �rt-e, minden szabad mez� felfed�se miatt
	 */
	private boolean victory = false;
	/**
	 * A j�t�kot j�tsz� j�t�kos neve
	 */
	private String playername;
	/**
	 * Az adott j�t�kkal elt�lt�tt id� m�sodpercben
	 */
	private int time;
	
	/**
	 * Egy param�teres konstruktor, amely be�ll�tja a j�t�kos nev�t
	 * �s az id�t alap (0) �rt�kre �ll�tja
	 * 
	 * @param playername
	 * 			A j�t�kos neve
	 */
	public Game(String playername) {
		this.playername = playername;
		time = 0;
	}
	
	/**
	 * �j j�t�k kezdetekor fel�ll�tja a t�bl�t, l�tre hozza a bomb�kat
	 * �s a mez�k �rt�keit kezd�shez megfelel�en be�ll�tja
	 * Az els� kattint�s hely�n, bomba nem gener�l�dhat
	 * 
	 * @param index
	 * 			az a mez� index ahol az els� kattint�s t�rt�nt
	 */
	public void InitGame(int index) {
		fields = new ArrayList<Field>();
		for(int i = 0 ; i < 81 ; i++)
			fields.add(new Field());
		Generatebomb(index);
		Generatenumbers();
		
	}
	
	/**
	 * Bomba gener�l�s�t �s a mez�k�n elhelyez�s�t v�gz� met�dus
	 * Random oszt�ly seg�ts�g�vel v�letlenszer�en elhelyezi a megfelel� sz�m� bomb�t
	 * 
	 * @param nobomb
	 * 			Az az index ahova bomba nem tehet�
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
	 * Be�ll�tja a mez�k k�r�l tal�lhat� bomb�k sz�m�t
	 * Minden bomba k�r�li mez� �rt�k�t megn�veli
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
	 * Rekurzivan felder�ti �s felfedi az erre alkalmas mez�ket
	 * Vizsg�lja hogy bomb�t pr�b�ltunk-e felfedni
	 * Vizsg�lja hogy minden szabad mez�t felfedt�nk-e
	 * 
	 * @param x
	 * 			felfedend� mez� X �rt�ke
	 * @param y
	 * 			felfedend� mez� Y �rt�ke
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
	 * A Game oszt�ly ment�se f�jlba k�s�bbi visszaolvas�s c�lj�b�l
	 * Szerializ�l�s alkalmaz�s�val
	 * @param filename
	 * 		a beolvasand� f�jl neve
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
	 * Az oszt�ly statisztikailag fontos adatait menti
	 * Ezek az attrib�tumok: 
	 * 		playername
	 * 		time
	 * Ment�s el�tt beolvassa a f�jlt, ha m�r l�tezik, majd hozz� f�zi az �j �rt�ket
	 * Id� szerinti rednez�s ut�n sz�veges f�jlba �rja
	 * @param filename
	 * 		a f�jl neve ahova ment�nk
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
	 * Vissza adja az adott index� mez�t
	 * 
	 * @param index
	 * 		a mez� indexe
	 * @return fields
	 * 		mez�ket tartalmaz� lista
	 */
	public Field getField(int index){
		return fields.get(index);
	}
	
	/**
	 * Vissza adja, hogy a j�t�k v�get �rt-e
	 * 
	 * @return gameover
	 * 
	 */
	public boolean getgameover() {
		return gameover;
	}
	
	/**
	 * Vissza adja, hogy a j�t�kot megnyert�k-e
	 * 
	 * @return victory
	 */
	public boolean getvictory() {
		return victory;
	}
	
	/**
	 * Felfed minden mez�t a j�t�ba
	 */
	public void gameover() {
		for(int i = 0 ; i < 81 ; i++) {
			fields.get(i).Reveal();
		}
	}
	
	/**
	 * A kapott param�ter alapj�n be�ll�tja a j�t�k idej�t
	 * 
	 * @param time
	 * 		a be�ll�tani k�v�nt �rt�k
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	/**
	 * Vissza adja a j�t�kos nev�t
	 * 
	 * @return playername
	 */
	public String getPlayerName() {
		return playername;
	}
	
	/**
	 * Vissza adja a j�t�k aktu�lis idej�t
	 * 
	 * @return time
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Visszat�r a felfedett mez�k sz�m�val
	 * @return revealednumber
	 * 		a felfedett mez�k sz�ma
	 */
	public int getRevealedNumber() {
		return revealednumber;
	}
	

}
