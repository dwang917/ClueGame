package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardCell {
	
	/*
	 * BoardCell class with setters and getters and helper methods
	 */
	private int row; //specific cell row in the board
	private int col; //specific cell column in the board
	private char initial; //initial of the cell
	private DoorDirection doorDirection = null; //if the cell has a door, saves the direction
	private boolean roomLabel = false; //true if cell is the label of room
	private boolean roomCenter = false; //true if cell is the center of room
	private char secretPassage = ' '; //if cell is connected to a secret passage, save initial of other room
	private boolean occupied; //true if a player is in the cell, false if not
	Set<BoardCell> adjList; //set to hold all adjacent cells of the current cell
	
	
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
	
	//Set the direction of doorway
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

	//This method identifies special cells and modifies their properties accordingly
	public void specialCell(char c, Map<Character, Room> roomMap) throws BadConfigFormatException {
		if(c == '<' || c == '>' || c == 'v'|| c == '^') {
			this.setDirection(c);
		}
		else if(c == '#') {
			this.setLabel(true);
			//sets the room's label cell to the caller cell
			roomMap.get(this.getInitial()).setLabelCell(this);
		}
		else if(c == '*') {
			this.setRoomCenter(true);
			//sets the room's center cell to the caller cell
			roomMap.get(this.getInitial()).setCenterCell(this);
		}
		else if(roomMap.get(c) == null){
			throw new BadConfigFormatException("The second letter does not represent any room in the setup file");
		}
		else {
			this.setSPassage(c);
		}
	}

	public void setOccupied(boolean b) {
		// TODO Auto-generated method stub
		occupied = b;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public Set <BoardCell>  getAdjList() {
		return adjList;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
