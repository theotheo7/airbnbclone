import ServiceEndpoints from "./ServiceEndpoints";
import {IUserBasicDetails} from "../models/IUserBasicDetails";
import {getToken} from "./Authentication";
import {IUserCompleteDetails} from "../models/IUserCompleteDetails";

export async function fetchUsers(page: number) {
    return await fetch(ServiceEndpoints.FetchUsers + "?page=" + page, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + getToken(),
        }
    }).then(async (resp) => {
        const parsedData: IUserBasicDetails[] = await resp.json();
        return parsedData;
    })
        .catch((error) => {
        console.log(error);
        return [];
    });
}

export async function fetchUser(username: string) {
    return await fetch(ServiceEndpoints.FetchUser + "/" + username, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + getToken(),
        }
    }).then(async (resp) => {
        const parsedData: IUserCompleteDetails = await resp.json();
        return parsedData;
    })
        .catch((error) => {
            console.log(error);
            return [];
        });
}