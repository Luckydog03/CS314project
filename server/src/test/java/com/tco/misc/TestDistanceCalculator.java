package com.tco.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static java.lang.Math.toRadians;
import static com.tco.misc.DistanceCalculator.calculate;
import static com.tco.misc.DistanceCalculator.useRandom;
import com.tco.requests.Place;

public class TestDistanceCalculator {

    final Place origin = new Place("0.", "0.");
    final static long small = 1L;
    final static long piSmall = Math.round(Math.PI * small);
    final static long piSmallHalf = Math.round(Math.PI / 2.0 * small);

    final static long big = 1000000000000L;
    final static long piBig = Math.round(Math.PI * big);
    final static long piBigHalf = Math.round(Math.PI / 2.0 * big);

    @Test
    @DisplayName("Bigg52: distance to self should equal zero.")
    public void testDistanceToSelf(){

        assertEquals( 0L, calculate(origin, origin, small ));
        assertEquals( 0L, calculate(origin, origin, big ));

    }

    //test same coordinates of two places
    final Place thing1 = new Place("1.0", "1.0");
    final Place thing2 = new Place("1.0", "1.0");
    double earthRadius = 6371.0;
    @Test
    @DisplayName("joel02: same")
    public void testSame(){
        assertEquals( 0L, calculate(thing1, thing2, earthRadius));

    }

    //Test cases for distances of two places from "agaribay" json file
    final Place ocean = new Place("24.0", "-60.0");
    final Place ocean1 = new Place("24.0", "-60.0");
    final Place ocean2 = new Place("24.0", "-60.0");
    final Place ocean3 = new Place("24.0", "-60.0");
    double earthRadius1 = 6371.0;

    @Test
    @DisplayName("Benzo79: Test distance from ocean to ocean with earthRadius = 6371.0")
    public void testDistanceFromOceanToOcean1(){
        assertEquals( 0L, calculate(ocean, ocean1, earthRadius1));

    }

    @Test
    @DisplayName("Benzo79: Test distance from ocean to ocean with earthRadius = 6371.0")
    public void testDistanceFromOcean1ToOcean2(){
        assertEquals( 0L, calculate(ocean1, ocean2, earthRadius1));

    }

    // Test cases for distances of two places from "alissaam" json file

    final Place novaSouth = new Place("51.5","-0.14");
    final Place victoriaPalace = new Place("51.5","-0.14");
    final Place chesterSquare = new Place("51.5","-0.15");
    final Place saintMarks = new Place("51.48","-0.11");
    double earthRadius2 =3440064.0;


    @Test
    @DisplayName("Benzo79: Test distance from khamisBoys to vpsHospital with earthRadius = 6371.0")
    public void testDistanceFromNovaSouthToVictoriaPalace() {
        assertEquals(0L, calculate(novaSouth, victoriaPalace, earthRadius2));
    }

    @Test
    @DisplayName("Benzo79: Test distance from vpsHospital to theIsLandInaLake with earthRadius = 6371.0")
    public void testDistanceFromVictoriaPalaceToChesterSquare(){
        assertEquals(374L, calculate(victoriaPalace, chesterSquare, earthRadius2));
    }


    @Test
    @DisplayName("Benzo79: Test distance from vpsHospital to theIsLandInaLake with earthRadius = 6371.0")
    public void testDistanceFromChesterSquareToSaintMarks(){
        assertEquals(1918L, calculate(chesterSquare, saintMarks, earthRadius2));
    }


    @Test
    @DisplayName("Benzo79: Test distance from vpsHospital to theIsLandInaLake with earthRadius = 6371.0")
    public void testDistanceFromSaintMarksToNovaSouth(){
        assertEquals(1643L, calculate(saintMarks, novaSouth, earthRadius2));
    }


    // Test cases for distances of two places from "anddrewjj" json file

    final Place place1 = new Place("0.0","0.0");
    final Place place2 = new Place("89.0","90.0");
    final Place place3 = new Place("90.0","90.0");
    final Place place4 = new Place("90.0","90.0");
    double earthRadius3 = 6371.0;

    @Test
    @DisplayName("Benzo79: Test distance from place1 to place2 with earthRadius = 6371.0")
    public void testDistanceFromPlace1ToPlace2() {
        assertEquals(10008L, calculate(place1, place2, earthRadius3));
    }


    @Test
    @DisplayName("Benzo79: Test distance from place2 to place3 with earthRadius = 6371.0")
    public void testDistanceFromPlace2ToPlace3(){
        assertEquals(111L, calculate(place2, place3, earthRadius3));
    }


    @Test
    @DisplayName("Benzo79: Test distance from place3 to place4 with earthRadius = 6371.0")
    public void testDistanceFromPlace3ToPlace4(){
        assertEquals(0L, calculate(place3, place4, earthRadius3));
    }


    @Test
    @DisplayName("Benzo79: Test distance from place3 to place4 with earthRadius = 6371.0")
    public void testDistanceFromPlace4ToPlace1(){
        assertEquals(10008L, calculate(place4, place1, earthRadius3));
    }

}

