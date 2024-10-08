package com.tco.database;

import com.tco.requests.Place;
import com.tco.requests.Places;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Select {

    private static final String COLUMNS = 
    " world.name as name, world.type as type, world.latitude as latitude, world.longitude as longitude, world.altitude as altitude, "
                    +"country.name as country, region.name as region, world.municipality as municipality, world.id as id, world.wikipedia_link as wikipedia_link,"
                    +"world.home_link as url, world.gps_code as gps_code, world.keywords as keywords, world.local_code as local_code, "
                    +"world.scheduled_service as scheduled_service ";
    
    static String match(String match, ArrayList<String> type, ArrayList<String> where, int limit) {
        if (limit == 0){
            return statement(match, type, where, "DISTINCT " + COLUMNS, "");

        }else {
            return statement(match, type, where, "DISTINCT " + COLUMNS, "LIMIT " + limit);
        }
    }



    static String found(String match, ArrayList<String> type, ArrayList<String> where) {
        return statement(match, type, where, "COUNT(*) AS count ", "");
    }

    private static String typeMatch(ArrayList<String> type){
        String typeStatement;
        if (type != null && !type.isEmpty()){
            typeStatement = " AND (";
            for (int i = 0; i < type.size()-1; i++) {
                typeStatement += " world.type LIKE \"%" + type.get(i) + "%\" OR";
            }
            typeStatement = typeStatement + " world.type LIKE \"%" + type.get(type.size()-1) + "%\" ) ";
        }else{
            typeStatement = " AND world.type LIKE \"%" + "" + "%\" ";
        }
         return typeStatement;
    }
 
    private static String whereMatch(ArrayList<String> where){
        String whereStatement;
        if (where != null && !where.isEmpty()){
            whereStatement = " AND (";
            for (int i = 0; i < where.size()-1; i++) {
                whereStatement += " country.name LIKE \"%" + where.get(i) + "%\" OR";
            }
            whereStatement = whereStatement + " country.name LIKE \"%" + where.get(where.size()-1) + "%\" ) ";
        }else{
            whereStatement = " AND country.name LIKE \"%" + "" + "%\" ";
        }
         return whereStatement;
    }
    static String statement(String match, ArrayList<String> type, ArrayList<String> where, String data, String limit) {

        String selectStatement = "SELECT "
                + data
                + "from continent "
                +"inner join country on continent.id = country.continent "
                +"inner join region on country.id = region.iso_country "
                +"inner join world on region.id = world.iso_region "
                + " WHERE (world.name LIKE \"%" + match + "%\" OR "
                + " world.id LIKE \"%" + match + "%\" OR "
                + " world.municipality LIKE \"%" + match + "%\" OR "
                + " region.name LIKE \"%" + match + "%\" OR "
                + " country.name LIKE \"%" + match + "%\")"
                + typeMatch(type)
                + whereMatch(where)
                + limit
                + " ;";
        return selectStatement;

    }

}



