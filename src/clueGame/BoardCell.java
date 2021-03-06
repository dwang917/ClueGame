package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	/*
	 * stubs for BoardCell class and attribtes from the UML 
	 */
	
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private Room inRoom;
	private boolean roomLabel = false;
	private boolean roomCenter = false;
	private char secretPassage;
	Set<BoardCell> adjList;
	
	
	public BoardCell(int row, int col){
		super();
		this.row = row;
		this.col = col;
		this.initial = ' ';
		this.doorDirection = null;
		this.roomLabel = false;
		this.roomCenter = false;
		this.secretPassage = ' ';
		this.adjList = new HashSet <BoardCell>();
	}
	
	public BoardCell(int row, int col, boolean roomLabel) {
		super();
		this.row = row;
		this.col = col;
		this.initial = ' ';
		this.doorDirection = null;
		this.roomLabel = roomLabel;
		this.roomCenter = false;
		this.secretPassage = ' ';
		this.adjList = new HashSet <BoardCell>();
	}
	
	public BoardCell(int row, int col, char initial, DoorDirection doorDirection, boolean roomLabel, boolean roomCenter,
			char secretPassage, Set<BoardCell> adjList) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.doorDirection = doorDirection;
		this.roomLabel = roomLabel;
		this.roomCenter = roomCenter;
		this.secretPassage = secretPassage;
		this.adjList = adjList;
	}

	//Default Constructor
	public BoardCell() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}

	public void setRoomCenter(boolean b) {
		roomCenter = b;
	}
	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return roomCenter;
	}

	public void setLabel(boolean b) {
		roomLabel = b;
	}
	
	public boolean isLabel() {
		// TODO Auto-generated method stub
		return roomLabel;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return secretPassage;
	}
	
	public Room getRoom() {
		return inRoom;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		if(doorDirection != null)
			return true;
		return false;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return doorDirection;
	}
	void setRoom(Room newRoom, char charAt) {
		// TODO Auto-generated method stub
		this.initial = charAt;
		this.inRoom = newRoom;
	}
	public void setSPassage(char c) {
		// TODO Auto-generated method stub
		this.secretPassage = c;
		
	}
	
	public void setInitial(char charAt) {
		 this.initial = charAt;
	}
	
	public char getInitial() {
		return initial;
	}
	public void setDirection(char charAt) {
		// TODO Auto-generated method stub
		if(charAt == '<')
			doorDirection = DoorDirection.LEFT;
		if(charAt == '>')
			doorDirection = DoorDirection.RIGHT;
		if(charAt == '^')
			doorDirection = DoorDirection.UP;
		if(charAt == 'v')
			doorDirection = DoorDirection.DOWN;
		
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + ", roomLabel=" + roomLabel + ", roomCenter=" + roomCenter
				+ "]";
	}
}
