package minesweepergame.minesweepermodell;

import java.io.Serializable;

/**
 * Egy mez� �llapot�t �s �rt�keit reprezent�l� oszt�ly.
 *
 */
public class Field implements Serializable{
	/**
	 * Akn�k Sz�ma a mez� k�r�l
	 */
	private int MinesNear;
	/**
	 * A mez�n bomba tal�lhat�-e
	 */
	private boolean IsBomb;
	/**
	 * A mez� felfedett-e
	 */
	private boolean IsRevealed = false;
	/**
	 * Status Enum oszt�ly seg�ts�g�vel a mez� �llapot�t jelz� param�ter
	 */
	private Status fieldstatus = Status.UNMARKED;

	/**
	 * Be�ll�tja, hogy a mez� bomba-e
	 * 
	 * @param isbomb
	 * 		bomba-e
	 */
	public void setBomb(Boolean isbomb) {
		IsBomb = isbomb;
	}
	
	/**
	 * Visszaadja, hogy a mez� bomba-e
	 * 
	 * @return IsBomb
	 */
	public boolean getBomb() {
		return IsBomb;
	}
	
	/**
	 * Vissza adja a mez� k�r�li bomb�k sz�m�t
	 * 
	 * @return a mez� k�r�li bomb�k sz�ma
	 */
	public int getMinesNear() {
		return MinesNear;
	}
	
	/**
	 * Megn�veli eggyel a mez� k�r�l tal�lhat� bomb�k sz�m�t
	 */
	public void IncreaseMinesNear() {
		MinesNear++;
	}
	
	/**
	 * Vissza adja, hogy a mez� fel van-e fedve
	 * 
	 * @return isRevealed
	 */
	public boolean getIsRevealed() {
		return IsRevealed;
	}
	
	/**
	 * A mez� �llapot�t felfedett� v�ltoztatja
	 */
	public void Reveal() {
		IsRevealed = true;
	}
	
	/**
	 * Be�ll�tja a mez� �llapot�t
	 */
	public void setfieldstatus() {
		fieldstatus = Status.rotate(fieldstatus);
	}
	
	/**
	 * Vissza adja a mez� �llapot�t
	 * 
	 * @return a mez� �llapota
	 */
	public Status getfieldstatus() {
		return fieldstatus;
	}
}
