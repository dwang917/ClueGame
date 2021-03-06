package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {

	/*
	 * Stubs for Board class with attributes from the UML
	 */
	private BoardCell[][] grid;
	private int numRows;
	private int numCols;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap <Character, Room>();
	//private Map <Character, Character> specialMap = new HashMap <Character, Character>();
	private static Board theInstance = new Board();
	private ArrayList<String> boardStrings = new ArrayList <String>();
	private ArrayList<String> roomStrings = new ArrayList <String>();

	
	private Board() {
        super();
	}
	
	public static Board getInstance() {
        return theInstance;
	}
	
	public void initialize() {
		
	}
	
	public void loadSetupConfig() {
		String roomName;
		char initial;
		String sub;
		String line;
		
		Room newRoom;
		
		try {
			FileReader obj = new FileReader(setupConfigFile);
			Scanner reader = new Scanner(obj);
			reader.nextLine();
			//Room newRoom;
			while(reader.hasNextLine()) {
				line = reader.nextLine();
				roomStrings.add(line);

			}
			reader.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();

		}
		for(String thisLine: roomStrings) {
			if(thisLine.charAt(0) == 'R') {
				sub = thisLine.substring(thisLine.indexOf(',')+1);
				roomName = sub.substring(0, sub.indexOf(','));
				sub = sub.substring(sub.indexOf(',')+1);
				initial = sub.charAt(1);
				newRoom = new Room(roomName);
				roomMap.put(initial,newRoom);
			}
		}
		
	}
	
	public void loadLayoutConfig() {
		int col = 0;
		int row = 0;
		String line;
		Room newRoom = new Room();
		
		try {
			FileReader obj2 = new FileReader(layoutConfigFile);
			Scanner reader2 = new Scanner(obj2);
			line = "";
			while(reader2.hasNextLine()) {
				line = reader2.nextLine();
				boardStrings.add(line);
			}
			reader2.close();
			
			numRows = boardStrings.size();
			numCols = boardStrings.get(0).length();
			grid = new BoardCell [numRows][numCols];
			BoardCell cell;
			cell = new BoardCell(0,0);
			
			for(row = 0; row < numRows; row++) {
				line = boardStrings.get(row);
				System.out.println();
				System.out.println(line.replace(',', ' '));
		
				col = 0;
				for(int charLoc = 0; charLoc < line.length(); charLoc++) {
					if(line.charAt(charLoc) == ',' && col+1 < numCols) {
						grid[row][col] = cell;
						System.out.print(grid[row][col].getInitial() + " ");
						col++;
						cell = new BoardCell(row,col);
						continue;
					}
					else {
						cell.setInitial(line.charAt(charLoc));
						if(charLoc +1 < line.length()) {
							if(line.charAt(charLoc+1) == '<' || line.charAt(charLoc+1) == '>' || line.charAt(charLoc+1) == 'v'||line.charAt(charLoc+1) == '^') {
								cell.setDirection(line.charAt(charLoc));
								charLoc++;
							}
							else if(line.charAt(charLoc+1) == '#') {
								cell.setLabel(true);
								charLoc++;
							}
							else if(line.charAt(charLoc+1) == '*' ){
								cell.setRoomCenter(true);
								charLoc++;
							}
							else if(line.charAt(charLoc) == 'S' && line.charAt(charLoc+1) == 'O') {
								cell.setSPassage('S');
								charLoc++;
							}
							else if(line.charAt(charLoc) == 'O' && line.charAt(charLoc+1) == 'S') {
								cell.setSPassage('O');
								charLoc++;
							}
							else if(line.charAt(charLoc) == 'K' && line.charAt(charLoc+1) == 'M') {
								cell.setSPassage('K');
								charLoc++;
							}
							else if(line.charAt(charLoc) == 'M' && line.charAt(charLoc+1) == 'K') {
								cell.setSPassage('M');
								charLoc++;
							}
						}	
					}
				}
				System.out.println();

			}
			//for(int i = 0; i < numRows; i++) {
			//	System.out.print(i);
			//	for(int j = 0; j < numCols; j++) {
			//		System.out.print(grid[i][j].getInitial());
			//	}
			//	System.out.println();
			//}
			
			for(row = 0; row < numRows; row++) {
				for(col = 0; col < numCols; col++) {
					if(grid[row][col].isRoomCenter()) {
						
						newRoom = roomMap.get(grid[row][col].getInitial());
						newRoom.setCenterCell(grid[row][col]);
						roomMap.put(grid[row][col].getInitial(), newRoom);
						
						}
					else if(grid[row][col].isLabel()) {
						
						newRoom = roomMap.get(grid[row][col].getInitial());
						newRoom.setLabelCell(grid[row][col]);
						roomMap.put(grid[row][col].getInitial(), newRoom);
						
						}
					grid[row][col].setRoom(newRoom, grid[row][col].getInitial());
				}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setConfigFiles(String string, String string2) {
		this.layoutConfigFile = string;
		this.setupConfigFile = string2;
		
		
		loadSetupConfig();
		
		
		loadLayoutConfig();
		

	}
	
	public Room getRoom(char c) {
		return roomMap.get(c);
	}
	
	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
	}

	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numCols;
	}
}
