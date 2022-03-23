package minesweepergame.minesweeperview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class StopWatchTest {
	Stopwatch watch;
	int basetime;
	int newtime;
	@Before
	public void setup() {
		watch = new Stopwatch(1000);
	}
	@Test
	public void testStartTimer() {
		basetime = watch.getCount();
		watch.startTimer();
		watch.actionPerformed(null);
		newtime = watch.getCount();
		assertEquals(1, newtime);
		assertNotEquals(basetime, newtime);
		watch.actionPerformed(null);
		newtime = watch.getCount();
		assertEquals(2, newtime);
	}
	@Test
	public void testTimeFormat1() {
		watch.setCount(10);
		watch.startTimer();
		String expected = "00:10";
		String actual = watch.getText();
		assertEquals(expected, actual);
	}
	@Test
	public void testTimeFormat2() {
		watch.setCount(90);
		watch.startTimer();
		String expected = "01:30";
		String actual = watch.getText();
		assertEquals(expected, actual);
	}
}
