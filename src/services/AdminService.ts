import ServiceEndpoints from "./ServiceEndpoints";
import {IUserBasicDetails} from "../models/IUserBasicDetails";
import {getToken} from "./Authentication";

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