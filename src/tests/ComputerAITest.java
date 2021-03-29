package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.Solution;

class ComputerAITest {
	
	private static Board board;
	ComputerPlayer player = new ComputerPlayer("testPlayer", Color.black, 0, 0);
	
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
	void selectTargetsTest() {
		
	}
	
	@Test
	void createSuggestionTest(){
		
	}
	
	

}
