package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private boolean room;
	private boolean occupied;
	private Set <TestBoardCell> adjacent = new HashSet <TestBoardCell>();
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	

	public void addAdjacency(TestBoardCell cell) {
		adjacent.add(cell);
	}
	
	public Set <TestBoardCell> getAdjList(){
		return adjacent;
	}
	
	void setRoom(boolean b) {
		room = b;
	}
	
	boolean IsRoom() {
		return room;
	}
	
	void setOccupied(boolean b) {
		occupied = b;
	}
	
	boolean getOccupied() {
		return occupied;
		
	}


	public int getRow() {
		return row;
	}


	public int getCol() {
		return col;
	}
	
}
