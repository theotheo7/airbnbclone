import './CreateListing.css'
import {
    Checkbox,
    DatePicker,
    DefaultButton,
    Dropdown,
    IDropdownOption,
    Stack,
    StackItem,
    TextField
} from "@fluentui/react";
import {MapContainer, Marker, Popup, TileLayer, useMapEvents} from "react-leaflet";
// @ts-ignore
import markerIconPng from "../../../assets/marker-icon.png"
import {Icon} from 'leaflet'
import React, {ChangeEvent, useState} from "react";
import {LatLng} from "leaflet";
import {Location} from "../../../models/Location";
import {Listing} from "../../../models/Listing";
import {createListing} from "../../../services/HostService";
import {useNavigate} from "react-router-dom";

function CreateListing() {
    const navigate = useNavigate();

    const [latitude, setLatitude] = useState<number>(37.9838);
    const [longitude, setLongitude] = useState<number>(23.727539);
    const [address, setAddress] = useState<string | undefined>("");
    const [city, setCity] = useState<string | undefined>("");
    const [state, setState] = useState<string | undefined>("");
    const [zipcode, setZipcode] = useState<string | undefined>("");

    const [fromDate, setFromDate] = useState<Date | undefined>(undefined);
    const [toDate, setToDate] = useState<Date | undefined>(undefined);

    const [name, setName] = useState<string | undefined>("");
    const [maxPeople, setMaxPeople] = useState<number>(0);
    const [price, setPrice] = useState<number>(0);
    const [extraPeople, setExtraPeople] = useState<number>(0);
    const [type, setType] = useState<IDropdownOption>();

    const [beds, setBeds] = useState<number>(0);
    const [baths, setBaths] = useState<number>(0);
    const [meters, setMeters] = useState<number>(0);

    const [living, setLiving] = useState<boolean>(false);
    const [party, setParty] = useState<boolean>(false);
    const [pets, setPets] = useState<boolean>(false);

    const [summary, setSummary] = useState<string | undefined>("");

    const [images, setImages] = useState<File[]>([]);

    const typeOptions: IDropdownOption[] = [
        { key: 'Room', text: 'Private room' },
        { key: 'Home', text: 'Entire home' }
    ];

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

    function _onChangeName(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setName(newValue);
    }

    function _onChangeMaxPeople(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            setMaxPeople(Number(newValue));
        }
    }

    function _onChangePrice(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            setPrice(Number(newValue));
        }
    }

    function _onChangeExtraPeople(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            setExtraPeople(Number(newValue));
        }
    }

    const _onChangeType = (event: React.FormEvent<HTMLDivElement>, option: IDropdownOption | undefined): void => {
        setType(option);
    };

    function _onChangeBeds(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            setBeds(Number(newValue));
        }
    }

    function _onChangeBaths(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            setBaths(Number(newValue));
        }
    }

    function _onChangeMeters(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            setMeters(Number(newValue));
        }
    }

    function _onChangeLiving() {
        setLiving(!living);
    }

    function _onChangeParty() {
        setParty(!party);
    }

    function _onChangePets() {
        setPets(!pets);
    }

    function _onChangeSummary(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setSummary(newValue);
    }

    function _onChangeImages(
        event: ChangeEvent<HTMLInputElement>
    ) {
        if (event.target.files) {
            let imageList = Array.prototype.slice.call(event.target.files);
            setImages(imageList);
        }
    }

    async function _onClickCreate() {
        const formData = new FormData();

        const loc = new Location(
            longitude,
            latitude,
            address ? address : "",
            city ? city : "",
            state ? state : "",
            zipcode ? zipcode : ""
        );

        const startMonth = fromDate?.getMonth() ? fromDate?.getMonth() + 1 : 1;
        const endMonth = toDate?.getMonth() ? toDate?.getMonth() + 1 : 1;

        const startDate = fromDate?.getFullYear() + (startMonth < 10 ? "-0" + startMonth : "-" + startMonth) + "-" + fromDate?.getDate();
        const endDate = toDate?.getFullYear() + (endMonth < 10 ? "-0" + endMonth : "-" + endMonth) + "-" + toDate?.getDate();

        const listing = new Listing(
            loc,
            name ? name : "",
            startDate,
            endDate,
            maxPeople,
            price,
            extraPeople,
            type?.id ? type.id : "",
            beds,
            baths,
            meters,
            living,
            party,
            pets,
            summary ? summary : "",
            images
        );

        if (images) {
            for (let i=0; i<images.length; i++) {
                formData.append("images", images[i]);
            }
        } else {
            formData.append("images", "");
        }

        formData.append("listing", JSON.stringify(listing));

        try {
            await createListing(formData);
            window.alert("Successfully created listing!");
            navigate("/host");
        } catch (err) {
            console.log(err);
            window.alert("Something went wrong! Try again.");
        }

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
                <h3 className="section-header">Location</h3>
                <StackItem className="location-container">
                    <TextField label="Longitude" value={longitude.toString()} onChange={_onChangeLongitude} type="number"/>
                    <TextField label="Latitude" value={latitude.toString()} onChange={_onChangeLatitude} type="number"/>
                    <TextField label="Address" value={address} onChange={_onChangeAddress}/>
                    <TextField label="City" value={city} onChange={_onChangeCity}/>
                    <TextField label="State" value={state} onChange={_onChangeState}/>
                    <TextField label="Zipcode" value={zipcode} onChange={_onChangeZipcode}/>
                </StackItem>
                <h3 className="section-header">Availability</h3>
                <StackItem className="availability-container">
                    <DatePicker
                        label="From"
                        value={fromDate}
                        onSelectDate={setFromDate as (date: Date | null | undefined) => void}
                        style={{minWidth: '15vw'}}
                    />
                    <DatePicker
                        label="To"
                        value={toDate}
                        onSelectDate={(setToDate as (date: Date | null | undefined) => void)}
                        style={{minWidth: '15vw'}}
                    />
                </StackItem>
                <h3 className="section-header">Information</h3>
                <StackItem className="name-container">
                    <TextField label="Name" type="number" value={name} onChange={_onChangeName} style={{minWidth: '15vw'}}/>
                </StackItem>
                <StackItem className="information-container">
                    <TextField label="Maximum people" type="number" value={maxPeople.toString()} onChange={_onChangeMaxPeople}/>
                    <TextField label="Minimum price" type="number" value={price.toString()} onChange={_onChangePrice}/>
                    <TextField label="Extra people cost" type="number" value={extraPeople.toString()} onChange={_onChangeExtraPeople}/>
                    <Dropdown label="Room type" options={typeOptions} onChange={_onChangeType} style={{minWidth: '10vw'}}/>
                    <TextField label="Beds" type="number" value={beds.toString()} onChange={_onChangeBeds}/>
                    <TextField label="Baths" type="number" value={baths.toString()} onChange={_onChangeBaths}/>
                    <TextField label="Square meters" type="number" value={meters.toString()} onChange={_onChangeMeters}/>
                </StackItem>
                <h3 className="section-header">Extras</h3>
                <StackItem className="extras-container">
                    <Checkbox label="Living room" onChange={_onChangeLiving}/>
                    <Checkbox label="Party" onChange={_onChangeParty}/>
                    <Checkbox label="Pets" onChange={_onChangePets}/>
                </StackItem>
                <h3 className="section-header">Summary</h3>
                <StackItem className="summary-container">
                    <TextField label="Write a quick summary" value={summary} onChange={_onChangeSummary} multiline className="summary-box"/>
                </StackItem>
                <h3 className="section-header">Images</h3>
                <StackItem className="images-container">
                    <input type="file" onChange={_onChangeImages} multiple/>
                </StackItem>
                <StackItem className="button-container">
                    <DefaultButton text="Create" onClick={_onClickCreate}/>
                </StackItem>
            </Stack>
        </div>
    );
}

export default CreateListing;