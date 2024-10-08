package com.tco.requests;

import static java.lang.Double.parseDouble;
import static java.lang.Math.toRadians;

import com.tco.misc.GeographicCoordinate;
import java.util.HashMap;

public class Place extends HashMap<String,String> implements GeographicCoordinate {
  //for testing purposes

  public Place(){}

  public Place(String lat, String lon){
    this.put("latitude",lat);
    this.put("longitude",lon);
  }
  
  //implements the interface
  public Double latRadians() {
    return toRadians(parseDouble(this.get("latitude")));
  }
  public Double lonRadians(){
    return toRadians(parseDouble(this.get("longitude")));
  }
  public String id(){
    return this.get("id");
  }

}