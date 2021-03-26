package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

	private String name;
	private Color coler;
	protected int row, column;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> seenCards = new ArrayList<Card>();
	private Solution Accusation;

	public void addHand(Card card) {
		hand.add(card);
	}

	public void updateHand(Card card) {

	}
	
	public Card disproveSuggestion() {
		return null;
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}

	public Solution getAccusation() {
		return Accusation;
	}

	public void setAccusation(Solution accusation) {
		Accusation = accusation;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public void setAccusation(Card p, Card r, Card w) {
		Accusation = new Solution();
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
