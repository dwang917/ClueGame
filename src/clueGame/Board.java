package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

//import experiment.TestBoardCell;

public class Board extends JPanel {

	private BoardCell[][] grid; // holds the board and each cell
	private int numRows; // total rows on board
	private int numCols; // total columns on board
	private String layoutConfigFile; // name of layout file
	private String setupConfigFile; // name of set up file
	public static Map<Character, Room> roomMap = new HashMap<Character, Room>(); // map to hold rooms
	private static Board theInstance = new Board(); // creates a new board
	private Set<BoardCell> targets = new HashSet<BoardCell>(); // holds the target of a certain board cell
	private Set<BoardCell> visited = new HashSet<BoardCell>(); // holds the visited list of the user
	public static final int SETUP_LINE_LENGTH = 3; // unchangeable number for how many words there are in each set up
													// line
	public static final int WEAPON_NUM = 6;
	public static final int PLAYER_LINE_LENGTH = 5;
	public static final int PLAYER_NUM = 6;
	public static final int ROOM_NUM = 9;
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Solution solution;

	// draw board
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int height = getHeight() / numRows;
		int width = getWidth() / numCols;
		// draw cells
		for (BoardCell[] two_cell : grid) {
			for (BoardCell cell : two_cell) { // get each cell
				cell.drawCell(g, height, width);
			}
		}
		//draw doorways
		for (BoardCell[] two_cell : grid) {
			for (BoardCell cell : two_cell) { // get each cell
				cell.drawDoorway(g, height, width);
			}
		}
		// draw labels
		for (Map.Entry<Character, Room> entry : roomMap.entrySet()) { // get each room
			if (entry.getKey() != 'W' && entry.getKey() != 'X') {
				entry.getValue().drawRoomName(g, height, width);
			}
		}
		// draw players
		for (Player p : players) {
			p.draw(g, height, width);
		}
	}
	
	public void mouseClicked() {
		
	}

	private Board() {
		super();
	}

	public static Board getInstance() {
		return theInstance;
	}

	// handles the BadConfigFormatException
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

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				setAdj(grid[row][col]); // fill adjacent set
			}
		}
	}

	// loads the setup file and creates room map. Also throws
	// BadConfigFormatException if file format is wrong
	public void loadSetupConfig() throws BadConfigFormatException {

		String roomName;
		char initial;

		ArrayList<String[]> setupStrings = readFile(setupConfigFile, ", ");

		for (String[] thisLine : setupStrings) {
			// if the line provides room info but the format is wrong then throw the
			// exception
			if (thisLine.length == SETUP_LINE_LENGTH && (!thisLine[0].equals("Room") && !thisLine[0].equals("Space"))) {
				throw new BadConfigFormatException("Setup file does not have a proper format");
			}
			// if the line provides room info then we extract the elements and create the
			// map, and create the room card and add to deck
			else if (thisLine.length == SETUP_LINE_LENGTH) {
				roomName = thisLine[1];
				initial = thisLine[2].charAt(0);
				Room newRoom = new Room(roomName);
				roomMap.put(initial, newRoom);
				if (!(thisLine[0].equals("Space")))
					deck.add(new Card(roomName, CardType.ROOM));
			}
			// If this line is player info, then create the corresponding player card and
			// add that to the deck
			else if (thisLine.length == PLAYER_LINE_LENGTH) {
				deck.add(new Card(thisLine[0], CardType.PERSON));
				// Add the player to the player arraylist
				addPlayer(thisLine);
			}
			// if this line is weapon, create the weapon cards and add to the deck
			else if (thisLine.length == WEAPON_NUM) {
				for (int i = 0; i < WEAPON_NUM; i++) {
					deck.add(new Card(thisLine[i], CardType.WEAPON));
				}
			}
		}
		dealCards();
	}

	// Deal the cards to solution and to each player
	private void dealCards() {
		// Choose one random card from each card types, and deal them to the solution
		int randRoom = (int) (Math.random() * ROOM_NUM);
		Card solutionR = deck.get(randRoom);

		int randPlayer = ROOM_NUM + (int) (Math.random() * PLAYER_NUM);
		Card solutionP = deck.get(randPlayer);

		int randWeapon = ROOM_NUM + PLAYER_NUM + (int) (Math.random() * WEAPON_NUM);
		Card solutionW = deck.get(randWeapon);
		solution = new Solution(solutionR, solutionP, solutionW);

		// Deal the rest of the deck to each player
		int count = 0;
		ArrayList<Card> deckClone = (ArrayList<Card>) deck.clone();
		deckClone.remove(randWeapon);
		deckClone.remove(randPlayer);
		deckClone.remove(randRoom);
		for (Player thisPlayer : players) {
			for (Card thisCard : deckClone) {
				thisPlayer.addnotSeenCard(thisCard);
			}
		}
		// make sure each player gets roughly same number of cards
		while (deckClone.size() != 0) {
			int randInt = (int) (Math.random() * deckClone.size());
			Card randCard = deckClone.get(randInt);
			players.get(count).addHand(randCard);
			deckClone.remove(randInt);
			count++;
			count = count % PLAYER_NUM;
		}
	}

	// Add the player to the player arraylist
	private void addPlayer(String[] thisLine) {
		{
			Color color = null;
			switch (thisLine[1]) {
			case "red":
				color = Color.red;
				break;
			case "pink":
				color = Color.pink;
				break;
			case "green":
				color = Color.green;
				break;
			case "cyan":
				color = Color.cyan;
				break;
			case "blue":
				color = Color.blue;
				break;
			case "orange":
				color = Color.orange;
				break;
			}
			// Check the file to see if the player is human or computer
			if (thisLine[2].equals("Human")) {
				players.add(new HumanPlayer(thisLine[0], color, Integer.parseInt(thisLine[3]),
						Integer.parseInt(thisLine[4])));
			} else {
				players.add(new ComputerPlayer(thisLine[0], color, Integer.parseInt(thisLine[3]),
						Integer.parseInt(thisLine[4])));
			}
		}
	}

	/*
	 * loads the layout file and creates the grid. Also throws
	 * BadConfigFormatException when the column format is wrong or the board layout
	 * refers to a room that is not in your setup file
	 */
	public void loadLayoutConfig() throws BadConfigFormatException {
		ArrayList<String[]> boardStrings = readFile(layoutConfigFile, ",");
		numRows = boardStrings.size();
		numCols = boardStrings.get(0).length;
		grid = new BoardCell[numRows][numCols];

		// checks if every row has the same number of columns, if not, throw the
		// exception
		for (String[] eachLine : boardStrings) {
			if (eachLine.length != numCols) {
				throw new BadConfigFormatException("The layout file has wrong columns setup");
			}
		}

		validate(boardStrings);
	}

	// make sure the format is correct and construct the grid
	private void validate(ArrayList<String[]> boardStrings) throws BadConfigFormatException {
		for (int row = 0; row < boardStrings.size(); row++) {
			for (int col = 0; col < boardStrings.get(0).length; col++) {
				String cellLetters = boardStrings.get(row)[col];

				// check if the initial represents a room in the setup file, if not, throw the
				// exception
				if (roomMap.get(cellLetters.charAt(0)) == null) {
					throw new BadConfigFormatException("board layout refers to a room that is not in your setup file");
				}

				grid[row][col] = new BoardCell(row, col, cellLetters.charAt(0));
				// if the cell has two letters, calls the specialCell method to identify and
				// handle it.
				if (cellLetters.length() == 2) {
					grid[row][col].specialCell(cellLetters.charAt(1), roomMap, grid[row][col]);

				}
			}
		}
	}

	// A helper method that reads the file and loads the lines into a arrayList
	private ArrayList<String[]> readFile(String fileName, String regex) {
		ArrayList<String[]> fileStrings = new ArrayList<String[]>();
		try {
			FileReader obj = new FileReader(fileName);
			Scanner reader = new Scanner(obj);
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				// parse the string using regex and store the string array in the arrayList
				fileStrings.add(line.split(regex));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileStrings;
	}

	public void setAdj(BoardCell cell) {
		char initial = cell.getInitial();
		// if on walkway, check if the surrounding is also walkway
		if (initial == 'W') {
			checkWalkwayAdj(cell);

			// check if it's on a door
			if (cell.getDoorDirection() != null) {
				checkDoorAdj(cell);
			}
		}
		// check if it's on a secret passage
		if (cell.getSecretPassage() != ' ') {
			BoardCell centerCell = roomMap.get(initial).getCenterCell();
			BoardCell connectedRoomCenter = roomMap.get(cell.getSecretPassage()).getCenterCell();
			// Set up the adjacency relation between the two connected rooms
			centerCell.addAdj(connectedRoomCenter);
			connectedRoomCenter.addAdj(centerCell);
		}
	}

	// A helper method that checks the surrounding walkways and set up adjList
	private void checkWalkwayAdj(BoardCell cell) {
		int col = cell.getCol();
		int row = cell.getRow();
		if (col + 1 < numCols && grid[row][col + 1].getInitial() == 'W')// test for column above
			cell.addAdj(getCell(row, col + 1));

		if (col > 0 && grid[row][col - 1].getInitial() == 'W') // test for column below
			cell.addAdj(getCell(row, col - 1));

		if (row + 1 < numRows && grid[row + 1][col].getInitial() == 'W') // test for row to the right
			cell.addAdj(getCell(row + 1, col));

		if (row > 0 && grid[row - 1][col].getInitial() == 'W')// test for row to the left
			cell.addAdj(getCell(row - 1, col));
	}

	// A helper method that set up the adjacency relation between doors and room
	// centers
	private void checkDoorAdj(BoardCell cell) {
		char room = 0;
		int row = cell.getRow();
		int col = cell.getCol();
		switch (cell.getDoorDirection()) {
		case LEFT:
			// add door to the center's adjcentList and vice versa
			room = getCell(row, col - 1).getInitial();
			break;
		case RIGHT:
			room = getCell(row, col + 1).getInitial();
			break;
		case DOWN:
			room = getCell(row + 1, col).getInitial();
			break;
		case UP:
			room = getCell(row - 1, col).getInitial();
			break;
		}
		cell.addAdj(roomMap.get(room).getCenterCell());
		roomMap.get(room).getCenterCell().addAdj(cell);
	}

	public void calcTargets(BoardCell cell, int i) {
		targets = new HashSet<BoardCell>(); // start with empty set
		visited = new HashSet<BoardCell>(); // start with empty set
		visited.add(cell); // add the starting cell to visited set
		findAllTarget(cell, i);
		// Avoid going back into the same room if started in a room
		if (targets.contains(roomMap.get(cell.getInitial()).getCenterCell())) {
			targets.remove(roomMap.get(cell.getInitial()).getCenterCell());
		}
	}

	public void findAllTarget(BoardCell thisCell, int numSteps) {
		for (BoardCell adjCell : thisCell.getAdjList()) { // go through each adjacent cell of the current cell
			if (!adjCell.isOccupied() || adjCell.isRoomCenter()) {
				if (adjCell.isRoomCenter()) {
					targets.add(adjCell);
				}

				if (!(visited.contains(adjCell))) {// if the cell being tested has not been visited then...
					visited.add(adjCell); // add adjacent cell to visited set
					if (numSteps == 1) { // if the number of steps equals 1...
						if (!adjCell.isOccupied()) { // if the adjacent cell isn't occupied
							targets.add(adjCell);// add the adjacent cell to targets
						}
					} else {
						if (!adjCell.isRoomCenter()) {
							findAllTarget(adjCell, numSteps - 1); // recursively go through all the adjacent cells,
																	// making the current cell the adjacent cell
						}
					}
					visited.remove(adjCell);
				}
			}
		}
	}

	// for testing only
	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public boolean checkAccusation(Card[] cards) {
		for (Card card : cards) {
			if (card.getType() == CardType.PERSON) {
				if (!card.equals(solution.getPerson())) {
					return false;
				}
			}
			if (card.getType() == CardType.ROOM) {
				if (!card.equals(solution.getRoom())) {
					return false;
				}
			}
			if (card.getType() == CardType.WEAPON) {
				if (!card.equals(solution.getWeapon())) {
					return false;
				}
			}
		}
		return true;
	}

	// returns the card that disproves suggestion if able, returns null if cannot be
	// disproved by any player
	public Card handleSuggestion(Solution suggestion, Player accuser) {
		for (Player thisPlayer : players) {
			if (!thisPlayer.equals(accuser)) {
				if (thisPlayer.disproveSuggestion(suggestion) != (null)) {
					return thisPlayer.disproveSuggestion(suggestion);
				}
			}
		}
		return null;
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

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return grid[i][j].getAdjList();
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public HumanPlayer getHumanPlayer() {
		return (HumanPlayer) players.get(0);
	}

	public Solution getSolution() {
		return solution;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void highlight(int row, int col, int i) {
		BoardCell cell = grid[row][col];
		calcTargets(cell, i);
		for(BoardCell c: targets) {
			c.setTargetFlag(true);
		}
		repaint();
	}

}
