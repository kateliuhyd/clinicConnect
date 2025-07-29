function initPrescriptionPage() {
    const patientId = sessionStorage.getItem('userId');
    $.ajax({
        url: `/api/getPrescriptions?patientId=${patientId}`,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            var tableBody = $('#prescription-table-body');
            tableBody.empty(); // Clear existing rows if any

            $.each(data, function (index, presc) {
                var row = '<tr>' +
                    '<td>' + presc.medicineName + '</td>' +
                    '<td>' + presc.date + '</td>' +
                    '<td>' + presc.doctorFirstName + '</td>' +
                    '<td>' + presc.doctorLastName + '</td>' +
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
