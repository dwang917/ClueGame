package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	/*
	 * TestBoard class with stub functions
	 */
	
	Set <TestBoardCell> targets = new HashSet<TestBoardCell>();

	public TestBoard() {
		super();
	}
	

	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set <TestBoardCell> getTargets(){
		//return an empty set
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		//return a literal value that will always fail the tests
		return new TestBoardCell(-1, -1);
	}
}
