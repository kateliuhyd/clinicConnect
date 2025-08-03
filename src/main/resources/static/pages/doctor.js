function initDoctorPage() {
    const doctorId = sessionStorage.getItem('userId');
    if (!doctorId) {
        alert('Please login first');
        window.location.hash = '#login';
        return;
    }

    // clear the data
    $('#schedule-container').html(`
    <table class="table">
      <thead><tr><th>Time</th><th>Patient First</th><th>Patient Last</th><th>Patient ID</th></tr></thead>
      <tbody id="schedule-table-body"></tbody>
    </table>
  `);

    $('#viewScheduleBtn').off('click').on('click', function() {
        const tbody = $('#schedule-table-body');
        tbody.html('<tr><td colspan="4">Loading…</td></tr>');

        $.ajax({
            url: `/api/doctor-schedule?doctorId=${doctorId}`,
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                tbody.empty();
                if (data.length === 0) {
                    tbody.append('<tr><td colspan="4">No appointments</td></tr>');
                    return;
                }
                data.forEach(appt => {
                    const dt = new Date(appt.dateTime).toLocaleString();
                    const row = `
            <tr>
              <td>${dt}</td>
              <td>${appt.patientFirstName}</td>
              <td>${appt.patientLastName}</td>
              <td>${appt.patientId}</td>
            </tr>`;
                    tbody.append(row);
                });
            },
            error: function(_, status, err) {
                tbody.html(`<tr><td colspan="4" class="text-danger">Error: ${status}</td></tr>`);
                console.error('Error fetching schedule:', status, err);
            }
        });
    });
}
