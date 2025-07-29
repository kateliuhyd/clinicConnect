function initAppointmentPage() {
    const patientId = sessionStorage.getItem('userId');
    $.ajax({
        url: `/api/getAppointments?patientId=${patientId}`,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            var tableBody = $('#dataTableBody');
            tableBody.empty(); // Clear existing rows if any

            $.each(data, function (index, appts) {
                var row = '<tr>' +
                    '<td>' + appts.date + '</td>' +
                    '<td>' + appts.doctorFirstName + '</td>' +
                    '<td>' + appts.doctorLastName + '</td>' +
                    '<td>' + appts.specialization + '</td>' +
                    '</tr>';
                tableBody.append(row);
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error fetching data:", textStatus, errorThrown);
        }
    });

    $(document).ready(function() {
        $('#back-button').on('click', function() {
            window.location.hash = '#patient';
        });
    });

}



