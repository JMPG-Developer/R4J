package no.stelar7.api.l4j8.tests.league;

import no.stelar7.api.l4j8.basic.constants.api.Platform;
import no.stelar7.api.l4j8.basic.constants.types.*;
import no.stelar7.api.l4j8.basic.utils.LazyList;
import no.stelar7.api.l4j8.impl.L4J8;
import no.stelar7.api.l4j8.impl.builders.league.LeagueBuilder;
import no.stelar7.api.l4j8.impl.builders.spectator.SpectatorBuilder;
import no.stelar7.api.l4j8.impl.builders.summoner.SummonerBuilder;
import no.stelar7.api.l4j8.pojo.league.*;
import no.stelar7.api.l4j8.pojo.summoner.Summoner;
import no.stelar7.api.l4j8.tests.SecretFile;
import org.junit.Test;

import java.util.List;

public class LeagueTest
{
    final L4J8 l4j8 = new L4J8(SecretFile.CREDS);
    LeagueBuilder lb = new LeagueBuilder().withPlatform(Platform.EUW1);
    
    @Test
    public void testMasterLeague()
    {
        LeagueList data = lb.withQueue(GameQueueType.RANKED_SOLO_5X5).getMasterLeague();
        assert data != null;
    }
    
    @Test
    public void testChallengerLeague()
    {
        LeagueList data = lb.withQueue(GameQueueType.RANKED_FLEX_SR).getChallengerLeague();
        assert data != null;
    }
    
    
    @Test
    public void testLeagueEntry()
    {
        String            id   = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        Summoner          s    = new SummonerBuilder().withPlatform(Platform.EUW1).withName(id).get();
        List<LeagueEntry> data = lb.withSummonerId(s.getSummonerId()).getLeagueEntries();
        assert data != null;
    }
    
    @Test
    public void testPositionalRanks()
    {
        LazyList<LeagueEntry> posLazy = l4j8.getLeagueAPI().getLeagueByTierDivisionLazy(Platform.NA1, GameQueueType.RANKED_SOLO_5X5, TierDivisionType.IRON_I);
        posLazy.loadFully();
        System.out.println();
    }
    
    @Test
    public void testPositionalRanksTFT()
    {
        LazyList<LeagueEntry> posLazy = l4j8.getLeagueAPI().getLeagueByTierDivisionLazy(Platform.OC1, GameQueueType.TEAMFIGHT_TACTICS_RANKED, TierDivisionType.IRON_IV);
        posLazy.loadFully();
        System.out.println();
    }
}
