package no.stelar7.api.r4j.impl.val;

import no.stelar7.api.r4j.basic.calling.*;
import no.stelar7.api.r4j.basic.constants.api.*;
import no.stelar7.api.r4j.basic.constants.api.regions.ValorantShard;
import no.stelar7.api.r4j.basic.utils.Pair;
import no.stelar7.api.r4j.pojo.val.match.Match;
import no.stelar7.api.r4j.pojo.val.matchlist.*;

import java.util.*;

public class VALMatchAPI
{
    private static final VALMatchAPI INSTANCE = new VALMatchAPI();
    
    public static VALMatchAPI getInstance()
    {
        return VALMatchAPI.INSTANCE;
    }
    
    private VALMatchAPI()
    {
        // Hide public constructor
    }
    
    public List<MatchReference> getMatchList(ValorantShard server, String PUUID)
    {
        DataCallBuilder builder = new DataCallBuilder().withURLParameter(Constants.PUUID_ID_PLACEHOLDER, PUUID)
                                                       .withHeader(Constants.X_RIOT_TOKEN_HEADER_KEY, DataCall.getCredentials().getTFTAPIKey())
                                                       .withEndpoint(URLEndpoint.V1_VAL_MATCHLIST_BY_PUUID)
                                                       .withPlatform(server);
        
        Map<String, Object> data = new TreeMap<>();
        data.put("platform", server);
        data.put("puuid", PUUID);
        
        Optional<?> chl = DataCall.getCacheProvider().get(URLEndpoint.V1_VAL_MATCHLIST_BY_PUUID, data);
        if (chl.isPresent())
        {
            return ((MatchList) chl.get()).getHistory();
        }
        
        Object matchObj = builder.build();
        if (matchObj instanceof Pair)
        {
            return Collections.emptyList();
        }
    
        MatchList matchList = (MatchList) matchObj;
        
        data.put("value", matchList);
        DataCall.getCacheProvider().store(URLEndpoint.V1_VAL_MATCHLIST_BY_PUUID, data);
        
        return matchList.getHistory();
    }
    
    
    public Match getMatch(ValorantShard platform, String gameId)
    {
        DataCallBuilder builder = new DataCallBuilder().withURLParameter(Constants.MATCH_ID_PLACEHOLDER, gameId)
                                                       .withHeader(Constants.X_RIOT_TOKEN_HEADER_KEY, DataCall.getCredentials().getTFTAPIKey())
                                                       .withEndpoint(URLEndpoint.V1_VAL_MATCH_BY_ID)
                                                       .withPlatform(platform);
        
        Map<String, Object> data = new TreeMap<>();
        data.put("platform", platform);
        data.put("gameid", gameId);
        
        Optional<?> chl = DataCall.getCacheProvider().get(URLEndpoint.V1_VAL_MATCH_BY_ID, data);
        if (chl.isPresent())
        {
            return (Match) chl.get();
        }
        
        try
        {
            Match match = (Match) builder.build();
            
            data.put("value", match);
            DataCall.getCacheProvider().store(URLEndpoint.V1_VAL_MATCH_BY_ID, data);
            
            return match;
        } catch (ClassCastException e)
        {
            return null;
        }
    }
}
