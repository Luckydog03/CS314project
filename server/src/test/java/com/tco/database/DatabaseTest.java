package com.tco.database;

import com.tco.requests.Place;
import com.tco.requests.Places;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseTest {
    Places places;
    String match;
    ArrayList<String> type;
    ArrayList<String> where;

    Integer limit;
    Integer found;

    @BeforeEach
    public void beforeEach(){
        match = "";
        type = new ArrayList<String>();
        where = new ArrayList<String>();
        limit = 0;
        places = new Places();
        found = 0;
    }
    private Database db;

    @BeforeEach
    void setUp() throws SQLException {
        db = new Database();
        db.connect();
    }

    @Test
    @DisplayName("Benzo79: Database Test Connection")
    void Testconnection() throws Exception {
        assertTrue(db.connect());
    }

    @Test
    @DisplayName("Benzo79: Test get match Airports with limit 10")
    void TestGetMatch10Airports() throws Exception {
        type.addAll(Arrays.asList("airport"));
        where.addAll(Arrays.asList("United States" ));
        places = db.getMatch("air", type, where,10);
        assertTrue(places.size() == 10);
    }

    @Test
    @DisplayName("Benzo79: Test get found Airports")
    void TestGetFoundAirports() throws Exception {
        type.addAll(Arrays.asList("airport", "heliport"));
        where.addAll(Arrays.asList("United States" , "Canada"));
        Integer found = db.getFound("port", type, where);
        assertTrue(found > 10);
    }

    @Test
    @DisplayName("joelpr02: Find Vancouver")
    void TestVancouverAirports()throws Exception {
        Integer found = db.getFound("Vancouver",type, where);
        assertTrue(found >= 8);
    }

    @Test
    @DisplayName("joelpr02: Find Denver")
    void TestDenverAirports() throws Exception {
        Integer found = db.getFound("Denver",type, where);
        assertTrue(found >= 7);
    }

    @Test
    @DisplayName("garrettsmet: Find Aurora")
    void TestAurora() throws Exception {
        Integer found = db.getFound("Aurora",type, where);
        assertTrue(found >= 42);
    }

    @Test
    @DisplayName("joelpr02: Find heliport in Vancouver")
    void TestHeliports() throws Exception {
        type.addAll(Arrays.asList("heliport"));
        Integer found = db.getFound("Vancouver",type, where);
        assertTrue(found == 16);
    }
    
    @Test
    @DisplayName("Bigg52: Find Colorado with limit of 1")
    void TestColorado()throws Exception {
        where.addAll(Arrays.asList("United States" ));
        places  = db.getMatch("Colorado", type, where, 1);
        assertTrue(places.size() == 1);
    }

    @Test
    @DisplayName("Benzo79: Test match by ID: KFNL Airport")
    void TestKFNL() throws Exception {
        Integer found = db.getFound("KFNL",type, where);
        assertTrue(found == 1);
        places  = db.getMatch("KFNL", type, where, 1);
        assertEquals ("Fort Collins Loveland Municipal Airport", places.get(0).get("name"));
    }

    @Test
    @DisplayName("Benzo79: Test match by Municipality: Riverview")
    void TestMunicipality() throws Exception {
        Integer found = db.getFound("Riverview",type, where);
        assertTrue(found == 7);
    }

    @Test
    @DisplayName("Benzo79: Test match by Region: Ordino")
    void TestRegion() throws Exception {
        Integer found = db.getFound("Abu Dhabi Emirate",type, where);
        assertTrue(found > 20);
    }

    @Test
    @DisplayName("Benzo79: Test match by Country name: Egypt")
    void TestContinent() throws Exception {
        Integer found = db.getFound("Egypt",type, where);
       assertTrue(found > 60);
    }

    @Test
    @DisplayName("Benzo79: Test empty match name: empty ")
    void TestContinents() throws Exception {
        Integer found = db.getFound("", type, where);
        places  = db.getMatch("", type, where, 0);
        assertTrue(found == 50427);
        }
        @Test
        @DisplayName("Benzo79: Test with special case ryp with zero limit: ryp")
        void Testryp() throws Exception {
            Integer found = db.getFound("ryp",type, where);
            places  = db.getMatch("ryp", type, where, 0);
            assertTrue(found == 4);
        }
        @Test
        @DisplayName("Luckydog: Test where")
        void TestWhere() throws Exception{
            where.addAll(Arrays.asList("Egypt" ));
            Integer found = db.getFound("",type,where);
            places  = db.getMatch("", type, where, 5);
            assertTrue(places.size() == 5);
        }

}
