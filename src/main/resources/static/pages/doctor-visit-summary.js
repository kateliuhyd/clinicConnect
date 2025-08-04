// static/pages/doctor-visit-summary.js

function initDoctorVisitSummaryPage() {
    const doctorId = sessionStorage.getItem('userId');
    if (!doctorId) {
        alert('Please log in');
        window.location.hash = '#login';
        return;
    }

    const $select = $('#appt-select');
    const $summary = $('#visit-summary');

    // 1) Load upcoming appointments for this doctor
    $.ajax({
        url: `/api/doctor-schedule?doctorId=${doctorId}`,
        method: 'GET',
        dataType: 'json',
        success(data) {
            $select.empty().append('<option value="">-- select appointment --</option>');
            data.forEach(appt => {
                const dt = new Date(appt.dateTime).toLocaleString();
                // show patient + date
                const label = `${dt} — ${appt.patientFirstName} ${appt.patientLastName}`;
                $select.append(`<option value="${appt.apptId}">${label}</option>`);
            });
        },
        error(_, status) {
            alert('Error loading appointments: ' + status);
        }
    });

    // 2) Save summary
    $('#save-summary').off('click').on('click', () => {
        const apptId = $select.val();
        const text = $summary.val().trim();
        if (!apptId || !text) {
            alert('Please select an appointment and enter a summary.');
            return;
        }

        $.ajax({
            url: `/api/appointments/${apptId}/summary`,
            method: 'PUT',
            contentType: 'text/plain',
            data: text
        })
            .done(() => {
                alert('Summary saved');
                window.location.hash = '#doctor';
            })
            .fail((xhr, status) => {
                alert('Error saving summary: ' + status);
            });
    });
}

// router hook
$(window).on('hashchange', () => {
    if (window.location.hash === '#doctor-write-summary') {
        initDoctorVisitSummaryPage();
    }
});
if (window.location.hash === '#doctor-write-summary') {
    initDoctorVisitSummaryPage();
}
