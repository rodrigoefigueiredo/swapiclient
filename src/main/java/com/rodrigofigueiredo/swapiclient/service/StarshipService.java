package com.rodrigofigueiredo.swapiclient.service;

import com.rodrigofigueiredo.swapiclient.model.StarshipCalculateDistanceToResupplyData;
import com.rodrigofigueiredo.swapiclient.model.StarshipModel;
import com.rodrigofigueiredo.swapiclient.model.StarshipsDistanceToResupply;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class StarshipService {

    @Autowired
    private WebClient webClient;

    public List<StarshipModel> getAllStarships() throws ParseException {
        List<StarshipModel> allStarships = new ArrayList<StarshipModel>();
        String nextPageUrl = "https://swapi.dev/api/starships";
        try {
            do {
                WebClient.ResponseSpec responseSpec = this.webClient
                        .method(HttpMethod.GET)
                        .uri(nextPageUrl)
                        .retrieve();
                String responseBody = responseSpec.bodyToMono(String.class).block();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = null;
                jsonObject = (JSONObject) jsonParser.parse(responseBody);
                nextPageUrl = jsonObject.get("next") != null ? jsonObject.get("next").toString() : null;
                JSONArray jsonArray = (JSONArray) jsonObject.get("results");
                jsonArray.forEach(element -> {
                    StarshipModel starshipModel = new StarshipModel();
                    starshipModel.setName(((JSONObject) element).get("name").toString());
                    starshipModel.setModel(((JSONObject) element).get("model").toString());
                    starshipModel.setManufacturer(((JSONObject) element).get("manufacturer").toString());
                    starshipModel.setCost_in_credits(((JSONObject) element).get("cost_in_credits").toString());
                    starshipModel.setLength(((JSONObject) element).get("length").toString());
                    starshipModel.setMax_atmosphering_speed(((JSONObject) element).get("max_atmosphering_speed").toString());
                    starshipModel.setCrew(((JSONObject) element).get("crew").toString());
                    starshipModel.setPassengers(((JSONObject) element).get("passengers").toString());
                    starshipModel.setCargo_capacity(((JSONObject) element).get("cargo_capacity").toString());
                    starshipModel.setConsumables(((JSONObject) element).get("consumables").toString());
                    starshipModel.setHyperdrive_rating(((JSONObject) element).get("hyperdrive_rating").toString());
                    starshipModel.setMGLT(((JSONObject) element).get("MGLT").toString());
                    starshipModel.setStarship_class(((JSONObject) element).get("starship_class").toString());
                    starshipModel.setPilots((JSONArray)((JSONObject) element).get("pilots"));
                    starshipModel.setFilms((JSONArray)((JSONObject) element).get("films"));
                    starshipModel.setCreated(((JSONObject) element).get("created").toString());
                    starshipModel.setEdited(((JSONObject) element).get("edited").toString());
                    starshipModel.setUrl(((JSONObject) element).get("url").toString());
                    allStarships.add(starshipModel);
                });
            } while(nextPageUrl != null);
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }
        return allStarships;


    }

    public StarshipCalculateDistanceToResupplyData calculateNecessaryStopsForDistance(List<StarshipModel> allStarships, Long distance) throws Exception {

        allStarships.forEach(starshipModel -> {
            starshipModel.calculateDaysToRessuplyInStandardMGLT();
        });
        Collections.sort(allStarships);
        List<StarshipsDistanceToResupply> distanceToResupply = new ArrayList<StarshipsDistanceToResupply>();
        allStarships.forEach(starshipModel -> {
            StarshipsDistanceToResupply starshipsDistanceToResupply = new StarshipsDistanceToResupply();
            starshipsDistanceToResupply.setStarshipName(starshipModel.getName());
            Long necessaryStops = starshipModel.getDaysToResupplyInStandardMGLT().longValue() > 0
                    ? (int) Math.abs(distance/starshipModel.getDaysToResupplyInStandardMGLT())
                    : 0L;
            starshipsDistanceToResupply.setNecessaryStops(necessaryStops);
            starshipsDistanceToResupply.setPassengersLong(starshipModel.getPassengersLong());
            distanceToResupply.add(starshipsDistanceToResupply);
            String[] consumablesSplit = starshipModel.getConsumables().split(" ");
            starshipsDistanceToResupply.setConsumables((consumablesSplit.length > 1) ? starshipModel.getConsumables() : null);

            starshipsDistanceToResupply.setMglt(starshipModel.getMGLT().chars().allMatch(Character::isDigit) ? starshipModel.getMGLT() : null);
            starshipsDistanceToResupply.setPassengers(starshipModel.getPassengers() != null
                        && starshipModel.getPassengers().replaceAll(",", "").chars().allMatch(Character::isDigit)
                    ? starshipModel.getPassengers() : null);
            starshipModel.getPassengers();
        });
        StarshipCalculateDistanceToResupplyData response = new StarshipCalculateDistanceToResupplyData();
        response.setDistance(distance);
        Collections.sort(distanceToResupply);
        response.setStarshipsDistanceToResupplyList(distanceToResupply);
        return response;
    }



    public StarshipsDistanceToResupply getBetterStarshipToPassengersAndDistance(Long passangers, Long distance) throws Exception {
        List<StarshipModel> allStarships = this.getAllStarships();
        StarshipCalculateDistanceToResupplyData starShipData = this.calculateNecessaryStopsForDistance(allStarships, distance);
        List<StarshipsDistanceToResupply> filtredList = new ArrayList<>();
        for (StarshipsDistanceToResupply s : starShipData.getStarshipsDistanceToResupplyList()) {
            if (s.getConsumables() != null && s.getPassengers() != null && s.getMglt() != null) {
                filtredList.add(s);
            }
        }
        Collections.sort(filtredList, new Comparator<StarshipsDistanceToResupply>() {
            @Override
            public int compare(StarshipsDistanceToResupply o1, StarshipsDistanceToResupply o2) {
                int comparation = o1.getPassengersLong().compareTo(o2.getPassengersLong());
                if (comparation == 0) {
                    return o1.getNecessaryStops().compareTo(o2.getNecessaryStops());
                } else {
                    return comparation;
                }
            }
        });
        Optional<StarshipsDistanceToResupply> result =
                filtredList.stream().filter(obj -> obj.getPassengersLong() > 0
                        && obj.getPassengersLong() >= passangers).findFirst();

        return result.get();
    }
}
