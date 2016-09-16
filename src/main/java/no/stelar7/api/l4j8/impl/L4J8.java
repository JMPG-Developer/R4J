package no.stelar7.api.l4j8.impl;

import no.stelar7.api.l4j8.basic.*;
import no.stelar7.api.l4j8.basic.DataCall.DataCallBuilder;
import no.stelar7.api.l4j8.basic.constants.api.*;
import no.stelar7.api.l4j8.pojo.summoner.Summoner;
import no.stelar7.api.l4j8.pojo.summoner.masteries.MasteryPages;
import no.stelar7.api.l4j8.pojo.summoner.runes.RunePages;

import java.util.*;

/**
 * The base class for calling anything from this api wrapper
 */
public class L4J8
{

    /**
     * Constructor for the L4J8 class.
     *
     * @param creds the API credentials used for the API (your token)
     */
    public L4J8(final APICredentials creds)
    {
        DataCallBuilder.setCredentials(creds);
    }

    /**
     * The response object contains the MasteryPages objects mapped by their userId.
     *
     * @param server  the region to execute against
     * @param initial list of summonerIds associated with summoners to retrieve.
     * @return a map of the userId to an Optional MasteryPages
     */
    public Map<Long, Optional<MasteryPages>> getMasteries(final Server server, final List<Long> initial)
    {
        final Map<Long, Optional<MasteryPages>> finalResult = new HashMap<>();

        List<Long> summonerIds = new ArrayList<>(initial);

        if (summonerIds.size() > Constants.SUMMONER_ENDPOINT_MAX_IDS)
        {
            // Recursively call this method, so it can be called with an arbitrary amount of userids
            final List<Long> subList = summonerIds.subList(Constants.SUMMONER_ENDPOINT_MAX_IDS, summonerIds.size());
            finalResult.putAll(this.getMasteries(server, subList));
            summonerIds = summonerIds.subList(0, Constants.SUMMONER_ENDPOINT_MAX_IDS);
        }

        // Build the query
        final DataCallBuilder builder = DataCall.builder();
        builder.withServer(server);
        builder.withRegion(server);
        builder.withEndpoint(URLEndpoint.SUMMONER_MASTERIES_BY_ID);
        summonerIds.forEach(sum -> builder.withURLData(Constants.SUMMONER_ID_PLACEHOLDER, String.valueOf(sum)));

        // Get the query result
        final Object callResult = builder.build();

        // Handle 404
        if ((callResult instanceof Optional) && !((Optional<?>) callResult).isPresent())
        {
            summonerIds.forEach(id -> finalResult.putIfAbsent(id, Optional.empty()));
            return finalResult;
        }

        final Map<Long, MasteryPages> castResult = (Map<Long, MasteryPages>) callResult;

        // Map result to Optional<Summoner>, and add missing names
        castResult.forEach((key, value) -> finalResult.put(key, Optional.of(value)));
        summonerIds.forEach(name -> finalResult.putIfAbsent(name, Optional.empty()));

        return finalResult;
    }

    /**
     * The response object contains the RunePages objects mapped by their userId.
     *
     * @param server  the region to execute against
     * @param initial list of summonerIds associated with summoners to retrieve.
     * @return a map of the userId to an Optional RunePages
     */
    public Map<Long, Optional<RunePages>> getRunes(final Server server, final List<Long> initial)
    {
        final Map<Long, Optional<RunePages>> finalResult = new HashMap<>();

        List<Long> summonerIds = new ArrayList<>(initial);

        if (summonerIds.size() > Constants.SUMMONER_ENDPOINT_MAX_IDS)
        {
            // Recursively call this method, so it can be called with an arbitrary amount of userids
            final List<Long> subList = summonerIds.subList(Constants.SUMMONER_ENDPOINT_MAX_IDS, summonerIds.size());
            finalResult.putAll(this.getRunes(server, subList));
            summonerIds = summonerIds.subList(0, Constants.SUMMONER_ENDPOINT_MAX_IDS);
        }

        // Build the query
        final DataCallBuilder builder = DataCall.builder();
        builder.withServer(server);
        builder.withRegion(server);
        builder.withEndpoint(URLEndpoint.SUMMONER_RUNES_BY_ID);
        summonerIds.forEach(sum -> builder.withURLData(Constants.SUMMONER_ID_PLACEHOLDER, String.valueOf(sum)));

        // Get the query result
        final Object callResult = builder.build();

        // Handle 404
        if ((callResult instanceof Optional) && !((Optional<?>) callResult).isPresent())
        {
            summonerIds.forEach(id -> finalResult.putIfAbsent(id, Optional.empty()));
            return finalResult;
        }

        final Map<Long, RunePages> castResult = (Map<Long, RunePages>) callResult;

        // Map result to Optional<Summoner>, and add missing names
        castResult.forEach((key, value) -> finalResult.put(key, Optional.of(value)));
        summonerIds.forEach(name -> finalResult.putIfAbsent(name, Optional.empty()));

        return finalResult;
    }

    /**
     * The response object contains the summoner objects mapped by their user id.
     *
     * @param server  the region to execute against
     * @param userIds list of summonerIds associated with summoners to retrieve.
     * @return a map of the userId to an Optional Summoner
     */
    public Map<Long, Optional<Summoner>> getSummonerById(final Server server, final List<Long> userIds)
    {
        final Map<Long, Optional<Summoner>> finalResult = new HashMap<>();

        List<Long> ids = new ArrayList<>(userIds);

        if (ids.size() > Constants.SUMMONER_ENDPOINT_MAX_IDS)
        {
            // Recursively call this method, so it can be called with an arbitrary amount of userids
            final List<Long> subList = ids.subList(Constants.SUMMONER_ENDPOINT_MAX_IDS, ids.size());
            finalResult.putAll(this.getSummonerById(server, subList));
            ids = ids.subList(0, Constants.SUMMONER_ENDPOINT_MAX_IDS);
        }

        // Build the query
        final DataCallBuilder builder = DataCall.builder();
        builder.withServer(server);
        builder.withRegion(server);
        builder.withEndpoint(URLEndpoint.SUMMONER_BY_ID);
        ids.forEach(id -> builder.withURLData(Constants.SUMMONER_ID_PLACEHOLDER, String.valueOf(id)));

        // Get the query result
        final Object callResult = builder.build();

        // Handle 404
        if ((callResult instanceof Optional) && !((Optional<?>) callResult).isPresent())
        {
            ids.forEach(id -> finalResult.putIfAbsent(id, Optional.empty()));
            return finalResult;
        }

        final Map<Long, Summoner> castResult = (Map<Long, Summoner>) callResult;

        // Map result to Optional<Summoner>, and add missing names
        castResult.forEach((key, value) -> finalResult.put(key, Optional.of(value)));
        ids.forEach(id -> finalResult.putIfAbsent(id, Optional.empty()));

        return finalResult;
    }

    /**
     * The response object contains the summoner objects mapped by their username.
     *
     * @param server    the region to execute against
     * @param usernames list of summoner names or standardized summoner names associated with summoners to retrieve.
     * @return a map of the userId to an Optional Summoner
     */
    public Map<String, Optional<Summoner>> getSummonerByName(final Server server, final List<String> usernames)
    {
        final Map<String, Optional<Summoner>> finalResult = new HashMap<>();

        List<String> names = new ArrayList<>(usernames);

        if (names.size() > Constants.SUMMONER_ENDPOINT_MAX_IDS)
        {
            // Recursively call this method, so it can be called with an arbitrary amount of userids
            final List<String> subList = names.subList(Constants.SUMMONER_ENDPOINT_MAX_IDS, names.size());
            finalResult.putAll(this.getSummonerByName(server, subList));
            names = names.subList(0, Constants.SUMMONER_ENDPOINT_MAX_IDS);
        }

        // Build the query
        final DataCallBuilder builder = DataCall.builder();
        builder.withServer(server);
        builder.withRegion(server);
        builder.withEndpoint(URLEndpoint.SUMMONER_BY_NAME);
        names.forEach(name -> builder.withURLData(Constants.SUMMONER_NAME_PLACEHOLDER, Utils.prepareForURL(name)));

        // Get the query result
        final Object callResult = builder.build();

        // Handle 404
        if ((callResult instanceof Optional) && !((Optional<?>) callResult).isPresent())
        {
            names.forEach(name -> finalResult.putIfAbsent(name, Optional.empty()));
            return finalResult;
        }

        final Map<String, Summoner> castResult = (Map<String, Summoner>) callResult;

        // Map result to Optional<Summoner>, and add missing names
        castResult.forEach((key, value) -> finalResult.put(key, Optional.of(value)));
        names.forEach(name -> finalResult.putIfAbsent(name, Optional.empty()));

        return finalResult;
    }

    /**
     * This method abstracts out the call to getSummonerByName, as there is NO good reason to call this.
     *
     * @param values a list of ids to get the name for
     * @return a mapping of the id to the name
     */
    public Map<Long, Optional<String>> getSummonerNames(final Server server, final List<Long> values)
    {
        final Map<Long, Optional<Summoner>> summoners = this.getSummonerById(server, values);
        final Map<Long, Optional<String>>   result    = new HashMap<>();

        summoners.forEach((k, v) -> result.put(k, v.map(Summoner::getName)));

        return result;
    }

    public TournamentAPI getTournamentAPI()
    {
        return TournamentAPI.getInstance();
    }

}
