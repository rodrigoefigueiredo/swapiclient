package com.rodrigofigueiredo.swapiclient.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StarshipModel implements Comparable{
    private String name;
    private String model;
    private String manufacturer;
    private String cost_in_credits;
    private String length;
    private String max_atmosphering_speed;
    private String crew;
    private String passengers;
    private String cargo_capacity;
    private String consumables;
    private String hyperdrive_rating;
    private String MGLT;
    private String starship_class;
    ArrayList< Object > pilots = new ArrayList < Object > ();
    ArrayList < Object > films = new ArrayList < Object > ();
    private String created;
    private String edited;
    private String url;
    private Long daysToResupplyInStandardMGLT = new Long(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCost_in_credits() {
        return cost_in_credits;
    }

    public void setCost_in_credits(String cost_in_credits) {
        this.cost_in_credits = cost_in_credits;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getMax_atmosphering_speed() {
        return max_atmosphering_speed;
    }

    public void setMax_atmosphering_speed(String max_atmosphering_speed) {
        this.max_atmosphering_speed = max_atmosphering_speed;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getCargo_capacity() {
        return cargo_capacity;
    }

    public void setCargo_capacity(String cargo_capacity) {
        this.cargo_capacity = cargo_capacity;
    }

    public String getConsumables() {
        return consumables;
    }

    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }

    public String getHyperdrive_rating() {
        return hyperdrive_rating;
    }

    public void setHyperdrive_rating(String hyperdrive_rating) {
        this.hyperdrive_rating = hyperdrive_rating;
    }

    public String getMGLT() {
        return MGLT;
    }

    public void setMGLT(String MGLT) {
        this.MGLT = MGLT;
    }

    public String getStarship_class() {
        return starship_class;
    }

    public void setStarship_class(String starship_class) {
        this.starship_class = starship_class;
    }

    public ArrayList<Object> getPilots() {
        return pilots;
    }

    public void setPilots(ArrayList<Object> pilots) {
        this.pilots = pilots;
    }

    public ArrayList<Object> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Object> films) {
        this.films = films;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getDaysToResupplyInStandardMGLT() {
        return daysToResupplyInStandardMGLT;
    }

    public void setDaysToResupplyInStandardMGLT(Long daysToResupplyInStandardMGLT) {
        this.daysToResupplyInStandardMGLT = daysToResupplyInStandardMGLT;
    }

    public StarshipModel(String name, String model, String manufacturer, String cost_in_credits, String length, String max_atmosphering_speed, String crew, String passengers, String cargo_capacity, String consumables, String hyperdrive_rating, String MGLT, String starship_class, ArrayList<Object> pilots, ArrayList<Object> films, String created, String edited, String url) {
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.cost_in_credits = cost_in_credits;
        this.length = length;
        this.max_atmosphering_speed = max_atmosphering_speed;
        this.crew = crew;
        this.passengers = passengers;
        this.cargo_capacity = cargo_capacity;
        this.consumables = consumables;
        this.hyperdrive_rating = hyperdrive_rating;
        this.MGLT = MGLT;
        this.starship_class = starship_class;
        this.pilots = pilots;
        this.films = films;
        this.created = created;
        this.edited = edited;
        this.url = url;
    }

    public StarshipModel() {
    }

    public void calculateDaysToRessuplyInStandardMGLT() {
        LocalDateTime now = LocalDateTime.now();
        String[] consumablesSplit = this.getConsumables().split(" ");
        if (consumablesSplit.length > 1) {
            if ( consumablesSplit[1].indexOf("year") == 0) {
                now = LocalDateTime.now().plusYears(Long.parseLong(consumablesSplit[0]));
            } else if (consumablesSplit[1].indexOf("month") == 0) {
                now = LocalDateTime.now().plusMonths(Long.parseLong(consumablesSplit[0]));
            } else if (consumablesSplit[1].indexOf("week") == 0) {
                now = LocalDateTime.now().plusDays(7 * Long.parseLong(consumablesSplit[0]));
            } else if (consumablesSplit[1].indexOf("day") == 0) {
                now = LocalDateTime.now().plusDays(Long.parseLong(consumablesSplit[0]));
            } else if (consumablesSplit[1].indexOf("hour") == 0) {
                now = LocalDateTime.now().plusHours(Long.parseLong(consumablesSplit[0]));
            }
            Duration maxTimeTraveledWithoutResupply = Duration.between(LocalDateTime.now(), now);
            boolean mgltIsNumeric = this.getMGLT().chars().allMatch(Character::isDigit);
            if (mgltIsNumeric) {
                Long maxMGLTTraveledInOneHour = Long.parseLong(this.getMGLT());
                this.setDaysToResupplyInStandardMGLT(maxMGLTTraveledInOneHour * 24 * maxTimeTraveledWithoutResupply.toDays());
            }
        }
    }

    public Long getPassengersLong() {
         return this.getPassengers() != null && this.getPassengers().replaceAll(",", "").chars().allMatch(Character::isDigit)
                ? Long.parseLong(this.getPassengers().replaceAll(",", "")) : new Long(0);
    }

    @Override
    public int compareTo(Object o) {
        StarshipModel object = (StarshipModel) o;
        if(this.daysToResupplyInStandardMGLT == null && object.daysToResupplyInStandardMGLT == null) {
            return 0;
        } else if(this.daysToResupplyInStandardMGLT == null) {
            return 1;
        } else if (object.daysToResupplyInStandardMGLT == null) {
            return -1;
        } else {
            return this.daysToResupplyInStandardMGLT.compareTo(((StarshipModel) o).getDaysToResupplyInStandardMGLT());
        }
    }
}
