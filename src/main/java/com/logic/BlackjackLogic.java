package com.logic;

import com.fasterxml.jackson.databind.JsonNode;

public class BlackjackLogic {

    public boolean isBlackjack(JsonNode hand) {
        return calculateHandValue(hand) == 21;
    }

    //Method to calculate hand value and adjust for Aces for total is > 21
    public int calculateHandValue(JsonNode hand) {
        int totalValue = 0;
        int aceCount = 0;

        // Calculate initial hand value and count Aces
        for (JsonNode card : hand) {
            String value = card.get("value").asText();
            if (value.equals("ACE")) {
                totalValue += 11;
                aceCount++;
            } else if (value.equals("KING") || value.equals("QUEEN") || value.equals("JACK")) {
                totalValue += 10;
            } else {
                totalValue += Integer.parseInt(value);
            }
        }

        // Adjust for Aces if total exceeds 21
        while (totalValue > 21 && aceCount > 0) {
            // Treat ACE as 1 instead of 11
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }
}
