package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
	private Map<Character, Room> roomMap;
	private static Board theInstance = new Board();
	private ArrayList<String> boardStrings = new ArrayList <String>();
	
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
		String line;
		
		try {
			FileReader obj = new FileReader(string);
			Scanner reader = new Scanner(obj);
			
			//string content;
			while(reader.hasNextLine()) {
				line = reader.nextLine();
				boardStrings.add(line.replaceAll(",",""));
			}
			reader.close();
			
			numRows = boardStrings.get(0).length();
			numCols = boardStrings.size();
			grid = new BoardCell [numRows][numCols];
			BoardCell cell;
			cell = new BoardCell(0,0);
			for(int i = 0; i < numRows; i++) {
				row = i;
				line = boardStrings.get(i);
				
				for(int charLoc = 0; charLoc < line.length(); charLoc++) {
					if(line.charAt(charLoc) == ',') {
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
					grid[row][col] = cell;
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

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
