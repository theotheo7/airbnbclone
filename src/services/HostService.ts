import ServiceEndpoints from "./ServiceEndpoints";
import {getToken} from "./UserService";
import {IListingBasicDetails} from "../models/Listing/IListingBasicDetails";
import {IListingCompleteDetails} from "../models/Listing/IListingCompleteDetails";

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

export async function fetchListing(name: string) {
    return await fetch(ServiceEndpoints.FetchListing + "/" + name, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + getToken()
        }
    }).then(async (resp) => {
        const parsedData: IListingCompleteDetails = await resp.json();
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

export async function updateListing(formData: FormData) {
    return await fetch(ServiceEndpoints.UpdateListing, {
        method: 'PUT',
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