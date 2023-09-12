import { Listing } from "../models/Listing";
import ServiceEndpoints from "./ServiceEndpoints";

export async function createListing(listing:Listing) {
    const jwt = sessionStorage.getItem("token");
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