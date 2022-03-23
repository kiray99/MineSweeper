package minesweepergame.minesweepermodell;

import java.io.Serializable;

/**
 * Egy mezõ állapotát és értékeit reprezentáló osztály.
 *
 */
public class Field implements Serializable{
	/**
	 * Aknák Száma a mezõ körül
	 */
	private int MinesNear;
	/**
	 * A mezõn bomba található-e
	 */
	private boolean IsBomb;
	/**
	 * A mezõ felfedett-e
	 */
	private boolean IsRevealed = false;
	/**
	 * Status Enum osztály segítségével a mezõ állapotát jelzõ paraméter
	 */
	private Status fieldstatus = Status.UNMARKED;

	/**
	 * Beállítja, hogy a mezõ bomba-e
	 * 
	 * @param isbomb
	 * 		bomba-e
	 */
	public void setBomb(Boolean isbomb) {
		IsBomb = isbomb;
	}
	
	/**
	 * Visszaadja, hogy a mezõ bomba-e
	 * 
	 * @return IsBomb
	 */
	public boolean getBomb() {
		return IsBomb;
	}
	
	/**
	 * Vissza adja a mezõ körüli bombák számát
	 * 
	 * @return a mezõ körüli bombák száma
	 */
	public int getMinesNear() {
		return MinesNear;
	}
	
	/**
	 * Megnöveli eggyel a mezõ körül található bombák számát
	 */
	public void IncreaseMinesNear() {
		MinesNear++;
	}
	
	/**
	 * Vissza adja, hogy a mezõ fel van-e fedve
	 * 
	 * @return isRevealed
	 */
	public boolean getIsRevealed() {
		return IsRevealed;
	}
	
	/**
	 * A mezõ állapotát felfedetté változtatja
	 */
	public void Reveal() {
		IsRevealed = true;
	}
	
	/**
	 * Beállítja a mezõ állapotát
	 */
	public void setfieldstatus() {
		fieldstatus = Status.rotate(fieldstatus);
	}
	
	/**
	 * Vissza adja a mezõ állapotát
	 * 
	 * @return a mezõ állapota
	 */
	public Status getfieldstatus() {
		return fieldstatus;
	}
}
