package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
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
	
	@Test
	void testStartLoc() {
		Player human = board.getPlayers().get(0);
		Player Computer1 = board.getPlayers().get(1);
		assertEquals(23, human.getRow());
		assertEquals(9, human.getColumn());
		assertEquals(14, Computer1.getRow());
		assertEquals(5, Computer1.getColumn());
	}
	

	@Test
	void testCardDeck() {
		int roomCnt = 0;
		int personCnt = 0;
		int weaponCnt = 0;
		int deckSize = board.getDeck().size();
		
		for(Card thisCard:board.getDeck()) {
			if(thisCard.getType() == CardType.ROOM)
				roomCnt++;
			else if (thisCard.getType() == CardType.PERSON)
				personCnt++;
			else if(thisCard.getType() == CardType.WEAPON)
				weaponCnt++;
		}
		
		assertEquals(21,deckSize);		
		assertEquals(9, roomCnt);
		assertEquals(6, personCnt);
		assertEquals(6, weaponCnt);

	}
	

}
