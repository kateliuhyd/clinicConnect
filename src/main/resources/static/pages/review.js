function initBookAppointmentPage() {
    let selectedDoctorId = null;
    let selectedDoctorName = null;
    let selectedDate = null;
    let selectedTime = null;

    const departmentSelect = $('#department-select');
    const doctorList = $('#doctor-list');
    const datePicker = $('#date-picker');
    const timeSlotsContainer = $('#time-slots-container');
    const timeSlots = $('#time-slots');
    const confirmSection = $('#step-4-confirm');

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
        $('#step-3-availability, #step-4-confirm').hide();
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

    // Event: Doctor selected
    doctorList.on('click', '.doctor-select-item', function(e) {
        e.preventDefault();
        selectedDoctorId = $(this).data('doctor-id');
        selectedDoctorName = $(this).data('doctor-name');
        $('#selected-doctor-name').text(selectedDoctorName);
        $('#step-3-availability').show();
        $('#step-4-confirm').hide();
        timeSlotsContainer.hide();
        datePicker.val('');
        datePicker.attr('min', new Date().toISOString().split('T')[0]);
    });

    // Event: Date changed
    datePicker.on('change', function() {
        selectedDate = $(this).val();
        if (!selectedDate || !selectedDoctorId) return;
        timeSlotsContainer.show();
        timeSlots.html('<p class="text-muted">Fetching availability...</p>');
        $('#step-4-confirm').hide();

        // Step 3: Fetch availability
        $.ajax({
            url: `/api/doctors/${selectedDoctorId}/availability?date=${selectedDate}`,
            type: 'GET',
            success: function(slots) {
                timeSlots.empty();
                if (slots.length === 0) {
                    timeSlots.html('<p class="text-danger">No available slots for this day.</p>');
                    return;
                }
                slots.forEach(slot => {
                    timeSlots.append(`<button class="btn btn-outline-primary time-slot-btn">${slot}</button>`);
                });
            }
        });
    });

    // Event: Time slot selected
    timeSlots.on('click', '.time-slot-btn', function() {
        selectedTime = $(this).text();
        $('.time-slot-btn').removeClass('btn-primary').addClass('btn-outline-primary');
        $(this).removeClass('btn-outline-primary').addClass('btn-primary');

        $('#confirm-doctor').text(selectedDoctorName);
        $('#confirm-date').text(selectedDate);
        $('#confirm-time').text(selectedTime);
        confirmSection.show();
    });

    // Event: Final booking confirmation
    confirmSection.on('click', '#book-now-btn', function() {
        const patientId = sessionStorage.getItem('userId');
        if (!patientId || !selectedDoctorId || !selectedDate || !selectedTime) {
            alert('An error occurred. Please start over.');
            return;
        }



        const requestData = {
            docId: selectedDoctorId
        };

        // Step 4: Book the appointment
        $.ajax({
            url: '/api/getDoctorReviews',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(requestData),
            success: function() {
                alert('Appointment successfully booked!');
                window.location.hash = '#patient';
            },
            error: function(xhr) {
                alert('An error occurred while booking. Please try again.');
            }
        });
    });
}