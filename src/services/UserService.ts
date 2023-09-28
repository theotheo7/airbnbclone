import {UserLogin} from "../models/User/UserLogin";
import ServiceEndpoints from "./ServiceEndpoints";
import {IUserCompleteDetails} from "../models/User/IUserCompleteDetails";

export function storeUserInfo(username: string, jwt: string, roles: string[]) {
    sessionStorage.setItem("username", username);
    sessionStorage.setItem("jwt", jwt);
    sessionStorage.setItem("roles", JSON.stringify(roles));
}

export function getUsername() {
    return sessionStorage.getItem("username");
}

export function getRoles() {
    return sessionStorage.getItem("roles");
}

export function getToken() {
    return sessionStorage.getItem("jwt");
}

export async function register(formData: FormData) {
    return await fetch(ServiceEndpoints.Register, {
        method: 'POST',
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

export async function authenticate(userLogin:UserLogin) {
    return await fetch(ServiceEndpoints.Authenticate, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userLogin)
    })
        .then(async (resp) => {
            return await resp.json();
        })
        .catch((error) => {
            console.log(error.message);
        });
}

export async function fetchMyProfile(username: string) {
    return await fetch(ServiceEndpoints.FetchMyProfile + "/" + username, {
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

export async function updateMyProfile(formdata: FormData) {
    return await fetch(ServiceEndpoints.UpdateMyProfile, {
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + getToken(),
        },
        body: formdata
    }).then(async (resp) => {
        const parsedData: IUserCompleteDetails = await resp.json();
        return parsedData;
    })
        .catch((error) => {
            console.log(error);
            return [];
        });
}

export function logout() {
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("jwt");
    sessionStorage.removeItem("roles");
}