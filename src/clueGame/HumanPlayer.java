package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, Color coler, int row, int column) {
		super(name, coler, row, column);
		// TODO Auto-generated constructor stub
	}
	
	public void move(Board b, int roll) {
		b.highlight(row, column, roll);
	}

}
