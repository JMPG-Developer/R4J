package no.stelar7.api.r4j.pojo.val.matchlist;

import java.util.Objects;

public class MatchListMatch
{
    private String matchId;
    private Long   gameStartTime;
    private String teamId;
    
    public String getMatchId()
    {
        return matchId;
    }
    
    public Long getGameStartTime()
    {
        return gameStartTime;
    }
    
    public String getTeamId()
    {
        return teamId;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        MatchListMatch match = (MatchListMatch) o;
        return Objects.equals(matchId, match.matchId) &&
               Objects.equals(gameStartTime, match.gameStartTime) &&
               Objects.equals(teamId, match.teamId);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(matchId, gameStartTime, teamId);
    }
    
    @Override
    public String toString()
    {
        return "Match{" +
               "matchId='" + matchId + '\'' +
               ", gameStartTime=" + gameStartTime +
               ", teamId='" + teamId + '\'' +
               '}';
    }
}
