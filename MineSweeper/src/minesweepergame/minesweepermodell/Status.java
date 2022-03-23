package minesweepergame.minesweepermodell;

import java.io.Serializable;

/**
 * Enum oszt�ly egy mez� st�tusz�nak reprezent�l�s�ra �s v�ltoztat�s�sra
 */
public enum Status implements Serializable{
		/**
		 * Nincs megjel�lve
		 */
		UNMARKED,
		/**
		 * Megvan jel�lve
		 */
		MARKED,
		/**
		 * Z�szl�val jel�lt
		 */
		FLAGGED;
		
		/**
		 * A kapott (Status stat) param�ter alapj�n megfelel� �rt�kkel t�r vissza
		 * L�pteti az enum �rt�k�t
		 * 
		 * @param stat
		 * 		a megadott kiindul�si �llapot
		 * @return �j, m�dos�tott �rt�k
		 */
		static public Status rotate(Status stat) {
			switch(stat) {
			case UNMARKED:
				return Status.FLAGGED;
			case FLAGGED:
				return Status.MARKED;
			case MARKED:
				return Status.UNMARKED;
			default:
				return Status.UNMARKED;
			}
		}
	}

