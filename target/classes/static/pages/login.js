document.addEventListener("DOMContentLoaded", function () {
    console.log("Ok! login.js loaded (no jQuery)");

    document.getElementById("login-form").addEventListener("submit", function (e) {
        e.preventDefault();

        const userId = document.getElementById("userId").value;
        const password = document.getElementById("password").value;

        fetch("/api/user/login/validate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",  // ⬅ same as xhrFields.withCredentials = true
            body: JSON.stringify({ userId, password })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Login failed");
                }
                return response.json();
            })
            .then(data => {
                sessionStorage.setItem("userId", data.userId);
                sessionStorage.setItem("userType", data.userType);

                if (data.userType === "doctor") {
                    window.location.href = "/pages/doctor.html";
                } else if (data.userType === "patient") {
                    window.location.href = "/pages/patient.html";
                } else {
                    alert("Unknown user type");
                }
            })
            .catch(err => {
                const errorDiv = document.getElementById("login-error");
                errorDiv.style.display = "block";
                errorDiv.innerText = "Login failed. Please check your credentials.";
            });
    });
});
