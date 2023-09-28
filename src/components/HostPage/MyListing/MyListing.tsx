import './MyListing.css'
import {useNavigate, useParams} from "react-router-dom";
import React, {ChangeEvent, useEffect, useState} from "react";
import {IListingCompleteDetails} from "../../../models/Listing/IListingCompleteDetails";
import {fetchListing, updateListing} from "../../../services/HostService";
import "react-responsive-carousel/lib/styles/carousel.min.css";
import {Checkbox, DefaultButton, Dropdown, IDropdownOption, Stack, StackItem, TextField} from "@fluentui/react";
import {Carousel} from "react-responsive-carousel";
import {MapContainer, Marker, Popup, TileLayer, useMapEvents} from "react-leaflet";
import {Icon, LatLng} from "leaflet";
// @ts-ignore
import markerIconPng from "../../../assets/marker-icon.png";
import {Listing} from "../../../models/Listing/Listing";

function MyListing() {
    const { name } = useParams();
    const [listing, setListing] = useState<IListingCompleteDetails>();
    const [images, setImages] = useState<File[]>([]);
    const navigate = useNavigate();

    const typeOptions: IDropdownOption[] = [
        { key: 'Room', text: 'Private room' },
        { key: 'Home', text: 'Entire home' }
    ];

    useEffect(() => {
        (async function() {
            if (name !== undefined && !listing) {
                const result: IListingCompleteDetails = await fetchListing(name).then();
                setListing(result);
            }
        })();
    }, [listing, name]);

    function _onChangeLongitude(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (listing) {
            setListing({...listing, location: {...listing?.location, longitude: Number(newValue)}});
        }
    }

    function _onChangeLatitude(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (listing) {
            setListing({...listing, location: {...listing?.location, latitude: Number(newValue)}});
        }
    }

    function _onChangeAddress(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (listing && newValue) {
            setListing({...listing, location: {...listing?.location, address: newValue}});
        }
    }

    function _onChangeCity(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (listing && newValue) {
            setListing({...listing, location: {...listing?.location, city: newValue}});
        }
    }

    function _onChangeState(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (listing && newValue) {
            setListing({...listing, location: {...listing?.location, address: newValue}});
        }
    }

    function _onChangeZipcode(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (listing && newValue) {
            setListing({...listing, location: {...listing?.location, zipcode: newValue}});
        }
    }

    function _onChangeName(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (listing && newValue) {
            setListing({...listing, name: newValue});
        }
    }

    function _onChangeMaxPeople(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            if (listing && newValue) {
                setListing({...listing, maxPeople: Number(newValue)});
            }
        }
    }

    function _onChangePrice(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            if (listing && newValue) {
                setListing({...listing, price: Number(newValue)});
            }
        }
    }

    function _onChangeExtraPeople(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            if (listing && newValue) {
                setListing({...listing, extraPeople: Number(newValue)});
            }
        }
    }

    const _onChangeType = (event: React.FormEvent<HTMLDivElement>, option: IDropdownOption | undefined): void => {
        if (listing && option) {
            setListing({...listing, type: option.key as string});
        }
    };

    function _onChangeBeds(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            if (listing && newValue) {
                setListing({...listing, beds: Number(newValue)});
            }
        }
    }

    function _onChangeBaths(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            if (listing && newValue) {
                setListing({...listing, baths: Number(newValue)});
            }
        }
    }

    function _onChangeMeters(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            if (listing && newValue) {
                setListing({...listing, meters: Number(newValue)});
            }
        }
    }

    function _onChangeLiving() {
        if (listing) {
            setListing({...listing, living: !listing.living});
        }
    }

    function _onChangeParty() {
        if (listing) {
            setListing({...listing, party: !listing.party});
        }
    }

    function _onChangePets() {
        if (listing) {
            setListing({...listing, pets: !listing.pets});
        }
    }

    function _onChangeSummary(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (listing && newValue) {
            setListing({...listing, summary: newValue});
        }
    }

    function _onChangeImages(
        event: ChangeEvent<HTMLInputElement>
    ) {
        if (event.target.files) {
            let imageList = Array.prototype.slice.call(event.target.files);
            setImages(imageList);
        }
    }

    async function _onClickUpdate() {
        const formData = new FormData();

        if (images.length > 0) {
            for (let i=0; i < images.length; i++) {
                formData.append("images", images[i]);
            }
        } else {
            formData.append("images", "");
        }

        if (listing) {
            const newListing = new Listing(
                listing.location,
                listing.name,
                "",
                "",
                listing.maxPeople,
                listing.price,
                listing.extraPeople,
                listing.type,
                listing.beds,
                listing.baths,
                listing.meters,
                listing.living,
                listing.party,
                listing.pets,
                listing.summary
            );
            formData.append("listing", JSON.stringify(newListing));
        }

        try {
            await updateListing(formData);
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
                if (listing) {
                    setListing({...listing, location: {
                            ...listing.location,
                            longitude: e.latlng.lng,
                            latitude: e.latlng.lat
                        }});
                }
            },
        })

        return (
            <Marker position={listing ? [listing.location.latitude, listing.location.longitude] : [1, 1]} icon={new Icon({iconUrl: markerIconPng, iconSize: [25, 41], iconAnchor: [12, 41]})}>
                <Popup>You are here</Popup>
            </Marker>
        )
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
                            <LocationMarker/>
                        </MapContainer>
                    :
                        <></>
                    }
                </StackItem>
                <h3 className="section-header">Images</h3>
                <StackItem className="carousel-container">
                    <Carousel className="carousel" showThumbs={false}>
                        {listing?.images.map((image) => (
                            <div key={image}>
                                <img src={require("../../../../../airbnbclone-backend/" + image)} alt="" width={100} height={500}/>
                            </div>
                        ))}
                    </Carousel>
                </StackItem>
                <h3 className="section-header">Location</h3>
                <StackItem className="location-container">
                    <TextField label="Longitude" value={listing?.location.longitude.toString()} onChange={_onChangeLongitude} type="number"/>
                    <TextField label="Latitude" value={listing?.location.latitude.toString()} onChange={_onChangeLatitude} type="number"/>
                    <TextField label="Address" value={listing?.location.address} onChange={_onChangeAddress}/>
                    <TextField label="City" value={listing?.location.city} onChange={_onChangeCity}/>
                    <TextField label="State" value={listing?.location.state} onChange={_onChangeState}/>
                    <TextField label="Zipcode" value={listing?.location.zipcode} onChange={_onChangeZipcode}/>
                </StackItem>
                <h3 className="section-header">Information</h3>
                <StackItem className="name-container">
                    <TextField label="Name" value={name} onChange={_onChangeName} style={{minWidth: '15vw'}}/>
                </StackItem>
                <StackItem className="information-container">
                    <TextField label="Maximum people" type="number" value={listing?.maxPeople.toString()} onChange={_onChangeMaxPeople}/>
                    <TextField label="Minimum price" type="number" value={listing?.price.toString()} onChange={_onChangePrice}/>
                    <TextField label="Extra people cost" type="number" value={listing?.extraPeople.toString()} onChange={_onChangeExtraPeople}/>
                    <Dropdown label="Room type" defaultValue={listing?.type} options={typeOptions} onChange={_onChangeType} style={{minWidth: '10vw'}}/>
                    <TextField label="Beds" type="number" value={listing?.beds.toString()} onChange={_onChangeBeds}/>
                    <TextField label="Baths" type="number" value={listing?.baths.toString()} onChange={_onChangeBaths}/>
                    <TextField label="Square meters" type="number" value={listing?.meters.toString()} onChange={_onChangeMeters}/>
                </StackItem>
                <h3 className="section-header">Extras</h3>
                <StackItem className="extras-container">
                    <Checkbox label="Living room" onChange={_onChangeLiving}/>
                    <Checkbox label="Party" onChange={_onChangeParty}/>
                    <Checkbox label="Pets" onChange={_onChangePets}/>
                </StackItem>
                <h3 className="section-header">Summary</h3>
                <StackItem className="summary-container">
                    <TextField label="Summary" value={listing?.summary} onChange={_onChangeSummary} multiline className="summary-box"/>
                </StackItem>
                <h3 className="section-header">Images</h3>
                <StackItem className="images-container">
                    <input type="file" onChange={_onChangeImages} multiple/>
                </StackItem>
                <StackItem className="button-container">
                    <DefaultButton text="Update" onClick={_onClickUpdate}/>
                </StackItem>
            </Stack>
        </div>
    );
}

export default MyListing;