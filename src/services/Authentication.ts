import {UserLogin} from "../models/UserLogin";
import ServiceEndpoints from "./ServiceEndpoints";
import {UserRegister} from "../models/UserRegister";

export function storeUserInfo(username: string, jwt: string) {
    sessionStorage.setItem("username", username);
    sessionStorage.setItem("jwt", jwt);
}

export function getUsername() {
    return sessionStorage.getItem("username");
}

export async function register(userRegister:UserRegister) {
    return await fetch(ServiceEndpoints.Register, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userRegister)
    })
        .then(async (resp) => {
            const response = await resp.json();
            console.log(response)
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
            const response = await resp.json();
            console.log(response)
            return response;
        })
        .catch((error) => {
            console.log(error.message);
        });
}

export function logout() {
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("jwt");
}