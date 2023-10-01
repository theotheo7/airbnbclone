import ServiceEndpoints from "./ServiceEndpoints";
import {getToken} from "./UserService";
import {IListingBasicDetails} from "../models/Listing/IListingBasicDetails";

export async function book(id: number, fromDate: string, toDate: string) {
    return await fetch(ServiceEndpoints.Book + "/" + id + "?from=" + fromDate + "&to=" + toDate, {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + getToken(),
        }
    }).then(async (resp) => {
        return await resp.json();
    })
        .catch((error) => {
            console.log(error);
            return [];
        });
}

export async function fetchRecommendations() {
    return await fetch(ServiceEndpoints.FetchRecommendations, {
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