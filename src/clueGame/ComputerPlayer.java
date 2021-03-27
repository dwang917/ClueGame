package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player {
	
	private Solution suggestion;

	public ComputerPlayer(String name, Color coler, int row, int column) {
		super(name, coler, row, column);
		// TODO Auto-generated constructor stub
	}

	public Solution getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Solution suggestion) {
		this.suggestion = suggestion;
	}

	public Solution createSuggestion(Card p, Card r, Card w) {
		suggestion = new Solution(r, p, w);
		return suggestion;
	}
	
	public BoardCell selectTargets() {
		return null;
	}
}
