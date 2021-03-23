package clueGame;

import java.awt.Color;

public abstract class Player {

	private String name;
	private Color coler;
	protected int row, column;
	
	public void updateHand(Card card) {
		
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
	
	
	
}
