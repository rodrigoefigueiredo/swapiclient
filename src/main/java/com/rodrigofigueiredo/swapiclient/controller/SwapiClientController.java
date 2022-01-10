package com.rodrigofigueiredo.swapiclient.controller;

import com.rodrigofigueiredo.swapiclient.model.StarshipCalculateDistanceToResupplyData;
import com.rodrigofigueiredo.swapiclient.model.StarshipModel;
import com.rodrigofigueiredo.swapiclient.model.StarshipsDistanceToResupply;
import com.rodrigofigueiredo.swapiclient.service.StarshipService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class SwapiClientController {

    @Autowired
    private StarshipService starshipService;
    @GetMapping("api/v1/spaceships/all/necessaryStops")
    public JSONObject necesaryStopsForAllStarships(@RequestParam("distance") long distance) throws Exception {
        List<StarshipModel> allStarships = starshipService.getAllStarships();
        StarshipCalculateDistanceToResupplyData objectReturn = starshipService.calculateNecessaryStopsForDistance(allStarships, distance);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("distance", objectReturn.getDistance());
        JSONArray jsonArray = new JSONArray();
        objectReturn.getStarshipsDistanceToResupplyList().forEach(starshipsDistanceToResupply -> {
            JSONObject jsonStarshipDistanceToResupply = new JSONObject();
            jsonStarshipDistanceToResupply.put("starshipName", starshipsDistanceToResupply.getStarshipName());
            jsonStarshipDistanceToResupply.put("necessaryStops", starshipsDistanceToResupply.getNecessaryStops());
            jsonArray.add(jsonStarshipDistanceToResupply);
        });
        jsonObject.put("starshipsDistanceToResupplyList", jsonArray);
        return jsonObject;
    }

    @GetMapping("api/v1/spaceships/all/betterStarship")
    public JSONObject betterStarshipToPassengersAndDistance(@RequestParam("passengers") long passengers, @RequestParam("distance") long distance) throws Exception {
        StarshipsDistanceToResupply objectReturn = starshipService.getBetterStarshipToPassengersAndDistance(passengers, distance);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("starshipName", objectReturn.getStarshipName());
        return jsonObject;
    }
}
