package com.tco.requests;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tco.misc.DistanceCalculator;

public class DistancesRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    private Double earthRadius;
    private Places places;
    private Distances distances;

    @Override
    public void buildResponse() {
        this.distances = buildDistanceList();
        log.trace("distanceResponse -> {}", this);
    }
    private Distances buildDistanceList() {

        Distances distancesArray = new Distances();
        Long calculateDistance = 0L;
        Long calculateRoundTrip = 0L;

        if( places.size() < 1){
            return distancesArray;
        }
        Place currentPlace = places.get(0);
        for(int i = 1; i <= places.size(); i++){
            //calculates last distance between first and last element
            if(i == places.size()){
                calculateDistance = DistanceCalculator.calculate(currentPlace, places.get(0), earthRadius);
                distancesArray.add(calculateDistance);
                return distancesArray;
            }
            calculateDistance = DistanceCalculator.calculate(currentPlace, places.get(i), earthRadius);
            distancesArray.add(calculateDistance);
            currentPlace = places.get(i);
            
        }
        return distancesArray;
    } 

  /* The following methods exist only for testing purposes and are not used
  during normal execution, including the constructor. */

    public DistancesRequest(Double earthRadius, Places places) {
        super();
        this.requestType = "distances";
        this.earthRadius = earthRadius;
        this.places = places;
    }

    public Double earthRadius(){ return this.earthRadius;}

    public Places places(){return this.places;}

    public Distances distances(){return this.distances;}

    //=====================

}
