package com.api;

import com.constants.APIEndpoints;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class DeckAPI extends BaseAPI {

    // Method to check if the site is up
    public boolean isSiteUp() {
        Response response = get("/", null);
        return response.statusCode() == 200;
    }

    // Method to get a new deck
    public JsonNode getNewDeck() {
        Response response = get(APIEndpoints.NEW_DECK, null);
        validateResponseStatus(response, 200);
        return response.as(JsonNode.class);
    }

    // Method to shuffle the deck
    public void shuffleDeck(String deckId) {
        String endpoint = APIEndpoints.SHUFFLE_DECK.replace("{deck_id}", deckId);
        Response response = get(endpoint, null);
        validateResponseStatus(response, 200);
    }

    // Method for deal cards
    public JsonNode dealCards(String deckId, int count) {
        String endpoint = APIEndpoints.DEAL_CARDS.replace("{deck_id}", deckId);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("count", count);
        Response response = get(endpoint, queryParams);
        validateResponseStatus(response, 200);
        return response.as(JsonNode.class);
    }

    // Utility method for response validation
    private void validateResponseStatus(Response response, int expectedStatusCode) {
        if (response.statusCode() != expectedStatusCode) {
            throw new RuntimeException("Unexpected response: " + response.statusLine());
        }
    }
}
