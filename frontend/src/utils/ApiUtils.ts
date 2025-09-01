const BACKEND_URL = 'http://localhost:8080';

const fetchWithToken = async (path, token, method = 'GET') => {
    return fetch(`${BACKEND_URL}${path}`, {
        method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Basic ${token}`,
        }
    });
}

export { BACKEND_URL, fetchWithToken };