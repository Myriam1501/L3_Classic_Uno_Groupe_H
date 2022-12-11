package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Discard;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.*;


/**
 * This class implements the war game with the network engine
 */
public class UnoGameNetworkEngine extends UnoGameEngine {
    private static final int PLAYER_COUNT = 3;

    private final HostFacade hostFacade;
    private final Set<String> players;
    private final Game uno;

    public UnoGameNetworkEngine(HostFacade hostFacade, Set<String> players, fr.pantheonsorbonne.miage.model.Game uno) {
        this.hostFacade = hostFacade;
        this.players = players;
        this.uno = uno;
    }

    public static void main(String[] args) {
        //create the host facade
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        //set the name of the player
        hostFacade.createNewPlayer("Host");

        //create a new game of war
        fr.pantheonsorbonne.miage.model.Game uno = hostFacade.createNewGame("uno");

        //wait for enough players to join
        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT);

        UnoGameEngine host = new UnoGameNetworkEngine(hostFacade, uno.getPlayers(), uno);
        host.play();


    }

     /**
     * provide the list of the initial players to play the game
     * @return
     */
    @Override
    protected Set<String> getInitialPlayers() {
        return this.uno.getPlayers();
    }

      /**
     * give some card to a player
     *
     * @param playerName the player that will receive the cards
     * @param hand       the cards as a string (to be converted later)
     */
    @Override
    protected void giveCardsToPlayer(String playerName, String hand) {
        Collection<Card> cards = Arrays.asList(Card.stringToCards(hand));
        hostFacade.sendGameCommandToPlayer(uno, playerName, new GameCommand("cardsForYou", Card.cardsToString(cards.toArray(new Card[cards.size()]))));
        
    }

     /**
    * this method must be called when a winner is identified
    * @param winner          the final winner of the same
    * @param players         a Deque with all the players   
    */
    @Override
    protected void declareWinner(String winner, Deque<String> players) {
        
        hostFacade.sendGameCommandToAll(uno, new GameCommand("gameOver"));
        
    }

     /**
    * Check the player still has a card or he has no more in his hand
    * if the player still has a card, return false
    * else return true. 
    * @param cardProviderPlayer    the player that should give a card
    * @return
    */
    @Override
    protected boolean getCardOrGameOver(String cardProviderPlayer) {
        hostFacade.sendGameCommandToPlayer(uno, cardProviderPlayer, new GameCommand("doYouHaveCards"));
        GameCommand expectedCard = hostFacade.receiveGameCommand(uno);
        if(expectedCard.name().equals("outOfCard")) {
            return true;
        }
        return false; 

    }

   /**
    * give some card to a player
    * @param player          the player that will receive the cards
    * @param cardToGive      the cards as a collection of cards
    */
    @Override
    protected void giveCardToPlayers(String player, Collection<Card> cardToGive) {
        List<Card> cards = new ArrayList<>();
        cards.addAll(cardToGive);
        //shuffle the round deck so we are not stuck
        Collections.shuffle(cards);
        hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("removeYourHand"));
        GameCommand expectedResponse = hostFacade.receiveGameCommand(uno);
        if (expectedResponse.name().equals("fin")) {
            hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("cardsForYou", Card.cardsToString(cards.toArray(new Card[cards.size()]))));
        }
    }

     /**
    * get a card from a player
    * @param cardProviderPlayer    the player to give card
    * @return                      the card from the player
    */

    @Override
    protected Card getCardToPlay(String player) {
        Card lastCardTalon = Discard.getLastCardOnDisCard();
        hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("playACard",lastCardTalon.toString()));
        GameCommand expectedCard = hostFacade.receiveGameCommand(uno);
        if (expectedCard.name().equals("card")) {
            //System.out.println("le joueur a jouer cette carte : "+Card.valueOf(expectedCard.body()));
            return Card.valueOf(expectedCard.body());
        }
        else{
            Card drowenCard = Deck.getACard();
            hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("drawACard", drowenCard.toString()));
            GameCommand expectedDrawenCard = hostFacade.receiveGameCommand(uno);
            System.out.println(player + " have drawen a card");
                if(expectedDrawenCard.name().equals("luckyCard")){
                    System.out.println("Good pick :"+ expectedDrawenCard.body());
                    return Card.valueOf(expectedDrawenCard.body());
                }
                else{
                    System.out.println("Bad pick ! "+expectedDrawenCard.body());
                }
        }
       
        return null;

    }

    /**
     * calculate the sum of score of the cards in hand of two losers
     * give these score to winner
     * @param winner
     */
    @Override
    protected void giveScoreToPlayer(String winner) {
        int pointsWinningOfTheRound=0;
        for (String player : players) {
            if(!player.equals(winner)){
               hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("yourLostHandScore"));
                GameCommand expectedScore = hostFacade.receiveGameCommand(uno);
                if (!getCardOrGameOver(player)) {
                    try{
                        pointsWinningOfTheRound+= Integer.parseInt(expectedScore.body());
                    }
                    catch (NumberFormatException exception){
                        exception.printStackTrace();
                    }
                } 
            }
            

        }
        System.out.println(winner +" have win "+pointsWinningOfTheRound+" points on this Round");
        hostFacade.sendGameCommandToPlayer(uno, winner, new GameCommand("scoreForYou", Integer.toString(pointsWinningOfTheRound)));
    }
    
    /**
     * We check the score of each player, if the score is more than 50 points, this player wins.
     * we return true, else we return false.
     * @return
     */
    @Override 
    protected boolean checkScoreToWin() {
        for (String player : players) {
            hostFacade.sendGameCommandToPlayer(uno, player, new GameCommand("whatIsYourScore"));
            GameCommand expectedCard = hostFacade.receiveGameCommand(uno);
            if(expectedCard.name().equals("outOfScore")){
                return true;
            }
        }
        return false;
        
    }



}
