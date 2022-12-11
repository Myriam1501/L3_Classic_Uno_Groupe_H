package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.CardColor;
import fr.pantheonsorbonne.miage.enums.CardValue;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Discard;

public class PlayerTest {
    UnoGamePlayer unoGamePlayer;

    @BeforeEach
    void setUp() {
       
    }

    @AfterEach
    void tearDown() {
    }
     
    @Test
    public void testinggetIfYouCanPlayTheCards()  {
       Card c1 = new Card(CardColor.BLEU,CardValue.EIGHT);
       Card cTalon = new Card(CardColor.WHITE,CardValue.PLUS4);
        assertFalse(unoGamePlayer.getIfYouCanPlayTheCards(c1, cTalon));
    }
    @Test
    public void testinggetIfYouCanPlayTheCard2()  {
       Card c1 = new Card(CardColor.BLEU,CardValue.EIGHT);
       Card cTalon = new Card(CardColor.BLEU,CardValue.ONE);
        assertTrue(unoGamePlayer.getIfYouCanPlayTheCards(c1, cTalon));
    }
    @Test
    public void testinggetgetCardYouCanPlay()  {
        LinkedList<Card> hand = new LinkedList<>();
       Card c1 = new Card(CardColor.BLEU,CardValue.EIGHT);
       Card c2 = new Card(CardColor.WHITE,CardValue.PLUS4);
       Card c3 = new Card(CardColor.RED,CardValue.ONE);
       hand.add(c1);
       hand.add(c2);
       hand.add(c3);

       Card cTalon = new Card(CardColor.WHITE,CardValue.PLUS4);
       LinkedList<Card> carteplay = new LinkedList<>();
       carteplay.add(c2);
       assertEquals(carteplay, unoGamePlayer.getCardYouCanPlay(hand, cTalon));
    }
    @Test
    public void testingmaxValueCard()  {
        List<Card> getCardYouCanPlay =new LinkedList<>();
        Card c1 = new Card(CardColor.BLEU,CardValue.EIGHT);
       Card c2 = new Card(CardColor.GREEN,CardValue.NINE);
       Card c3 = new Card(CardColor.RED,CardValue.ONE);
       getCardYouCanPlay.add(c1);
       getCardYouCanPlay.add(c2);
       getCardYouCanPlay.add(c3);

       assertEquals(c2, unoGamePlayer.maxValueCard(getCardYouCanPlay));
    }
    @Test
    public void testingContainSpecialCard()  {
        List<Card> getCardYouCanPlay =new LinkedList<>();
        Card c1 = new Card(CardColor.BLEU,CardValue.EIGHT);
       Card c2 = new Card(CardColor.GREEN,CardValue.NINE);
       Card c3 = new Card(CardColor.RED,CardValue.ONE);
       getCardYouCanPlay.add(c1);
       getCardYouCanPlay.add(c2);
       getCardYouCanPlay.add(c3);

       assertFalse(unoGamePlayer.containSpecialCard(getCardYouCanPlay));
    }
    @Test
    public void testingContainSpecialCard2()  {
        List<Card> getCardYouCanPlay =new LinkedList<>();
        Card c1 = new Card(CardColor.BLEU,CardValue.EIGHT);
       Card c2 = new Card(CardColor.WHITE,CardValue.PLUS4);
       Card c3 = new Card(CardColor.RED,CardValue.ONE);
       getCardYouCanPlay.add(c1);
       getCardYouCanPlay.add(c2);
       getCardYouCanPlay.add(c3);

       assertTrue(unoGamePlayer.containSpecialCard(getCardYouCanPlay));
    }
    @Test
    public void testingplayThisSpecialCard()  {
        LinkedList<Card> getCardYouCanPlay =new LinkedList<>();
        Card c1 = new Card(CardColor.BLEU,CardValue.EIGHT);
        Card c2 = new Card(CardColor.WHITE,CardValue.PLUS4);
        Card c3 = new Card(CardColor.RED,CardValue.ONE);
        getCardYouCanPlay.add(c1);
        getCardYouCanPlay.add(c2);
        getCardYouCanPlay.add(c3);

        assertEquals(c2, unoGamePlayer.playThisSpecialCard(getCardYouCanPlay));
    }
    @Test
    public void testingplayThisSpecialCard2()  {
        LinkedList<Card> getCardYouCanPlay =new LinkedList<>();
        Card c1 = new Card(CardColor.BLEU,CardValue.EIGHT);
        Card c2 = new Card(CardColor.GREEN,CardValue.THREE);
        Card c3 = new Card(CardColor.RED,CardValue.ONE);
        getCardYouCanPlay.add(c1);
        getCardYouCanPlay.add(c2);
        getCardYouCanPlay.add(c3);

        assertNull(unoGamePlayer.playThisSpecialCard(getCardYouCanPlay));
    }
    @Test
    public void testingPlayThisCard()  {
        Card lastCard = new Card(CardColor.BLEU,CardValue.EIGHT);
        Discard.getACardToDicard(lastCard);
        LinkedList<Card> getCardYouCanPlay =new LinkedList<>();
        Card c1 = new Card(CardColor.BLEU,CardValue.ONE);
        Card c2 = new Card(CardColor.GREEN,CardValue.EIGHT);
        Card c3 = new Card(CardColor.RED,CardValue.EIGHT);
        getCardYouCanPlay.add(c1);
        getCardYouCanPlay.add(c2);
        getCardYouCanPlay.add(c3);

        assertEquals(c2, unoGamePlayer.playThisCard(getCardYouCanPlay, lastCard));
    }
    @Test
    public void testingPlayThisCard2()  {
        Card lastCard = new Card(CardColor.BLEU,CardValue.EIGHT);
        Discard.getACardToDicard(lastCard);
        LinkedList<Card> getCardYouCanPlay =new LinkedList<>();
        Card c1 = new Card(CardColor.BLEU,CardValue.ONE);
        Card c2 = new Card(CardColor.WHITE,CardValue.PLUS4);
        Card c3 = new Card(CardColor.RED,CardValue.EIGHT);
        getCardYouCanPlay.add(c1);
        getCardYouCanPlay.add(c2);
        getCardYouCanPlay.add(c3);

        assertEquals(c2, unoGamePlayer.playThisCard(getCardYouCanPlay,lastCard));
    }
    @Test
    public void testingPlayThisCard3()  {
        Card lastCard = new Card(CardColor.BLEU,CardValue.EIGHT);
        Discard.getACardToDicard(lastCard);
        LinkedList<Card> getCardYouCanPlay =new LinkedList<>();
        Card c1 = new Card(CardColor.BLEU,CardValue.ONE);
        getCardYouCanPlay.add(c1);
        assertEquals(c1, unoGamePlayer.playThisCard(getCardYouCanPlay,lastCard));
    }

  @Test
    public void playTheDrawenCard(){
        Card carte = new Card(CardColor.BLEU, CardValue.EIGHT);
        assertNotNull(unoGamePlayer.playTheDrawenCard(carte));
    }
     
    @Test
    public void testingColorChooseInHand()  {
        Card c1 = new Card(CardColor.BLEU,CardValue.ONE);
        Card c2 = new Card(CardColor.WHITE,CardValue.PLUS4);
        Card c3 = new Card(CardColor.RED,CardValue.EIGHT);
        unoGamePlayer.hand.add(c1);
        unoGamePlayer.hand.add(c2);
        unoGamePlayer.hand.add(c3);

        assertNotNull(unoGamePlayer.colorChooseInHand());
    }
    @Test
    public void testingColorChooseInHand1()  {
        Card c1 = new Card(CardColor.BLEU,CardValue.ONE);
        Card c2 = new Card(CardColor.RED,CardValue.PLUS4);
        Card c3 = new Card(CardColor.RED,CardValue.EIGHT);
        unoGamePlayer.hand.add(c1);
        unoGamePlayer.hand.add(c2);
        unoGamePlayer.hand.add(c3);
        assertEquals(CardColor.RED, unoGamePlayer.colorChooseInHand());
    }
    @Test
    public void testingColorChooseInHand2()  {
        Card c1 = new Card(CardColor.BLEU,CardValue.ONE);
        Card c2 = new Card(CardColor.WHITE,CardValue.PLUS4);
        Card c3 = new Card(CardColor.BLEU,CardValue.EIGHT);
        unoGamePlayer.hand.add(c1);
        unoGamePlayer.hand.add(c2);
        unoGamePlayer.hand.add(c3);
        assertEquals(CardColor.BLEU, unoGamePlayer.colorChooseInHand()); 
    }

}

