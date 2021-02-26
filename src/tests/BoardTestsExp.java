package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {

	private TestBoard board;
	
	@BeforeEach
	public void setup() {
		board = new TestBoard();
	}

	@Test
	public void TestAjacency1() {
		TestBoardCell  cell = board.getCell(0,0);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 2);
		Assert.assertTrue(aja.contains(board.getCell(1, 0)));
		Assert.assertTrue(aja.contains(board.getCell(0, 1)));
	}
	
	@Test
	public void TestAjacency2() {
		TestBoardCell  cell = board.getCell(3,3);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 2);
		Assert.assertTrue(aja.contains(board.getCell(2, 3)));
		Assert.assertTrue(aja.contains(board.getCell(3, 2)));
	}
	
	@Test
	public void TestAjacency3() {
		TestBoardCell  cell = board.getCell(1,3);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 3);
		Assert.assertTrue(aja.contains(board.getCell(0, 3)));
		Assert.assertTrue(aja.contains(board.getCell(1, 2)));
		Assert.assertTrue(aja.contains(board.getCell(2, 3)));
	}
	
	@Test
	public void TestAjacency4() {
		TestBoardCell  cell = board.getCell(3,0);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 3);
		Assert.assertTrue(aja.contains(board.getCell(2, 0)));
		Assert.assertTrue(aja.contains(board.getCell(3, 1)));
		Assert.assertTrue(aja.contains(board.getCell(4, 0)));
	}
}