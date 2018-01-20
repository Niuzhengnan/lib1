package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import darts.*;

public class StudentTests {

	@Test
	public void testDoubleOut1(){
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		Player Lina = new Player("Lina");
		DoubleOut game1 = new DoubleOut(10,301);
		game1.addPlayer(Rick);
		game1.addPlayer(Sven);
		game1.addPlayer(Lina);
		game1.start();
		assertEquals(3,game1.getPlayerCount());
		// -------- Round 1 ---------
		game1.throwDart(15, 1);
		game1.throwDart(20, 3);
		game1.throwDart(15, 1);
		assertEquals(211,game1.getScore()[0]);
		game1.throwDart(13, 2);
		game1.throwDart(17, 2);
		game1.throwDart(15, 2);
		assertEquals(211,game1.getScore()[1]);
		game1.throwDart(10, 3);
		game1.throwDart(20, 3);
		game1.throwDart(0, 0);
		assertEquals(211,game1.getScore()[2]);
		// -------- Round 2 ---------
		game1.throwDart(15, 1);
		game1.throwDart(20, 3);
		game1.throwDart(15, 1);
		assertEquals(121,game1.getScore()[0]);
		game1.throwDart(13, 2);
		game1.throwDart(17, 2);
		game1.throwDart(15, 2);
		assertEquals(121,game1.getScore()[1]);
		game1.throwDart(10, 3);
		game1.throwDart(20, 3);
		game1.throwDart(0, 0);
		assertEquals(121,game1.getScore()[2]);
		// -------- Round 3 ---------
		game1.throwDart(15, 1);
		game1.throwDart(20, 3);
		game1.throwDart(15, 1);
		assertEquals(31,game1.getScore()[0]);
		game1.throwDart(13, 2);
		game1.throwDart(17, 2);
		game1.throwDart(15, 2);
		assertEquals(31,game1.getScore()[1]);
		game1.throwDart(10, 3);
		game1.throwDart(20, 3);
		game1.throwDart(0, 0);
		assertEquals(31,game1.getScore()[2]);
		//-------- Round 4 -----------
		game1.throwDart(19, 1);
		game1.throwDart(11, 1);
		// Rick has now only 1 point left, so his points return to 
		//the points at the beginning of the round.
		assertEquals(31,game1.getScore()[0]);
		game1.throwDart(9, 2);
		game1.throwDart(5, 1);
		game1.throwDart(19, 1);
		// Sven has now minus points left, so his points return to
		//the points at the beginning of the round.
		assertEquals(31,game1.getScore()[1]);
		game1.throwDart(1,1);
		game1.throwDart(8,2);
		game1.throwDart(4,3);
		assertEquals(2,game1.getScore()[2]);
		// --------- Round 5 ----------
		game1.throwDart(19, 1);
		game1.throwDart(11, 1);
		assertEquals(31,game1.getScore()[0]);
		game1.throwDart(9, 2);
		game1.throwDart(5, 1);
		game1.throwDart(8, 1);
		// Sven has 0 points now but his last shot contained multiplier 1 but not 2.
		assertEquals(31,game1.getScore()[1]);
		game1.throwDart(1,2);
		// Lina wins this.
		assertEquals("Lina",game1.getWinner().getName());
		assertEquals(0,game1.getScore()[2]);
	}
	
	@Test
	public void testDoubleOut2(){
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		Player Lina = new Player("Lina");
		DoubleOut game2 = new DoubleOut(10,501);
		game2.addPlayer(Rick);
		game2.addPlayer(Sven);
		game2.addPlayer(Lina);
		game2.start();
		assertEquals(3,game2.getPlayerCount());
		// -------- Round 1 ---------
		game2.throwDart(20, 2);
		game2.throwDart(20, 3);
		game2.throwDart(20, 3);
		assertEquals(341,game2.getScore()[0]);
		game2.throwDart(20, 3);
		game2.throwDart(25, 2);
		game2.throwDart(20, 3);
		assertEquals(331,game2.getScore()[1]);
		game2.throwDart(20, 3);
		game2.throwDart(20, 2);
		game2.throwDart(20, 3);
		assertEquals(341,game2.getScore()[2]);
		// -------- Round 2 ---------
		game2.throwDart(15, 3);
		game2.throwDart(20, 3);
		game2.throwDart(15, 3);
		assertEquals(191,game2.getScore()[0]);
		game2.throwDart(15, 3);
		game2.throwDart(20, 3);
		game2.throwDart(15, 3);
		assertEquals(181,game2.getScore()[1]);
		game2.throwDart(15, 3);
		game2.throwDart(20, 3);
		game2.throwDart(15, 3);
		assertEquals(191,game2.getScore()[2]);
		// -------- Round 3 ---------
		game2.throwDart(15, 3);
		game2.throwDart(20, 3);
		game2.throwDart(15, 3);
		assertEquals(41,game2.getScore()[0]);
		game2.throwDart(15, 3);
		game2.throwDart(20, 3);
		game2.throwDart(15, 3);
		assertEquals(31,game2.getScore()[1]);
		game2.throwDart(15, 3);
		game2.throwDart(20, 3);
		game2.throwDart(15, 3);
		assertEquals(41,game2.getScore()[2]);
		//-------- Round 4 -----------
		game2.throwDart(11, 1);
		game2.throwDart(19, 1);
		game2.throwDart(20, 3);
		assertEquals(41,game2.getScore()[0]);
		game2.throwDart(9, 2);
		game2.throwDart(5, 1);
		game2.throwDart(19, 1);
		assertEquals(31,game2.getScore()[1]);	
		game2.throwDart(3,2);
		game2.throwDart(13,1);
		game2.throwDart(11,2);
		assertEquals(0,game2.getScore()[2]);
		assertEquals("Lina",game2.getWinner().getName());	
	}	

	@Test
	public void testDoubleOut3(){
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		DoubleOut game3 = new DoubleOut(3,501);
		game3.addPlayer(Rick);
		game3.addPlayer(Sven);
		game3.start();
		assertEquals(2,game3.getPlayerCount());
		// -------- Round 1 ---------
		game3.throwDart(20, 2);
		game3.throwDart(20, 3);
		game3.throwDart(20, 3);
		assertEquals(341,game3.getScore()[0]);
		game3.throwDart(20, 3);
		game3.throwDart(25, 2);
		game3.throwDart(20, 3);
		assertEquals(331,game3.getScore()[1]);
		// -------- Round 2 ---------
		game3.throwDart(15, 3);
		game3.throwDart(20, 3);
		game3.throwDart(15, 3);
		assertEquals(191,game3.getScore()[0]);
		game3.throwDart(15, 3);
		game3.throwDart(20, 3);
		game3.throwDart(15, 3);
		assertEquals(181,game3.getScore()[1]);
		// -------- Round 3 ---------
		game3.throwDart(15, 3);
		game3.throwDart(20, 3);
		game3.throwDart(15, 3);
		assertEquals(41,game3.getScore()[0]);
		game3.throwDart(15, 3);
		game3.throwDart(20, 3);
		game3.throwDart(15, 3);
		assertEquals(31,game3.getScore()[1]);
		// -------- Round 4 ---------
		game3.throwDart(20, 1);
		game3.throwDart(19, 1);
		game3.throwDart(1, 2);
		assertEquals(0,game3.getScore()[0]);
		assertEquals("Rick",game3.getWinner().getName());	
	}
	
	@Test
	public void testTactics1() {
		Player Rick = new Player("Rick");
		Player Dylan = new Player("Dylan");
		Player Lina = new Player("Lina");
		Tactics game4 = new Tactics(10);
		game4.addPlayer(Rick);
		game4.addPlayer(Dylan);
		game4.addPlayer(Lina);
		game4.start();
		// ---------- Round 1 ---------------
		game4.throwDart(15, 1);
		game4.throwDart(20, 2);
		game4.throwDart(15, 1);
		assertEquals(2,game4.getScore()[0][5]);
		assertEquals(2,game4.getScore()[0][10]);
		game4.throwDart(20, 1);
		game4.throwDart(25, 2);
		game4.throwDart(20, 1);
		assertEquals(2,game4.getScore()[1][10]);
		assertEquals(2,game4.getScore()[1][11]);
		game4.throwDart(15, 2);
		game4.throwDart(10, 1);
		game4.throwDart(7, 1);
		assertEquals(2,game4.getScore()[2][5]);
		assertEquals(1,game4.getScore()[2][0]);
		// ----------- Round 2 ----------------
		game4.throwDart(10, 2);
		game4.throwDart(15, 1);
		game4.throwDart(15, 3);
		assertEquals(6,game4.getScore()[0][5]);
		game4.throwDart(25, 1);
		game4.throwDart(19, 2);
		game4.throwDart(20, 1);
		assertEquals(3,game4.getScore()[1][10]);
		assertEquals(3,game4.getScore()[1][11]);
		assertEquals(2,game4.getScore()[1][9]);
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		assertEquals(3,game4.getScore()[2][11]);
		// ----------- Round 3 ----------------
		game4.throwDart(25, 2);
		game4.throwDart(25, 1);
		game4.throwDart(8, 1);
		assertEquals(3,game4.getScore()[0][11]);
		game4.throwDart(19, 1);
		game4.throwDart(18, 3);
		game4.throwDart(17, 3);
		assertEquals(3,game4.getScore()[1][9]);
		assertEquals(3,game4.getScore()[1][8]);
		assertEquals(3,game4.getScore()[1][7]);
		game4.throwDart(3, 3);
		game4.throwDart(6, 3);
		game4.throwDart(9, 3);
		assertEquals(3,game4.getScore()[2][11]);
		// ----------- Round 4 ----------------
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		assertEquals(6,game4.getScore()[0][11]);
		game4.throwDart(16, 3);
		game4.throwDart(15, 3);
		game4.throwDart(14, 3);
		assertEquals(3,game4.getScore()[1][6]);
		assertEquals(3,game4.getScore()[1][5]);
		assertEquals(3,game4.getScore()[1][4]);
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		assertEquals(6,game4.getScore()[2][11]);
		// ----------- Round 5 ----------------
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		assertEquals(9,game4.getScore()[0][11]);
		game4.throwDart(13, 3);
		game4.throwDart(12, 3);
		game4.throwDart(11, 3);
		assertEquals(3,game4.getScore()[1][3]);	
		assertEquals(3,game4.getScore()[1][2]);
		assertEquals(3,game4.getScore()[1][1]);
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		assertEquals(9,game4.getScore()[2][11]);
		// ----------- Round 6 ----------------
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		game4.throwDart(25, 1);
		assertEquals(12,game4.getScore()[0][11]);
		game4.throwDart(10, 1);
		game4.throwDart(10, 1);
		game4.throwDart(10, 1);
		assertEquals(3,game4.getScore()[1][0]);
		assertEquals("Dylan",game4.getWinner().getName());		
	}

	@Test
	public void testTactics2() {
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		Tactics game5 = new Tactics(5);
		game5.addPlayer(Rick);
		game5.addPlayer(Sven);
		game5.start();
		// ---------- Round 1 ---------------
		game5.throwDart(20, 3);
		game5.throwDart(20, 3);
		game5.throwDart(20, 3);
		assertEquals(9,game5.getScore()[0][10]);
		game5.throwDart(17, 1);
		game5.throwDart(17, 2);
		game5.throwDart(17, 1);
		assertEquals(4,game5.getScore()[1][7]);
		// ----------- Round 2 ----------------
		game5.throwDart(19, 3);
		game5.throwDart(18, 3);
		game5.throwDart(17, 3);
		assertEquals(3,game5.getScore()[0][9]);
		assertEquals(3,game5.getScore()[0][8]);
		assertEquals(3,game5.getScore()[0][7]);
		game5.throwDart(16, 1);
		game5.throwDart(16, 2);
		game5.throwDart(16, 1);
		assertEquals(4,game5.getScore()[1][6]);
		// ----------- Round 3 ----------------
		game5.throwDart(16, 3);
		game5.throwDart(15, 3);
		game5.throwDart(14, 3);
		assertEquals(3,game5.getScore()[0][6]);
		assertEquals(3,game5.getScore()[0][5]);
		assertEquals(3,game5.getScore()[0][4]);
		game5.throwDart(15, 1);
		game5.throwDart(15, 2);
		game5.throwDart(15, 1);
		assertEquals(4,game5.getScore()[1][5]);
		// ----------- Round 4 ----------------
		game5.throwDart(13, 3);
		game5.throwDart(12, 3);
		game5.throwDart(11, 3);
		assertEquals(3,game5.getScore()[0][3]);
		assertEquals(3,game5.getScore()[0][2]);
		assertEquals(3,game5.getScore()[0][1]);
		game5.throwDart(14, 1);
		game5.throwDart(14, 2);
		game5.throwDart(14, 1);
		assertEquals(4,game5.getScore()[1][4]);
		// ----------- Round 5 ----------------
		game5.throwDart(10, 3);
		game5.throwDart(10, 3);
		game5.throwDart(10, 3);
		assertEquals(9,game5.getScore()[0][0]);
		game5.throwDart(13, 1);
		game5.throwDart(13, 2);
		game5.throwDart(13, 1);
		assertEquals(4,game5.getScore()[1][3]);
		// ----------- Round 6 ----------------
		game5.throwDart(10, 3);
		game5.throwDart(10, 3);
		game5.throwDart(10, 3);
		assertEquals(18,game5.getScore()[0][0]);
		game5.throwDart(12, 1);
		game5.throwDart(12, 2);
		game5.throwDart(12, 1);
		assertEquals(4,game5.getScore()[1][2]);
		// ----------- Round 7 ----------------
		game5.throwDart(25, 1);
		game5.throwDart(25, 2);
		//Rick already wins this.
		assertEquals(false,game5.isRunning());
		assertEquals("Rick",game5.getWinner().getName());
	}
	
	@Test
	public void testTactics3() {
		Player Razor = new Player("Razor");
		Player Lesharc = new Player("Lesharc");
		Tactics game6 = new Tactics(5);
		game6.addPlayer(Razor);
		game6.addPlayer(Lesharc);
		game6.start();
		// ---------- Round 1 ---------------
		game6.throwDart(25, 2);
		game6.throwDart(25, 1);
		game6.throwDart(20, 3);
		assertEquals(3,game6.getScore()[0][11]);
		assertEquals(3,game6.getScore()[0][10]);
		game6.throwDart(25, 2);
		game6.throwDart(25, 1);
		game6.throwDart(20, 3);
		assertEquals(3,game6.getScore()[1][11]);
		assertEquals(3,game6.getScore()[1][10]);
		// ---------- Round 2 ---------------
		game6.throwDart(19, 3);
		game6.throwDart(18, 3);
		game6.throwDart(17, 3);
		assertEquals(3,game6.getScore()[0][9]);
		assertEquals(3,game6.getScore()[0][8]);
		assertEquals(3,game6.getScore()[0][7]);
		game6.throwDart(19, 3);
		game6.throwDart(18, 3);
		game6.throwDart(17, 3);
		assertEquals(3,game6.getScore()[1][9]);
		assertEquals(3,game6.getScore()[1][8]);
		assertEquals(3,game6.getScore()[1][7]);
		// ---------- Round 3 ---------------
		game6.throwDart(16, 3);
		game6.throwDart(15, 3);
		game6.throwDart(14, 3);
		assertEquals(3,game6.getScore()[0][6]);
		assertEquals(3,game6.getScore()[0][5]);
		assertEquals(3,game6.getScore()[0][4]);
		game6.throwDart(16, 3);
		game6.throwDart(15, 3);
		game6.throwDart(14, 3);
		assertEquals(3,game6.getScore()[1][6]);
		assertEquals(3,game6.getScore()[1][5]);
		assertEquals(3,game6.getScore()[1][4]);
		// ---------- Round 4 ---------------
		game6.throwDart(13, 3);
		game6.throwDart(12, 3);
		game6.throwDart(11, 3);
		assertEquals(3,game6.getScore()[0][3]);
		assertEquals(3,game6.getScore()[0][2]);
		assertEquals(3,game6.getScore()[0][1]);
		game6.throwDart(13, 3);
		game6.throwDart(12, 3);
		game6.throwDart(11, 3);
		assertEquals(3,game6.getScore()[1][3]);
		assertEquals(3,game6.getScore()[1][2]);
		assertEquals(3,game6.getScore()[1][1]);
		// ---------- Round 5 ---------------
		game6.throwDart(10, 1);
		game6.throwDart(10, 1);
		game6.throwDart(9, 1);
		assertEquals(2,game6.getScore()[0][0]);
		game6.throwDart(10, 1);
		game6.throwDart(10, 2);
		assertEquals(false,game6.isRunning());
		assertEquals("Lesharc",game6.getWinner().getName());
	}
			
	@Test
	public void testShanghai1() {
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		Player Lina = new Player("Lina");
		Shanghai game7 = new Shanghai(10);
		game7.addPlayer(Rick);
		game7.addPlayer(Sven);
		game7.addPlayer(Lina);
		game7.start();
		// ------- Round 1 ---------
		game7.throwDart(15, 1);
		game7.throwDart(20, 2);
		game7.throwDart(1, 1);
		assertEquals(1,game7.getScore()[0]);
		game7.throwDart(3, 1);
		game7.throwDart(1, 3);
		game7.throwDart(1, 2);
		assertEquals(5,game7.getScore()[1]);
		game7.throwDart(13, 3);
		game7.throwDart(14, 1);
		game7.throwDart(3, 2);
		assertEquals(0,game7.getScore()[2]);
		// ------ Round 2 -------
		game7.throwDart(12, 1);
		game7.throwDart(2, 2);
		game7.throwDart(7, 3);
		// Rick shanghais.
		assertEquals("Rick",game7.getWinner().getName());
	}
	
	@Test
	public void testShanghai2() {
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		Player Lina = new Player("Lina");
		Shanghai game8 = new Shanghai(10);
		game8.addPlayer(Rick);
		game8.addPlayer(Sven);
		game8.addPlayer(Lina);
		game8.start();
		//----------------- Round 1 --------------------
		game8.throwDart(1, 2);
		game8.throwDart(1, 3);
		game8.throwDart(10, 3);
		assertEquals(5,game8.getScore()[0]);
		game8.throwDart(20, 3);
		game8.throwDart(1, 3);
		game8.throwDart(5, 2);
		assertEquals(3,game8.getScore()[1]);
		game8.throwDart(6, 2);
		game8.throwDart(7, 1);
		game8.throwDart(9, 3);
		assertEquals(0,game8.getScore()[2]);
		//----------------- Round 2 --------------------
		game8.throwDart(2, 1);
		game8.throwDart(2, 3);
		game8.throwDart(10, 3);
		assertEquals(13,game8.getScore()[0]);
		game8.throwDart(2, 2);
		game8.throwDart(25, 1);
		game8.throwDart(25, 2);
		assertEquals(7,game8.getScore()[1]);
		game8.throwDart(16, 2);
		game8.throwDart(17, 1);
		game8.throwDart(19, 3);
		assertEquals(0,game8.getScore()[2]);
		//----------------- Round 3 ---------------------
		game8.throwDart(12, 1);
		game8.throwDart(12, 3);
		game8.throwDart(10, 3);
		assertEquals(13,game8.getScore()[0]);
		game8.throwDart(3, 2);
		game8.throwDart(3, 1);
		game8.throwDart(3, 2);
		assertEquals(22,game8.getScore()[1]);
		game8.throwDart(11, 2);
		game8.throwDart(14, 1);
		game8.throwDart(16, 3);
		assertEquals(0,game8.getScore()[2]);
		//----------------- Round 4 ---------------------
		game8.throwDart(13, 1);
		game8.throwDart(17, 3);
		game8.throwDart(7, 3);
		assertEquals(13,game8.getScore()[0]);
		game8.throwDart(4, 2);
		game8.throwDart(4, 1);
		game8.throwDart(4, 2);
		assertEquals(42,game8.getScore()[1]);
		game8.throwDart(11, 2);
		game8.throwDart(14, 1);
		game8.throwDart(16, 3);
		assertEquals(0,game8.getScore()[2]);
		//----------------- Round 5 ---------------------
		game8.throwDart(13, 1);
		game8.throwDart(17, 3);
		game8.throwDart(7, 3);
		assertEquals(13,game8.getScore()[0]);
		game8.throwDart(5, 1);
		game8.throwDart(5, 2);
		game8.throwDart(5, 1);
		assertEquals(62,game8.getScore()[1]);
		game8.throwDart(11, 2);
		game8.throwDart(14, 1);
		game8.throwDart(16, 3);
		assertEquals(0,game8.getScore()[2]);
		//----------------- Round 6 ---------------------
		game8.throwDart(13, 1);
		game8.throwDart(6, 3);
		game8.throwDart(7, 3);
		assertEquals(31,game8.getScore()[0]);
		game8.throwDart(6, 1);
		game8.throwDart(6, 2);
		game8.throwDart(6, 1);
		assertEquals(86,game8.getScore()[1]);
		game8.throwDart(11, 2);
		game8.throwDart(14, 1);
		game8.throwDart(16, 3);
		assertEquals(0,game8.getScore()[2]);
		//----------------- Round 7 ---------------------
		game8.throwDart(13, 1);
		game8.throwDart(17, 3);
		game8.throwDart(7, 3);
		assertEquals(52,game8.getScore()[0]);
		game8.throwDart(7, 1);
		game8.throwDart(5, 2);
		game8.throwDart(7, 1);
		assertEquals(100,game8.getScore()[1]);
		game8.throwDart(11, 2);
		game8.throwDart(14, 1);
		game8.throwDart(16, 3);
		assertEquals(0,game8.getScore()[2]);
		//----------------- Round 8 ---------------------
		game8.throwDart(13, 1);
		game8.throwDart(8, 3);
		game8.throwDart(7, 3);
		assertEquals(76,game8.getScore()[0]);
		game8.throwDart(5, 1);
		game8.throwDart(8, 2);
		game8.throwDart(5, 1);
		assertEquals(116,game8.getScore()[1]);
		game8.throwDart(11, 2);
		game8.throwDart(14, 1);
		game8.throwDart(16, 3);
		assertEquals(0,game8.getScore()[2]);
		//----------------- Round 9 ---------------------
		game8.throwDart(13, 1);
		game8.throwDart(17, 3);
		game8.throwDart(7, 3);
		assertEquals(76,game8.getScore()[0]);
		game8.throwDart(9, 1);
		game8.throwDart(9, 2);
		game8.throwDart(9, 1);
		assertEquals(152,game8.getScore()[1]);
		game8.throwDart(11, 2);
		game8.throwDart(14, 1);
		game8.throwDart(16, 3);
		assertEquals(0,game8.getScore()[2]);
	}
	
	@Test
	public void testShanghai3() {
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		Player Lina = new Player("Lina");
		Player Pugna = new Player("Pugna");
		Player Luna = new Player("Luna");
		Player Zeus = new Player("Zeus");
		Shanghai game9 = new Shanghai(10);
		game9.addPlayer(Rick);
		game9.addPlayer(Sven);
		game9.addPlayer(Lina);
		game9.addPlayer(Pugna);
		game9.addPlayer(Luna);
		game9.addPlayer(Zeus);
		game9.start();
		//----------------- Round 1 --------------------
		game9.throwDart(1, 2);
		game9.throwDart(2, 3);
		game9.throwDart(16, 3);
		assertEquals(2,game9.getScore()[0]);
		game9.throwDart(18, 3);
		game9.throwDart(1, 2);
		game9.throwDart(5, 2);
		assertEquals(2,game9.getScore()[1]);
		game9.throwDart(5, 2);
		game9.throwDart(0, 0);
		game9.throwDart(12, 2);
		assertEquals(0,game9.getScore()[2]);
		game9.throwDart(20, 1);
		game9.throwDart(8, 2);
		game9.throwDart(19,3);
		// Pugna shanghais.
		assertEquals("Pugna",game9.getWinner().getName());
	}
	
	@Test
	public void basistest1() {
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		Player Lina = new Player("Lina");
		Player Pugna = new Player("Pugna");
		Player Luna = new Player("Luna");
		Player Zeus = new Player("Zeus");
		Shanghai game10 = new Shanghai(10);
		game10.addPlayer(Rick);
		game10.addPlayer(Sven);
		game10.addPlayer(Lina);
		game10.addPlayer(Pugna);
		game10.addPlayer(Luna);
		game10.addPlayer(Zeus);
		assertEquals(false, game10.isRunning());
		game10.start();
		assertEquals(true, game10.isRunning());
		game10.endGame();
		assertEquals(false, game10.isRunning());	
	}
	
	@Test
	public void basistest2() {
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		Player Lina = new Player("Lina");
		Player Pugna = new Player("Pugna");
		Player Luna = new Player("Luna");
		Player Zeus = new Player("Zeus");
		DoubleOut game11 = new DoubleOut(10,501);
		game11.addPlayer(Rick);
		game11.addPlayer(Sven);
		game11.addPlayer(Lina);
		game11.addPlayer(Pugna);
		game11.addPlayer(Luna);
		game11.addPlayer(Zeus);
		assertEquals(6,game11.getPlayerCount());
		assertEquals("DoubleOut",game11.getGamemode());
		assertEquals("Rick",game11.getPlayers()[0].getName());
		assertEquals("Luna",game11.getPlayers()[4].getName());
		assertEquals(game11.getPlayerCount(),game11.getPlayers().length);
	}
	
	@Test
	public void basistest3() {
		Player Rick = new Player("Rick");
		Player Sven = new Player("Sven");
		Player Lina = new Player("Lina");
		Player Pugna = new Player("Pugna");
		Player Luna = new Player("Luna");
		Player Zeus = new Player("Zeus");
		Tactics game12 = new Tactics(10);
		game12.addPlayer(Rick);
		game12.addPlayer(Sven);
		game12.addPlayer(Lina);
		game12.addPlayer(Pugna);
		game12.addPlayer(Luna);
		game12.addPlayer(Zeus);
		game12.start();
		// ----- Invalid throw judgement ----
		game12.throwDart(3, 1);
		assertEquals(2,game12.getLeftDarts());
		game12.throwDart(25, 3);
		assertEquals(2,game12.getLeftDarts());
		game12.throwDart(0, 2);
		assertEquals(2,game12.getLeftDarts());
		game12.throwDart(0, 0);
		assertEquals(1,game12.getLeftDarts());
		game12.throwDart(100,100);
		assertEquals(1,game12.getLeftDarts());
		game12.throwDart(10, 2);
		//----- Above is a valid throw, so there are 3 chance for the next player
		assertEquals(3,game12.getLeftDarts());
	}
}
