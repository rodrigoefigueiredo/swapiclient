package com.rodrigofigueiredo.swapiclient.utils;

import com.rodrigofigueiredo.swapiclient.model.StarshipModel;

import java.util.Comparator;

public class StarshipComparator implements Comparator<StarshipModel> {
    @Override
    public int compare(StarshipModel o1, StarshipModel o2) {
        int comparation = o1.getPassengersLong().compareTo(o2.getPassengersLong());
        if (comparation == 0) {
            return o1.getDaysToResupplyInStandardMGLT().compareTo(o2.getDaysToResupplyInStandardMGLT());
        } else {
            return comparation;
        }
    }
}
