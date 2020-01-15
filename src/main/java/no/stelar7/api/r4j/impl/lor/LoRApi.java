package no.stelar7.api.r4j.impl.lor;

import com.google.gson.JsonObject;
import no.stelar7.api.r4j.basic.calling.DataCallBuilder;
import no.stelar7.api.r4j.basic.constants.api.*;
import no.stelar7.api.r4j.pojo.lor.LoRConnection;
import no.stelar7.api.r4j.pojo.lor.card.LoRDeck;
import no.stelar7.api.r4j.pojo.lor.expedition.LoRExpeditionInfo;
import no.stelar7.api.r4j.pojo.lor.game.*;

public class LoRApi
{
    /**
     * Gets the players active deck, this deck is static and does not change during a game.
     * <p>
     * Returns null of not in a game
     */
    public static LoRDeck getActiveDeck()
    {
        JsonObject obj = (JsonObject) new DataCallBuilder()
                .withLimiters(false)
                .withProxy(LoRConnection.getConnectionString() + Constants.GSVR)
                .withEndpoint(URLEndpoint.LOR_STATIC_ACTIVE_DECK)
                .build();
        
        try
        {
            return LoRDeckCode.decode(obj.get("DeckCode").getAsString());
        } catch (Exception e)
        {
            return null;
        }
    }
    
    
    /**
     * Gets the result from the last played game
     */
    public static LoRGameResult getLastGameResult()
    {
        return (LoRGameResult) new DataCallBuilder()
                .withLimiters(false)
                .withProxy(LoRConnection.getConnectionString() + Constants.GSVR)
                .withEndpoint(URLEndpoint.LOR_GAME_RESULT)
                .build();
    }
    
    public static LoRGameInfo getCardPositions()
    {
        return (LoRGameInfo) new DataCallBuilder()
                .withLimiters(false)
                .withProxy(LoRConnection.getConnectionString() + Constants.GSVR)
                .withEndpoint(URLEndpoint.LOR_CARD_POSITIONS)
                .build();
    }
    
    public static LoRExpeditionInfo getExpeditionsState()
    {
        return (LoRExpeditionInfo) new DataCallBuilder()
                .withLimiters(false)
                .withProxy(LoRConnection.getConnectionString() + Constants.GSVR)
                .withEndpoint(URLEndpoint.LOR_EXPEDITIONS_STATE)
                .build();
    }
    
}