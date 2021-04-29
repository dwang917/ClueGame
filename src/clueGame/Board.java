package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
	// private Set<BoardCell> compVisited = new HashSet <BoardCell>();
	public static final int SETUP_LINE_LENGTH = 3; // unchangeable number for how many words there are in each set up
													// line
	public static final int WEAPON_NUM = 6;
	public static final int PLAYER_LINE_LENGTH = 5;
	public static final int PLAYER_NUM = 6;
	public static final int ROOM_NUM = 9;
	private int size;
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Solution solution;
	private int currentPlayerCount = 0;
	// keep track of the current players
	private Player currentPlayer = null;
	private boolean turnFinished = false;
	private boolean humanMoved = false;
	// record the disproving card
	private Card disproveCard = null;
	private Solution guess;
	private Player disprovePerson = null;
	private boolean suggestionMade = false;

	// draw board
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// calculate the cell size
		int height = getHeight() / numRows;
		int width = getWidth() / numCols;
		if (height > width) {
			size = width;
		} else {
			size = height;
		}
		// draw cells
		for (BoardCell[] two_cell : grid) {
			for (BoardCell cell : two_cell) { // get each cell
				cell.drawCell(g, size);
			}
		}
		// draw doorways
		for (BoardCell[] two_cell : grid) {
			for (BoardCell cell : two_cell) { // get each cell
				cell.drawDoorway(g, size);
			}
		}
		// draw secret passage
		for (BoardCell[] two_cell : grid) {
			for (BoardCell cell : two_cell) { // get each cell
				cell.drawSPassage(g, size);
			}
		}
		// draw labels
		for (Map.Entry<Character, Room> entry : roomMap.entrySet()) { // get each room
			if (entry.getKey() != 'W' && entry.getKey() != 'X') {
				entry.getValue().drawRoomName(g, size);
			}
		}
		// draw players
		for (Player p : players) {
			p.draw(g, size);
			grid[p.getRow()][p.getColumn()].setOccupied(true);
		}
	}

	private class TargetListener implements MouseListener {
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

		BoardCell whichTarget = null;

		@Override
		public void mouseClicked(MouseEvent e) {
			// if human has already moved, disable the mouse clicks on the grid
			if (humanMoved) {
				return;
			}
			// check if user clicks on any valid target
			for (BoardCell[] row : grid) {
				for (BoardCell eachCell : row) {
					if (eachCell.isTargetFlag()) {
						if (eachCell.containsClick(e.getX(), e.getY(), size)) {
							whichTarget = eachCell;
							break;
						}
					}
				}
			}
			if (whichTarget == null) {
				JOptionPane.showMessageDialog(null, "This is not a target");
			} else {
				// if a target is a room, only move the player to the center cell
				if (whichTarget.getInitial() != 'W') {
					BoardCell centerCell = roomMap.get(whichTarget.getInitial()).getCenterCell();
					moveAndDraw(centerCell.getRow(), centerCell.getCol());
				} else {
					moveAndDraw(whichTarget.getRow(), whichTarget.getCol());
				}
				// finish the human turn
				for (BoardCell[] row : grid) {
					for (BoardCell eachCell : row) {
						eachCell.setTargetFlag(false);
					}
					targets.clear();
					turnFinished = true;
					humanMoved = true;
					repaint();
					whichTarget = null;
				}
			}
		}
	}

	public boolean isHumanMoved() {
		return humanMoved;
	}

	// move and draw the players
	private void moveAndDraw(int row, int col) {
		// if the player was called to a room and choose to stay in the room, then
		// create suggestion
		if (currentPlayer.getRow() == row && currentPlayer.getColumn() == col) {
			if (currentPlayer instanceof HumanPlayer) {
				createSuggestionPanel(grid[row][col]);
			}
			return;
		}

		updatePlayerDrawing(currentPlayer, row, col);
		if (grid[row][col].isRoomCenter()) {
			if (currentPlayer instanceof HumanPlayer) {
				createSuggestionPanel(grid[row][col]);
			}
		}
		// set the destination walkway cell occupied
		else if (grid[row][col].getInitial() == 'W') {
			grid[row][col].setOccupied(true);
		}
	}

	// deal with offset when players overlap
	private void updatePlayerDrawing(Player player, int row, int col) {
		player.resetOffset();
		int prevRow = player.getRow();
		int prevCol = player.getColumn();
		// set previous cell unoccupied
		grid[prevRow][prevCol].setOccupied(false);
		for (Player p : players) {
			if (!p.equals(player)) {
				// if players overlap in a room, set an offset for the later player
				if (p.getRow() == row && p.getColumn() == col) {
					player.offsetIncrement(p.getOffset());
				}
			}
		}
		// update the player's location
		player.setRow(row);
		player.setColumn(col);
		player.draw(getGraphics(), size);
	}

	public void createSuggestionPanel(BoardCell cell) {
		JFrame frame = new JFrame();
		frame.setSize(350, 300);
		frame.setTitle("Suggest");
		frame.setVisible(true);

		JButton submit, cancel;

		JPanel suggestion = new JPanel();
		suggestion.setLayout(new GridLayout(4, 2));

		JTextField roomText = new JTextField("Room");
		JTextField personText = new JTextField("Person");
		JTextField weaponText = new JTextField("Weapon");

		// setting the roomBox fixed to the current room
		JTextField roomBox = new JTextField(roomMap.get(cell.getInitial()).getName());
		JComboBox<String> personBox = new JComboBox<String>();
		JComboBox<String> weaponBox = new JComboBox<String>();

		suggestion.add(roomText);
		suggestion.add(roomBox);
		suggestion.add(personText);
		suggestion.add(personBox);
		suggestion.add(weaponText);
		suggestion.add(weaponBox);

		frame.add(suggestion);

		// adding options for the combo boxes
		for (Card card : deck) {
			if (card.getType() == CardType.PERSON) {
				personBox.addItem(card.getName());
			} else if (card.getType() == CardType.WEAPON) {
				weaponBox.addItem(card.getName());
			}
		}

		class SubmitListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// create the suggestion and go into the disprove phase
				Card personChosen = getCard((String) personBox.getSelectedItem());
				Card weaponChosen = getCard((String) weaponBox.getSelectedItem());
				Card roomChosen = getCard(roomMap.get(cell.getInitial()).getName());
				guess = new Solution(roomChosen, personChosen, weaponChosen);
				disprovePhase(guess);
				frame.dispose();
			}
		}

		class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
			}
		}

		submit = new JButton("Submit");
		submit.addActionListener(new SubmitListener());
		cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener());

		suggestion.add(submit);
		suggestion.add(cancel);
	}

	// reset the parameters for a new turn
	public void newTurn() {
		suggestionMade = false;
		guess = null;
		disproveCard = null;
		disprovePerson = null;
		turnFinished = false;
		currentPlayerCount = (currentPlayerCount + 1) % PLAYER_NUM;
		currentPlayer = players.get(currentPlayerCount);
	}

	public void disprovePhase(Solution s) {
		suggestionMade = true;
		// if the suggesting player is not accusing player itself, gather the accused
		// player to the room
		if (!s.getPerson().getName().equals(currentPlayer.getName())) {
			gatherPlayer(s.getPerson(), s.getRoom());
		}
		// record the disproving card
		disproveCard = handleSuggestion(s, currentPlayer);
		// if a player has a disproving card, the current player adds that card to their
		// seen cards
		if (disproveCard != null) {
			currentPlayer.addSeenCard(disproveCard);
		} else if (currentPlayer instanceof ComputerPlayer && !currentPlayer.getHand().contains(s.getRoom())) {
			// if a computer player makes a suggestion and no one can disprove, and the
			// room card is not in hand, then make the accusation next turn
			currentPlayer.setAccusation(s.getPerson(), s.getRoom(), s.getWeapon());
			// flag to know to accuse next turn
			((ComputerPlayer) currentPlayer).setMakeAccusation(true);
		}
		// update both panels
		ClueGame.updatePanels();
	}

	public void gatherPlayer(Card playerCalled, Card roomCalled) {
		Player calledPlayer = null;
		Room r;
		for (Player player : players) {
			if (player.getName() == playerCalled.getName())
				calledPlayer = player;
		}
		r = roomMap.get(grid[currentPlayer.getRow()][currentPlayer.getColumn()].getInitial());
		int roomRow = r.getCenterCell().getRow();
		int roomCol = r.getCenterCell().getCol();
		// if the player called was already in the room, then do not redraw the player
		if (calledPlayer.getRow() == roomRow && calledPlayer.getColumn() == roomCol) {
			return;
		}
		// flag that the player was called to a room
		calledPlayer.setCalledToARoom(true);

		updatePlayerDrawing(calledPlayer, roomRow, roomCol);
		repaint();
	}

	// highlight the target cells for the user
	public void highlight(int row, int col, int roll) {
		BoardCell cell = grid[row][col];
		calcTargets(cell, roll);
		if (currentPlayer.isCalledToARoom()) {
			targets.add(cell);
			currentPlayer.setCalledToARoom(false);
		}
		// if there's no possible move, display the message and end the turn
		if (targets.isEmpty()) {
			JOptionPane.showMessageDialog(null, "There's no possible move, click Next.");
			turnFinished = true;
			humanMoved = true;
			return;
		}
		// set the highlight flag of target cells to true
		for (BoardCell c : targets) {
			// if a room is a target, highlight the whole room
			if (c.isRoomCenter()) {
				for (BoardCell[] rows : grid) {
					for (BoardCell eachCell : rows) {
						if (eachCell.getInitial() == c.getInitial()) {
							eachCell.setTargetFlag(true);
						}
					}
				}
			}
			c.setTargetFlag(true);
		}
		repaint();
	}

	//
	public void processTurn(int roll) {
		// if the player is human, highlight the cells
		if (currentPlayer instanceof HumanPlayer) {
			humanMoved = false;
			highlight(currentPlayer.getRow(), currentPlayer.getColumn(), roll);
		} else {
			// if the computer player is to make accusation this turn, do that
			if (((ComputerPlayer) currentPlayer).isMakeAccusation()) {
				if (checkAccusation(currentPlayer.getAccusation())) {
					youLost();
				}
			}

			calcTargets(grid[currentPlayer.getRow()][currentPlayer.getColumn()], roll);
			// if the player was called to this room last turn, the player can choose to
			// stay
			if (currentPlayer.isCalledToARoom()) {
				targets.add(grid[currentPlayer.getRow()][currentPlayer.getColumn()]);
				currentPlayer.setCalledToARoom(false);
			}
			// if computer player doesn't have possible targets, end the turn
			if (targets.isEmpty()) {
				turnFinished = true;
				return;
			}
			BoardCell targetSelected = ((ComputerPlayer) currentPlayer).selectTargets(targets);

			moveAndDraw(targetSelected.getRow(), targetSelected.getCol());
			if (targetSelected.isRoomCenter()) {
				// when the computer player enters a room, make a suggestion with that room
				Card roomCard = getCard(roomMap.get(targetSelected.getInitial()).getName());
				guess = ((ComputerPlayer) currentPlayer).createSuggestion(roomCard);
				// enter the disprove phase
				disprovePhase(guess);
			}
			turnFinished = true;
			repaint();
		}
	}

	private void youLost() {

		String roomName = currentPlayer.getAccusation()[1].getName();
		String personName = currentPlayer.getAccusation()[0].getName();
		String weaponName = currentPlayer.getAccusation()[2].getName();
		String answerMsg = "The answers are: " + roomName + ", " + personName + ", " + weaponName + ". ";
		JOptionPane.showMessageDialog(null,
				answerMsg + currentPlayer.getName() + " made the correct accusation! You lost!");
		System.exit(0);
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
		addMouseListener(new TargetListener());
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
				if (!(thisLine[0].equals("Space"))) {
					deck.add(new Card(roomName, CardType.ROOM));
				}
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
		currentPlayer = players.get(0);
		for (Player thisPlayer : players) {
			for (Card thisCard : deck) {
				thisPlayer.addnotSeenCard(thisCard);
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

		// make sure each player gets roughly same number of cards
		while (deckClone.size() != 0) {
			int randInt = (int) (Math.random() * deckClone.size());
			Card randCard = deckClone.get(randInt);
			players.get(count).addHand(randCard);
			players.get(count).getHand();
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
			case "white":
				color = Color.white;
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
					disprovePerson = thisPlayer;
					return thisPlayer.disproveSuggestion(suggestion);
				}
			}
		}
		return null;
	}

	public Card getCard(String s) {
		for (Card c : deck) {
			if (c.getName() == s)
				return c;
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

	public boolean isTurnFinished() {
		return turnFinished;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Solution getGuess() {
		return guess;
	}

	public boolean isSuggestionMade() {
		return suggestionMade;
	}

	public Card getDisproveCard() {
		return disproveCard;
	}

	public Player getDisprovePerson() {
		return disprovePerson;
	}
}