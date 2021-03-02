package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row, col;
	private Boolean isRoom, isOccupied;
	private Set <TestBoardCell> adjList = new HashSet <TestBoardCell>();
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.isRoom = false;
		this.isOccupied = false;
		
	}
	

	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set <TestBoardCell> getAdjList(){
		return adjList;
	}
	
	public void setIsRoom(boolean b) {
		isRoom = b;
	}
	
	public boolean IsRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean b) {
		isOccupied = b;
		
	}
	
	public boolean getOccupied() {
		return isOccupied;
		
	}


	public int getRow() {
		return row;
	}


	public int getCol() {
		return col;
	}


	@Override
	public String toString() {
		return "TestBoardCell [row=" + row + ", col=" + col + "]";
	}
	
}
