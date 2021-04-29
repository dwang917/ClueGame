package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	// check whether to make accusation at the start of turn or not
	private boolean makeAccusation = false;
	private Set<BoardCell> visited = new HashSet<BoardCell>();

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

	// create a suggestion based on the room in
	public Solution createSuggestion(Card r) {
		int cardChooser = (int) (Math.random() * notSeenCards.size());
		Card p;
		Card w;
		while (notSeenCards.get(cardChooser).getType() != CardType.PERSON) {
			cardChooser = (int) (Math.random() * notSeenCards.size());
		}
		p = notSeenCards.get(cardChooser);

		while (notSeenCards.get(cardChooser).getType() != CardType.WEAPON) {
			cardChooser = (int) (Math.random() * notSeenCards.size());
		}
		w = notSeenCards.get(cardChooser);

		suggestion = new Solution(r, p, w);
		return suggestion;
	}

	public BoardCell selectTargets(Set<BoardCell> targets) {
		int randomNum;
		BoardCell chosenTarget = null;
		ArrayList<BoardCell> targetRooms = new ArrayList<BoardCell>();
		boolean inSeen = false;

		// check if this target has been visited
		for (BoardCell target : targets) {
			for (Card thisCard : seenCards) {
				if (thisCard.getName().equals((Board.roomMap.get(target.getInitial()).getName()))) {
					inSeen = true;
					break;
				}
			}
			// if the target room has not been visited, add the room to targetRooms
			if (!inSeen && target.getInitial() != 'W' && !visited.contains(target)) {
				targetRooms.add(target);
			}
			inSeen = false;
		}

		// if there are room options then the computer must chose a room, not a walkway
		if (targetRooms.size() > 0) {
			randomNum = (int) (Math.random() * targetRooms.size());
			chosenTarget = targetRooms.get(randomNum);
			// keep track of rooms that the Computer Player has visited
			visited.add(chosenTarget);
			return chosenTarget;
		}
		// if there's no room option then the computer player chooses a target randomly
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
