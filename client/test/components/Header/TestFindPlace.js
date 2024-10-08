import React from 'react';
import { beforeEach, describe, expect, jest, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { render, screen, waitFor } from '@testing-library/react';
import AddPlace from '../../../src/components/Header/FindPlace';
describe("SearchResults", () => {
    const mockToggleOpen = jest.fn();
    const mockAddTrip = jest.fn();
    const findResponse = {
        found: 1,
        limit: 1,
        match: "",
        places: [   {latitude: "100",
                    longitude: "10",
                    name: "testName",
                    country: "testCountry",
                    municipality: "testMunic",
                    region: "testRegion",
                    type: "airport"}
        ],
        requestType: "find"
    };
    let isOpen = true;

    let searchResultsWrapper;

    beforeEach(() => {
        searchResultsWrapper = shallow(<SearchResults   isOpen={isOpen}
                                                        toggleOpen={mockToggleOpen}
                                                        findResponse={findResponse}
                                                        addTrip={mockAddTrip} />)
    });

    it("building results with blank and make sure place not null", () => {
        expect(searchResultsWrapper.instance().populateSearch({name: "", latitude: "", longitude: ""}, 5)).toBeDefined();
        const instance = searchResultsWrapper.instance();
        instance.populateSearch= jest.fn();
        instance.toggle = jest.fn();
        instance.setState({findResponse: {places: [{name: ""}]}});
        instance.render();
        expect(instance.populateSearch).toBeCalled();
        expect(true).toEqual(true);
    });

    it("correctly handles invalid properties", () => {
        const mockDestination = {
            "altitude": "700",
            "country": "Canada",
            "latitude": "47.809722223",
            "name": "Englehart (Dave's Field)",
            "municipality": "Englehart",
            "id": "CDF3",
            "type": "small_airport",
            "region": "Ontario",
            "longitude": "-79.8111111111"
        }

        const properties = ['foooo'];

        const result = searchResultsWrapper.instance().parsePropertyValue(mockDestination,properties);
        expect(result).toEqual('');
    });

    it("correctly finds property value", () => {
        const mockDestination = {
            "altitude": "700",
            "country": "Canada",
            "latitude": "47.809722223",
            "name": "Englehart (Dave's Field)",
            "municipality": "Englehart",
            "id": "CDF3",
            "type": "small_airport",
            "region": "Ontario",
            "longitude": "-79.8111111111"
        }

        const properties = ['country'];

        const result = searchResultsWrapper.instance().parsePropertyValue(mockDestination,properties);
        expect(result).toEqual("Canada");
    });

    it("correctly handles multiple valid duplicate properties", () => {
        const mockDestination = {
            "continent": "NA",
            "altitude": "700",
            "iso_country": "CA",
            "continent.name": "North America",
            "latitude": "47.809722223",
            "municipality": "Englehart",
            "type": "small_airport",
            "name": "Englehart (Dave's Field)",
            "country.name": "Canada",
            "id": "CDF3",
            "region.name": "Ontario",
            "longitude": "-79.8111111111"
        }

        const properties = ['small_airport'];

        const result = searchResultsWrapper.instance().parsePropertyValue(mockDestination,properties);
        expect(result).toEqual('small_airport');
    });
});