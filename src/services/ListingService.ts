import { Listing } from "../models/Listing";
import ServiceEndpoints from "./ServiceEndpoints";
import {getToken} from "./Authentication";

export async function createListing(listing:Listing) {
    return await fetch(ServiceEndpoints.CreateListing, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + getToken(),
        },
        body: JSON.stringify(listing)
    })
        .catch((error) => {
            console.log(error.message);
        });
}