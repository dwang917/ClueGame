package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardCell {

	/*
	 * BoardCell class with setters and getters and helper methods
	 */
	private int row; // specific cell row in the board
	private int col; // specific cell column in the board
	private char initial; // initial of the cell
	private DoorDirection doorDirection = null; // if the cell has a door, saves the direction
	private boolean roomLabel = false; // true if cell is the label of room
	private boolean roomCenter = false; // true if cell is the center of room
	private char secretPassage = ' '; // if cell is connected to a secret passage, save initial of other room
	private boolean occupied; // true if a player is in the cell, false if not
	Set<BoardCell> adjList; // set to hold all adjacent cells of the current cell
	boolean targetFlag = false;

	public BoardCell(int row, int col, char ini) {
		super();
		this.row = row;
		this.col = col;
		this.initial = ini;
		this.secretPassage = ' ';
		this.adjList = new HashSet<BoardCell>();
	}
	
	public boolean containsClick(int mouseX, int mouseY, int width, int height) {
		Rectangle rect= new Rectangle(col * width, row * height, width, height);
		return(rect.contains(new Point(mouseX, mouseY)));
	}

	public boolean isTargetFlag() {
		return targetFlag;
	}

	public void setTargetFlag(boolean targetFlag) {
		this.targetFlag = targetFlag;
	}

	public void drawCell(Graphics g, int height, int width) {
		if (initial == 'W' || initial == 'X') {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.GRAY);
		}

		// draw rectangle
		g.drawRect(col * width, row * height, width, height);

		// set color to fill walkway
		if (initial == 'W') {
			g.setColor(Color.YELLOW);
		}
		// fill cells
		g.fillRect(col * width, row * height, width, height);
		
		if(targetFlag) {
			g.setColor(Color.MAGENTA);
			g.fillRect(col * width, row * height, width, height);
		}
	}

	//draw the doorway rectangle on boardcells
	public void drawDoorway(Graphics g, int height, int width) {
		int doorX = 0, doorY = 0;
		g.setColor(Color.BLUE);
		//if current cell is a doorway, then calculate the rectangle location
		if (doorDirection != null) {
			if (doorDirection == DoorDirection.UP) {
				doorX = col * width;
				doorY = row * height - 3;
			} else if (doorDirection == DoorDirection.DOWN) {
				doorX = col * width;
				doorY = (row + 1) * height;
			} else if (doorDirection == DoorDirection.RIGHT) {
				doorX = (col + 1) * width;
				doorY = row * height;
			} else if (doorDirection == DoorDirection.LEFT) {
				doorX = col * width - 3;
				doorY = row * height;
			}
			//check the orientation of the doorways
			if (doorDirection == DoorDirection.RIGHT || doorDirection == DoorDirection.LEFT) {
				g.fillRect(doorX, doorY, 3, height);
			} else {
				g.fillRect(doorX, doorY, width, 3);
			}
		}
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
		if (doorDirection != null)
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

	// Set the direction of doorway
	public void setDirection(char charAt) {
		if (charAt == '<')
			doorDirection = DoorDirection.LEFT;
		if (charAt == '>')
			doorDirection = DoorDirection.RIGHT;
		if (charAt == '^')
			doorDirection = DoorDirection.UP;
		if (charAt == 'v')
			doorDirection = DoorDirection.DOWN;

	}

	// This method identifies special cells and modifies their properties
	// accordingly
	public void specialCell(char c, Map<Character, Room> roomMap, BoardCell cell) throws BadConfigFormatException {
		Room updateRoom = new Room();
		if (c == '<' || c == '>' || c == 'v' || c == '^') {
			setDirection(c);

		} else if (c == '#') {
			roomLabel = true;
			// sets the room's label cell to the caller cell
			// roomMap.get(this.getInitial()).setLabelCell(cell);
			updateRoom = roomMap.get(this.initial);
			updateRoom.setLabelCell(cell);

			roomMap.put(this.initial, updateRoom);
		} else if (c == '*') {
			// this.setRoomCenter(true);
			roomCenter = true;
			// sets the room's center cell to the caller cell
			// roomMap.get(this.getInitial()).setCenterCell(cell);
			updateRoom = roomMap.get(this.initial);
			updateRoom.setCenterCell(cell);

			roomMap.put(this.initial, updateRoom);
		} else if (roomMap.get(c) == null) {
			throw new BadConfigFormatException("The second letter does not represent any room in the setup file");
		} else {
			setSPassage(c);
		}
	}

	public void setOccupied(boolean b) {
		occupied = b;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
