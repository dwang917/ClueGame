package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	final static int COLS = 4;
	final static int ROWS = 4;
	private TestBoardCell [][] grid;
	private Set <TestBoardCell> targets;
	private Set <TestBoardCell> visited;

	public TestBoard() {
		super();
		this.grid = new TestBoardCell [ROWS][COLS]; //set up grid 2D array
		
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				grid[row][col] = new TestBoardCell(row, col); //fill grid array with filled TestBoardCell objects
			}
		}
		
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				setAdjacent(grid[row][col]); //fill adjacent set
			}
		}
	}
	
	public void setAdjacent(TestBoardCell cell) { //create the adjacent set of the cell
			if(cell.getCol()+1 < COLS)//test for column above
				cell.addAdjacency(getCell(cell.getRow(),cell.getCol()+1));
			if(cell.getCol() > 0) //test for column below
				cell.addAdjacency(getCell(cell.getRow(),cell.getCol()-1));
			if(cell.getRow()+1 < ROWS) //test for row to the right
				cell.addAdjacency(getCell(cell.getRow()+1,cell.getCol()));
			if(cell.getRow() > 0)//test for row to the left
				cell.addAdjacency(getCell(cell.getRow()-1,cell.getCol()));

		}

	public void calcTargets(TestBoardCell startCell, int pathlength) {
		targets = new HashSet<TestBoardCell>(); //start with empty set
		visited = new HashSet<TestBoardCell>(); //start with empty set
		visited.add(startCell); //add the starting cell to visited set
		findAllTargets(startCell,pathlength);
	}
	
	public void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for(TestBoardCell adjCell: thisCell.getAdjList()) { //go through each adjacent cell of the current cell
			if(adjCell.IsRoom()) {
				targets.add(adjCell);
			}
			
			if(!(visited.contains(adjCell))) {//if the cell being tested has not been visited then...
				visited.add(adjCell); //add adjacent cell to visited set
				if(numSteps == 1) { //if the number of steps equals 1...
					if(!adjCell.getOccupied()) { //if the adjacent cell isn't occupied
						targets.add(adjCell);//add the adjacent cell to targets
					}
				}
				else {
					findAllTargets(adjCell,numSteps - 1); //recursively go through all the adjacent cells, making the current cell the adjacent cell
				}
				visited.remove(adjCell);
			}
		}
		
	}
	
	public Set <TestBoardCell> getTargets(){
		return targets; //return the set of targets
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
