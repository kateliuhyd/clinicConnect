function initVisitPage() {
    const patientId = sessionStorage.getItem('userId');
    $.ajax({
        url: `/api/getVisitSummary?patientId=${patientId}`,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            var tableBody = $('#visit-table-body');
            tableBody.empty(); // Clear existing rows if any

            $.each(data, function (index, visit) {
                var row = '<tr>' +
                    '<td>' + visit.apptDate + '</td>' +
                    '<td>' + visit.apptSummary + '</td>' +
                    '<td>' + visit.doctorFirstName + '</td>' +
                    '<td>' + visit.doctorLastName + '</td>' +
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