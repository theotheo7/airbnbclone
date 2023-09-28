import './MyListings.css'
import {useEffect, useState} from "react";
import {IListingBasicDetails} from "../../../models/Listing/IListingBasicDetails";
import {fetchListings} from "../../../services/HostService";
import {DefaultButton, Stack, StackItem} from "@fluentui/react";
import {useNavigate} from "react-router-dom";

function MyListings() {
    const [listings, setListings] = useState<IListingBasicDetails[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        (async function() {
            const result = await fetchListings();
            setListings(result);
            console.log(result);
        })();
    }, []);

    function _onClickView(name: string) {
        navigate("/host/listing/" + name);
    }

    function _onClickCreate() {
        navigate("/host/create");
    }

    return (
        <div>
            <Stack>
                <StackItem className="create-button-container">
                    <DefaultButton text="Create new listing" onClick={_onClickCreate}/>
                </StackItem>
                <StackItem className="listings-list-container">
                    {listings.map((listing) => (
                        <div className="listing-details-row" key={listing.name}>
                            <img src={require("../../../../../airbnbclone-backend/" + listing.image)} alt={listing.name} width={100} height={100}/>
                            <div>{listing.name}</div>
                            <DefaultButton text="View listing" onClick={() => _onClickView(listing.name)}/>
                        </div>
                    ))}
                </StackItem>
            </Stack>
        </div>
    );
}

export default MyListings;