package com.tco.requests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestFindRequest {

    FindRequest request;
    Places places;
    String match;
    ArrayList<String> type;
    ArrayList<String> where;

    Integer limit;
    Integer found;

    @BeforeEach
    public void beforeEach(){
        match = "";
        limit = 0;
        places = new Places();
        found = 0;
    }

    @Test
    @DisplayName("Benzo79: without type and where")
    public void testNoTypeandWhere(){
        match = "port";
        limit = 10;
        request = new FindRequest(match, type, where, limit, places, found);
        request.buildResponse();
        assertEquals(10, request.places().size());
        assertTrue(request.found() > 100);
    }

    @Test
    @DisplayName("luckydog: where and type array")
    public void testEmpty(){
        match = "port";
        type = new ArrayList<String>();
        where = new ArrayList<String>();
        type.addAll(Arrays.asList("airport", "heliport"));
        where.addAll(Arrays.asList("United States" , "Canada"));
        limit = 10;
        request = new FindRequest(match, type, where, limit, places, found);
        request.buildResponse();
        System.out.println(request.found());
        assertTrue(request.found() > 100);
    }

    @Test
    @DisplayName("luckydog: AGIndigo8 test")
    public void aGIndigo8Test(){
        match = "air";
        limit = 1;
        request = new FindRequest(match, type, where, limit, places, found);
        request.buildResponse();
        assertEquals(1, request.places().size());
        assertEquals(35566, request.found());
        assertEquals(null, request.type());
        assertEquals(null, request.where());
    }

    @Test
    @DisplayName("luckydog: AlissaMorgan test")
    public void alissaMorganTest(){
        match = "CSU";
        limit = 5;
        request = new FindRequest(match, type, where, limit, places, found);
        request.buildResponse();
        assertEquals(5, request.places().size());
        assertEquals(7, request.found());

    }

    @Test
    @DisplayName("Benzo79: cath2731.json")
    public void testcath2731(){
        match = "Woosley";
        limit = 5;
        request = new FindRequest(match, type, where, limit, places, found);
        request.buildResponse();
        assertEquals(1, request.places().size());
        assertTrue(request.found() == 1);
    }

    @Test
    @DisplayName("joelpr02: match, type, where, limit")
    public void testMTWL(){
        match = "Vancouver";
        type = new ArrayList<String>();
        where = new ArrayList<String>();
        type.addAll(Arrays.asList("heliport"));
        where.addAll(Arrays.asList("Canada"));
        limit = 5;
        request = new FindRequest(match, type, where, limit, places, found);
        request.buildResponse();
        assertEquals("Vancouver", request.match());
        assertEquals(type, request.type());
        assertEquals(where, request.where());
        assertEquals(5, request.limit());
        assertTrue(request.found() > 0);

    }

    @Test
    @DisplayName("Benzo79: type and where return null ")
    public void testNullType(){
        match = "Vancouver";
        limit = 5;
        request = new FindRequest(match, type, where, limit, places, found);
        request.buildResponse();
        assertEquals("Vancouver", request.match());
        assertEquals(null, request.type());
        assertEquals(null, request.where());
    }
       
    @Test
    @DisplayName("Bigg52 Denver with null type and where")
    public void bigg52DenverNull(){
        match = "Denver";
        limit = 5;
        request = new FindRequest(match, type, where, limit, places, found);
        request.buildResponse();
        assertEquals("Denver", request.match());
        assertEquals(null, request.type());
        assertEquals(null, request.where());
    }

    @Test
    @DisplayName("joel02: add test with empty")
    public void nothing(){
        match = "";
        limit = 5;
        request = new FindRequest(match, type, where, limit, places, found);
        request.buildResponse();
        assertEquals("", request.match());
        assertEquals(null, request.type());
        assertEquals(null, request.where());
    }
}
