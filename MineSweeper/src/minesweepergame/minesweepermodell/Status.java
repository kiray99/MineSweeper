package minesweepergame.minesweepermodell;

import java.io.Serializable;

/**
 * Enum osztály egy mezõ státuszának reprezentálására és változtatásásra
 */
public enum Status implements Serializable{
		/**
		 * Nincs megjelölve
		 */
		UNMARKED,
		/**
		 * Megvan jelölve
		 */
		MARKED,
		/**
		 * Zászlóval jelölt
		 */
		FLAGGED;
		
		/**
		 * A kapott (Status stat) paraméter alapján megfelelõ értékkel tér vissza
		 * Lépteti az enum értékét
		 * 
		 * @param stat
		 * 		a megadott kiindulási állapot
		 * @return új, módosított érték
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

