package clueGame;

import java.awt.Color;
import java.awt.Graphics;


public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private int size;
	
	public Room(String name) {
		super();
		this.name = name;
	}
	public Room() {
		// TODO Auto-generated constructor stub
		super();
	}
	public void drawRoomName(Graphics g) {
		g.setColor(Color.BLUE);
		if(!(name.equals("Walkway") && !(name.equals("Unused"))))
			g.drawString(name, labelCell.getCol() * size, labelCell.getRow() * size);
	}
	
	public String getName() {
		return name;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	public void setCenterCell(BoardCell cell) {
		centerCell = cell;
		size = centerCell.getSize();
	}
	public void setLabelCell(BoardCell cell) {
		labelCell = cell;
	}
}
