import {UserLogin} from "../models/UserLogin";
import ServiceEndpoints from "./ServiceEndpoints";

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

export function logout() {
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("jwt");
    sessionStorage.removeItem("roles");
}