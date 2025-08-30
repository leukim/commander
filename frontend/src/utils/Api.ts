import {StatusType} from "../types/StatusType";

const BACKEND_URL = 'http://localhost:8080';

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
            body: JSON.stringify({ username, password })
        })
            .then(resolve)
    });


export default {getStatus, install};