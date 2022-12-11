package fr.pantheonsorbonne.miage.game;
import java.util.LinkedList;


/**
 * Represents a Discard of cards (C'est le talon du jeux )
 */
public class Discard {
    public final static LinkedList<Card> discard = new LinkedList<>();

    /**
     * we put a card in the Discard
     * @param card the card you want put in
     */
    public static void getACardToDicard(Card card){
        discard.offer(card);
    }
    /**
     * get the last card in Discard 
     * @return the last card in the Discard
     */
    public static Card getLastCardOnDisCard(){
        return (Card) discard.getLast();
    }


}