package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Player;

class GameSetupTests {

	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	@Test
	void testPlayerName() {
		Player human = board.getPlayers().get(0);
		Player Computer4 = board.getPlayers().get(4);
		assertEquals(human.getName(), "SpongeBob");
		assertEquals(Computer4.getName(), "Mr.Krabs");
	}

}
