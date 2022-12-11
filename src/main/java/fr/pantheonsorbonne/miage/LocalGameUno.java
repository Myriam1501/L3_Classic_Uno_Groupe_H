package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Discard;

import java.util.*;
import java.util.stream.Collectors;

public class LocalGameUno extends UnoGameEngine {

    private final Set<String> initialPlayers;
    protected final Map<String, Queue<Card>> playerCards = new HashMap<>();
    protected final Map<String, Integer> playerScore = new HashMap<>();

    public LocalGameUno(Set<String> initialPlayers) {
        this.initialPlayers = initialPlayers;
        for (String player : initialPlayers) {
            playerCards.put(player, new LinkedList<>());
            playerScore.put(player, 0);
        }
    }

    public static void main(String... args) {
        LocalGameUno localWarGame = new LocalGameUno(Set.of("Joueur1", "Joueur2", "Joueur3"));
        localWarGame.play();

    }

    /**
     * initialize players
     */
    @Override
    protected Set<String> getInitialPlayers() {
        return this.initialPlayers;
    }

 
    /**
     * Display the cards available in each player's hand
     */
    @Override
    protected String playRound(Deque<String> players, Card LastCardOnDisCard) {
        System.out.println(this.playerCards.keySet()
                                .stream()
                                .filter(p -> !this.playerCards.get(p).isEmpty())
                                .map(p -> p + " has " + this.playerCards
                                                            .get(p)
                                                            .stream()
                                                            .map(c -> c.toString())
                                                            .collect(Collectors.joining(" ; ")))
                                .collect(Collectors.joining("\n")));
        System.out.println();
        return super.playRound(players,LastCardOnDisCard);

    }
    /**
     * calculate the sum of score of the cards in hand of two losers
     * give these score to winner
     */
    @Override
    protected void giveScoreToPlayer(String winner) {
        int pointsWinningOfTheRound =0;
        for (Map.Entry mapentry : playerCards.entrySet()) {     
            Queue<Card> value = (Queue<Card>) mapentry.getValue();
            for(Card card : value){
                pointsWinningOfTheRound += card.getValue().getRank();

            }  
        }
        if (playerScore.containsKey(winner)){
            int scoreOld = playerScore.get(winner);
            playerScore.replace(winner, (int)playerScore.get(winner), scoreOld+pointsWinningOfTheRound);
            System.out.println(winner +" won, He have "+playerScore.get(winner)+" now !\n");
        }
        
    }
    /**
     * We check the score of each player, if the score is more than 50 points, this player wins.
     * we return true, else we return false.
     */
    @Override
    protected boolean checkScoreToWin() {
        
        for (Map.Entry scoreplayer : playerScore.entrySet()) {  
            if ((int)scoreplayer.getValue()>=50){
                System.out.println("\n\nscore of the winner is : " +scoreplayer+"\n");
                return true;
            }
        }
        return false;
        
    }

    /**
     * We delete all players and declare the winner
     */
    @Override
    protected void declareWinner(String winner, Deque<String> players) {
        for (int index = 0; index < players.size(); index++) {
            players.poll();
            
        }
        System.out.println(winner + " has won!");
    }
    
    /**
     * Check the player still has a card or he has no more in his hand
     * if the player still has a card, return false
     * else return true. 
     */
    @Override
    protected boolean getCardOrGameOver(String cardProviderPlayer) {
        if (!this.playerCards.containsKey(cardProviderPlayer) || this.playerCards.get(cardProviderPlayer).isEmpty()) {
            return true;
        } 
        return false; 

           
    }

      /**
     * By using the playThisCard function in the UnoGamePlayer class to retrieve a best choice playable 
     * card with the strategies.
     * if this card is not null, retrun this card
     * otherwise the player draws a card from the deck and rechecks this card, 
     * if this drawn card is not yet playable, we return null.
     */
    @Override
    protected Card getCardToPlay(String cardProviderPlayer) {
        Card lastCardTalon = Discard.getLastCardOnDisCard();
        Card cardplay = UnoGamePlayer.playThisCard((LinkedList<Card>) this.playerCards.get(cardProviderPlayer),lastCardTalon);
            if(cardplay!= null){
                return cardplay;
            }
            else{
                Card drowenCard = Deck.getACard();
                Card drawenCardToPlay = UnoGamePlayer.playThisCard(UnoGamePlayer.playTheDrawenCard(drowenCard),lastCardTalon);
                if(drawenCardToPlay!=null){
                    System.out.println("Good pick ! ");
                    return drawenCardToPlay;
                }
                else{
                    System.out.println("Bad pick ! ");
                    this.playerCards.get(cardProviderPlayer).add(drowenCard);
                }
                

            }
            return null;
        
    }

    /**
     * Give cards to Players
     */
    @Override
    protected void giveCardsToPlayer(String playerName, String hand) {
        Collection<Card> cards = Arrays.asList(Card.stringToCards(hand));
        this.playerCards.get(playerName).addAll(cards);
    }

     /**
     * Give cards to Players
     */
    @Override
    protected void giveCardToPlayers(String player,Collection<Card> cardToGive){
        System.out.println(player + " : card To Give : "+cardToGive);
        List<Card> cards = new ArrayList<>();
        cards.addAll(cardToGive);
        Collections.shuffle(cards);
        this.playerCards.get(player).removeAll(this.playerCards.get(player));
        this.playerCards.get(player).addAll(cards);
    }
    

    

   
}

