import { Listing } from "../models/Listing";
import ServiceEndpoints from "./ServiceEndpoints";

export async function createListing(listing:Listing) {
    return await fetch(ServiceEndpoints.CreateListing, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXNhcmFza2FtIiwiaWF0IjoxNjkxNzQ4NDcxLCJleHAiOjE2OTE3NDk5MTF9.-amQyRGcvwd2htAVuEgrOoypkL0gDNZr6iirB3I1ONY'
        },
        body: JSON.stringify(listing)
    })
        .catch((error) => {
            console.log(error.message);
        });
}