package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {

	private static Board board;
	
	static ComputerPlayer player;
	
	@BeforeEach
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		board.initialize();
		
		player = new ComputerPlayer("testPlayer", Color.black, 0, 0);
	}
	@Test
	void checkCorrectAccusation() {
		Solution solution = new Solution(board.getDeck().get(0), board.getDeck().get(10), board.getDeck().get(20));
		board.setSolution(solution);
		
		player.setAccusation(board.getDeck().get(0), board.getDeck().get(10), board.getDeck().get(20));
		boolean accusation1 = board.checkAccusation(player.getAccusation());
		assertTrue(accusation1);
	}
	
	@Test
	void checkWrongAccusation() {
		Solution solution = new Solution(board.getDeck().get(0), board.getDeck().get(10), board.getDeck().get(20));
		board.setSolution(solution);
		
		player.setAccusation(board.getDeck().get(1), board.getDeck().get(10), board.getDeck().get(20));
		boolean accusation2 = board.checkAccusation(player.getAccusation());
		assertFalse(accusation2);
		
		player.setAccusation(board.getDeck().get(0), board.getDeck().get(9), board.getDeck().get(20));
		boolean accusation3 = board.checkAccusation(player.getAccusation());
		assertFalse(accusation3);
		
		player.setAccusation(board.getDeck().get(0), board.getDeck().get(10), board.getDeck().get(19));
		boolean accusation4= board.checkAccusation(player.getAccusation());
		assertFalse(accusation4);
	}
	
	@Test
	void testDisproveSuggestion() {
		player.addHand(board.getDeck().get(0));
		player.addHand(board.getDeck().get(10));
		player.addHand(board.getDeck().get(20));
		player.createSuggestion(board.getDeck().get(3), board.getDeck().get(10), board.getDeck().get(16));
		Card target = player.disproveSuggestion(player.getSuggestion());
		assertEquals(target.getName(), "Patrick");
	}
	
	@Test
	void testDisproveSuggestion2() {
		player.addHand(board.getDeck().get(0));
		player.addHand(board.getDeck().get(10));
		player.addHand(board.getDeck().get(20));
		player.createSuggestion(board.getDeck().get(3), board.getDeck().get(10), board.getDeck().get(16));
		Card target = player.disproveSuggestion(player.getSuggestion());
		assertEquals(target.getName(), "Patrick");
	}

}
