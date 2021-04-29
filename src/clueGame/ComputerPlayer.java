package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	private boolean makeAccusation = false;

	public boolean isMakeAccusation() {
		return makeAccusation;
	}

	public void setMakeAccusation(boolean makeAccusation) {
		this.makeAccusation = makeAccusation;
	}

	public ComputerPlayer(String name, Color coler, int row, int column) {
		super(name, coler, row, column);
	}

	public Solution getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Solution suggestion) {
		this.suggestion = suggestion;
	}

	public Solution createSuggestion(Card r) {
		int cardChooser = (int) (Math.random() * notSeenCards.size());
		Card p;
		Card w;
		while (notSeenCards.get(cardChooser).getType() != CardType.PERSON) {
			cardChooser = (int) (Math.random() * notSeenCards.size());
		}
		p = notSeenCards.get(cardChooser);
		// seen.add(p);

		while (notSeenCards.get(cardChooser).getType() != CardType.WEAPON) {
			cardChooser = (int) (Math.random() * notSeenCards.size());
		}
		w = notSeenCards.get(cardChooser);
		// seen.add(w);

		suggestion = new Solution(r, p, w);
		return suggestion;
	}

	public BoardCell selectTargets(Set<BoardCell> targets) {
		int randomNum;
		BoardCell chosenTarget = null;
		ArrayList<BoardCell> targetRooms = new ArrayList<BoardCell>();
		boolean inSeen = false;

		for (BoardCell target : targets) {
			for (Card thisCard : seenCards) {
				if (thisCard.getName().equals((Board.roomMap.get(target.getInitial()).getName()))) {
					inSeen = true;
					break;
				}
			}
			if (!inSeen && target.getInitial() != 'W') {
				targetRooms.add(target);
			}
			inSeen = false;
		}

		// if there are room options then the computer must chose a room, not a walkway
		if (targetRooms.size() > 0) {
			randomNum = (int) (Math.random() * targetRooms.size());
			chosenTarget = targetRooms.get(randomNum);
			// keep track of rooms that the Computer Player has visited
			return chosenTarget;
		}
		randomNum = (int) (Math.random() * targets.size());
		int i = 0;
		for (BoardCell cell : targets) {
			if (i == randomNum) {
				chosenTarget = cell;
			}
			i++;
		}
		return chosenTarget;
	}

	public Solution createSuggestion(Card r, Card p, Card w) {
		suggestion = new Solution(r, p, w);
		return suggestion;
	}

	public ArrayList<Card> getNotSeen() {
		return notSeenCards;
	}
}
