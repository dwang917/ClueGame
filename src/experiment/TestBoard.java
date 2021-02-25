package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	Set <TestBoardCell> board;
	Set <TestBoardCell> targets;

	public TestBoard() {
		super();
		this.board = new HashSet <TestBoardCell>();
	}

	void calcTargets(TestBoardCell startCell, int pathlength) {
		//int left = pathlength;
		//targets = new HashSet<TestBoardCell>();
		
		
	}
	
	
	Set <TestBoardCell> getTargets(){
		return targets;
	}
	
	TestBoardCell getCell(int row, int col) {
		TestBoardCell cell = new TestBoardCell(row,col);
		for(TestBoardCell current : board) {
			if(current.equals(cell)) {
				return current;
			}
		}
		return null;
	}
}
