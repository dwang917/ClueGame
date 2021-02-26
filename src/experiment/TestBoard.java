package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	Set <TestBoardCell> targets = new HashSet<TestBoardCell>();

	public TestBoard() {
		super();
	}
	

	void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	
	Set <TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(-1, -1);
	}
}
