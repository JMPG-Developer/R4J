package no.stelar7.api.l4j8.pojo.stats;

import no.stelar7.api.l4j8.basic.constants.Champion;

public class ChampionStats
{
    private Long            id;
    private AggregatedStats stats;

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (this.getClass() != obj.getClass())
        {
            return false;
        }
        final ChampionStats other = (ChampionStats) obj;
        if (this.id == null)
        {
            if (other.id != null)
            {
                return false;
            }
        } else if (!this.id.equals(other.id))
        {
            return false;
        }
        if (this.stats == null)
        {
            if (other.stats != null)
            {
                return false;
            }
        } else if (!this.stats.equals(other.stats))
        {
            return false;
        }
        return true;
    }

    /**
     * Returns the Champion representing this id
     *
     * @return the champion
     */
    public Champion getChampion()
    {
        return Champion.getFromId(this.id);
    }

    /**
     * Champion ID. Note that champion ID 0 represents the combined stats for all champions. For static information correlating to champion IDs, please refer to the LoL Static Data API.
     *
     * @return the id
     */
    public Long getId()
    {
        return this.id;
    }

    /**
     * Aggregated stats associated with the champion.
     *
     * @return the stats
     */
    public AggregatedStats getStats()
    {
        return this.stats;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
        result = (prime * result) + ((this.stats == null) ? 0 : this.stats.hashCode());
        return result;
    }

}