package clueGame;

import java.util.Map;

public class Board {

	/*
	 * Stubs for Board class with attributes from the UML
	 */
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private static Board theInstance = new Board();
	
	private Board() {
        super() ;
	}
	
	public static Board getInstance() {
        return theInstance;
	}
	
	public void initialize() {
		
	}
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	
	public void setConfigFiles(String string, String string2) {
		
	}
	
	public Room getRoom(char c) {
		return new Room();
	}
	
	public Room getRoom(BoardCell cell) {
		return new Room();
	}

	public BoardCell getCell(int i, int j) {
		return new BoardCell();
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return -1;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return -1;
	}
}
