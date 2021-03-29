package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Room;

class ComputerAITest {
	
	private static Board board;
	ComputerPlayer player = new ComputerPlayer("testPlayer", Color.black, 7, 2);
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		board.initialize();
		
	}
	
	@Test //test to see if computer can generate and select a correct target option if there are room available
	void selectTargetsTest() {
		BoardCell target;
		boolean inSeen = false;
		 Set<BoardCell> targets = new HashSet<BoardCell>();
		 ArrayList <Card> seen = new ArrayList<Card>(); 
		 targets.add(board.getRoom(('M')).getCenterCell());
		 targets.add(board.getRoom(('R')).getCenterCell());
		 targets.add(board.getRoom(('B')).getCenterCell());
		 targets.add(board.getCell(7, 5));
		 
		 seen.add(new Card("Master Bedroom", CardType.ROOM));
		 player.setSeenCards(seen);
		 player.setTargets(targets);
		 target = player.selectTargets();
		 
		 for(Card thisCard: player.getnotSeenCards()) {
			 if(thisCard.getType() == CardType.ROOM){
				 if(board.getRoom(thisCard.getName().charAt(0)).getCenterCell() == target){
					 inSeen = true;
				 }
			 }
			 
		 }
		 
		 assertFalse(inSeen);
	}
	
	@Test//test to see if computer can generate a suggestion out of unseen cards
	void createSuggestionTest(){
		Room currentRoom = board.getRoom(board.getCell(player.getRow(), player.getColumn()));
		Card room = null;
		boolean inSeen = false;
		
		for(Card thisCard:board.getDeck()) {
			player.addnotSeenCard(thisCard);
			if(thisCard.getName().equals(currentRoom.getName())) {
				room = thisCard;
			}
		}
		
		Card one = board.getDeck().get(9);
		Card two = board.getDeck().get(15);
		
		player.addSeenCard(one);
		player.addSeenCard(two);
		player.createSuggestion(room, board.getDeck().size()-1);
		
		for(Card thisSeen: player.getSeenCards()) {
			if(player.getSuggestion().getWeapon() == thisSeen) {
				inSeen = true;
			}
		}
		assertFalse(inSeen);
		
		inSeen = false;
		for(Card thisSeen: player.getSeenCards()) {
			if(player.getSuggestion().getPerson() == thisSeen) {
				inSeen = true;
			}
		}
		assertFalse(inSeen);
		
	}
	
	

}
