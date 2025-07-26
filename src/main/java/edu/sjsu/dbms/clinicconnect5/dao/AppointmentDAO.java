package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.model.AppointmentRequest;
import edu.sjsu.dbms.clinicconnect5.model.Department;
import edu.sjsu.dbms.clinicconnect5.model.DoctorProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class AppointmentDAO {

    private final JdbcTemplate jdbcTemplate;

    public AppointmentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Fetches all departments from the database.
     * @return A list of Department objects.
     */
    public List<Department> findAllDepartments() {
        String sql = "SELECT dept_id, dept_name FROM Department ORDER BY dept_name";
        RowMapper<Department> rowMapper = (rs, rowNum) -> new Department(
                rs.getInt("dept_id"),
                rs.getString("dept_name")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * Finds all doctors associated with a given department ID.
     * @param deptId The department ID to search for.
     * @return A list of DoctorProfile objects.
     */
    public List<DoctorProfile> findDoctorsByDepartment(Integer deptId) {
        String sql = "SELECT doc_id, first_name, last_name, specialization FROM Doctor WHERE dept_id = ?";
        RowMapper<DoctorProfile> rowMapper = (rs, rowNum) -> new DoctorProfile(
                rs.getString("doc_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                null, // addressLine - not needed for this view
                null, // city - not needed for this view
                null, // state - not needed for this view
                null, // zipCode - not needed for this view
                null, // country - not needed for this view
                deptId,
                rs.getString("specialization")
        );
        return jdbcTemplate.query(sql, rowMapper, deptId);
    }

    /**
     * Fetches the timestamps of all booked appointments for a specific doctor on a given date.
     * @param doctorId The ID of the doctor.
     * @param date The date to check for appointments.
     * @return A list of LocalDateTime objects representing booked appointment times.
     */
    public List<LocalDateTime> findBookedAppointmentTimes(String doctorId, LocalDate date) {
        // This query uses your 'Appointment' table structure.
        String sql = "SELECT date FROM Appointment WHERE doc_id = ? AND DATE(date) = ?";

        RowMapper<LocalDateTime> rowMapper = (rs, rowNum) -> {
            Timestamp timestamp = rs.getTimestamp("date");
            return timestamp != null ? timestamp.toLocalDateTime() : null;
        };

        return jdbcTemplate.query(sql, rowMapper, doctorId, date);
    }

    /**
     * Creates a new appointment in the database.
     * @param request The details of the appointment to book.
     * @return The number of rows affected
     */
    public int createAppointment(AppointmentRequest request) {
        String sql = "INSERT INTO Appointment (appt_id, date, patient_id, doc_id) VALUES (?, ?, ?, ?)";

        // Generate a unique ID for the new appointment.
        String newApptId = UUID.randomUUID().toString().substring(0, 20);
        LocalDateTime appointmentDateTime = LocalDateTime.parse(request.getAppointmentTimestamp());

        return jdbcTemplate.update(sql, newApptId, appointmentDateTime, request.getPatientId(), request.getDocId());
    }
}

