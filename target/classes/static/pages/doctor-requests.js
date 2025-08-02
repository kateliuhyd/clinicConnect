// pages/doctor-requests.js

function initDoctorRequestsPage() {
    const doctorId = sessionStorage.getItem('userId');
    if (!doctorId) {
        alert('Please log in');
        window.location.hash = '#login';
        return;
    }

    const tbody = $('#requests-body');
    tbody.html('<tr><td colspan="3">Loading…</td></tr>');

    // 1) Load pending requests
    $.ajax({
        url: `/api/appointment-requests?doctorId=${doctorId}`,
        method: 'GET',
        dataType: 'json',
        success(data) {
            tbody.empty();
            if (data.length === 0) {
                tbody.append('<tr><td colspan="3">No requests.</td></tr>');
                return;
            }

            // 2) Render each row with buttons
            data.forEach(req => {
                const dt = new Date(req.dateTime).toLocaleString();
                tbody.append(`
          <tr>
            <td>${dt}</td>
            <td>${req.patientFirstName} ${req.patientLastName}</td>
            <td>
              <button class="btn btn-success btn-sm accept" data-id="${req.apptId}">
                Accept
              </button>
              <button class="btn btn-danger btn-sm reject" data-id="${req.apptId}">
                Reject
              </button>
            </td>
          </tr>`);
            });

            // 3) Wire up button handlers via delegation on tbody
            tbody.off('click', '.accept').on('click', '.accept', function() {
                const apptId = $(this).data('id');
                updateRequestStatus(apptId, 'CONFIRMED');
            });
            tbody.off('click', '.reject').on('click', '.reject', function() {
                const apptId = $(this).data('id');
                updateRequestStatus(apptId, 'REJECTED');
            });
        },
        error(xhr, status) {
            tbody.html(`<tr><td colspan="3" class="text-danger">
        Error loading requests: ${status}
      </td></tr>`);
        }
    });
}

// Helper to POST status change, then reload list
function updateRequestStatus(apptId, status) {
    $.ajax({
        url: `/api/appointment-requests/${apptId}/status?status=${status}`,
        method: 'POST',
        dataType: 'text'
    })
        .done(() => {
            // Refresh the table so the resolved request disappears
            initDoctorRequestsPage();
        })
        .fail((xhr, status) => {
            alert(`Failed to ${status === 'CONFIRMED' ? 'accept' : 'reject'}: ${status}`);
            console.error('Error updating status:', status, xhr);
        });
}

// Router hookup
$(window).on('hashchange', () => {
    if (window.location.hash === '#doctor-requests') {
        initDoctorRequestsPage();
    }
});
if (window.location.hash === '#doctor-requests') {
    initDoctorRequestsPage();
}
