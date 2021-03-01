package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private int size = 4;
	Set <TestBoardCell> board;
	Set <TestBoardCell> targets;

	public TestBoard() {
		super();
		this.board = new HashSet <TestBoardCell>();
		for(int i = 0; i < size; i++) {
			for(int j = 0; j<size; j++) {
				board.add(new TestBoardCell(i,j));
			}
		}
		for(TestBoardCell cell : board) {
			setAdjacent(cell);
		}
	}
	void setAdjacent(TestBoardCell cell) {
			if(cell.getRow()+1 < size)
				cell.addAdjacency(new TestBoardCell(cell.getCol(),cell.getRow()+1));
			if(cell.getRow()-1 > -1)
				cell.addAdjacency(new TestBoardCell(cell.getCol(),cell.getRow()-1));
			if(cell.getCol()+1 < size)
				cell.addAdjacency(new TestBoardCell(cell.getCol()+1,cell.getRow()));
			if(cell.getCol()-1 > -1)
				cell.addAdjacency(new TestBoardCell(cell.getCol()-1,cell.getRow()));

		}

	void calcTargets(TestBoardCell startCell, int pathlength) {
		//int left = pathlength;
		//targets = new HashSet<TestBoardCell>();
	}
	
	
	Set <TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell cell = new TestBoardCell(row,col);
		for(TestBoardCell current : board) {
			if(current.equals(cell)) {
				return current;
			}
		}
		return null;
	}
}
