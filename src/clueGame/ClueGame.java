package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{
	private Board board;
	
	public ClueGame() {
		board = Board.getInstance();
		GameControlPanel controlPanel = new GameControlPanel();
		KnownCardsPanel cardsPanel = new KnownCardsPanel();
		setSize(720, 750);
		add(board, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		add(cardsPanel, BorderLayout.EAST);
		
	}
		
	public void updateDrawing(int dx, int dy) {
		//board.translate(dx,dy);
	}
	
	public static void main(String[] args) {
		Board b = Board.getInstance();
		b.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		b.initialize();
		ClueGame game = new ClueGame();
		game.setVisible(true);
		game.setDefaultCloseOperation(EXIT_ON_CLOSE);
		game.updateDrawing(100, 100);
	}

}
