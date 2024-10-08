import React, { useEffect, useState } from 'react';
import {
	Button,
	Col,
	Modal,
	ModalBody,
	ModalHeader,
	Input,
	Collapse,
	ModalFooter,
	Form,
	FormGroup,
	Label,
	CloseButton,
	Row
} from 'reactstrap';
import { sendAPIRequest } from '../../utils/restfulAPI';
import { LOG } from '../../utils/constants';
import { latLngToText, placeToLatLng } from '../../utils/transformers';
import Select from 'react-select';
import CreatableSelect from 'react-select/creatable';

export default function FindPlace(props) {

	const [foundPlace, setFoundPlace] = useState();
	const [nameString, setNameString] = useState("");
    const [found, setFound] = useState(-1);
	const [foundPlaces, setFoundPlaces] = useState([]);
	const [searchedTerm, setSearchedTerm] = useState('');
	const [typeArray, setTypeArray] = useState(null);
	const [type, setType] = useState([]);

	useEffect(()=> {
		const temp = [];
		if(typeArray){
			if(typeArray.length > 0){
				for(let i in typeArray){
					temp.push(typeArray[i].value);
				}
			}
		}
		setType(temp);
	},[typeArray])

    const findRequestBody = {
        "requestType": "find",
        "match": nameString,
        "limit": 5
    }

	useEffect(() => {
		if(type){
			findRequestBody["type"] = type;
		}
		console.log(findRequestBody);
	},[type])


    function processFindSuccess(found) {
		setFound(found);
		LOG.info('Setting found: ', found);
	}

    async function sendFindRequest() {
        const findResponse = await sendAPIRequest(findRequestBody, props.serverSettings.serverUrl);
        if (findResponse) {
			processFindSuccess(findResponse.found, props.serverSettings.serverUrl);
			setFoundPlaces(findResponse.places);
			setSearchedTerm(findResponse.match);
			setNameString('');
		} else {
			setFound(-1);
			LOG.info(`Find request to ${props.serverSettings.serverUrl} failed. Check the log for more details.`, 'error');
		}
    }
	useEffect(()=>{

		setFoundPlaces([]);
		
		},[props.isOpen])

	return (
		<Modal isOpen={props.isOpen} toggle={props.toggleFindPlace}>
			<FindPlaceHeader toggleFindPlace={props.toggleFindPlace} />
			<PlaceSearch
				foundPlaces={foundPlaces}
				setFoundPlaces={setFoundPlaces}
				nameString={nameString}
				setNameString={setNameString}
				append={props.append}

				setTypeArray={setTypeArray}
			/>
			<PlaceType
				setTypeArray={setTypeArray}
				typeArray={typeArray}
			/>
			
			<FindPlaceFooter
				foundPlace={foundPlace}
                disabled={nameString.length < 3 ? true : false}
                nameString={nameString}
				setNameString={setNameString}
                sendFindRequest={sendFindRequest}
				searchedTerm={searchedTerm}
			/>
		</Modal>
	);
}
function FindPlaceHeader(props) {
	return (
		<ModalHeader className='ml-2' toggle={props.toggleFindPlace}>
			Find a Place
		</ModalHeader>
	);
}

function PlaceSearch(props) {
	return (
		<ModalBody>
			<Col>
			    Place: &nbsp;
				<Input
					onChange={(input) => props.setNameString(input.target.value)}
					placeholder='Enter Place Name'
					data-testid='name-input'
					value={props.nameString}
					
				/>

				<table>
				<div
					style={{
					background: 'black',
					height: '2px',
					}}
				/>
				<tbody>

					{props.foundPlaces.map((place, index) => (
							<FoundPlaceRow place={place} index={index} append={props.append}/>

					))}
				</tbody>
				<div
					style={{
					background: 'black',
					height: '2px',
					}}
				/>
				</table>
			</Col>
		</ModalBody>
	);
}

function PlaceType(props){
	const options = [
		{value: "airport", label: "Airport"},
		{value: "balloonport", label: "Balloonport"},
		{value: "heliport", label: "Heliport"},
		{value: "other", label: "Other"},
	]
	return (
		<ModalBody>
			<Col>
				Type: &nbsp; <br/>
				<Select 
				onChange={props.setTypeArray}
				options={options}
				data-testid='type-input'
				isMulti value={props.typeArray} 
				 />
			</Col>
		</ModalBody>
	)
}


function FoundPlaceRow(props){

	const name = props.place.name;
	const location = latLngToText(placeToLatLng(props.place));
	const region = props.place.region;
	const municipality = props.place.municipality;
	
	
	return(
	<ModalBody>
			<tr>
				<td data-testid={`place-row-${props.index}`}>
					<strong>{name}</strong>
				</td> &nbsp;&nbsp;

				<td > {municipality}, {region}</td> &nbsp;&nbsp;
				<td >{location}</td> &nbsp;&nbsp;
				
				<td > <Button
						color='primary'
						style={{ height: 37, width: 37}}
						onClick={() => {
							props.append(props.place);
						}}
						data-testid='add-place-button'
						disabled={!props.place}
						
					>
						+
					</Button>
				</td>
			</tr>
	</ModalBody>
	);
}

function FindPlaceFooter(props) {
	return (
		<ModalFooter>
			<Button
				color='primary'
				data-testid='find-place-button'
				disabled={props.disabled}
                onClick={() => props.sendFindRequest()}
			>
				Find Place
			</Button>
		</ModalFooter>
	);
}
