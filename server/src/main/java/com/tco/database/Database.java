package com.tco.database;

import com.tco.requests.Place;
import com.tco.requests.Places;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    String url      = Credential.url();
    String user     = Credential.USER;
    String password = Credential.PASSWORD;

    private Connection conn = null;
    private Statement query = null;
    private ResultSet results = null;

    private final static String FIELDS = "name,latitude,longitude,id,altitude,municipality,type,region,country,url";

    public Database() {}

    public boolean connect() throws SQLException {
        try{
            // connect to the database and query
            conn = DriverManager.getConnection(url,user,password) ;
            System.out.println("Database is connected");
            return true;
        } catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }

    }
    public Places getMatch(String match, ArrayList<String> type, ArrayList<String> where, int limit) throws Exception {
            try{
                String SQL = Select.match(match, type, where, limit);
                query   = conn.createStatement();
                results = query.executeQuery(SQL);
                return   convertQueryResultsToPlaces(results, FIELDS);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw e;
            }

        }
        
    public Integer getFound(String match, ArrayList<String> type, ArrayList<String> where) throws Exception {
        try{
            String SQL = Select.found(match, type, where);
            query   = conn.createStatement();
            results = query.executeQuery(SQL);
            return   count(results);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }
    
    private static Places convertQueryResultsToPlaces(ResultSet results, String columns) throws Exception {
        int count = 0;
        String[] cols = columns.split(",");
        Places places = new Places();
        while (results.next()) {
            Place place = new Place();
            for (String col: cols) {
                place.put(col, results.getString(col));
            }
            place.put("index", String.format("%d",++count));
            places.add(place);
        }
        return places;
    }
    
    private static Integer count(ResultSet results) throws Exception {
        if (results.next()) {
            return results.getInt("count");
        }
        throw new Exception("No count results in found query.");
    }

}
