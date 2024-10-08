package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.lang.Math.toRadians;

public class TestTourRequest {
    TourRequest request;
    Places places;
    Double earthRadius = 3959.0;
    
    @BeforeEach
    public void beforeEach(){
        places = new Places();
        earthRadius = 3959.0;
    }

    @Test
    @DisplayName("Bigg52 tourrequest Test")
    public void testTourRequest(){
        Place Mount = new Place("27.98791","86.92529");
        Place Death = new Place("36.2466","-116.81700");
        Place Kola = new Place("69.39593","30.60886");
        Place Burj = new Place("25.19720","55.27420");
        places.add(Mount);
        places.add(Death);
        places.add(Kola);
        places.add(Burj);

        request = new TourRequest(places, 3959.0);

        assertEquals(4, request.places().size());
        assertEquals(3959.0, request.earthRadius());
    }
    @Test
    @DisplayName("AGIndigo8 test")
    public void aGIndigo8test(){
        Place North1 = new Place("0.0","90.0");
        Place North2 = new Place("10.0","90.0");
        Place North3 = new Place("20.0","90.0");
        Place North4 = new Place("-10.0","90.0");
        Place North5 = new Place("-20.0","90.0");
        places.add(North1);
        places.add(North2);
        places.add(North3);
        places.add(North4);
        places.add(North5);

        request = new TourRequest(places, 3959.0);

        assertEquals(5, request.places().size());
        assertEquals(3959.0, request.earthRadius());
    }
    @Test
    @DisplayName("Luckydog 1 test")
    public void oneRequesttest(){
        Place one = new Place("0.0", "1.0");

        places.add(one);

        request = new TourRequest(places, 3959.0);

        assertEquals(1, request.places().size());
        assertEquals(3959.0, request.earthRadius());
    }

    @Test
    @DisplayName("joelpr02: olsen981")
    public void olsenTest(){
        
        Place Manhattan = new Place("40.776676","-73.971321");
        Place Paris = new Place("48.864716","2.349014");
        Place London = new Place("51.509865","-0.118092");
        Place Orlando = new Place("-28.538336","-81.379234");
        Place Tokyo = new Place("35.652832","139.839478");
        Place Helsinki = new Place("60.192059", "24.945831");
        Place MexicoCity = new Place("19.432608","-99.133209");
        Place Berlin = new Place("52.520008", "13.404954");
        Place Santiago = new Place("-33.447487", "-70.673676");
        Place Beijing = new Place("39.916668","116.383331");
        Place Oslo = new Place("59.911491", "10.757933");
        Place Sydney = new Place("-33.865143", "151.209900");
        Place CapeTown = new Place("-33.918861","18.423300");

        places.add(Manhattan);
        places.add(Paris);
        places.add(London);
        places.add(Orlando);
        places.add(Tokyo);
        places.add(Helsinki);
        places.add(MexicoCity);
        places.add(Berlin);
        places.add(Santiago);
        places.add(Beijing);
        places.add(Oslo);
        places.add(Sydney);
        places.add(CapeTown);

        request = new TourRequest(places, 3959.0);

        assertEquals(13, request.places().size());
        assertEquals(3959.0, request.earthRadius());
    }

}
