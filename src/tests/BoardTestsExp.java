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
	public void TestAjacency() {
		TestBoardCell  cell = board.getCell(0,0);
		Set<TestBoardCell> aja = cell.getAdjList();
		Assert.assertTrue(aja.size() == 2);
		Assert.assertTrue(aja.contains(board.getCell(1, 0)));
		Assert.assertTrue(aja.contains(board.getCell(0, 1)));
	}
}