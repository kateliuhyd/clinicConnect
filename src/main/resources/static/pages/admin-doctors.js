function initAdminDoctorsPage() {
    loadLookupData();
    loadDoctors();

    $('#doctor-form').off('submit').on('submit', function(e) {
        e.preventDefault();
        const d = {
            id: $('#doc-id').val().trim(),
            firstName: $('#doc-first').val().trim(),
            lastName:  $('#doc-last').val().trim(),
            deptId:    +$('#dept-select').val(),
            specializationId: $('#spec-select').val()
        };
        $.ajax({
            url: '/api/admin/doctors',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(d)
        }).done(() => {
            loadDoctors();
            $('#doctor-form')[0].reset();
        });
    });
}

function loadLookupData() {
    $.getJSON('/api/admin/departments', data => {
        const sel = $('#dept-select').empty().append('<option value="">Dept</option>');
        data.forEach(d => sel.append(`<option value="${d.deptId}">${d.deptName}</option>`));
    });
    $.getJSON('/api/admin/specializations', data => {
        const sel = $('#spec-select').empty().append('<option value="">Spec</option>');
        data.forEach(s => sel.append(`<option value="${s.specializationId}">${s.specializationName}</option>`));
    });
}

function loadDoctors() {
    const tbody = $('#doctors-body').empty();
    $.getJSON('/api/admin/doctors', data => {
        data.forEach(d => {
            tbody.append(`
        <tr>
          <td>${d.docId}</td>
          <td>${d.firstName} ${d.lastName}</td>
          <td>${d.deptId}</td>
          <td>${d.specializationName}</td>
          <td>
            <button class="btn btn-sm btn-danger delete" data-id="${d.docId}">
              Delete
            </button>
          </td>
        </tr>`);
        });

        // wire up delete buttons
        tbody.find('.delete').off('click').on('click', function() {
            const id = $(this).data('id');
            $.ajax({
                url: `/api/admin/doctors/${id}`,
                method: 'DELETE'
            }).done(loadDoctors);
        });
    });
}
