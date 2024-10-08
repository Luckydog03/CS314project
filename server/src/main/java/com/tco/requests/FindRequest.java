package com.tco.requests;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.tco.database.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(FindRequest.class);
    // request properties
    private String match;
    private ArrayList<String> type;
    private ArrayList<String> where;
    private Integer limit;
    private Places places;

    // response properties
    private Integer found = 0;

    public String match() {
        return match;
    }

    public ArrayList<String> type() {
        return type;
    }

    public ArrayList<String> where() {
        return where;
    }

    public Integer limit() {
        return limit;
    }

    public Integer found() {
        return found;
    }

    public Places places() {
        return places;
    }

    @Override
    public void buildResponse() {

        this.places = buildFindList();
        log.trace("findResponse -> {}", this);
    }
    private Places buildFindList() {

        Places foundPlaces = new Places();
        try {
            Database db = new Database();
            if (db.connect()) {
                foundPlaces.addAll(db.getMatch(this.match,  this.type, this.where, this.limit));
                this.found = db.getFound(this.match, this.type, this.where);

                if(this.type.isEmpty()) {
                    this.type = null;
                }
                if(this.where.isEmpty()) {
                    this.where = null;
                }

            }
        }catch (Exception e){}
        return foundPlaces;
    }

  /* The following methods exist only for testing purposes and are not used
  during normal execution, including the constructor. */

    public FindRequest(String match, ArrayList<String> type, ArrayList<String> where, Integer limit, Places places, Integer found) {
        super();
        this.requestType = "find";
        this.match = match;
        this.type = type;
        this.where = where;
        this.limit = limit;
        this.places = places;
        this.found = found;
    }
}
