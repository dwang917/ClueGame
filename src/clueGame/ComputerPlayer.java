package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import clueGame.Board;

public class ComputerPlayer extends Player {
	
	private Solution suggestion;
	//private ArrayList <Card> seen = new ArrayList<Card>();
	//private ArrayList <Card> notSeen = new ArrayList <Card>();
	private Set<BoardCell> targets = new HashSet<BoardCell>(); // holds the target of a certain board cell


	public ComputerPlayer(String name, Color coler, int row, int column) {
		super(name, coler, row, column);
		// TODO Auto-generated constructor stub
	}
	
	public void addSeen(Card add) {
		seenCards.add(add);
		notSeenCards.remove(notSeenCards.indexOf(add));
	}
	
	public void addnotSeen(Card add) {
		notSeenCards.add(add);
	}
	
	public Solution getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Solution suggestion) {
		this.suggestion = suggestion;
	}

	public Solution createSuggestion(Card r, int deckLength) {
		int cardChooser = (int)(Math.random() * deckLength);
		Card p;
		Card w;
		while(notSeenCards.get(cardChooser).getType() != CardType.PERSON) {
			cardChooser = (int)(Math.random()*deckLength);
		}
		p = notSeenCards.get(cardChooser);
		//seen.add(p);
		
		while(notSeenCards.get(cardChooser).getType() != CardType.WEAPON) {
			cardChooser = (int)(Math.random()*deckLength);
		}
		w = notSeenCards.get(cardChooser);
		//seen.add(w);
		
		suggestion = new Solution(r, p, w);
		return suggestion;
	}
	
	public BoardCell selectTargets() {
		int randomNum;
		ArrayList <BoardCell> targetRooms = new ArrayList <BoardCell>();
		boolean inSeen = false;
		
		for(BoardCell target: targets) {
			if(Board.roomMap.containsKey(target.getInitial())){
				for(Card thisCard: seenCards) {
					if(thisCard.getName().equals((Board.roomMap.get(target.getInitial()).getName()))){
						inSeen = true;
						break;
					}
				}
				if(!inSeen) {
					targetRooms.add(target);
				}
			}
			inSeen = false;
		}
		
		randomNum =  (int)(Math.random() * targetRooms.size());
		return targetRooms.get(randomNum);
	}

	public Solution createSuggestion(Card r, Card p, Card w) {
		// TODO Auto-generated method stub
		suggestion = new Solution(r, p, w);
		return suggestion;
		
	}

	//public ArrayList<Card> getSeen() {
	//	return seen;
	//}

	public ArrayList<Card> getNotSeen() {
		return notSeenCards;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void setTargets(Set<BoardCell> targets) {
		this.targets = targets;
	}
}
