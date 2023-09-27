import ServiceEndpoints from "./ServiceEndpoints";
import {getToken} from "./Authentication";
import {IListingBasicDetails} from "../models/IListingBasicDetails";

export async function fetchListings() {
    return await fetch(ServiceEndpoints.FetchListings + "?page=1", {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + getToken()
        }
    }).then(async (resp) => {
        const parsedData: IListingBasicDetails[] = await resp.json();
        return parsedData;
    }).catch((error) => {
        console.log(error);
        return [];
    });
}

export async function createListing(formData: FormData) {
    return await fetch(ServiceEndpoints.CreateListing, {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + getToken()
        },
        body: formData
    })
        .then(async (resp) => {
            const response = await resp.json();
            console.log(response);
            return response;
        })
        .catch((error) => {
            console.log(error.message);
        });
}