package clueGame;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String name) {
		super();
		this.name = name;
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
