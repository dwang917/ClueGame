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
	private static Board theInstance = new Board();
	private ArrayList<String> boardStrings = new ArrayList <String>();
	//private ArrayList<String> roomStrings = new ArrayList <String>();
	//private Map <String, String> roomMap = new HashMap<String,String>();
	//private ArrayList<Room> rooms = new ArrayList <Room>();

	
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
		int col = 0;
		int row = 0;
		String roomName;
		char initial;
		String line;
		String sub;

		try {
			FileReader obj = new FileReader(string2);
			Scanner reader = new Scanner(obj);
			reader.nextLine();
			Room newRoom;
			while(reader.hasNextLine()) {
				line = reader.nextLine();
				if(line.charAt(0) == 'R') {
					sub = line.substring(line.indexOf(',')+1);
					roomName = sub.substring(0, sub.indexOf(','));
					sub = sub.substring(sub.indexOf(',')+1);
					initial = sub.charAt(0);
					//System.out.println(roomName + " and " + initial);
					newRoom = new Room(roomName);
					roomMap.put(initial,newRoom);
				}
			}
			reader.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();

		}
		
		try {
			FileReader obj2 = new FileReader(string);
			Scanner reader2 = new Scanner(obj2);
			line = "";
			//string content;
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
			for(int i = 0; i < numRows; i++) {
				row = i;
				line = boardStrings.get(i);
				
				for(int charLoc = 0; charLoc < line.length(); charLoc++) {
					if(line.charAt(charLoc) == ',' && col+1 < numCols) {
						col++;
						cell = new BoardCell(row,col);
						continue;
					}
					else if(charLoc+1 < line.length() && line.charAt(charLoc) == 'S' && line.charAt(charLoc+1) == 'O') {
						cell.setSPassage('S');
						charLoc++;
					}
					else if(charLoc+1 < line.length() && line.charAt(charLoc) == 'O' && line.charAt(charLoc+1) == 'S') {
						cell.setSPassage('O');
						charLoc++;
					}
					else if(charLoc+1 < line.length() && line.charAt(charLoc) == 'K' && line.charAt(charLoc+1) == 'M') {
						cell.setSPassage('K');
						charLoc++;
					}
					else if(charLoc+1 < line.length() && line.charAt(charLoc) == 'M' && line.charAt(charLoc+1) == 'K') {
						cell.setSPassage('M');
						charLoc++;
					}
					else if(line.charAt(charLoc) == '*' || line.charAt(charLoc) == '#') {
						
					}
					else if(line.charAt(charLoc) == '<' || line.charAt(charLoc) == '>' || line.charAt(charLoc) == 'v'||line.charAt(charLoc) == '^') {
						cell.setDirection(line.charAt(charLoc));
					}
					else {
						cell.setRoom(roomMap.get(line.charAt(charLoc)), line.charAt(charLoc));
					}
					grid[row][col] = cell;
				}
			}
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		//catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//
		
		
		

	}
	
	public Room getRoom(char c) {
		return roomMap.get;
	}
	
	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
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
