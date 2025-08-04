function initAdminPatientsPage() {
    loadPatients();

    $('#patient-form').off('submit').on('submit', function(e) {
        e.preventDefault();
        const p = {
            patientId: $('#patient-id').val().trim(),
            firstName: $('#first-name').val().trim(),
            lastName:  $('#last-name').val().trim()
            //...
        };
        $.ajax({
            url: '/api/admin/patients',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(p)
        }).done(loadPatients);
    });
}

function loadPatients() {
    const tbody = $('#patients-body').empty();
    $.getJSON('/api/admin/patients', data => {
        data.forEach(p => {
            tbody.append(`
        <tr>
          <td>${p.patientId}</td>
          <td>${p.firstName}</td>
          <td>${p.lastName}</td>
          <td>
            <button class="btn btn-sm btn-danger delete" data-id="${p.patientId}">
              Delete
            </button>
          </td>
        </tr>`);
        });
        tbody.find('.delete').off('click').on('click', function() {
            const id = $(this).data('id');
            $.ajax({
                url: `/api/admin/patients/${id}`,
                method: 'DELETE'
            }).done(loadPatients);
        });
    });
}
