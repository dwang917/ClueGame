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
		this.grid = new TestBoardCell [ROWS][COLS];
		
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				grid[row][col] = new TestBoardCell(row, col);
			}
		}
		
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				setAdjacent(grid[row][col]);
			}
		}
	}
	
	public void setAdjacent(TestBoardCell cell) {
			if(cell.getCol()+1 < COLS)
				cell.addAdjacency(getCell(cell.getRow(),cell.getCol()+1));
			if(cell.getCol() > 0)
				cell.addAdjacency(getCell(cell.getRow(),cell.getCol()-1));
			if(cell.getRow()+1 < ROWS)
				cell.addAdjacency(getCell(cell.getRow()+1,cell.getCol()));
			if(cell.getRow() > 0)
				cell.addAdjacency(getCell(cell.getRow()-1,cell.getCol()));

		}

	public void calcTargets(TestBoardCell startCell, int pathlength) {
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		visited.add(startCell);
		System.out.println("New find: ");
		findAllTargets(startCell,pathlength);
		for(TestBoardCell cell:targets)
			System.out.println(cell);
		System.out.println("\n");
	}
	
	public void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for(TestBoardCell adjCell: thisCell.getAdjList()) {
			if(adjCell.IsRoom()) {
				targets.add(adjCell);
			}
			if(!(visited.contains(adjCell))) {
				visited.add(adjCell);
				if(numSteps == 1) {
					if(!adjCell.getOccupied()) {
						targets.add(adjCell);
					}
				}
				else {
					findAllTargets(adjCell,numSteps - 1);
				}
				visited.remove(adjCell);
			}
		}
		
	}
	
	public Set <TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
