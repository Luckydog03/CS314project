package com.tco.misc;

import com.tco.requests.Place;

public final class DistanceCalculator {

    final static boolean useRandom = false;

    public DistanceCalculator() { }

    public static long calculate(
            Place from,
            Place to,
            double earthRadius)
    {
        // Coordinate From and To are equal so distance = 0
        if (from.latRadians() == 0.0 && from.lonRadians() == 0.0 &&
                to.latRadians() == 0.0 && to.lonRadians() == 0.0){
            return 0L;
        }
        // Coordinate from equals coordinate two so distance = 0
        if (from.latRadians() == to.latRadians() && from.lonRadians() == to.lonRadians()){
            return 0L;
        }
        double distanceposi = Math.abs((to.lonRadians() - from.lonRadians()));

        //Vincenty formula to calculate Distance Between Two Points on Earth
        double numerator1 = Math.pow((Math.cos(to.latRadians()) * Math.sin(distanceposi)), 2);
        double numerator2 = Math.pow(Math.cos(from.latRadians()) * Math.sin(to.latRadians()) - Math.sin(from.latRadians()) * Math.cos(to.latRadians()) * Math.cos(distanceposi), 2);
        double denominator = Math.sin(from.latRadians()) * Math.sin(to.latRadians()) + Math.cos(from.latRadians()) * Math.cos(to.latRadians()) * Math.cos(distanceposi);
        double sum = numerator1 + numerator2;
        double sqroot = Math.sqrt(Math.abs(sum));
        double result1 = Math.atan2(sqroot, denominator);
        return (long) Math.round(result1 * earthRadius);

    }
}
