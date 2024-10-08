import React, { useState } from 'react';
import { useToggle } from '../../../hooks/useToggle';
import { Table, Collapse, Button } from 'reactstrap';
import { latLngToText, placeToLatLng } from '../../../utils/transformers';
import { BsChevronDown } from 'react-icons/bs';
import PlaceActions from './PlaceActions';

export default function Itinerary(props) {
	let total = 0;
    	if(props.distances){
        	for(let i = 0; i < props.distances.length; i++){
            	total += props.distances[i];
        	};
    	}
	return (
		<Table responsive>
			<TripHeader
				tripName={props.tripName}
			/>
			<PlaceList
				{...props}
			/>
			<thead>
				<tr>
					<th align = "left">
						Total Round Trip Distance
					</th>
					<th alight = "right">
						{total}
					</th>
				</tr>
			</thead>
		</Table>
	);
}

function TripHeader(props) {
	return (
		<thead>
			<tr>
				<th style={{ color: 'grey' }}>
					For more information on a destination click on it's name down below.
				</th>
			</tr>
			<tr>
				<th
					className='trip-header-title'
					data-testid='trip-header-title'
				>
					{props.tripName}
				</th>
				
				<th align = "right">
					Miles
				</th>
			</tr>
		</thead>
	);
}

function PlaceList(props) {
	return (
		<tbody>
			{props.places.map((place, index) => (
				<PlaceRow
					key={`table-${JSON.stringify(place)}-${index}`}
					place={place}
					{...props}
					index={index}
					
				/>
			))}
		</tbody>
	);
}

function PlaceRow(props) {
	const [showFullName, toggleShowFullName] = useToggle(false);
	const name = props.place.defaultDisplayName;
	const location = latLngToText(placeToLatLng(props.place));
	let cumulative = 0;
	if(props.distances){
		for(let i = 0; i < props.index; i++){
			cumulative += props.distances[i];
		};
	}
	return (
		
		<tr className={props.selectedIndex === props.index ? 'selected-row' : ''}>
			
			<td
			    
				data-testid={`place-row-${props.index}`}
				onClick={() =>
					placeRowClicked(
						toggleShowFullName,
						props.placeActions.selectIndex,
						props.index
					)
				}
			>
				
				<strong>{name}</strong>
				<AdditionalPlaceInfo showFullName={showFullName} location={location} placeActions={props.placeActions} index={props.index} place={props.place} cumulative={cumulative}/>
			</td>
			<td align = {"right"}> {props.index > 0 ? (props.distances ? props.distances[props.index-1] : 0) : 0} </td>
			<RowArrow toggleShowFullName={toggleShowFullName} index={props.index}/>
		</tr>
	);
}

function AdditionalPlaceInfo(props) {
	return (
		
		<Collapse isOpen={props.showFullName}>
			
			{props.place.formatPlace().replace(`${props.place.defaultDisplayName}, `, '')}
			<br />
			{props.location}
			<br />
			Cumulative (Miles): {props.cumulative}
			<br />
			<PlaceActions placeActions={props.placeActions} index={props.index} />
			
		</Collapse>
	);
}

function placeRowClicked(toggleShowFullName, selectIndex, placeIndex) {
	toggleShowFullName();
	selectIndex(placeIndex);
}

function RowArrow(props) {
	return (
		<td>
			<BsChevronDown data-testid={`place-row-toggle-${props.index}`} onClick={props.toggleShowFullName}/>
		</td>
	);
}
