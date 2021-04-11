package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{
	private Board board;
	
	public ClueGame() {
		board = Board.getInstance();
		setSize(720, 750);
		add(board, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		Board b = Board.getInstance();
		b.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		b.initialize();
		ClueGame game = new ClueGame();
		game.setVisible(true);
		game.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
