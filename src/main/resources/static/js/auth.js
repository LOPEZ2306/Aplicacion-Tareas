function login() {
    const username = document.getElementById("username");
    const password = document.getElementById("password");
    const error = document.getElementById("error");

    request("/auth/login", "POST", {
        username: username.value,
        password: password.value
    })
    .then(res => {
        localStorage.setItem("accessToken", res.token);
        localStorage.setItem("role", res.role);
        location.href = res.role === "ADMIN" ? "admin.html" : "user.html";
    })
    .catch(err => {
        error.innerText = "Authentication failed";
        console.error(err);
    });
}

function logout() {
    localStorage.clear();
    location.href = "login.html";
}

function checkAuth(requiredRole) {
    const token = localStorage.getItem("accessToken");
    const role = localStorage.getItem("role");

    if (!token) {
        location.href = "login.html";
        return;
    }

    if (requiredRole && role !== requiredRole) {
        location.href = role === "ADMIN" ? "admin.html" : "user.html";
    }
}
