import {StatusType} from "../types/StatusType";
import {BACKEND_URL} from "./ApiUtils";

const getStatus = async () =>
    new Promise<StatusType>((resolve, reject) => {
        fetch(`${BACKEND_URL}/status`)
            .then(res => res.json()).then(resolve);
    });

const install = async (username: string, password: string) =>
    new Promise((resolve, reject) => {
        fetch(`${BACKEND_URL}/install`, {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({username, password})
        })
            .then(resolve)
    });

const login = async (username: string, password: string) : Promise<string> =>
    new Promise((resolve, reject) => {
        fetch(`${BACKEND_URL}/login`, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Basic " + btoa(`${username}:${password}`),
            }
        }).then(response => {
            if (!response.ok) {
                reject(response);
            }
            resolve(btoa(`${username}:${password}`))
        })
    });


export default {getStatus, install, login};