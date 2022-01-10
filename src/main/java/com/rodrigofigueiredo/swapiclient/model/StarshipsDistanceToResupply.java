package com.rodrigofigueiredo.swapiclient.model;

public class StarshipsDistanceToResupply implements Comparable {
    private String starshipName;
    private Long necessaryStops;
    private Long passengersLong;
    private String consumables;
    private String passengers;
    private String mglt;

    public String getConsumables() {
        return consumables;
    }

    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getMglt() {
        return mglt;
    }

    public void setMglt(String mglt) {
        this.mglt = mglt;
    }

    public Long getPassengersLong() {
        return passengersLong;
    }

    public void setPassengersLong(Long passengersLong) {
        this.passengersLong = passengersLong;
    }

    public StarshipsDistanceToResupply() {
    }

    public String getStarshipName() {
        return starshipName;
    }

    public void setStarshipName(String starshipName) {
        this.starshipName = starshipName;
    }

    public Long getNecessaryStops() {
        return necessaryStops;
    }

    public void setNecessaryStops(Long necessaryStops) {
        this.necessaryStops = necessaryStops;
    }

    @Override
    public int compareTo(Object o) {
        StarshipsDistanceToResupply object = (StarshipsDistanceToResupply) o;
        if(this.necessaryStops == null
                && object.necessaryStops == null) {
            return 0;
        } else if (this.necessaryStops == null) {
            return 1;
        } else if (object.necessaryStops == null) {
            return -1;
        } else {
            return this.necessaryStops.compareTo(object.necessaryStops);
        }
    }
}
