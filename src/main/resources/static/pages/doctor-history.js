// pages/doctor-history.js

function initDoctorHistoryPage() {
    const doctorId = sessionStorage.getItem('userId');
    if (!doctorId) {
        alert('Please log in');
        window.location.hash = '#login';
        return;
    }

    // Clear any previous results
    $('#visit-list').empty();
    $('#record-list').remove();
    $('#history-error').remove();

    // Wire up the Load History button
    $('#load-history').off('click').on('click', () => {
        const patientId = $('#patient-id').val().trim();
        if (!patientId) {
            alert('Please enter a Patient ID');
            return;
        }

        // 1) Fetch visit summary
        $.ajax({
            url: `/api/getVisitSummary?patientId=${patientId}`,
            method: 'GET',
            dataType: 'json'
        })
            .done(data => {
                const list = $('#visit-list').empty();
                if (data.length === 0) {
                    list.append('<p>No visit history found.</p>');
                } else {
                    data.forEach(v => {
                        const when = new Date(v.apptDate).toLocaleString();
                        list.append(`
            <div class="card mb-2">
              <div class="card-body">
                <h5 class="card-title">${when}</h5>
                <p class="card-text">${v.apptSummary || 'No summary available.'}</p>
              </div>
            </div>`);
                    });
                }
            })
            .fail((xhr, status) => {
                $('#visit-list').html(`
        <p id="history-error" class="text-danger">
          Error loading history: ${status}
        </p>`);
                console.error('Error fetching visit history:', status, xhr);
            })
            .always(() => {
                // 2) Once visits are rendered (or errored), fetch medical record
                $.ajax({
                    url: `/api/medical-record?patientId=${patientId}`,
                    method: 'GET',
                    dataType: 'json'
                })
                    .done(record => {
                        $('#visit-list').after(`
          <div id="record-list" class="mb-5">
            <h4>Medical Record</h4>
            <p><strong>Medications:</strong> ${record.medicineList}</p>
          </div>`);
                    })
                    .fail(() => {
                        $('#visit-list').after(`
          <div id="record-list" class="mb-5">
            <h4>Medical Record</h4>
            <p class="text-warning">No medical record found.</p>
          </div>`);
                    });
            });
    });

    // Prescription form submission stays unchanged
    $('#prescription-form').off('submit').on('submit', function(e) {
        /* …your existing prescription code… */
    });
}

// Router hookup
$(window).on('hashchange', () => {
    if (window.location.hash === '#doctor-history') {
        initDoctorHistoryPage();
    }
});
if (window.location.hash === '#doctor-history') {
    initDoctorHistoryPage();
}
