package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

	private String name;
	private Color coler;
	protected int row, column;
	private ArrayList<Card> hand = new ArrayList<Card>();

	public void addHand(Card card) {
		hand.add(card);
	}

	public void updateHand(Card card) {

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

	public ArrayList<Card> getHand() {
		return hand;
	}

}
