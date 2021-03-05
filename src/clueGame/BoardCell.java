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
	private boolean roomLabel;
	private boolean roomCenter;
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
		
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Room getRoom() {
		return inRoom;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
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
}
