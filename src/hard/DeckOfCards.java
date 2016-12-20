package hard;

import java.util.ArrayList;
import java.util.Random;

public class DeckOfCards {
	
	ArrayList<Card> cards;
	
	public DeckOfCards(){
		this.cards = new ArrayList<Card>();
	}
	
	public void shuffle(){
		Random rand = new Random();
		for(int i=0 ; i<cards.size(); i++){
			int index = rand.nextInt(cards.size());
		}
	}
}
