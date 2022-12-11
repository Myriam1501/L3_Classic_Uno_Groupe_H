package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Discard;

public class TestGame {

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
    public void testingplayToInitARound()  {
        engine.playToInitARound();
        assertEquals(3,players.size());
       
    }
    
    @Test
    public void effectPlus4() {
        engine.giveCardToPlayers("Joueur1", Arrays.asList(Card.valueOf("PLUS4_W")));
        engine.giveCardToPlayers("Joueur2", Arrays.asList(Card.valueOf("6_R")));
        engine.giveCardToPlayers("Joueur3", Arrays.asList(Card.valueOf("3_R")));
        Discard.getACardToDicard(new Card(CardColor.RED, CardValue.SIX));
        Card carte = Discard.getLastCardOnDisCard();
        assertEquals(null, engine.playRound(players, carte));
    }


    
    @Test
    public void testingRandomCards()  {
        assertEquals(7,Deck.getCards(7).size());
       
    }

    @Test
    public void testingDeckCards()  {
        System.out.println(Deck.deck.size());
        assertTrue(Deck.deck.size()!=0);
       
    }
    @Test
    public void testingGetColorCards()  {
        Card carte = new Card(CardColor.BLEU,CardValue.EIGHT);
        assertEquals(CardColor.BLEU, carte.getColor());
    }
    @Test
    public void testingGetvalueCards()  {
        Card carte = new Card(CardColor.BLEU,CardValue.EIGHT);
        assertEquals(CardValue.EIGHT, carte.getValue());
    }
    @Test
    public void testingToStringCards()  {
        Card carte = new Card(CardColor.BLEU,CardValue.EIGHT);
        assertEquals("8_B", carte.toString());
    }
    @Test
    public void testingSetColorCards()  {
        Card carte = new Card(CardColor.BLEU,CardValue.EIGHT);
        carte.setColor(CardColor.GREEN);
        assertEquals("8_G", carte.toString());
    }
    
    @Test
    public void testingFindColorCards()  {
        assertEquals(CardColor.BLEU, CardColor.valueOfStr("B"));
    }
    @Test
    public void testingCardToString()  {
        Card[] carte = {new Card(CardColor.BLEU, CardValue.EIGHT),new Card(CardColor.RED, CardValue.ONE),new Card(CardColor.YELLOW, CardValue.THREE)};
        assertEquals("8_B;1_R;3_Y", Card.cardsToString(carte));
    }
    @Test
    public void testingStringToCards() {
        Card[] cards = Card.stringToCards("PLUS4_W;8_B");
        assertEquals(new Card(CardColor.WHITE, CardValue.PLUS4).getClass(), cards[0].getClass());
        assertEquals(new Card(CardColor.BLEU, CardValue.EIGHT).getClass(), cards[1].getClass());
    }
    @Test
    public void valueOf() {
        assertEquals(CardValue.SEVEN, Card.valueOf("7_B").getValue());
        assertEquals(CardColor.YELLOW, Card.valueOf("8_Y").getColor());
    }
    @Test
    public void valueOf1() {
        assertEquals(CardValue.INVERSE, Card.valueOf("INVERSER_B").getValue());
        assertEquals(CardValue.PLUS4, Card.valueOf("PLUS4_W").getValue());
    }
    @Test
    public void testingLastCardOnDiscard()  {
        Card carte = new Card(CardColor.BLEU,CardValue.EIGHT);
        Discard.getACardToDicard(carte);
        assertEquals(carte,Discard.getLastCardOnDisCard() );
    }
    @Test
    public void getCards() {
        Collection<Card> cards = Deck.getCards(2);
        assertEquals(2, cards.size());
    }
    @Test
    public void getCardsIni() {
        Card[] cards = Deck.getCardsIni(2);
        assertEquals(2, cards.length);
    }
    @Test
    void getACard() {
        Card card = Deck.getACard();
        Card newCard = null;
        do {

            assertNotEquals(card, newCard);
            newCard = Deck.getACard();
        } while (newCard != null);

    }
    
    

    
}

