import './CreateListing.css'
import {Stack, StackItem, TextField} from "@fluentui/react";
import {MapContainer, Marker, Popup, TileLayer, useMapEvents} from "react-leaflet";
// @ts-ignore
import markerIconPng from "../../../assets/marker-icon.png"
import {Icon} from 'leaflet'
import React, {useState} from "react";
import {LatLng} from "leaflet";

function CreateListing() {
    const [latitude, setLatitude] = useState<number>(37.9838);
    const [longitude, setLongitude] = useState<number>(23.727539);
    const [address, setAddress] = useState<string | undefined>("");
    const [city, setCity] = useState<string | undefined>("");
    const [state, setState] = useState<string | undefined>("");
    const [zipcode, setZipcode] = useState<string | undefined>("");

    function _onChangeLongitude(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setLongitude(Number(newValue));
    }

    function _onChangeLatitude(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setLatitude(Number(newValue));
    }

    function _onChangeAddress(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setAddress(newValue);
    }

    function _onChangeCity(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setCity(newValue);
    }

    function _onChangeState(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setState(newValue);
    }

    function _onChangeZipcode(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setZipcode(newValue);
    }

    function LocationMarker() {
        const map = useMapEvents({
            click(e) {
                map.flyTo(e.latlng, map.getZoom());
                setLatitude(e.latlng.lat);
                setLongitude(e.latlng.lng);
            }
        })

        return (
            <Marker position={new LatLng(latitude, longitude)} icon={new Icon({iconUrl: markerIconPng, iconSize: [25, 41], iconAnchor: [12, 41]})}>
                <Popup>You are here</Popup>
            </Marker>
        )
    }

    return (
        <div>
            <Stack>
                <StackItem className="map-container">
                    <MapContainer style={{height: '100%'}} center={new LatLng(latitude, longitude)} zoom={13} scrollWheelZoom={true}>
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        <LocationMarker/>
                    </MapContainer>
                </StackItem>
                <h3 style={{paddingLeft: '25px'}}>Location</h3>
                <StackItem className="location-container">
                    <TextField label="Longitude" value={longitude.toString()} onChange={_onChangeLongitude} type="number"/>
                    <TextField label="Latitude" value={latitude.toString()} onChange={_onChangeLatitude} type="number"/>
                    <TextField label="Address" value={address} onChange={_onChangeAddress}/>
                    <TextField label="City" value={city} onChange={_onChangeCity}/>
                    <TextField label="State" value={state} onChange={_onChangeState}/>
                    <TextField label="Zipcode" value={zipcode} onChange={_onChangeZipcode}/>
                </StackItem>
            </Stack>
        </div>
    );
}

export default CreateListing;