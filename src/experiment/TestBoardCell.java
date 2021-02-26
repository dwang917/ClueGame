package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	
	private int row;
	private int col;
	private Set <TestBoardCell> adjList = new HashSet<TestBoardCell>();
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	

	public void addAdjacency(TestBoardCell cell) {
	}
	
	public Set <TestBoardCell> getAdjList(){
		return adjList;
	}
	
	public void setRoom(boolean b) {
		
	}
	
	public boolean IsRoom() {
		return false;
	}
	
	public void setOccupied(boolean b) {
	
	}
	
	public boolean getOccupied() {
		return false;
		
	}
	
}
