package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Represents a Deck of cards
 */
public class Deck {
    public static LinkedList<Card> deck = new LinkedList<>();

    static {
        
        //generate all Classiccards 
        for (CardColor color : CardColor.values()) {
            if (color.compareTo(CardColor.WHITE)!=0){
                for (CardValue value : CardValue.values()) {
                    if (value.compareTo(CardValue.ZERO)==0){
                        deck.add(new Card(color, value));
                        
                    }
                    else if(value.compareTo(CardValue.PLUS4)!=0 && value.compareTo(CardValue.JOKER)!=0){
                        for (int index = 1; index <= 2; index++) {
                            deck.add( new Card(color, value));
                        }
                    }
                }
            }
            
            else{
                
                for (CardValue value : CardValue.values()) {
                    if (value.compareTo(CardValue.PLUS4)==0 || value.compareTo(CardValue.JOKER)==0) {
                        for (int index = 1; index <= 4; index++) { 
                            deck.add( new Card(color, value));
                        }
                    }
                }
            }
        }

        //shuffle them
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        
    }
    /**
     * disallow instantiation
     */
    private Deck() {
    }



    /**
     * return an array of cards
     *
     * @param length the size of the array
     * @return
     */
    public static Collection<Card> getCards(int length) {
        Collection<Card> result = new LinkedList<Card>();
        for (int i = 0; i < length; i++) {
            result.add((Card) deck.poll());
        }
        return result;
    }

     /**
     * return an array of cards in form of Card[]
     *
     * @param length the size of the array
     * @return
     */
    public static Card[] getCardsIni(int length) {
        Card[]  result = new Card[length];
        for (int i = 0; i < length; i++) {
            result[i]= (Card) deck.poll();
        }
        return result;
    }

      /**
     * return a cards in form of Card
     *
     * @return the card
     */
    public static Card getACard() {
        Card result = (Card) deck.poll();

        return result;
    }
  


    

    
}
