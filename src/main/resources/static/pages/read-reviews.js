
function initReadReviewPage() {
    let selectedDoctorId = null;
    let selectedDoctorName = null;
    let selectedDate = null;
    let selectedTime = null;
    let rating = null;
    let review = null;

    const departmentSelect = $('#department-select');
    const doctorList = $('#doctor-list');

    const ReadeReview = $('#step-3-Read-Review');

    // Step 1: Fetch and populate departments
    $.ajax({
        url: '/api/departments',
        type: 'GET',
        success: function(departments) {
            departmentSelect.empty().append('<option selected disabled>Select a department...</option>');
            departments.forEach(dept => {
                departmentSelect.append(`<option value="${dept.deptId}">${dept.deptName}</option>`);
            });
        },
        error: function() {
            departmentSelect.empty().append('<option selected disabled>Error loading departments</option>');
        }
    });

    // Event: Department changed
    departmentSelect.on('change', function() {
        const deptId = $(this).val();
        $('#step-2-doctor').show();
        $('#step-3-read, #step-4-write').hide();
        doctorList.html('<p class="text-muted">Loading doctors...</p>');

        // Step 2: Fetch doctors for the selected department
        $.ajax({
            url: `/api/doctors/department/${deptId}`,
            type: 'GET',
            success: function(doctors) {
                doctorList.empty();
                if (doctors.length === 0) {
                    doctorList.html('<p class="text-muted">No doctors found in this department.</p>');
                    return;
                }
                doctors.forEach(doc => {
                    const doctorCard = `
                        <a href="#" class="list-group-item list-group-item-action doctor-select-item" data-doctor-id="${doc.id}" data-doctor-name="Dr. ${doc.firstName} ${doc.lastName}">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">Dr. ${doc.firstName} ${doc.lastName}</h5>
                            </div>
                            <p class="mb-1">${doc.specialization}</p>
                        </a>`;
                    doctorList.append(doctorCard);
                });
            }
        });
    });


    doctorList.on('click', '.doctor-select-item', function(e) {
        e.preventDefault();
        selectedDoctorId = $(this).data('doctor-id');
        selectedDoctorName = $(this).data('doctor-name');
        $('#selected-doctor-name').text(selectedDoctorName);
        $('#step-3-Read-Review').show();

        $.ajax({
            url: `/api/getReviewDetails?doctorId=${selectedDoctorId}`,
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                var tableBody = $('#review-table-body');
                tableBody.empty(); // Clear existing rows if any

                $.each(data, function (index, reviews) {
                    var row = '<tr>' +
                        '<td>' + reviews.reviewDate + '</td>' +
                        '<td>' + reviews.comment + '</td>' +
                        '<td>' + reviews.rating + '</td>' +
                        '<td>' + reviews.patientFirstName + '</td>' +
                        '<td>' + reviews.patientLastName + '</td>' +
                        '</tr>';
                        tableBody.append(row);
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.error("Error fetching data:", textStatus, errorThrown);
                }
            });

        });

    $(document).ready(function() {
        $('#back-button').on('click', function() {
            window.location.hash = '#patient';
        });
    });

}