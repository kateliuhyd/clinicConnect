$(document).ready(function() {

    const appContainer = $('#app-container');
    const mainNavbar = $('.navbar');
    const userInfoSpan = $('#user-info');

    // --- CLIENT-SIDE ROUTER ---
    const routes = {
        '#login': 'pages/login.html',
        '#admin': 'pages/admin.html',
        '#doctor': 'pages/doctor.html',
        '#patient': 'pages/patient.html',
        '#book-appointment': 'pages/book-appointment.html',
        '#view-appointment': 'pages/patient-appointment.html',
        '#view-prescription': 'pages/prescription.html',
        '#view-visit-summary': 'pages/visit.html'
    };

    // The router will now call the initializer for the booking page directly.
    const pageInitializers = {
        '#login': initLoginPage,
        '#book-appointment': initBookAppointmentPage,
        '#view-appointment': initAppointmentPage,
        '#view-prescription': initPrescriptionPage,
        '#view-visit-summary': initVisitPage
    };

    function router() {
        const path = window.location.hash || '#login';
        if (path === '#login') { mainNavbar.hide(); } else { mainNavbar.show(); fetchUserProfile(); }

        const pageFile = routes[path];
        if (pageFile) {
            appContainer.load(pageFile, function(response, status) {
                if (status == "error") { appContainer.html("<h2>Error 404</h2>"); return; }

                // If an initializer function exists for the loaded page, run it.
                if (pageInitializers[path]) {
                    pageInitializers[path]();
                }
            });
        } else {
            window.location.hash = '#login';
        }
    }

    // --- USER PROFILE & SESSION ---
    function fetchUserProfile() {
        const userId = sessionStorage.getItem('userId');
        const userType = sessionStorage.getItem('userType');
        if (!userId || !userType) {
            window.location.hash = '#login';
            return;
        }
        $.ajax({
            url: `/api/user/${userType}/${userId}`,
            success: function(profile) {
                userInfoSpan.text(`Welcome, ${profile.firstName} ${profile.lastName}`);
            },
            error: function() {
                alert('Your session is invalid. Please log in again.');
                clearSession();
                window.location.hash = '#login';
            }
        });
    }

    function clearSession() {
        sessionStorage.removeItem('userId');
        sessionStorage.removeItem('userType');
        userInfoSpan.text('');
    }

    // --- GLOBAL EVENT LISTENERS ---
    mainNavbar.on('click', '#logout-btn', function(e) { e.preventDefault(); clearSession(); window.location.hash = '#login'; });
    $(window).on('hashchange', router);
    router();

    // --- PAGE-SPECIFIC INITIALIZATION ---
    function initLoginPage() {
        $('#login-form').on('submit', function(e) {
            e.preventDefault();
            const userId = $('#userId').val();
            const password = $('#password').val();
            const loginErrorDiv = $('#login-error');
            loginErrorDiv.hide();

            if (!userId || !password) {
                loginErrorDiv.text('Please enter both User ID and password.').show();
                return;
            }

            $.ajax({
                url: '/api/user/login/validate',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ userId: userId, password: password }),
                success: function(response) {
                    sessionStorage.setItem('userId', response.userId);
                    sessionStorage.setItem('userType', response.userType);
                    const role = response.userType.toLowerCase();
                    if (routes['#' + role]) {
                        window.location.hash = '#' + role;
                    } else {
                        loginErrorDiv.text('Invalid user role received.').show();
                    }
                },
                error: function() {
                    loginErrorDiv.text('Invalid credentials. Please try again.').show();
                }
            });
        });
    }
});
