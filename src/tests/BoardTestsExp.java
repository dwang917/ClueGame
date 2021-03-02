package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {

	TestBoard board;
	
	//Set up the board for each test
	@BeforeEach
	public void setup() {
		board = new TestBoard();
	}
	
	/*Test adjacencies for several different locations
	 * Test middle, edges and corners
	 */
	@Test
	public void TestAjacency1() {
		//upper left corner
		TestBoardCell  cell = board.getCell(0,0);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 2);
		Assert.assertTrue(aja.contains(board.getCell(1, 0)));
		Assert.assertTrue(aja.contains(board.getCell(0, 1)));
	}
	
	@Test
	public void TestAjacency2() {
		//lower right corner
		TestBoardCell  cell = board.getCell(3,3);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 2);
		Assert.assertTrue(aja.contains(board.getCell(2, 3)));
		Assert.assertTrue(aja.contains(board.getCell(3, 2)));
	}
	
	@Test
	public void TestAjacency3() {
		//right edge
		TestBoardCell  cell = board.getCell(1,3);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 3);
		Assert.assertTrue(aja.contains(board.getCell(0, 3)));
		Assert.assertTrue(aja.contains(board.getCell(1, 2)));
		Assert.assertTrue(aja.contains(board.getCell(2, 3)));
	}
	
	@Test
	public void TestAjacency4() {
		//left corner
		TestBoardCell  cell = board.getCell(2,0);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 3);
		Assert.assertTrue(aja.contains(board.getCell(1, 0)));
		Assert.assertTrue(aja.contains(board.getCell(2, 1)));
		Assert.assertTrue(aja.contains(board.getCell(3, 0)));
	}
	
	@Test
	public void TestAjacency5() {
		//middle
		TestBoardCell  cell = board.getCell(1,1);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 4);
		Assert.assertTrue(aja.contains(board.getCell(0, 1)));
		Assert.assertTrue(aja.contains(board.getCell(1, 0)));
		Assert.assertTrue(aja.contains(board.getCell(1, 2)));
		Assert.assertTrue(aja.contains(board.getCell(2, 1)));
	}
	
	//test target creation without rooms and occupations
	@Test
	public void TestTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set <TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		//TestBoardCell cell = board.getCell(0, 0);
		//board.calcTargets(cell, 2);
		//Set<TestBoardCell> targets = board.getTargets();
		//Assert.assertEquals(3, targets.size());
		//Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		//Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		//Assert.assertTrue(targets.contains(board.getCell(0, 2)));
	}
	
	/*
	 * Test target creation with occupation
	 * Different rolls and different start locations
	 */
	@Test
	public void TestTargetsOccupied1() {
		//TestBoardCell cell = board.getCell(0, 0);
		//board.getCell(0, 1).setOccupied(true);
		//board.calcTargets(cell, 3);
		//Set<TestBoardCell> targets = board.getTargets();
		//Assert.assertEquals(5, targets.size());
		//Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		//Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		//Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		//Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		//Assert.assertTrue(targets.contains(board.getCell(0, 3)));
	}
	
	@Test
	public void TestTargetsOccupied2() {
		TestBoardCell cell = board.getCell(1, 3);
		board.getCell(1, 1).setOccupied(true);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	/*
	 * Test target creation with room
	 * Different start locations and room locations
	 */
	@Test
	public void TestTargetsRoom1() {
		TestBoardCell cell = board.getCell(1, 3);
		board.getCell(1,2).setIsRoom(true);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
	}
	
	@Test
	public void TestTargetsRoom2() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(0,1).setIsRoom(true);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
	}
}