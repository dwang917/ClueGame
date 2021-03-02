package clueGame;

public class Room {

	/*
	 * Stubs for Room class
	 */
	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Object getName() {
		return name;
	}
	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return null;
	}
	public BoardCell getLabelCell() {
		// TODO Auto-generated method stub
		return null;
	}
}
