package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {

	private BoardCell[][] grid;
	private int numRows;
	private int numCols;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap <Character, Room>();
	private static Board theInstance = new Board();
	private ArrayList<String[]> boardStrings = new ArrayList <String[]>();
	private ArrayList<String[]> setupStrings = new ArrayList <String[]>();

	
	private Board() {
        super();
	}
	
	public static Board getInstance() {
        return theInstance;
	}
	
	public void initialize() {
		loadSetupConfig();
		loadLayoutConfig();
	}
	
	public void loadSetupConfig() {
		String roomName;
		char initial;
		try {
			FileReader obj = new FileReader(setupConfigFile);
			Scanner reader = new Scanner(obj);
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				setupStrings.add(line.split(", "));

			}
			reader.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		for(String[] thisLine: setupStrings) {
			if(thisLine[0].equals("Room")|| thisLine[0].equals("Space")) {
				roomName = thisLine[1];
				initial = thisLine[2].charAt(0);
				Room newRoom = new Room(roomName);
				roomMap.put(initial,newRoom);
			}
		}
	}
	
	public void loadLayoutConfig() {
		try {
			FileReader obj2 = new FileReader(layoutConfigFile);
			Scanner reader2 = new Scanner(obj2);
			while(reader2.hasNextLine()) {
				String line = reader2.nextLine();
				boardStrings.add(line.split(","));
				}
			reader2.close();
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}	
			numRows = boardStrings.size();
			numCols = boardStrings.get(0).length;
			grid = new BoardCell [numRows][numCols];
			
			for(int row = 0; row < boardStrings.size(); row++) {
				for(int col = 0; col < boardStrings.get(0).length; col++) {
					grid[row][col] = new BoardCell(row, col, boardStrings.get(row)[col].charAt(0));
					if(boardStrings.get(row)[col].length() == 2) {
						grid[row][col].specialCell(boardStrings.get(row)[col].charAt(1), roomMap);
					}
				}
			}
		
		
	}
	
	public void setConfigFiles(String string, String string2) {
		this.layoutConfigFile = string;
		this.setupConfigFile = string2;
	}
	
	public Room getRoom(char c) {
		return roomMap.get(c);
	}
	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}
	
}
