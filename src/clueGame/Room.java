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
	
	public Room(String name) {
		super();
		this.name = name;
	}
	
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public String getName() {
		return name;
	}
	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return centerCell;
	}
	public BoardCell getLabelCell() {
		// TODO Auto-generated method stub
		return labelCell;
	}
	
	public void setCenterCell(BoardCell cell) {
		// TODO Auto-generated method stub
		centerCell = cell;
	}
	public void setLabelCell(BoardCell cell) {
		// TODO Auto-generated method stub
		labelCell = cell;
	}
	@Override
	public String toString() {
		return "Room [name=" + name + ", centerCell=" + centerCell + ", labelCell=" + labelCell + "]";
	}
}
