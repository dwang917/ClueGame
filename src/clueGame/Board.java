package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//import experiment.TestBoardCell;

public class Board {

	private BoardCell[][] grid;
	private int numRows;
	private int numCols;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private static Board theInstance = new Board();
	private Set<BoardCell> targets = new HashSet <BoardCell>();
	private Set <BoardCell> visited = new HashSet <BoardCell>();

	private Board() {
		super();
	}

	public static Board getInstance() {
		return theInstance;
	}

	//handles the BadConfigFormatException 
	public void initialize() {
		try {
			loadSetupConfig();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}

		try {
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}
		
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				setAdj(grid[row][col]); //fill adjacent set
			}
		}

	}

	//loads the setup file and creates room map. Also throws BadConfigFormatException if file format is wrong
	public void loadSetupConfig() throws BadConfigFormatException {

		String roomName;
		char initial;
		ArrayList<String[]> setupStrings = readFile(setupConfigFile, ", ");

		for (String[] thisLine : setupStrings) {
			//if the line provides room info but the format is wrong then throw the exception
			if (thisLine.length == 3 && (!thisLine[0].equals("Room") && !thisLine[0].equals("Space"))) {
				throw new BadConfigFormatException("Setup file does not have a proper format");
			}

			else if (thisLine.length == 3) {
				roomName = thisLine[1];
				initial = thisLine[2].charAt(0);
				Room newRoom = new Room(roomName);
				roomMap.put(initial, newRoom);
			}
		}
	}

	/*
	 * loads the layout file and creates the grid. Also throws BadConfigFormatException when
	 * the column format is wrong or the board layout refers to a room that is not in your setup file
	 */
	public void loadLayoutConfig() throws BadConfigFormatException {
		ArrayList<String[]> boardStrings = readFile(layoutConfigFile, ",");
		numRows = boardStrings.size();
		numCols = boardStrings.get(0).length;
		grid = new BoardCell[numRows][numCols];
		
		// checks if every row has the same number of columns, if not, throw the exception
		for (String[] eachLine : boardStrings) {
			if (eachLine.length != numCols) {
				throw new BadConfigFormatException("The layout file has wrong columns setup");
			}
		}

		for (int row = 0; row < boardStrings.size(); row++) {
			for (int col = 0; col < boardStrings.get(0).length; col++) {
				String cellLetters = boardStrings.get(row)[col];

				//check if the initial represents a room in the setup file, if not, throw the exception
				if (roomMap.get(cellLetters.charAt(0)) == null) {
					throw new BadConfigFormatException("board layout refers to a room that is not in your setup file");
				}

				grid[row][col] = new BoardCell(row, col, cellLetters.charAt(0));
				//if the cell has two letters, calls the specialCell method to identify and handle it.
				if (cellLetters.length() == 2) {
					grid[row][col].specialCell(cellLetters.charAt(1), roomMap);
				}
			}
		}
	}

	public void setConfigFiles(String string, String string2) {
		this.layoutConfigFile = string;
		this.setupConfigFile = string2;
	}

	//A helper method that reads the file and loads the lines into a arrayList
	private ArrayList<String[]> readFile(String fileName, String regex) {
		ArrayList<String[]> fileStrings = new ArrayList<String[]>();
		try {
			FileReader obj = new FileReader(fileName);
			Scanner reader = new Scanner(obj);
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				//parse the string using regex and store the string array in the arrayList
				fileStrings.add(line.split(regex));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileStrings;
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

	
	public void setAdj(BoardCell cell) {
		char room;
		char initial = cell.getInitial();
		if(initial == 'W') {
			if(cell.getCol()+1 < numCols && grid[cell.getRow()][cell.getCol()+1].getInitial() == 'W')//test for column above
				cell.addAdj(getCell(cell.getRow(),cell.getCol()+1));
			
			
			if(cell.getCol() > 0 && grid[cell.getRow()][cell.getCol()-1].getInitial() == 'W') //test for column below
				cell.addAdj(getCell(cell.getRow(),cell.getCol()-1));
			
			
			if(cell.getRow()+1 < numRows && grid[cell.getRow()+1][cell.getCol()].getInitial() == 'W') //test for row to the right
				cell.addAdj(getCell(cell.getRow()+1,cell.getCol()));
			
			
			if(cell.getRow() > 0 && grid[cell.getRow()-1][cell.getCol()].getInitial() == 'W')//test for row to the left
				cell.addAdj(getCell(cell.getRow()-1,cell.getCol()));
			
			
			if(cell.getDoorDirection() != null) {
				if(cell.getDoorDirection().equals(DoorDirection.LEFT)) {
					room = getCell(cell.getRow(),cell.getCol()-1).getInitial();		
					cell.addAdj(roomMap.get(room).getCenterCell());
					roomMap.get(room).getCenterCell().addAdj(cell);
				}
				else if(cell.getDoorDirection().equals(DoorDirection.RIGHT)) {
					room = getCell(cell.getRow(),cell.getCol()+1).getInitial();
					cell.addAdj(roomMap.get(room).getCenterCell());
					roomMap.get(room).getCenterCell().addAdj(cell);
				}
				else if(cell.getDoorDirection().equals(DoorDirection.DOWN)) {
					room = getCell(cell.getRow()+1,cell.getCol()).getInitial();
					cell.addAdj(roomMap.get(room).getCenterCell());
					roomMap.get(room).getCenterCell().addAdj(cell);
				}
				else if(cell.getDoorDirection().equals(DoorDirection.UP)) {
					room = getCell(cell.getRow()-1,cell.getCol()).getInitial();
					cell.addAdj(roomMap.get(room).getCenterCell());
					roomMap.get(room).getCenterCell().addAdj(cell);
				}
			}
		}
		if(cell.getSecretPassage() != ' ') {
			cell.addAdj(roomMap.get(cell.getSecretPassage()).getCenterCell());
		}
	

	}
	
	
	
	public Set<BoardCell> getAdjList(int i, int j) {
		// TODO Auto-generated method stub
		return grid[i][j].getAdjList();
	}

	public void calcTargets(BoardCell cell, int i) {
		// TODO Auto-generated method stub
		targets = new HashSet<BoardCell>(); //start with empty set
		visited = new HashSet<BoardCell>(); //start with empty set
		visited.add(cell); //add the starting cell to visited set
		findAllTarget(cell,i);
	}
	
	public void findAllTarget(BoardCell thisCell, int numSteps) {
		for(BoardCell adjCell: thisCell.getAdjList()) { //go through each adjacent cell of the current cell
			if(adjCell.isRoomCenter()) {
				targets.add(adjCell);
			}
			
			if(!(visited.contains(adjCell))) {//if the cell being tested has not been visited then...
				visited.add(adjCell); //add adjacent cell to visited set
				if(numSteps == 1) { //if the number of steps equals 1...
					if(!adjCell.getOccupied()) { //if the adjacent cell isn't occupied
						targets.add(adjCell);//add the adjacent cell to targets
					}
				}
				else {
					findAllTarget(adjCell,numSteps - 1); //recursively go through all the adjacent cells, making the current cell the adjacent cell
				}
				visited.remove(adjCell);
			}
		}
		
	}


	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return targets;
	}

}
