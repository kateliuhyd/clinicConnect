// pages/doctor-history.js

// Initialize the Doctor History page
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

        const patientId = $('#patient-id').val().trim();
        if (!patientId) {
            alert('Please enter a Patient ID');
            return;
        }

        const medicineName    = $('#medicine-name').val().trim();
        const prescriptionDate = $('#prescription-date').val(); // yyyy-MM-dd

        if (!medicineName || !prescriptionDate) {
            alert('Please fill out all prescription fields');
            return;
        }

        const payload = {
            medicineName: medicineName,
            prescriptionDate: prescriptionDate,
            patientId: patientId,
            doctorId: doctorId
        };

        $.ajax({
            url: '/api/prescriptions',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(payload),
            success() {
                alert('Prescription submitted successfully');
                // Optionally clear form
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

// Wire up router to initialize when hash changes
$(window).on('hashchange', () => {
    if (window.location.hash === '#doctor-history') {
        initDoctorHistoryPage();
    }
});

// If user loads this page directly with #doctor-history
if (window.location.hash === '#doctor-history') {
    initDoctorHistoryPage();
}
