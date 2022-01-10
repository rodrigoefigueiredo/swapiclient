package com.rodrigofigueiredo.swapiclient.model;

import java.util.List;

public class StarshipCalculateDistanceToResupplyData {
    private long distance;
    List<StarshipsDistanceToResupply> starshipsDistanceToResupplyList;

    public StarshipCalculateDistanceToResupplyData(long distance, List<StarshipsDistanceToResupply> starshipsDistanceToResupplyList) {
        this.distance = distance;
        this.starshipsDistanceToResupplyList = starshipsDistanceToResupplyList;
    }

    public StarshipCalculateDistanceToResupplyData() {
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public List<StarshipsDistanceToResupply> getStarshipsDistanceToResupplyList() {
        return starshipsDistanceToResupplyList;
    }

    public void setStarshipsDistanceToResupplyList(List<StarshipsDistanceToResupply> starshipsDistanceToResupplyList) {
        this.starshipsDistanceToResupplyList = starshipsDistanceToResupplyList;
    }
}

