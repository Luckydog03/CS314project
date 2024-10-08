import React, { useEffect, useState } from 'react';
import { Collapse } from 'reactstrap';
import Header from './Header/Header';
import About from './About/About';
import Planner from './Trip/Planner';
import { useToggle } from '../hooks/useToggle';
import { LOG } from '../utils/constants';
import { getOriginalServerUrl, sendAPIRequest } from '../utils/restfulAPI';
import { usePlaces } from '../hooks/usePlaces';

export default function Page(props) {
	const [showAbout, toggleAbout] = useToggle(false);
	const [serverSettings, processServerConfigSuccess] = useServerSettings(
		props.showMessage
	);
	const { places, selectedIndex, placeActions } = usePlaces();
	const [tripName, setTripName] = useState('My Trip');
	const [earthRadius, setEarthRadius] = useState(3959);
	const [distances, setDistances] = useState([]);

	useEffect(() => {
		sendDistancesRequest();
	}, [places, earthRadius]);

	const distancesRequestBody = {
		"requestType"    : "distances",
		"places"         : places,
		"earthRadius"    : earthRadius
	  }

	  function processDistancesSuccess(distances) {
		setDistances(distances);
		LOG.info('Setting distances: ', distances);
	}

	  async function sendDistancesRequest() {
		const serverUrl = serverSettings.serverUrl;
		const distancesResponse = await sendAPIRequest(distancesRequestBody, serverUrl);
		if (distancesResponse) {
			processDistancesSuccess(distancesResponse.distances, serverUrl);
		} else {
			setDistances([]);
			LOG.info(`Distances request to ${serverUrl} failed. Check the log for more details.`, 'error');
		}
	}

	return (
		<>
			<Header
				toggleAbout={toggleAbout}
				showAbout={showAbout}
				placeActions={placeActions}
				disableRemoveAll={!places?.length}
				serverSettings={serverSettings}
				processServerConfigSuccess={processServerConfigSuccess}
				setTripName={setTripName}
			/>
			
			<MainContentArea
				showAbout={showAbout}
				toggleAbout={toggleAbout}
				places={places}
				selectedIndex={selectedIndex}
				placeActions={placeActions}
				tripName={tripName}
				distances={distances}
			/>
		</>
	);
}

function MainContentArea(props) {
	return (
		<div className='body'>
			<Collapse isOpen={props.showAbout}>
				<About closePage={props.toggleAbout} />
			</Collapse>
			<Collapse isOpen={!props.showAbout} data-testid='planner-collapse'>
				<Planner
					{...props}
				/>
			</Collapse>
		</div>
	);
}

function useServerSettings(showMessage) {
	const [serverUrl, setServerUrl] = useState(getOriginalServerUrl());
	const [serverConfig, setServerConfig] = useState(null);

	useEffect(() => {
		sendConfigRequest();
	}, []);

	function processServerConfigSuccess(config, url) {
		LOG.info('Switching to Server:', url);
		setServerConfig(config);
		setServerUrl(url);
	}

	async function sendConfigRequest() {
		const configResponse = await sendAPIRequest({ requestType: 'config' },serverUrl);
		if (configResponse) {
			processServerConfigSuccess(configResponse, serverUrl);
		} else {
			setServerConfig(null);
			showMessage(`Config request to ${serverUrl} failed. Check the log for more details.`, 'error');
		}
	}

	return [{ serverUrl: serverUrl, serverConfig: serverConfig }, processServerConfigSuccess,];
}
