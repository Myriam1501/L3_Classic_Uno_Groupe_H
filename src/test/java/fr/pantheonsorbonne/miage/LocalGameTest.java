package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;

import org.junit.jupiter.api.Test;
import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Discard;

public class LocalGameTest {

    LocalGameUno localWarGame = new LocalGameUno(Set.of("Joueur1", "Joueur2", "Joueur3"));
    Deque<String> players = new LinkedList();


    @Test
    public void testingGetInitialPlayers()  {
        assertEquals(3, localWarGame.getInitialPlayers().size());
    }
    @Test
    public void getInitialPlayers() {
        assertTrue(localWarGame.getInitialPlayers().containsAll(Set.of("Joueur1", "Joueur2", "Joueur3")));
    }
    
    @Test
    public void testingGiveCardsToPlayer() {
        //Map<String, Queue<Card>> testCards = new HashMap<>();
        String player = "Joueur1";
        String hand = "PLUS4_W;6_B";
        localWarGame.giveCardsToPlayer(player,hand);
        assertEquals(2, localWarGame.playerCards.get(player).size());
    }
    @Test
    public void testingGiveCardsToPlayer2() {
        String player = "Joueur1";
        String hand = "PLUS4_W;6_B";
        String player2 = "Joueur2";
        String hand2 = "7_G;8_R;9_Y";
        String player3 = "Joueur3";
        String hand3 = "JOKER_W;5_B;2_Y;PLUS2_B";
        localWarGame.giveCardsToPlayer(player,hand);
        localWarGame.giveCardsToPlayer(player2, hand2);
        localWarGame.giveCardsToPlayer(player3, hand3);
        assertEquals(3, localWarGame.playerCards.size());
    }
    @Test
    public void testingCheckScoreToWin()  {
        localWarGame.playerScore.put("Joueur1", 10);
        assertFalse(localWarGame.hasWon());
    }
    @Test
    public void testingCheckScoreToWin2()  {
        localWarGame.playerScore.put("Joueur1", 80);
        localWarGame.playerScore.put("Joueur3", 110);
        assertEquals(110, localWarGame.playerScore.get("Joueur3"));
    }

    @Test
    public void testingCheckScoreToWin1()  {
        localWarGame.playerScore.put("Joueur1", 80);
        localWarGame.playerScore.put("Joueur3", 10);
        assertTrue(localWarGame.hasWon());
    }
    @Test
    public void testingCheckScoreToWin3()  {
        assertEquals(3,localWarGame.playerScore.size());
    }
    @Test
    public void testingGetCardOrGameOver()  {
        System.out.println(localWarGame.playerCards);
        assertTrue(localWarGame.hasAtLeastACard("Joueur1"));
    }
    @Test
    public void testingGetCardOrGameOver1() {
        System.out.println(localWarGame.playerCards);
        String player = "Joueur1";
        String hand = "PLUS4_W;6_B";
        localWarGame.giveCardsToPlayer(player,hand);
        System.out.println(localWarGame.playerCards);
        assertFalse(localWarGame.hasAtLeastACard("Joueur1"));
    }
    @Test
    public void testingGetCardToPlay()  {
        Discard.getACardToDicard(new Card(CardColor.BLEU, CardValue.SIX));
        System.out.println(localWarGame.playerCards);
        String player = "Joueur1";
        String hand = "8_Y;6_B";
        localWarGame.giveCardsToPlayer(player,hand);
        System.out.println(localWarGame.playerCards);
        Card play = new Card(CardColor.BLEU, CardValue.SIX);
        assertEquals(play.toString() ,localWarGame.getCardToPlay("Joueur1").toString());
    }
    @Test
    public void testingGetCardToPlay1()  {
        Discard.getACardToDicard(new Card(CardColor.BLEU, CardValue.SIX));
        System.out.println(localWarGame.playerCards);
        String player = "Joueur1";
        String hand = "8_Y;6_B";
        localWarGame.giveCardsToPlayer(player,hand);
        System.out.println(localWarGame.playerCards);
        //Card play = new Card(CardColor.BLEU, CardValue.SIX);
        assertNotNull(localWarGame.getCardToPlay("Joueur1"));
    }
    @Test
    public void testingDeclareWinner()  {
        players.add("Joueur1");
        players.add("Joueur2");
        players.add("Joueur3");
        localWarGame.declareWinner("Joueur1", players);
        assertEquals(1, players.size());
    }
    @Test
    public void testingGiveScoreToPlayer()  {
        String player2 = "Joueur2";
        String hand2 = "7_G;8_R;9_Y";
        String player3 = "Joueur3";
        String hand3 = "5_B;2_Y";
        localWarGame.giveCardsToPlayer(player2, hand2);
        localWarGame.giveCardsToPlayer(player3, hand3);
        localWarGame.playerScore.put("Joueur1", 20);
        localWarGame.playerScore.put("Joueur3", 30);
        localWarGame.giveScoreToPlayer("Joueur1");
        assertEquals(51, localWarGame.playerScore.get("Joueur1"));
    }
    @Test
    public void testingGiveScoreToPlayer1()  {
        String player2 = "Joueur2";
        String hand2 = "7_G;8_R;9_Y";
        String player1 = "Joueur1";
        String hand1 = "5_B;2_Y";
        localWarGame.giveCardsToPlayer(player2, hand2);
        localWarGame.giveCardsToPlayer(player1, hand1);
        localWarGame.playerScore.put("Joueur1", 20);
        localWarGame.giveScoreToPlayer("Joueur3");
        assertEquals(31, localWarGame.playerScore.get("Joueur3"));
    }
    
}

