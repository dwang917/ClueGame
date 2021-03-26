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
	
	public Card() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setPerson() {
		type = CardType.PERSON;
	}
	
	public void setWeapon() {
		type = CardType.WEAPON;
	}
	
	public CardType getType() {
		return type;
	}
	
	public String getName() {
		return cardName;
	}
}
