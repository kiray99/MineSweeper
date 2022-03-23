package minesweepergame.minesweepermodell;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StatusTest {
	Status status;
	@Before
	public void setup() {
		status = Status.UNMARKED;
	}
	@Test
	public void testrotate() {
		status = Status.rotate(status);
		assertEquals(status, Status.FLAGGED);
		status = Status.rotate(status);
		assertEquals(status, Status.MARKED);
		status = Status.rotate(status);
		assertEquals(status, Status.UNMARKED);
	}
}
