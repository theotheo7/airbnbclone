import {UserLogin} from "../models/User/UserLogin";
import ServiceEndpoints from "./ServiceEndpoints";
import {IUserCompleteDetails} from "../models/User/IUserCompleteDetails";
import {ReceivedMessage} from "../models/Message/ReceivedMessage";
import {SentMessage} from "../models/Message/SentMessage";
import {MessagePosting} from "../models/Message/MessagePosting";

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

export async function sendMessage(message: MessagePosting) {
    return await fetch(ServiceEndpoints.SendMessage + "/" + getUsername(), {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + getToken(),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(message)
    }).then(async (resp) => {
        return await resp.json();
    })
        .catch((error) => {
            console.log(error);
            return [];
        });
}

export async function fetchReceivedMessages(page: number) {
    return await fetch(ServiceEndpoints.SendMessage + "/" + getUsername() + "?sent=" + false + "&page=" + page, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + getToken(),
        }
    }).then(async (resp) => {
        const parsedData: ReceivedMessage[] = await resp.json();
        return parsedData;
    })
        .catch((error) => {
            console.log(error);
            return [];
        });
}

export async function fetchSentMessages(page: number) {
    return await fetch(ServiceEndpoints.SendMessage + "/" + getUsername() + "?sent=" + true + "&page=" + page, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + getToken(),
        }
    }).then(async (resp) => {
        const parsedData: SentMessage[] = await resp.json();
        return parsedData;
    })
        .catch((error) => {
            console.log(error);
            return [];
        });
}

export async function deleteMessage(id: number) {
    return await fetch(ServiceEndpoints.DeleteMessage + "/" + getUsername() + "/" + id, {
        method: 'DELETE',
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

export async function readMessage(id: number) {
    return await fetch(ServiceEndpoints.ReadMessage + "/" + getUsername() + "/" + id, {
        method: 'PUT',
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

export function logout() {
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("jwt");
    sessionStorage.removeItem("roles");
}