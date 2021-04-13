package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame{
	private Board board;
	
	public ClueGame() {
		board = Board.getInstance();
	
		setTitle("Clue Game");
		GameControlPanel controlPanel = new GameControlPanel();
		KnownCardsPanel cardsPanel = new KnownCardsPanel();
		setSize(800, 750);
		//adding the components
		add(board, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		add(cardsPanel, BorderLayout.EAST);
	}
	
	public static void main(String[] args) {
		Board b = Board.getInstance();
		b.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		b.initialize();
		ClueGame game = new ClueGame();
		game.setVisible(true);
		game.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JOptionPane.showMessageDialog(game, "You are " + b.getHumanPlayer().getName() + 
				".\n Can you find the solutions before the computer players?");
	}

}
