import {UserLogin} from "../models/UserLogin";
import ServiceEndpoints from "./ServiceEndpoints";

export async function authenticate(userLogin:UserLogin) {
    return await fetch(ServiceEndpoints.Authenticate, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userLogin)
    })
        .then(async (resp) => {
            const response = await resp.json();
            console.log(response)
            sessionStorage.setItem("token", response.token);
            return response;
        })
        .catch((error) => {
            console.log(error.message);
        });
}