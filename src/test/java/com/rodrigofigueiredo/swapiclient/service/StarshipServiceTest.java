package com.rodrigofigueiredo.swapiclient.service;

import com.rodrigofigueiredo.swapiclient.model.StarshipCalculateDistanceToResupplyData;
import com.rodrigofigueiredo.swapiclient.model.StarshipModel;
import com.rodrigofigueiredo.swapiclient.model.StarshipsDistanceToResupply;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StarshipServiceTest {

    @Autowired
    private StarshipService starshipService;

    @Test
    public void getAllStarshipsTest() throws ParseException {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://swapi.dev/api")
                .defaultHeader(HttpHeaders.USER_AGENT)
                .build();
        Mono<String> mono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/starships").build())
                .retrieve()
                .bodyToMono(String.class);
        String retorno = mono.block();
        assertNotNull(retorno);
        List<StarshipModel> allStarships = starshipService.getAllStarships();
        assertEquals(36, allStarships.size());
        assertNotNull(allStarships);
    }

    @Test
    public void calculateNecessaryStopsForDistanceTest() throws Exception {
        Long distance = new Long(1000000L);
        StarshipCalculateDistanceToResupplyData objectReturn = starshipService.calculateNecessaryStopsForDistance(starshipService.getAllStarships(), distance);
        assertEquals(11,objectReturn.getStarshipsDistanceToResupplyList()
                .stream()
                .filter(starshipsDistanceToResupply ->
                        starshipsDistanceToResupply.getStarshipName().equals("Rebel transport"))
                .findFirst().get().getNecessaryStops().longValue());
        assertEquals(9,objectReturn.getStarshipsDistanceToResupplyList()
                .stream()
                .filter(starshipsDistanceToResupply ->
                        starshipsDistanceToResupply.getStarshipName().equals("Millennium Falcon"))
                .findFirst().get().getNecessaryStops().longValue());
        assertEquals(49,objectReturn.getStarshipsDistanceToResupplyList()
                .stream()
                .filter(starshipsDistanceToResupply ->
                        starshipsDistanceToResupply.getStarshipName().equals("A-wing"))
                .findFirst().get().getNecessaryStops().longValue());
    }

    @Test
    public void getBetterStarshipToPassengersAndDistanceTest() throws Exception {
        StarshipsDistanceToResupply objectReturn = starshipService.getBetterStarshipToPassengersAndDistance(6L, 1080000L);
        assertEquals("Millennium Falcon", objectReturn.getStarshipName());
        objectReturn = starshipService.getBetterStarshipToPassengersAndDistance(7L, 1080000L);
        assertEquals("Imperial shuttle", objectReturn.getStarshipName());
        objectReturn = starshipService.getBetterStarshipToPassengersAndDistance(30L, 1000000000L);
        assertEquals("EF76 Nebulon-B escort frigate", objectReturn.getStarshipName());
        objectReturn = starshipService.getBetterStarshipToPassengersAndDistance(76L, 1000000000L);
        assertEquals("Rebel transport", objectReturn.getStarshipName());
    }


}