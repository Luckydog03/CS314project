import React from 'react';
import { beforeEach, describe, expect, jest, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { render, screen, waitFor } from '@testing-library/react';
import AddPlace from '../../../src/components/Header/FindPlace';
import FindPlace from '../../../src/components/Header/FindPlace';
describe("SearchResults", () => {
    const mockToggleOpen = jest.fn();
    const mockAddTrip = jest.fn();
    
    const findResponse = {
        limit: 5,
        match: "Denver International Airport",
        type: ["airport"],
        where: "",
        places: [   {latitude: "39.86",
                    longitude: "-104.67",
                    name: "Denver International Airporter",
                    municipality: "Denver",
                    region: "Colorado",
                    type: ["airport"]}
        ],
        requestType: "find"
    };
    let isOpen = true;


    beforeEach(() => {
        render(<FindPlace   isOpen={isOpen}
                                toggleOpen={mockToggleOpen}
                                findResponse={findResponse}
                                addTrip={mockAddTrip} />)
    });

    test('joelpr02: validates name', async () => {
        const matchName = screen.getByTestId('name-input');
        user.type(matchName, findResponse.match);

        await waitFor(() => {
            expect(matchName.value).toEqual(findResponse.match);
        });
    });

    const findWithID = {
        limit: 5,
        match: "CO35",
        type: "",
        where: "",
        places: [   {latitude: "39.73",
                    longitude: "-104.99",
                    name: "Denver Health Heliport",
                    municipality: "Denver",
                    region: "Colorado",
                    type: ["heliport"]}
        ],
        requestType: "find"
    };

    test('joelpr02: validates name', async () => {
        const matchID = screen.getByTestId('name-input');
        user.type(matchID, findWithID.match);

        await waitFor(() => {
            expect(matchID.value).toEqual(findWithID.match);
        });
    });
    
    const findName52 = {
        limit: 5,
        match: "Two Leggs Airport",
        type: "",
        where: "",
        places: [   {latitude: "32.94",
                    longitude: "-103.00",
                    name: "Two Leggs Airport",
                    municipality: "Denver City",
                    region: "Texas",
                    type: ["airport"]}
        ],
        requestType: "find"
    };

    test('Bigg52: validates name', async () => {
        const matchID = screen.getByTestId('name-input');
        user.type(matchID, findName52.match);

        await waitFor(() => {
            expect(matchID.value).toEqual(findName52.match);
        });
    });

});

