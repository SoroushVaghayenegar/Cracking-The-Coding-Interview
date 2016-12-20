package hard;

public class Card {
	
	public enum Suit{
		Spades, Diamonds, Hearts, Clubs
	}
	
	private Suit suit;
	private int value;
	
	public Card(int value, Suit suit){
		this.value = value;
		this.suit = suit; 
	}
}
