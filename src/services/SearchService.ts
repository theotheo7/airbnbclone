import ServiceEndpoints from "./ServiceEndpoints";
import {IListingBasicDetails} from "../models/Listing/IListingBasicDetails";
import {IListingCompleteDetails} from "../models/Listing/IListingCompleteDetails";

export async function fetchResults(urlQuery: string, page: number) {
    return await fetch(ServiceEndpoints.FetchResults + "?page=" + page + urlQuery, {
        method: 'GET',
    }).then(async (resp) => {
        const parsedData: IListingBasicDetails[] = await resp.json();
        return parsedData;
    })
        .catch((error) => {
            console.log(error);
            return [];
        });
}

export async function fetchListingFromSearch(id: string) {
    return await fetch(ServiceEndpoints.FetchListingFromSearch + "/" + id, {
        method: 'GET'
    }).then(async (resp) => {
        const parsedData: IListingCompleteDetails = await resp.json();
        return parsedData;
    }).catch((error) => {
        console.log(error);
        return [];
    });
}