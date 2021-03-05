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
		this.isRoom = false; //default to not a room
		this.isOccupied = false; //default to not occupied
		
	}
	

	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell); //add the parameter cell to the set of adjacent cells
	}
	
	public Set <TestBoardCell> getAdjList(){
		return adjList;
	}
	
	public void setRoom(boolean b) {
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


	//created toString for testing
	@Override
	public String toString() {
		return "TestBoardCell [row=" + row + ", col=" + col + "]";
	}
	
}
