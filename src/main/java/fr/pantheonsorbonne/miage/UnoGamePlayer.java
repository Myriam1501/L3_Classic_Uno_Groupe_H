package fr.pantheonsorbonne.miage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;


public class UnoGamePlayer {
    static final String playerId = "Player-" + new Random().nextInt();
    static int score = 0;
    static LinkedList<Card> hand = new LinkedList<>();
    static boolean UNO = false;

     /**
    * Check this card can be play or not 
    * @param card              the card will be check
    * @param lastCardTalon     the last card in Discard
    * @return                  return true if this card can be play else return false 
    */
    protected static boolean getIfYouCanPlayTheCards(Card card, Card lastCardTalon){
        boolean canPlayThisCard = false ;
        if(card.getValue()==lastCardTalon.getValue() || card.getColor() == lastCardTalon.getColor()  || card.getColor() == CardColor.WHITE){
            canPlayThisCard = true; 
            return canPlayThisCard;
        }
        return canPlayThisCard;

    }
    
     /**
     * all playable cards in hand, and put in a array list 
     * @param hand             all the cards in hand 
     * @param lastCardTalon    last card in the discard 
     * @return                return a array list card but if this list is null return null. 
     */
    protected static LinkedList<Card> getCardYouCanPlay(LinkedList<Card> hand, Card lastCardTalon)
    {
        LinkedList<Card> getCardYouCanPlay = new LinkedList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (getIfYouCanPlayTheCards((Card) hand.get(i), lastCardTalon)){
                getCardYouCanPlay.add((Card) hand.get(i));
            }
        }

        if(getCardYouCanPlay.isEmpty()){
            return null;
        }
        return getCardYouCanPlay;
    }

    /**
     * Draw a card and add in hand 
     * @param drawACard       the card he draws
     * @return                all cards in hand
     */
    protected static LinkedList<Card> playTheDrawenCard(Card drawACard){
            
            hand.add(drawACard);
            return hand;
    }

    
    /**
     * retrieve a best choice playable card with the strategies. make the best choice
     * @param hand             all cards in hand
     * @param lastCardTalon    last card in the discard 
     * @return                 theCardToPlay or null 
     */
    protected static Card playThisCard(LinkedList<Card> hand,Card lastCardTalon){
    
        LinkedList<Card> cardYouCanPlay = getCardYouCanPlay(hand, lastCardTalon);
        if(cardYouCanPlay!=null){
            Card theCardToPlay;
            if(cardYouCanPlay.size()==1){
                theCardToPlay = (Card) cardYouCanPlay.get(0);
                giveToDiscard(hand, theCardToPlay);
                return theCardToPlay;
            }
            else{
                if(containSpecialCard(cardYouCanPlay)==true){
                    theCardToPlay = playThisSpecialCard(cardYouCanPlay);
                    giveToDiscard(hand, theCardToPlay);
                    return theCardToPlay;
                }
                else{
                    theCardToPlay = maxValueCard(cardYouCanPlay);
                    giveToDiscard(hand, theCardToPlay);
                    return theCardToPlay;
                }
            }
        }
        else{
            return null;
        }
    }
     /**
     * check the special card he can play 
     * @param getCardYouCanPlay    a array list with all playable card
     * @return                     return the special card or return null 
     */
    protected static Card playThisSpecialCard(LinkedList<Card> getCardYouCanPlay){
        CardValue[] orderlist ={CardValue.JOKER, CardValue.PLUS4,CardValue.SKIPTURN,CardValue.INVERSE,CardValue.PLUS2};
            for (CardValue order : orderlist) {
                for (Card i : getCardYouCanPlay) {
                    if(i.getValue()== order){
                        System.out.println("card special playing : "+i);
                        return i;
                    }
                }
            }
            return null;

    }

    /**
     * check the existence of special card in getCardYouCanPlay
     * @param getCardYouCanPlay     a array list with all playable card
     * @return                      if it contains return true else return false.
     */
    protected static boolean containSpecialCard(List<Card> getCardYouCanPlay){
        for (Card i : getCardYouCanPlay) {
            if( i.getValue().getRank()>=20){
                return true;
            }
        }
        return false;
    }

    /**
     * if there are only normal cards, he take the card with value max 
     * @param getCardYouCanPlay         a array list with all playable card
     * @return                          return the card with value max 
     */
    protected static Card maxValueCard(List<Card> getCardYouCanPlay){
        Card max = getCardYouCanPlay.get(0);
        for (Card i : getCardYouCanPlay) {
            if(max.getValue().getRank()<i.getValue().getRank()){
                max=i;
            }
            
        }
        return max;
    }

     /**
     * declare UNO 
     * @param hand           all cards in hand
     * @param cardPlayed     
     */
    protected static void giveToDiscard(LinkedList<Card> hand, Card cardPlayed){
        hand.remove(cardPlayed);
        if (hand.size()==1) {
            UNO = true;
            System.out.println("uno !");
        }
        
    }
    
    /**
     * Make the best choice in color 
     * @return       the color with best choice
     */
    public static CardColor colorChooseInHand (){
        Map<CardColor, Integer> numberColor = new HashMap<>();

        numberColor.put(CardColor.BLEU, 0); int b =0;
        numberColor.put(CardColor.GREEN, 0); int g = 0;
        numberColor.put(CardColor.RED, 0); int r =0;
        numberColor.put(CardColor.YELLOW, 0); int y =0;

        for (Card c : hand){
            if (c.getColor()==CardColor.BLEU){
                b++;
                numberColor.replace(CardColor.BLEU, (int) numberColor.get(CardColor.BLEU), b);
            }
            else if (c.getColor()==CardColor.GREEN){
                g++;
                numberColor.replace(CardColor.GREEN, (int)numberColor.get(CardColor.GREEN), g);
            }
            else if (c.getColor()==CardColor.RED){
                r++;
                numberColor.replace(CardColor.RED, (int)numberColor.get(CardColor.RED), r);
            }
            else if (c.getColor()==CardColor.YELLOW){
                y++;
                numberColor.replace(CardColor.YELLOW, (int)numberColor.get(CardColor.YELLOW), y);
            }
        }
        int [] tab = {b, g, r, y};
        Arrays.sort(tab);
        int max = tab[tab.length-1];

        for (Map.Entry mapentry : numberColor.entrySet()) {
            if ((int) mapentry.getValue()==max){
                return (CardColor) mapentry.getKey();

            }
        }
        for (Map.Entry mapentry : numberColor.entrySet()) {
            if ((int) mapentry.getValue()!=0){
                CardColor coler = (CardColor) mapentry.getKey();
                return coler; 
            }
        }
        
        return null;
    }
}
