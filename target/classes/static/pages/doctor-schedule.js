
// Doctor Schedule
function initDoctorSchedulePage() {
    const doctorId =
        sessionStorage.getItem('userId') ||
        localStorage.getItem('userId');
    if (!doctorId) {
        alert('Please log in');
        window.location.hash = '#login';
        return;
    }

    const tbody = $('#schedule-table-body');
    tbody.html('<tr><td colspan="4">Loading…</td></tr>');

    $.ajax({
        url: `/api/doctor-schedule?doctorId=${doctorId}`,
        method: 'GET',
        dataType: 'json',
        success(data) {
            console.log('Schedule JSON:', data);
            tbody.empty();
            if (data.length === 0) {
                tbody.append('<tr><td colspan="4">No appointments found.</td></tr>');
                return;
            }
            data.forEach(appt => {
                const dt = new Date(appt.dateTime).toLocaleString();
                tbody.append(`
                    <tr>
                      <td>${dt}</td>
                      <td>${appt.patientFirstName}</td>
                      <td>${appt.patientLastName}</td>
                      <td>${appt.patientId}</td>
                    </tr>`);
            });
        },
        error(_, status) {
            tbody.html(`
              <tr>
                <td colspan="4" class="text-danger">
                  Error loading schedule: ${status}
                </td>
              </tr>`);
            console.error('Error fetching doctor schedule:', status);
        }
    });
}