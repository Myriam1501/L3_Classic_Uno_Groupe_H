package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Card {

    private CardColor color;
    private final CardValue value;


    public Card(CardColor color, CardValue value) {
        this.color = color;
        this.value = value;
    }

    /**
     * For a String representation of a card, return the card
     *
     * @param str
     * @return the card
     * @throws RuntimeException if the String representation is Invaliid
     */
    public static Card valueOf(String str) {
        CardValue value;
        CardColor color;
        if (str.length() == 7) {
            value = CardValue.valueOfStr(str.substring(0, 5));
            color = CardColor.valueOfStr(str.substring(6, 7));
        } 
        else if (str.length() == 10){
            value = CardValue.valueOfStr(str.substring(0, 8));
            color = CardColor.valueOfStr(str.substring(9, 10));
        }
        else {
            value = CardValue.valueOfStr(str.substring(0, 1));
            color = CardColor.valueOfStr(str.substring(2, 3));
        }
        return new Card(color, value);

    }
    /**
     * This function puts a card in Card [] form into String
     * @param cards in the form of card []
     * @return card in String 
     */
   
    public static String cardsToString(Card[] cards) {
        
        return Arrays.stream(cards).map(Card::toString).collect(Collectors.joining(";"));
    }

    /**
     * This function puts a card in String form into Card []
     * @param cards  card in String 
     * @return in the form of card []
     */

    public static Card[] stringToCards(String cards) {
        if (cards.isEmpty()) {
            return new Card[0];
        }
        return (Card[]) Arrays.stream(cards.split(";")).map(Card::valueOf).toArray(Card[]::new);
    }


    /**
     * Get the color of the card
     *
     * @return
     */
    public CardColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return this.getValue().getStringRepresentation() +"_"+ this.getColor().getStringRepresentation();
    }

    /**
     * get the value of the card
     *
     * @return
     */
    public CardValue getValue() {
        return value;
    }
    /**
     *  change the color of a Card 
     * @param color2 the color you want 
     */

    public void setColor(CardColor color2) {
        this.color = color2;
        
    }


    
}
