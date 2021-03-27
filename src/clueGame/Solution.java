package clueGame;

public class Solution {
	
	public Solution() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Card person;
	public Card room;
	public Card weapon;
	
	public Solution(Card room, Card person, Card weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public Card getPerson() {
		return person;
	}
	public Card getRoom() {
		return room;
	}
	public Card getWeapon() {
		return weapon;
	}
	
}
