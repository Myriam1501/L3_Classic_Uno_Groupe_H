package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.exception.NoMoreCardException;
import fr.pantheonsorbonne.miage.game.Card;

import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;


import java.util.stream.Collectors;


/**
 * this is the player part of the network version of the war game
 */
public class UnoGameNetorkPlayer extends UnoGamePlayer{
    static final PlayerFacade playerFacade = Facade.getFacade();
    static Game uno;

    public static void main(String[] args) throws NoMoreCardException {

        playerFacade.waitReady();
        playerFacade.createNewPlayer(playerId);
        uno = playerFacade.autoJoinGame("uno");
        while (true) {

            GameCommand command = playerFacade.receiveGameCommand(uno);
            switch (command.name()) {
                case "cardsForYou":
                    handleCardsForYou(command);
                    break;
                case "playACard":
                    System.out.println("\n New Play \nI have " + hand.stream().map(Card::toString).collect(Collectors.joining(" ")));
                    handlePlayACard(command);
                    break;
                case "gameOver":
                    handleGameOverCommand(command);
                    break;
                case "scoreForYou":
                    handleScoreForYou(command);
                    break;
                case "doYouHaveCards":
                    handleNoCardCommand(command);
                    break;
                case "removeYourHand":
                    handleARemove(command);
                    break;
                case "whatIsYourScore":
                    handOutOfScore(command);
                    break;
                case "yourLostHandScore":
                    handScoreToWinner(command);
                    break;
            }
        }
    }

    private static void handScoreToWinner(GameCommand command) {
        System.out.println("Cards left from the Round :\n"+hand);
        int myPointsforTheWinner = 0;
        for(Card card:hand){
            myPointsforTheWinner += card.getValue().getRank();
        }
        System.out.println("I give "+myPointsforTheWinner+" points to the winner");
        playerFacade.sendGameCommandToAll(uno, new GameCommand("myLostHandScore", Integer.toString(myPointsforTheWinner)));
    }

    private static void handOutOfScore(GameCommand command) {
        if(score>=50){
            playerFacade.sendGameCommandToAll(uno, new GameCommand("outOfScore"));
        }
        else{
            playerFacade.sendGameCommandToAll(uno, new GameCommand("no :-("));
        }
    }

    private static void handleScoreForYou(GameCommand command) {
        int pointsWinningOfTheRound = Integer.parseInt(command.body());
        if (command.params().get("playerId").equals(playerId)) {
            score+=pointsWinningOfTheRound;
        }
        System.out.println(playerId+" win and have "+ score +"points now");
        playerFacade.sendGameCommandToAll(uno, new GameCommand("newScore"));
        
    }

    private static void handleNoCardCommand(GameCommand command){
        if (command.params().get("playerId").equals(playerId)) {
            if (!hand.isEmpty()) {
                playerFacade.sendGameCommandToAll(uno, new GameCommand("no",  hand.toString()));
            } else {
                playerFacade.sendGameCommandToAll(uno, new GameCommand("outOfCard"));
            }
        }

    } 

    private static void handleCardsForYou(GameCommand command) {

        for (Card card : Card.stringToCards(command.body())) {
            hand.add(card);
        }

    }
    private static void handleARemove(GameCommand command){
        hand.removeAll(hand);
        playerFacade.sendGameCommandToAll(uno, new GameCommand("fin"));

    }

    private static void handlePlayACard(GameCommand command) {
        if (command.params().get("playerId").equals(playerId)) {
            if (!hand.isEmpty()) {
                Card lastCardTalon = Card.valueOf(command.body());
                System.out.println("\n");
                System.out.println("Last card in Discard : "+ lastCardTalon);
                Card CardToPlay = playThisCard(hand,lastCardTalon);
                if(CardToPlay!=null){
                    System.out.println("\n");
                    System.out.println("Card playing : "+CardToPlay.toString());
                    playerFacade.sendGameCommandToAll(uno, new GameCommand("card", CardToPlay.toString()));
                }
                else{
                    playerFacade.sendGameCommandToAll(uno, new GameCommand("IdrawenACard"));
                    GameCommand expectedDrawenCard = playerFacade.receiveGameCommand(uno);
                    System.out.println("\n");
                    if(expectedDrawenCard.name().equals("drawACard")){
                        hand.add( Card.valueOf(expectedDrawenCard.body()));
                        Card cardToPlayAfterDrawACard = playThisCard(hand,lastCardTalon);
                        if(cardToPlayAfterDrawACard!=null){
                            System.out.println("\n");
                            System.out.println("Good pick : "+cardToPlayAfterDrawACard.toString());
                            hand.remove(Card.valueOf(expectedDrawenCard.body()));
                            playerFacade.sendGameCommandToAll(uno, new GameCommand("luckyCard", cardToPlayAfterDrawACard.toString()));
                        }
                        else{
                            System.out.println("\n");
                            System.out.println("Bad pick ! ");
                            playerFacade.sendGameCommandToAll(uno, new GameCommand("Unlucky"));
                        }
                    }

                    

                }
                
            } 
        }
    }

    private static void handleGameOverCommand(GameCommand command) {
        if (score>=50) {
            System.out.println("\n\n\n\n");
            System.out.println("I've won! I have a total of " + score);
        } else {
            System.out.println("\n\n\n\n");
            System.out.println("I've lost :-(");
           
        }
        System.exit(0);
    }
    



}