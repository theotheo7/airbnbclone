import { Listing } from "../models/Listing";
import ServiceEndpoints from "./ServiceEndpoints";
import {getToken} from "./Authentication";

export async function createListing(listing:Listing) {
    const jwt = getToken();
    return await fetch(ServiceEndpoints.CreateListing, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + jwt,
        },
        body: JSON.stringify(listing)
    })
        .catch((error) => {
            console.log(error.message);
        });
}