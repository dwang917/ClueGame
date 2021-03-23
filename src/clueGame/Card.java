package clueGame;

public class Card {

	private String cardName;
	private CardType type;
	
	public boolean equals(Card target) {
		return false;
	}
	
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}

	public void setRoom() {
		type = CardType.ROOM;
	}
	
	public void setPerson() {
		type = CardType.PERSON;
	}
	
	public void setWeapon() {
		type = CardType.WEAPON;
	}
}
