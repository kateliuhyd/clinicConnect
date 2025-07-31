package edu.sjsu.dbms.clinicconnect5.controller;

import edu.sjsu.dbms.clinicconnect5.dao.AppointmentDAO;
import edu.sjsu.dbms.clinicconnect5.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import edu.sjsu.dbms.clinicconnect5.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class AppointmentController {


    private final AppointmentDAO appointmentDao;

    public AppointmentController(AppointmentDAO appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    /**
     * Fetches a list of all medical departments.
     * @return A list of Department objects.
     */
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = appointmentDao.findAllDepartments();
        return ResponseEntity.ok(departments);
    }

    /**
     * Fetches a list of doctors belonging to a specific department.
     * @param deptId The ID of the department.
     * @return A list of DoctorProfile objects.
     */
    @GetMapping("/doctors/department/{deptId}")
    public ResponseEntity<List<DoctorProfile>> getDoctorsByDepartment(@PathVariable Integer deptId) {
        List<DoctorProfile> doctors = appointmentDao.findDoctorsByDepartment(deptId);
        return ResponseEntity.ok(doctors);
    }

    /**
     * Generates and returns available hourly time slots for a doctor on a given date.
     * @param doctorId The ID of the doctor.
     * @param dateString The date in 'YYYY-MM-DD' format.
     * @return A list of available time slots.
     */
    @GetMapping("/doctors/{doctorId}/availability")
    public ResponseEntity<List<String>> getDoctorAvailability(@PathVariable String doctorId, @RequestParam("date") String dateString) {
        LocalDate date = LocalDate.parse(dateString);

        // Get all times that are already booked for that day.
        List<LocalTime> bookedTimes = appointmentDao.findBookedAppointmentTimes(doctorId, date)
                .stream()
                .map(LocalDateTime::toLocalTime)
                .toList();

        // Generate all possible hourly slots for a workday (8 AM to 4 PM).
        List<LocalTime> allPossibleSlots = Stream.iterate(LocalTime.of(8, 0), time -> time.plusHours(1))
                .limit(9)
                .toList();

        // 3. Filter out the booked slots to find what's available.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        List<String> availableSlots = allPossibleSlots.stream()
                .filter(slot -> !bookedTimes.contains(slot))
                .map(slot -> slot.format(formatter))
                .collect(Collectors.toList());

        return ResponseEntity.ok(availableSlots);
    }

    /**
     * Books a new appointment.
     * @param request The details of the appointment to book.
     * @return HTTP 201 Created on success, or 409 Conflict if the slot is already taken.
     */
    @PostMapping("/appointments")
    public ResponseEntity<Void> bookAppointment(@RequestBody AppointmentRequest request) {
        LocalDate appointmentDate = LocalDateTime.parse(request.getAppointmentTimestamp()).toLocalDate();

        // 1. Check if the slot is already booked to prevent race conditions.
        List<LocalTime> bookedTimes = appointmentDao.findBookedAppointmentTimes(request.getDocId(), appointmentDate)
                .stream()
                .map(LocalDateTime::toLocalTime)
                .toList();

        LocalTime requestedTime = LocalDateTime.parse(request.getAppointmentTimestamp()).toLocalTime();

        if (bookedTimes.contains(requestedTime)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }

        // 2. If the slot is free, create the appointment.
        int rowsAffected = appointmentDao.createAppointment(request);
        if (rowsAffected > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAppointments")
    public ResponseEntity<List<AppointmentDetails>> getAppointments(@RequestParam("patientId") String patientId) {
        List<AppointmentDetails> appts = appointmentDao.viewAppointments(patientId);
        return ResponseEntity.ok(appts);
    }

    @GetMapping("/doctor-schedule")
    public ResponseEntity<List<AppointmentDetails>> getDoctorSchedule(@RequestParam String doctorId) {
        List<AppointmentDetails> appointments = appointmentDao.viewDoctorAppointments(doctorId);
        return ResponseEntity.ok(appointments);
    }

}
