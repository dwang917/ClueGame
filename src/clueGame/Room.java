package clueGame;

import java.awt.Color;
import java.awt.Graphics;


public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String name) {
		super();
		this.name = name;
	}
	public Room() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	//draw each room label
	public void drawRoomName(Graphics g, int height, int width) {
		g.setColor(Color.BLUE);
		if(!(name.equals("Walkway") && !(name.equals("Unused"))))
			g.drawString(name, labelCell.getCol() * width, labelCell.getRow() * height);
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
	}
	public void setLabelCell(BoardCell cell) {
		labelCell = cell;
	}
}
