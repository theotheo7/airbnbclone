import './ViewListing.css';
import {DefaultButton, Stack, StackItem, TextField} from "@fluentui/react";
import {MapContainer, Marker, Popup, TileLayer} from "react-leaflet";
import {Icon, LatLng} from "leaflet";
// @ts-ignore
import markerIconPng from "../../assets/marker-icon.png";
import React, {useEffect, useState} from "react";
import {IListingCompleteDetails} from "../../models/Listing/IListingCompleteDetails";
import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import {fetchListingFromSearch} from "../../services/SearchService";
import {Carousel} from "react-responsive-carousel";
import {getRoles} from "../../services/UserService";
import {book} from "../../services/GuestService";

function ViewListing() {
    const navigate = useNavigate();
    const { id } = useParams();
    const [dateParams] = useSearchParams();
    const [fromDate, setFromDate] = useState<Date>();
    const [toDate, setToDate] = useState<Date>();
    const [listing, setListing] = useState<IListingCompleteDetails>();

    const oneDay = 24 * 60 * 60 * 1000;

    function isNotGuest() {
        return !getRoles()?.includes("GUEST");
    }

    function getNights(fromDate: Date, toDate: Date) {
        return Math.round(Math.abs(fromDate.getTime() - toDate.getTime()) / oneDay);
    }

    useEffect(() => {
        (async function() {
            if (id !== undefined && !listing) {
                const result: IListingCompleteDetails = await fetchListingFromSearch(id).then();
                setListing(result);
                let fDate = dateParams.get("from");
                let tDate = dateParams.get("to");
                if (fDate) {
                    setFromDate(new Date(fDate));
                    console.log(fDate);
                }
                if (tDate) {
                    setToDate(new Date(tDate));
                }
            }
        })();
    }, [listing, id, dateParams]);

    async function _onClickBook() {
        if (listing) {
            try {
                const startMonth = fromDate?.getMonth() ? fromDate?.getMonth() + 1 : 1;
                const endMonth = toDate?.getMonth() ? toDate?.getMonth() + 1 : 1;

                const startDay = fromDate?.getDate() ? fromDate?.getDate() : 1;
                const endDay = toDate?.getDate() ? toDate?.getDate() : 1;

                const fDate = fromDate?.getFullYear() + (startMonth < 10 ? "-0" + startMonth : "-" + startMonth) + (startDay < 10 ? "-0" + startDay : "-" + startDay);
                const tDate = toDate?.getFullYear() + (endMonth < 10 ? "-0" + endMonth : "-" + endMonth) + (endDay < 10 ? "-0" + endDay : "-" + endDay)

                await book(listing.id, fDate, tDate);
                console.log("Success");
                navigate("/");
            } catch (err) {
                console.log(err);
            }
        }
    }

    return (
        <div>
            <Stack>
                <StackItem className="map-container">
                    {listing ?
                        <MapContainer style={{height: '100%'}} center={new LatLng(listing.location.latitude, listing.location.longitude)} zoom={13} scrollWheelZoom={true}>
                            <TileLayer
                                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                            />
                            <Marker position={[listing.location.latitude, listing.location.longitude]} icon={new Icon({iconUrl: markerIconPng, iconSize: [25, 41], iconAnchor: [12, 41]})}>
                                <Popup>I am here</Popup>
                            </Marker>
                        </MapContainer>
                        :
                        <></>
                    }
                </StackItem>
                <StackItem className="listing-name">
                    <h1>{listing?.name}</h1>
                </StackItem>
                <StackItem className="carousel-container">
                    <Carousel className="carousel" showThumbs={false}>
                        {listing?.images.map((image) => (
                            <div key={image}>
                                <img src={require("../../../../airbnbclone-backend/" + image)} alt="" width={100} height={500}/>
                            </div>
                        ))}
                    </Carousel>
                </StackItem>
                <h2 className="section-header">Location</h2>
                <StackItem className="location-container">
                    <TextField label="Longitude" value={listing?.location.longitude.toString()} readOnly/>
                    <TextField label="Latitude" value={listing?.location.latitude.toString()} readOnly/>
                    <TextField label="Address" value={listing?.location.address} readOnly/>
                    <TextField label="City" value={listing?.location.city} readOnly/>
                    <TextField label="State" value={listing?.location.state} readOnly/>
                    <TextField label="Zipcode" value={listing?.location.zipcode} readOnly/>
                </StackItem>
                <h2 className="section-header">The Space</h2>
                <h4 className="section-header">{listing?.type === "Home" ? "Entire home" : "Private room"}</h4>
                <StackItem className="space-container">
                    <div>{listing?.meters} square meters</div>
                    <div>- {listing?.beds} bedroom(s)</div>
                    <div>- {listing?.baths} bathroom(s)</div>
                    <div>{listing?.living ? "- Living room" : "- No living room"}</div>
                    <div>{listing?.kitchen ? "- Kitchen" : "- No kitchen"}</div>
                    <div>{listing?.elevator ? "- Elevator" : "- No elevator"}</div>
                    <div>{listing?.parking ? "- Parking" : "- No parking"}</div>
                    <div>{listing?.ac ? "- AC" : "- No AC"}</div>
                    <div>{listing?.heat ? "- Heating" : "- No heating"}</div>
                    <div>{listing?.tv ? "- TV" : "- No TV"}</div>
                    <div>{listing?.wifi ? "- WiFi" : "- No WiFi"}</div>
                </StackItem>
                <h2 className="section-header">Rules</h2>
                <StackItem className="rules-container">
                    <div>Pets {listing?.pets ? "allowed" : "not allowed"}</div>
                    <div>- Parties {listing?.party ? "allowed" : "not allowed"}</div>
                </StackItem>
                <h2 className="section-header">Summary</h2>
                    <TextField style={{width: '70vw', height: '20vh'}} value={listing?.summary} className="summary-box" multiline readOnly/>
                <h2 className="section-header">Meet the host</h2>
                <StackItem className="host-container">
                    {listing ? <img src={require("../../../../airbnbclone-backend/" + listing?.host.image) } alt={listing?.host.username} width={100} height={100}/> : <></>}
                    <div>{listing?.host.firstName}</div>
                    <div>{listing?.host.lastName}</div>
                    <DefaultButton text="Message host" disabled={isNotGuest()}/>
                </StackItem>
                <h3 className="section-header">Total nights: {fromDate && toDate ? getNights(fromDate, toDate) : 1}</h3>
                <h3 className="section-header">Total price: {fromDate && toDate && listing?.price ? getNights(fromDate, toDate) * listing.price + "$" : 0}</h3>
                <StackItem className="booking-button">
                    <DefaultButton style={{width: '5vw'}} text="Book" disabled={isNotGuest()} onClick={_onClickBook}/>
                </StackItem>
            </Stack>
        </div>
    );
}

export default ViewListing;