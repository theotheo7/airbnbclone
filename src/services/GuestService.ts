import ServiceEndpoints from "./ServiceEndpoints";
import {getToken} from "./UserService";

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