package com.cardgame.tests;

import com.api.DeckAPI;
import com.logic.BlackjackLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.JsonNode;

public class CardGameTest {

    private DeckAPI deckAPI = new DeckAPI();
    private BlackjackLogic blackjackLogic = new BlackjackLogic();
    public Logger logger = LoggerFactory.getLogger(CardGameTest.class);

    @Test
    public void testCardGame() {
        // validation for site is up
        Assert.assertTrue(deckAPI.isSiteUp(), "The Deck of Cards API is not up!");

        // Get a new deck
        JsonNode deck = deckAPI.getNewDeck();
        String deckId = deck.get("deck_id").asText();
        Assert.assertNotNull(deckId, "Failed to retrieve a new deck");

        // Shuffle the deck
        deckAPI.shuffleDeck(deckId);

        // Deal cards to two players
        JsonNode player1Hand = deckAPI.dealCards(deckId, 3).get("cards");
        JsonNode player2Hand = deckAPI.dealCards(deckId, 3).get("cards");


        int player1HandValue = blackjackLogic.calculateHandValue(player1Hand);
        int player2HandValue = blackjackLogic.calculateHandValue(player2Hand);

        String result = determineWinner(player1HandValue, player2HandValue);
        logger.info("Result: {}", result);

    }

    // Method to determine winner
    public String determineWinner(int player1Value, int player2Value) {
        if (player1Value > 21 && player2Value > 21) {
            return "Both players busted!";
        } else if (player1Value > 21) {
            return "Player 2 wins! Player 1 busted.";
        } else if (player2Value > 21) {
            return "Player 1 wins! Player 2 busted.";
        } else if (player1Value == 21 && player2Value == 21) {
            return "Both players hit blackjack!";
        } else if (player1Value == 21) {
            return "Player 1 hits blackjack!";
        } else if (player2Value == 21) {
            return "Player 2 hits blackjack!";
        } else if (player1Value > player2Value) {
            return "Player 1 wins with " + player1Value + "!";
        } else if (player2Value > player1Value) {
            return "Player 2 wins with " + player2Value + "!";
        } else {
            return "It's a tie!";
        }
    }
}
