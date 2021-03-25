package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

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
	//check the number of player loaded, and their types
	void testPlayerNum() {
		assertEquals(6, board.getPlayers().size());
		int humanNum = 0;
		int compNum = 0;
		for (Player player : board.getPlayers()) {
			if (player instanceof HumanPlayer) {
				humanNum++;
			} else {
				compNum++;
			}
		}
		assertEquals(1, humanNum);
		assertEquals(5, compNum);
	}

	@Test
	//check players' names
	void testPlayerName() {
		Player human = board.getPlayers().get(0);
		Player Computer4 = board.getPlayers().get(4);
		assertEquals(human.getName(), "SpongeBob");
		assertEquals(Computer4.getName(), "Mr.Krabs");
	}

	@Test
	//check players' start locations
	void testStartLoc() {
		Player human = board.getPlayers().get(0);
		Player Computer1 = board.getPlayers().get(1);
		assertEquals(23, human.getRow());
		assertEquals(9, human.getColumn());
		assertEquals(14, Computer1.getRow());
		assertEquals(5, Computer1.getColumn());
	}

	@Test
	//check the deck size and the numbers of cards of each card type
	void testCardDeck() {
		int roomCnt = 0;
		int personCnt = 0;
		int weaponCnt = 0;
		int deckSize = board.getDeck().size();

		for (Card thisCard : board.getDeck()) {
			if (thisCard.getType() == CardType.ROOM)
				roomCnt++;
			else if (thisCard.getType() == CardType.PERSON)
				personCnt++;
			else if (thisCard.getType() == CardType.WEAPON)
				weaponCnt++;
		}

		assertEquals(21, deckSize);
		assertEquals(9, roomCnt);
		assertEquals(6, personCnt);
		assertEquals(6, weaponCnt);
	}

	@Test
	//check if solution is dealt correctly
	void testSolution() {
		Solution solution = board.getSolution();
		assertEquals(CardType.PERSON, solution.getPerson().getType());
		assertEquals(CardType.ROOM, solution.getRoom().getType());
		assertEquals(CardType.WEAPON, solution.getWeapon().getType());
	}

	@Test
	//check if the rest of the deck is dealt to the players correctly
	void testCardsDealt() {
		for (Player player : board.getPlayers()) {
			boolean size = player.getHand().size() >= (board.getDeck().size() - 3) / Board.PLAYER_NUM;
			assertTrue(size);
		}
	}
}
