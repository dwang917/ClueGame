package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the master bedroom that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(6, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(7, 5)));
		
		// now test the ballroom (note not marked since multiple test here)
		testList = board.getAdjList(10, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(7, 2)));
		
		// one more room, the kitchen
		testList = board.getAdjList(20, 20);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(17, 17)));
		assertTrue(testList.contains(board.getCell(2, 3)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(6, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(6, 6)));
		assertTrue(testList.contains(board.getCell(6, 4)));
		assertTrue(testList.contains(board.getCell(7, 5)));

		testList = board.getAdjList(11, 4);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(11, 5)));
		assertTrue(testList.contains(board.getCell(10, 2)));
		assertTrue(testList.contains(board.getCell(12, 4)));
		
		testList = board.getAdjList(15, 19);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(11, 21)));
		assertTrue(testList.contains(board.getCell(15, 20)));
		assertTrue(testList.contains(board.getCell(15, 18)));
		assertTrue(testList.contains(board.getCell(16, 19)));
	}
	
	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(19, 0);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(19, 1)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(7, 18);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(7, 17)));
		assertTrue(testList.contains(board.getCell(7, 19)));
		assertTrue(testList.contains(board.getCell(8, 18)));

		// Test adjacent to walkway
		testList = board.getAdjList(19, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(19, 6)));
		assertTrue(testList.contains(board.getCell(19, 4)));
		assertTrue(testList.contains(board.getCell(18, 5)));
		assertTrue(testList.contains(board.getCell(20, 5)));

		// Test next to supply closet
		testList = board.getAdjList(20,5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(21, 3)));
		assertTrue(testList.contains(board.getCell(19, 5)));
		assertTrue(testList.contains(board.getCell(20, 6)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	@Test
	public void testTargetsInTerraceRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 21), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(8, 19)));

		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 21), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(8, 17)));
		assertTrue(targets.contains(board.getCell(7, 18)));	
		assertTrue(targets.contains(board.getCell(8, 21)));
		assertTrue(targets.contains(board.getCell(9, 18)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 21), 4);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(8, 16)));
		assertTrue(targets.contains(board.getCell(7, 21)));	
		assertTrue(targets.contains(board.getCell(9, 17)));
		assertTrue(targets.contains(board.getCell(10, 18)));	
		
	}
	
	@Test
	public void testTargetsInKitchen() {
		// test a roll of 1
		board.calcTargets(board.getCell(20, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(18, 17)));
		assertTrue(targets.contains(board.getCell(17, 17)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(20, 20), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(19, 16)));
		assertTrue(targets.contains(board.getCell(15, 17)));	
		assertTrue(targets.contains(board.getCell(16, 18)));
		assertTrue(targets.contains(board.getCell(17, 15)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(20, 20), 4);
		targets= board.getTargets();
		assertEquals(21, targets.size());
		assertTrue(targets.contains(board.getCell(20, 16)));
		assertTrue(targets.contains(board.getCell(15, 16)));	
		assertTrue(targets.contains(board.getCell(16, 19)));
		assertTrue(targets.contains(board.getCell(17, 15)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(11, 4), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(11, 5)));
		assertTrue(targets.contains(board.getCell(12, 4)));	
		assertTrue(targets.contains(board.getCell(10, 2)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 4), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(13, 3)));
		assertTrue(targets.contains(board.getCell(12, 6)));
		assertTrue(targets.contains(board.getCell(10, 6)));	
		assertTrue(targets.contains(board.getCell(13, 5)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 4), 4);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(9, 6)));
		assertTrue(targets.contains(board.getCell(14, 5)));	
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(13, 2)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 1), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(13, 2)));


		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 1), 3);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(13, 4)));
		assertTrue(targets.contains(board.getCell(16, 2)));

		// test a roll of 4
		board.calcTargets(board.getCell(13, 1), 4);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(12, 4)));
		assertTrue(targets.contains(board.getCell(13, 5)));
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 6), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(13, 5)));
		assertTrue(targets.contains(board.getCell(12, 6)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 6), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(13, 3)));
		assertTrue(targets.contains(board.getCell(13, 5)));
		assertTrue(targets.contains(board.getCell(10, 6)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(13, 6), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(12, 5)));
		assertTrue(targets.contains(board.getCell(14, 5)));
		assertTrue(targets.contains(board.getCell(16, 7)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(15, 6).setOccupied(true);
		board.calcTargets(board.getCell(13, 6), 4);
		board.getCell(15, 6).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(9, 6)));
		assertTrue(targets.contains(board.getCell(16, 5)));
		assertTrue(targets.contains(board.getCell(13, 4)));	
		assertFalse( targets.contains( board.getCell(14, 4))) ;
		assertFalse( targets.contains( board.getCell(15, 6))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(21, 3).setOccupied(true);
		board.getCell(19, 5).setOccupied(true);
		board.calcTargets(board.getCell(20, 5), 1);
		board.getCell(12, 3).setOccupied(false);
		board.getCell(19, 5).setOccupied(false);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(21, 3)));	
		assertTrue(targets.contains(board.getCell(20, 6)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(15, 19).setOccupied(true);
		board.calcTargets(board.getCell(11, 21), 3);
		board.getCell(12, 15).setOccupied(false);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(8, 17)));
		assertTrue(targets.contains(board.getCell(8, 21)));	
		assertTrue(targets.contains(board.getCell(7, 18)));

	}
}
