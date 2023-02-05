package fr.pantheonsorbonne.miage;
import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Discard;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Deque;
import java.util.Set;



/**
 * this class is a abstract version of the engine, to be used locally on through the network
 */
public abstract class UnoGameEngine {

    public static final int CARDS_IN_HAND_INITIAL_COUNT = 7;
    public static String sens = "sens"; //should be boolean or at least enum, not string
    /**
     * play a Uno game wit the provided players
     */
    public void play() {
        //send the initial hand to every players
         playToInitARound();
        // make a Deque with all the players
        final Deque<String> players = new LinkedList<>();
        players.addAll(this.getInitialPlayers());
        //repeat until only 1 player has over 50 points
        while(!hasWon()){
            int i=0;
            // The card can not initialize by card special. 
            Card cardToInitDiscard = pickACardFromDiscardToDebut();
            
            // Put the first card in Discard to start the game. 
            Discard.getACardToDicard(cardToInitDiscard);
            System.out.println("\nLast card in Discard : "+Discard.discard.getLast());

            String winner = null;
            while(winner==null){
                ++i;
                //we check the numbre of cards in deck and shuffle
                reinitDeckFromDiscard();
                System.out.println("\n\n");
                System.out.println(" new play " +i+" : \n");
                //get the last card of Discard. 
                Card LastCardOnDisCard = Discard.getLastCardOnDisCard();
                //Play until there is a winner for this round
                winner = playRound(players, LastCardOnDisCard);
                System.out.println("\n");
                    
            }
            playAnOtherRound(players, winner);
                
        }
        // we have 1 player has over 50 points. this player is winner of the game 
        String winner = players.pollLast();
        // declare the winner 
        declareWinner(winner,players);
        System.out.println(winner + " won! bye\n");
        //send him the gameover and leave
        System.exit(0);
    }
    protected void playToInitARound(){
        for (String playerName : getInitialPlayers()) {
            //get random cards
            Card[] cards = Deck.getCardsIni(CARDS_IN_HAND_INITIAL_COUNT);
            // transform them to String
            String hand = Card.cardsToString(cards);
            //send them to this players
            giveCardsToPlayer(playerName, hand);
            System.out.println("\nplayer "+playerName+" have : "+hand);
        } 
    }
    protected void playAnOtherRound(Deque<String> players, String winner){
        //we check the numbre of cards in deck and shuffle
        reinitDeckFromDiscard();
        // Give score of this round to winner 
        giveScoreToPlayer(winner);
        // add this player to the game to continue, until 1 player has over 50 points.
        players.offer(winner);
        for (String playerName : players) {
            //send the initial hand to every players
            //get random cards
            Card[] cards = Deck.getCardsIni(CARDS_IN_HAND_INITIAL_COUNT);
            // transform them to String
            String hand = Card.cardsToString(cards);
            //send them to this players
            giveCardsToPlayer(playerName, hand);
        }
    }

    protected Card pickACardFromDiscardToDebut(){
        Card cardToInitDiscard = Deck.getACard();
            
        while(cardToInitDiscard.getColor() == CardColor.WHITE){
            Deck.deck.offerLast(cardToInitDiscard);
            cardToInitDiscard = Deck.getACard();
            
        }
        return cardToInitDiscard;

    }
    /** 
     * provide the list of the initial players to play the game
     * @return
     */
    protected abstract Set<String> getInitialPlayers();

      /**
     * give some card to a player
     *
     * @param playerName the player that will receive the cards
     * @param hand       the cards as a string (to be converted later)
     */
    protected abstract void giveCardsToPlayer(String hand, String playerName);

    /**
     * calculate the sum of score of the cards in hand of two losers
     * give these score to winner
     * @param winner
     */
    protected abstract void giveScoreToPlayer(String winner);

     /**
     * We check the score of each player, if the score is more than 50 points, this player wins.
     * we return true, else we return false.
     * @return
     */
    protected abstract boolean hasWon(); //better name
    
    /**
     * Play a single round
     * @param players               the Deque containing the remaining players
     * @param LastCardOnDisCard     the last card in the Discard 
     * @return
     */
    protected String playRound(Deque<String> players, Card LastCardOnDisCard) {
        String firstPlayerInRound = sensOfRound(players);
        System.out.println("\n");
        System.out.println("Player Playing : "+firstPlayerInRound);
        
        //here, we try to get the first player card
        if(hasAtLeastACard(firstPlayerInRound)){
            String winnerOfTheRound = firstPlayerInRound;
            players.remove(winnerOfTheRound);
            return winnerOfTheRound;
            
        }
        else{
            Card firstPlayerCard = getCardToPlay(firstPlayerInRound);
            System.out.println("Last card in the Discard:  "+Discard.discard.getLast());
            System.out.println("\n");
            System.out.println(firstPlayerInRound+" play the Card : "+firstPlayerCard);
            if (firstPlayerCard == null) {
                 // if this card is null, the player draw a card. 
                System.out.println("\n");
                System.out.println("I draw and pass ! ");   
            }
            else{
                
                CardColor colorMaxHand = UnoGamePlayer.colorChooseInHand();
                effetOfTheCard(players, firstPlayerCard, colorMaxHand);
                Discard.getACardToDicard(firstPlayerCard);
            }
        }
        return null; // exception needed

        //otherwise we do another round.
    }

    /**
    * this method must be called when a winner is identified
    * @param winner          the final winner of the same
    * @param players         a Deque with all the players   
    */
    protected abstract void declareWinner(String winner, Deque<String> players);

   /**
    * Check the player still has a card or he has no more in his hand
    * if the player still has a card, return false
    * else return true. 
    * @param cardProviderPlayer    the player that should give a card
    * @return
    */
    protected abstract boolean hasAtLeastACard(String cardProviderPlayer);

   /**
    * give some card to a player
    * @param player          the player that will receive the cards
    * @param cardToGive      the cards as a collection of cards
    */
    protected abstract void giveCardToPlayers(String player, Collection<Card> cardToGive);

     /**
    * get a card from a player
    * @param cardProviderPlayer    the player to give card
    * @return                      the card from the player
    */
    protected abstract Card getCardToPlay(String cardProviderPlayer);

     /**
     * draw a card 
     * @return the card the player draw 
     */
    protected  Card drawACard(){
        return Deck.getACard();
    }

    /**
     * effect of each card special 
     * @param players           a Deque with all the players   
     * @param cardPlay          the card from the player will play
     * @param colorMaxHand      the color often appears in hand of this player
     */
    public  void effetOfTheCard(Deque<String> players, Card cardPlay, CardColor colorMaxHand ){
        switch (cardPlay.getValue()) {
            case SKIPTURN:
                effectSkipTurn(players);
                break;
            case INVERSE:
                effectInverse(players);
                break;
            case PLUS2:
                effectPlus(players,2);
                break;
            case JOKER:
                effectJocker(cardPlay,colorMaxHand);
                break;
            case PLUS4:
                effectJocker(cardPlay,colorMaxHand);
                effectPlus(players,4);
                break;
        
            default:
                break;
        }
    }


    /**
     * effet of card SkipTurn
     * @param players      a Deque with all the players  
     */
    public void effectSkipTurn(Deque<String> players) {
        String secondPlayerInRound = players.poll();
        players.offerLast(secondPlayerInRound);
    }

    /**
     * effet of card Inverser
     * @param players      a Deque with all the players  
     */
    public void effectInverse(Deque<String> players){
        sens="Inverse";
        String firstPlayerInRound = players.pollLast();
        players.offerFirst(firstPlayerInRound);

    }

    /**
     * effet of card +2 and +4
     * @param players       a Deque with all the players  
     * @param length        the number of cards the next player will add
     */
    public void effectPlus(Deque<String> players,int length){
        String secondPlayerInRound = players.poll();
        players.offer(secondPlayerInRound);
        Card[] PLUS = Deck.getCardsIni(length);
        String plus = Card.cardsToString(PLUS);
        giveCardsToPlayer(secondPlayerInRound, plus);
    }

     /**
     * effet of card Joker
     * @param cardPlay         the card to play 
     * @param choosenColor     the color choose
     */
    public void effectJocker(Card cardPlay, CardColor choosenColor){
        CardColor color = cardPlay.getColor();
        if(color == CardColor.WHITE){
            cardPlay.setColor(choosenColor);
        }
    }
    
      /**
     * get the sens of round 
     * @param players        a Deque with all the players  
     * @return           the player will play 
     */
    public String sensOfRound(Deque<String> players){
        switch (sens) { //not really the best option for a boolean outcome
            case "Inverse":

                String firstPlayerInRoundInvers = players.pollLast();
                //and put it immediately at the end
                players.offerFirst(firstPlayerInRoundInvers);
                sens="sens";
                return firstPlayerInRoundInvers;
                
            default:
                String firstPlayerInRound = players.poll();
                //and put it immediately at the end
                players.offer(firstPlayerInRound);
                return firstPlayerInRound;
            
        }

    }

    /**
     * we check the numbre of cards in deck and shuffle
     */
    public void reinitDeckFromDiscard(){
        Card lastCardOnDiscard = Discard.getLastCardOnDisCard();
        if(Deck.deck.size()<=20){
            for(Card card : Discard.discard){
                if (card.getValue()== CardValue.JOKER || card.getValue()== CardValue.PLUS4){
                    card.setColor(CardColor.WHITE);
                    Deck.deck.offerLast(card);
                }
                Deck.deck.offerLast(card);
            }
            Discard.getACardToDicard(lastCardOnDiscard);
            
            Collections.shuffle(Deck.deck);
            Collections.shuffle(Deck.deck);
            Collections.shuffle(Deck.deck);
            Collections.shuffle(Deck.deck); 
            Collections.shuffle(Deck.deck);
        }
    }



  
   

   
}
