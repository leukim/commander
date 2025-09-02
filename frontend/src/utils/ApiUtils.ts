const BACKEND_URL = 'http://localhost:8080';

const fetchWithTokenAndBody = async (
    path: string,
    token: string,
    method: string,
    body: {}
) => {
    return fetch(`${BACKEND_URL}${path}`, {
        method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Basic ${token}`,
        },
        body: JSON.stringify(body),
    });
}

const fetchWithToken = async (
    path: string,
    token: string,
    method = 'GET',
    body = {}
) => {
    if (method !== 'GET' && method !== 'HEAD') {
        return fetchWithTokenAndBody(path, token, method, body);
    }

    return fetch(`${BACKEND_URL}${path}`, {
        method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Basic ${token}`,
        }
    });
}

export {BACKEND_URL, fetchWithToken};