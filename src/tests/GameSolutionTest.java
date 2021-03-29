package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {

	private static Board board;
	ComputerPlayer player = new ComputerPlayer("testPlayer", Color.black, 0, 0);
	ComputerPlayer player2 = new ComputerPlayer("testPlayer2", Color.yellow, 1, 0);
	HumanPlayer humanPlayer = new HumanPlayer("testPlayer3",Color.red, 2, 0);
	
	Card testPersonOne = new Card("man", CardType.PERSON);
	Card testPersonTwo = new Card("man_two", CardType.PERSON);
	Card testPersonThree = new Card("woman", CardType.PERSON);
	Card testPersonFour = new Card("woman_two", CardType.PERSON);
	
	Card testRoomOne = new Card("bedroom", CardType.ROOM);
	Card testRoomTwo = new Card("kitchen", CardType.ROOM);
	Card testRoomThree = new Card("closet", CardType.ROOM);
	Card testRoomFour = new Card("bathroom", CardType.ROOM);
	
	Card testWeaponOne = new Card("bedroom", CardType.WEAPON);
	Card testWeaponTwo = new Card("kitchen", CardType.WEAPON);
	Card testWeaponThree = new Card("closet", CardType.WEAPON);
	Card testWeaponFour = new Card("bathroom", CardType.WEAPON);
	
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
		player.createSuggestion(board.getDeck().get(4), board.getDeck().get(14), board.getDeck().get(20));
		Card target = player.disproveSuggestion(player.getSuggestion());
		assertEquals(target.getName(), "Sword");
	}
	
	@Test 
	void handleSuggestion() {
		ArrayList<Player> players = new ArrayList<Player>();
		
		
		boolean checkSuggestion = false;
		player.addHand(testPersonOne);
		player.addHand(testRoomOne);
		player.addHand(testWeaponOne);
		
		player2.addHand(testPersonTwo);
		player2.addHand(testRoomTwo);
		player2.addHand(testWeaponTwo);
		
		humanPlayer.addHand(testPersonThree);
		humanPlayer.addHand(testRoomThree);
		humanPlayer.addHand(testWeaponThree);
		
		players.add(player);
		players.add(player2);
		players.add(humanPlayer);
		
		board.setPlayers(players);
		
		//query that no player can disprove (should return null)
		Solution solution = new Solution(testPersonFour,testRoomFour,testWeaponFour);
		assertEquals(null, board.handleSuggestion(solution,player));
		
		//query that only accuser can disprove (return null)
		solution = new Solution(testPersonOne,testRoomOne,testWeaponOne);
		assertEquals(null, board.handleSuggestion(solution,player));
		
		
		//query that can be disproved
		if((player.getHand().get(0) == board.handleSuggestion(solution,player2) || player.getHand().get(1)== board.handleSuggestion(solution,player2) || player.getHand().get(2)== board.handleSuggestion(solution,player2))){
			checkSuggestion = true;
		}
		assertEquals(true, checkSuggestion);
		
		//query that can be disproved by both other players, but only matched the first to show up
		solution = new Solution(testPersonTwo,testRoomThree,testWeaponThree);
		assertEquals(player2.getHand().get(0), board.handleSuggestion(solution,player));
	}

}
