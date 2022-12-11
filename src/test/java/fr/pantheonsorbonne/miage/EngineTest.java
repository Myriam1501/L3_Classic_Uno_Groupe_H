package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Discard;

public class EngineTest {
    
    UnoGameEngine engine;
    Deque<String> players;

    @BeforeEach
    void setUp() {


        this.engine = new LocalGameUno(Set.of("Joueur1", "Joueur2", "Joueur3"));
        this.players = new LinkedList<>();
        this.players.addAll(Arrays.asList("Joueur1", "Joueur2", "Joueur3"));

    }
    @AfterEach
    void tearDown() {
    }
    @Test
    public void getInitialPlayers() {
        assertTrue(engine.getInitialPlayers().containsAll(Set.of("Joueur1", "Joueur2", "Joueur3")));
    }
    @Test
    public void pickACardFromDiscardToDebut() {
        Card cartePick = engine.pickACardFromDiscardToDebut();
        assertTrue(cartePick.getColor()!= CardColor.WHITE);
    } 

    @Test
    public void playAnOtherRound() {
        Discard.getACardToDicard(new Card(CardColor.BLEU, CardValue.SIX));
        String player2 = "Joueur2";
        String hand2 = "7_G;8_R;9_Y";
        String player3 = "Joueur3";
        String hand3 = "5_B;2_Y";
        engine.giveCardsToPlayer(player2, hand2);
        engine.giveCardsToPlayer(player3, hand3);
        players.remove("Joueur1");
        engine.playAnOtherRound(players, "Joueur1");
       assertEquals(3, players.size());
    }
    @Test
    public void playRoundEquality() {
        engine.giveCardToPlayers("Joueur1", Arrays.asList(Card.valueOf("8_B")));
        engine.giveCardToPlayers("Joueur2", Arrays.asList(Card.valueOf("6_R")));
        engine.giveCardToPlayers("Joueur3", Arrays.asList(Card.valueOf("3_R")));
        engine.reinitDeckFromDiscard();
        Discard.getACardToDicard(new Card(CardColor.BLEU, CardValue.SIX));
        Card carte = Discard.getLastCardOnDisCard();
        assertNull(engine.playRound(players, carte));
    }
    
    @Test
    public void playRoundEquality1() {
        engine.giveCardToPlayers("Joueur1", Arrays.asList(Card.valueOf("6_G")));
        engine.giveCardToPlayers("Joueur2", Arrays.asList(Card.valueOf("6_Y")));
        engine.giveCardToPlayers("Joueur3", Arrays.asList(Card.valueOf("3_Y")));
        engine.drawACard();
        Discard.getACardToDicard(new Card(CardColor.YELLOW, CardValue.SIX));
        Card carte = Discard.getLastCardOnDisCard();
        assertNull(engine.playRound(players, carte));
    }
    @Test
    public void playRoundEquality2() {
        engine.giveCardToPlayers("Joueur2", Arrays.asList(Card.valueOf("6_Y")));
        engine.giveCardToPlayers("Joueur3", Arrays.asList(Card.valueOf("3_Y")));
        Discard.getACardToDicard(new Card(CardColor.YELLOW, CardValue.SIX));
        Card carte = Discard.getLastCardOnDisCard();
        assertEquals("Joueur1",engine.playRound(players, carte));
    }
    @Test
    public void effectPlus2() {
        engine.giveCardToPlayers("Joueur1", Arrays.asList(Card.valueOf("PLUS2_B")));
        engine.giveCardToPlayers("Joueur2", Arrays.asList(Card.valueOf("6_R")));
        engine.giveCardToPlayers("Joueur3", Arrays.asList(Card.valueOf("3_R")));
        Discard.getACardToDicard(new Card(CardColor.BLEU, CardValue.FIVE));
        Card carte = Discard.getLastCardOnDisCard();
        assertNull(engine.playRound(players, carte));
    }
   
    @Test
    public void effectInverse() {
        engine.giveCardToPlayers("Joueur1", Arrays.asList(Card.valueOf("INVERSER_B")));
        engine.giveCardToPlayers("Joueur2", Arrays.asList(Card.valueOf("6_R")));
        engine.giveCardToPlayers("Joueur3", Arrays.asList(Card.valueOf("8_B")));
        Discard.getACardToDicard(new Card(CardColor.BLEU, CardValue.FIVE));
        Card carte = Discard.getLastCardOnDisCard();
        assertNull(engine.playRound(players, carte));   
    }
    @Test
    public void effectSkipTurn() {
        engine.giveCardToPlayers("Joueur1", Arrays.asList(Card.valueOf("SKIPTURN_R")));
        engine.giveCardToPlayers("Joueur2", Arrays.asList(Card.valueOf("6_R")));
        engine.giveCardToPlayers("Joueur3", Arrays.asList(Card.valueOf("8_B")));
        Discard.getACardToDicard(new Card(CardColor.RED, CardValue.FIVE));
        Card carte = Discard.getLastCardOnDisCard();
        assertNull(engine.playRound(players, carte));   
    }

    @Test
    public void effectJocker() {
        engine.giveCardToPlayers("Joueur1", Arrays.asList(Card.valueOf("JOKER_W")));
        engine.giveCardToPlayers("Joueur2", Arrays.asList(Card.valueOf("6_R")));
        engine.giveCardToPlayers("Joueur3", Arrays.asList(Card.valueOf("8_B")));
        Discard.getACardToDicard(new Card(CardColor.BLEU, CardValue.FIVE));
        Card carte = Discard.getLastCardOnDisCard();
        assertNull(engine.playRound(players, carte));   
    }
    
}
