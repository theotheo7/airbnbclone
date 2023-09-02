import { Listing } from "../models/Listing";
import ServiceEndpoints from "./ServiceEndpoints";

export async function createListing(listing:Listing) {
    return await fetch(ServiceEndpoints.CreateListing, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXNhcmFza2FtIiwiaWF0IjoxNjkzNTY1NTQ3LCJleHAiOjE2OTM1NjY5ODd9.w37I3sKa-tshk6iffMWkTrXae_Xq_CvmQQIOpf6Wc5Q'
        },
        body: JSON.stringify(listing)
    })
        .catch((error) => {
            console.log(error.message);
        });
}