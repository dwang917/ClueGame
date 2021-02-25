package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private Set <TestBoardCell> adjacent = new HashSet <TestBoardCell>();
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	void addAdjacency(TestBoardCell cell) {
		adjacent.add(cell);
	}
	
	Set <TestBoardCell> getAdjList(){
		return adjacent;
	}
	
	void setRoom(boolean b) {
		
	}
	
	void booleanIsRoom() {
		
	}
	
	void setOccupied(boolean b) {
		
	}
	
	boolean getOccupied() {
		return false;
		
	}
	
}
