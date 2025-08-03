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
    $('#history-error').remove();

    // Load visit history when user clicks "Load History"
    $('#load-history').off('click').on('click', () => {
        const patientId = $('#patient-id').val().trim();
        if (!patientId) {
            alert('Please enter a Patient ID');
            return;
        }

        // Fetch visit summary
        $.ajax({
            url: `/api/getVisitSummary?patientId=${patientId}`,
            method: 'GET',
            dataType: 'json',
            success(data) {
                const list = $('#visit-list');
                list.empty();

                if (data.length === 0) {
                    list.append('<p>No visit history found.</p>');
                    return;
                }

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
            },
            error(xhr, status) {
                $('#visit-list').html(`
                  <p id="history-error" class="text-danger">
                    Error loading history: ${status}
                  </p>`);
                console.error('Error fetching visit history:', status, xhr);
            }
        });
    });

    // Handle new prescription form submission
    $('#prescription-form').off('submit').on('submit', function(e) {
        e.preventDefault();

        const patientId       = $('#patient-id').val().trim();
        const medicineName    = $('#medicine-name').val().trim();
        const prescriptionDate= $('#prescription-date').val(); // "YYYY-MM-DD"

        if (!patientId || !medicineName || !prescriptionDate) {
            alert('Please fill out all fields');
            return;
        }

        // Convert the YYYY-MM-DD string into an ISO string
        const dateObj = new Date(prescriptionDate);
        const isoDate = dateObj.toISOString();

        const payload = {
            patientId:    patientId,
            doctorId:     doctorId,
            medicineName: medicineName,
            date:         isoDate    // <-- send ISO string so Jackson populates java.util.Date
        };

        $.ajax({
            url: '/api/prescriptions',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(payload),
            success() {
                alert('Prescription submitted successfully');
                $('#medicine-name').val('');
                $('#prescription-date').val('');
            },
            error(xhr, status) {
                alert('Error submitting prescription: ' + status);
                console.error('Error adding prescription:', status, xhr);
            }
        });
    });
}

// Router hookup
$(window).on('hashchange', () => {
    if (window.location.hash === '#doctor-history') {
        initDoctorHistoryPage();
    }
});

// If page loaded directly at #doctor-history
if (window.location.hash === '#doctor-history') {
    initDoctorHistoryPage();
}
