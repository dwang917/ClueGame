package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {

	private Board board;
	private static GameControlPanel controlPanel;
	private static KnownCardsPanel cardsPanel;

	
	public ClueGame() {
		board = Board.getInstance();
		setTitle("Clue Game");
		cardsPanel = new KnownCardsPanel(board);
		controlPanel = new GameControlPanel(board, cardsPanel);
		setSize(800, 750);
		// adding the components
		add(board, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		add(cardsPanel, BorderLayout.EAST);
	}

	
	// start the game
	public void start() {
		controlPanel.startTurn();
	}
	
	public static void updatePanels() {
		controlPanel.update();
		cardsPanel.update();
	}
	

	
	public static void main(String[] args) {
		Board b = Board.getInstance();
		b.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		b.initialize();
		ClueGame game = new ClueGame();
		game.setVisible(true);
		game.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// display the splash screen
		JOptionPane.showMessageDialog(game, "You are " + b.getHumanPlayer().getName()
				+ ".\n Can you find the solutions before the computer players?");
		game.start();
	}
}