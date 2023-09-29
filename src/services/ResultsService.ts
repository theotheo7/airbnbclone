import ServiceEndpoints from "./ServiceEndpoints";
import {IListingBasicDetails} from "../models/Listing/IListingBasicDetails";

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