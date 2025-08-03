function initAppointmentPage() {
    const patientId = sessionStorage.getItem('userId') ||
        localStorage.getItem('userId');
    if (!patientId) {
        alert('Please log in');
        window.location.hash = '#login';
        return;
    }

    const tableBody = $('#dataTableBody');
    tableBody.html('<tr><td colspan="4">Loading…</td></tr>');

    $.ajax({
        url: `/api/getAppointments?patientId=${patientId}`,
        method: 'GET',
        dataType: 'json',
        success(data) {
            console.log('Appointments JSON:', data);
            tableBody.empty();
            if (data.length === 0) {
                tableBody.append('<tr><td colspan="4">No appointments found.</td></tr>');
                return;
            }
            data.forEach(appt => {
                const dt = new Date(appt.dateTime).toLocaleString();
                tableBody.append(`
                  <tr>
                    <td>${dt}</td>
                    <td>${appt.doctorFirstName}</td>
                    <td>${appt.doctorLastName}</td>
                    <td>${appt.specialization}</td>
                  </tr>`);
            });
        },
        error(_, status) {
            tableBody.html(`
              <tr>
                <td colspan="4" class="text-danger">
                  Error fetching appointments: ${status}
                </td>
              </tr>`);
            console.error('Error fetching patient appointments:', status);
        }
    });

    $('#back-button').off('click').on('click', function() {
        window.location.hash = '#patient';
    });
}

// ─── Router Hooks ─────────────────────────────────────────────────
$(window).on('hashchange', () => {
    const hash = window.location.hash;
    if (hash === '#doctor-schedule') {
        initDoctorSchedulePage();
    } else if (hash === '#view-appointment') {
        initAppointmentPage();
    }
});

// If user opens the page directly with a matching hash:
if (window.location.hash === '#doctor-schedule') {
    initDoctorSchedulePage();
} else if (window.location.hash === '#view-appointment') {
    initAppointmentPage();
}