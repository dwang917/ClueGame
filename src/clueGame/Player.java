package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

	private String name;
	private Color coler;
	protected int row, column;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> seenCards = new ArrayList<Card>();
	private Card[] Accusation = new Card[3];

	public void addHand(Card card) {
		hand.add(card);
	}

	public void updateHand(Card card) {

	}
	
	public Card disproveSuggestion(Solution s) {
		ArrayList<Card> match = new ArrayList<Card>();
		for(Card card : hand) {
			if(card.equals(s.getPerson()) || card.equals(s.getRoom()) || card.equals(s.getWeapon())) {
				match.add(card);
			}
		}
		if(match.size() == 0) {
			return null;
		}
		else if(match.size() == 1) {
			return match.get(0);
		}
		else {
			return match.get((int) (Math.random()*match.size()));
		}
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}

	public Card[] getAccusation() {
		return Accusation;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public void setAccusation(Card p, Card r, Card w) {
		Accusation[0] = p;
		Accusation[1] = r;
		Accusation[2] = w;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player(String name, Color coler, int row, int column) {
		super();
		this.name = name;
		this.coler = coler;
		this.row = row;
		this.column = column;
	}

	public Color getColer() {
		return coler;
	}

	public void setColer(Color coler) {
		this.coler = coler;
	}

	//for testing
	public ArrayList<Card> getHand() {
		return hand;
	}

}
