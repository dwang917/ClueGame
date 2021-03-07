package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardCell {
	/*
	 * stubs for BoardCell class and attribtes from the UML 
	 */
	
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection = null;
	private boolean roomLabel = false;
	private boolean roomCenter = false;
	private char secretPassage;
	Set<BoardCell> adjList;
	
	
	
	public BoardCell(int row, int col, char ini){
		super();
		this.row = row;
		this.col = col;
		this.initial = ini;
		this.secretPassage = ' ';
		this.adjList = new HashSet <BoardCell>();
	}
	

	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}

	public void setRoomCenter(boolean b) {
		roomCenter = b;
	}
	public boolean isRoomCenter() {
		return roomCenter;
	}

	public void setLabel(boolean b) {
		roomLabel = b;
	}
	
	public boolean isLabel() {
		return roomLabel;
	}

	public char getSecretPassage() {
		return secretPassage;
	}
	

	public boolean isDoorway() {
		if(doorDirection != null)
			return true;
		return false;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public void setSPassage(char c) {
		this.secretPassage = c;
	}
	
	public void setInitial(char charAt) {
		 this.initial = charAt;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public void setDirection(char charAt) {
		if(charAt == '<')
			doorDirection = DoorDirection.LEFT;
		if(charAt == '>')
			doorDirection = DoorDirection.RIGHT;
		if(charAt == '^')
			doorDirection = DoorDirection.UP;
		if(charAt == 'v')
			doorDirection = DoorDirection.DOWN;
		
	}

	
	public void specialCell(char c, Map<Character, Room> roomMap) {
		if(c == '<' || c == '>' || c == 'v'|| c == '^') {
			this.setDirection(c);
		}
		else if(c == '#') {
			this.setLabel(true);
			roomMap.get(this.getInitial()).setLabelCell(this);
			
		}
		else if(c == '*') {
			this.setRoomCenter(true);
			roomMap.get(this.getInitial()).setCenterCell(this);
		}
		else {
			this.setSPassage(c);
		}
	}
}
