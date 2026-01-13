const API_BASE = "";

function request(url, method, body) {
    const token = localStorage.getItem("accessToken");

    const headers = {
        "Content-Type": "application/json"
    };

    if (token) {
        headers["Authorization"] = "Bearer " + token;
    }

    return fetch(API_BASE + url, {
        method,
        headers,
        body: body ? JSON.stringify(body) : null
    }).then(async res => {
        if (!res.ok) {
            const text = await res.text();
            throw new Error(`HTTP ${res.status}: ${text}`);
        }

        // Handle empty responses (like 204 NO CONTENT)
        const text = await res.text();
        return text ? JSON.parse(text) : {};
    });
}
