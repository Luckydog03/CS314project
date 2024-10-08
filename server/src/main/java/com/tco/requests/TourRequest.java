package com.tco.requests;

import com.tco.misc.DistanceCalculator;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.tco.database.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TourRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(TourRequest.class);
    private Places places;
    private long sumShort = 0;
    private Double earthRadius;
    private Places TourArray;

    @Override
    public void buildResponse(){
        this.places = Tour();
        log.trace("TourReponse ->{}", this);
    }
    
    public TourRequest(Places places, Double earthRadius){
        super();
        this.requestType = "tour";
        this.earthRadius = earthRadius;
        this.places = places;
    }

    public Places Tour(){

        for(Place place: places){
            Place VisitedCity = place;
            
                for(Place notvisited: places){
                    Long shortestDist = 0L;
                    Long shortestDistLast = 0L;

                    shortestDist = DistanceCalculator.calculate(VisitedCity, notvisited, earthRadius);
                
                    if(shortestDist < shortestDistLast){
                        shortestDist = shortestDistLast;
                    }
                    
                    else if(shortestDist == 0L){
                        shortestDist = shortestDistLast;
                    }
                    TourArray.add(notvisited);
            }
            
            places.remove(VisitedCity);
        }
        return TourArray;
    }


    //Testing Purposes
    public Places places(){return this.places;}
    public Double earthRadius(){ return this.earthRadius;}
}
