package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.lang.Math.toRadians;

public class TestDistancesRequest {

    DistancesRequest request;
    Places places;
    Distances distances;

    static final long bigRadius = 1000000L;
    static final long bigPi = Math.round(Math.PI * bigRadius);
    static final long bigPiHalf = Math.round(Math.PI / 2.0 * bigRadius);

    @BeforeEach
    public void beforeEach(){
        places = new Places();
        request = new DistancesRequest((double) bigRadius, places);
    }

    @Test
    @DisplayName("luckydog: Empty Places")
    public void testEmptyPlaces(){
        request.buildResponse();
        distances = request.distances();
        //check distances
        assertEquals(0, distances.size());
        assertEquals(0L, distances.total());
        //check original values
        assertEquals(0, request.places().size());
        assertEquals(bigRadius, request.earthRadius());
    }

    @Test
    @DisplayName("luckydog: 1 place")
    public void testOnePlace(){
        Place One = new Place("1.0","1.0");
        places.add(One);

        request = new DistancesRequest(3959.0, places);
        request.buildResponse();

        assertEquals(1, request.distances().size());
        assertEquals(1, request.places().size());
        assertEquals(0L, request.distances().get(0));

    }

    @Test
    @DisplayName("luckydog: Bryson's Test")
    public void brysonCookTest(){
        places = new Places();
        Place Mount = new Place("27.98791","86.92529");
        Place Death = new Place("36.2466","-116.81700");
        Place Kola = new Place("69.39593","30.60886");
        Place Burj = new Place("25.19720","55.27420");
        places.add(Mount);
        places.add(Death);
        places.add(Kola);
        places.add(Burj);

        request = new DistancesRequest(3959.0, places);
        request.buildResponse();

        assertEquals(4, request.places().size());
        assertEquals(3959.0, request.earthRadius());
        assertEquals(4, request.distances().size());
        assertEquals(7738L, request.distances().get(0));
        assertEquals(3216L, request.distances().get(2));
        assertEquals(17867L, request.distances().total());
    }



    @Test
    @DisplayName("luckydog: Luckydog's Test")
    public void luckydogTest(){
        places = new Places();
        Place Dog = new Place("27.98791","86.92529");
        Place Cat = new Place("27.98791","86.92529");

        places.add(Dog);
        places.add(Cat);

        request = new DistancesRequest(3959.0, places);
        request.buildResponse();

        assertEquals(2, request.places().size());
        assertEquals(3959.0, request.earthRadius());
        assertEquals(2, request.distances().size());
        assertEquals(0L, request.distances().get(0));
        assertEquals(0L, request.distances().total());
    }

    @Test
    @DisplayName("joelpr02: mikylab test")
    public void mikylabTest(){
        
        places = new Places();
        Place MtEverest = new Place("27.9", "86.9");
        Place Aconcagua = new Place("-32.6", "-70.0");
        Place Denali = new Place("63.0", "-151.006989");
        Place Kilimanjaro = new Place("-3.0", "37.3");
        Place MtElbrus = new Place("43.3", "42.4");
        Place PuncakJaya = new Place("-3.4", "137.8");
        Place Vinson = new Place("-78.6", "-85.1");

        places.add(MtEverest);
        places.add(Aconcagua);
        places.add(Denali);
        places.add(Kilimanjaro);
        places.add(MtElbrus);
        places.add(PuncakJaya);
        places.add(Vinson);

        request = new DistancesRequest(3959.0, places);
        request.buildResponse();

        assertEquals(7, request.places().size());
        assertEquals(3959.0, request.earthRadius());
        assertEquals(7, request.distances().size());
        assertEquals(52583L, request.distances().total());

    }

    @Test
    @DisplayName("Luckydog: 100 place test")
    public void hundredPlaceTest(){
        places = new Places();
        for(double i = 0.0; i < 100.0; i++){
            String latLongVal = Double.toString(i);
            Place temp = new Place(latLongVal, latLongVal);
            places.add(temp);
        }
        
        request = new DistancesRequest(3959.0, places);
        request.buildResponse();

        assertEquals(100, request.places().size());
        assertEquals(100, request.distances().size());
        assertEquals(14305L, request.distances().total());

    }

}