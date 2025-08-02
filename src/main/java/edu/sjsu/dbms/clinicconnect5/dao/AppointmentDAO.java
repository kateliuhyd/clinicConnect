package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.AppointmentDetails;
import edu.sjsu.dbms.clinicconnect5.model.Department;
import edu.sjsu.dbms.clinicconnect5.model.DoctorProfile;
import edu.sjsu.dbms.clinicconnect5.model.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JDBCConfiguration jdbcConfiguration;

    /**
     * Fetches all departments from the database.
     */
    public List<Department> findAllDepartments() {
        String sql = "SELECT dept_id, dept_name FROM Department ORDER BY dept_name";
        RowMapper<Department> rowMapper = (rs, rowNum) ->
                new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name")
                );
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * Finds all doctors in a given department.
     */
    public List<DoctorProfile> findDoctorsByDepartment(Integer deptId) {
        String sql = "SELECT doc_id, first_name, last_name, specialization "
                + "FROM Doctor WHERE dept_id = ?";
        RowMapper<DoctorProfile> rowMapper = (rs, rowNum) ->
                new DoctorProfile(
                        rs.getString("doc_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        null, null, null, null, null,
                        deptId,
                        rs.getString("specialization")
                );
        return jdbcTemplate.query(sql, rowMapper, deptId);
    }

    /**
     * Returns all booked appointment timestamps for a doctor on a date.
     */
    public List<LocalDateTime> findBookedAppointmentTimes(String doctorId, LocalDate date) {
        String sql = "SELECT date FROM Appointment WHERE doc_id = ? AND DATE(date) = ?";
        RowMapper<LocalDateTime> rowMapper = (rs, rowNum) -> {
            Timestamp ts = rs.getTimestamp("date");
            return ts != null ? ts.toLocalDateTime() : null;
        };
        return jdbcTemplate.query(sql, rowMapper, doctorId, date);
    }

    /**
     * Creates a new appointment.
     */
    public int createAppointment(AppointmentRequest request) {
        String sql = "INSERT INTO Appointment (appt_id, date, patient_id, doc_id) "
                + "VALUES (?, ?, ?, ?)";
        String newApptId = UUID.randomUUID().toString().substring(0, 20);
        LocalDateTime dt = LocalDateTime.parse(request.getAppointmentTimestamp());
        return jdbcTemplate.update(
                sql,
                newApptId,
                Timestamp.valueOf(dt),
                request.getPatientId(),
                request.getDocId()
        );
    }

    /**
     * Patient-side: view all appointments for a patient.
     * Joins Doctor to get doctor's name and specialization.
     */
    public List<AppointmentDetails> viewAppointments(String patientId) {
        String sql = """
            SELECT
              A.appt_id                AS apptId,
              A.date                   AS dateTime,
              D.first_name             AS doctorFirstName,
              D.last_name              AS doctorLastName,
              D.specialization         AS specialization
            FROM Appointment A
            JOIN Doctor D ON A.doc_id = D.doc_id
            WHERE A.patient_id = ?
            ORDER BY A.date
            """;

        return jdbcTemplate.query(sql, new Object[]{patientId}, (rs, rowNum) -> {
            AppointmentDetails a = new AppointmentDetails();
            a.setApptId(rs.getString("apptId"));
            a.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
            a.setDoctorFirstName(rs.getString("doctorFirstName"));
            a.setDoctorLastName(rs.getString("doctorLastName"));
            a.setSpecialization(rs.getString("specialization"));
            return a;
        });
    }

    /**
     * Doctor-side: view schedule for a doctor.
     * Joins Patient to get patient names and IDs.
     */
    public List<AppointmentDetails> viewDoctorAppointments(String doctorId) {
        String sql = """
            SELECT
              A.appt_id                AS apptId,
              A.date                   AS dateTime,
              P.first_name             AS patientFirstName,
              P.last_name              AS patientLastName,
              P.patient_id             AS patientId
            FROM Appointment A
            JOIN Patient P ON A.patient_id = P.patient_id
            WHERE A.doc_id = ?
            ORDER BY A.date
            """;

        return jdbcTemplate.query(sql, new Object[]{doctorId}, (rs, rowNum) -> {
            AppointmentDetails a = new AppointmentDetails();
            a.setApptId(rs.getString("apptId"));
            a.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
            a.setPatientFirstName(rs.getString("patientFirstName"));
            a.setPatientLastName(rs.getString("patientLastName"));
            a.setPatientId(rs.getString("patientId"));
            return a;
        });
    }

    /**
     * Doctor-side: view pending appointment requests for a doctor.
     */
    public List<AppointmentDetails> viewAppointmentRequests(String doctorId) {
        String sql = """
            SELECT
              A.appt_id                AS apptId,
              A.date                   AS dateTime,
              P.first_name             AS patientFirstName,
              P.last_name              AS patientLastName,
              P.patient_id             AS patientId
            FROM Appointment A
            JOIN Patient P ON A.patient_id = P.patient_id
            WHERE A.doc_id = ? AND A.status = 'PENDING'
            ORDER BY A.date
            """;

        return jdbcTemplate.query(sql, new Object[]{doctorId}, (rs, rowNum) -> {
            AppointmentDetails a = new AppointmentDetails();
            a.setApptId(rs.getString("apptId"));
            a.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
            a.setPatientFirstName(rs.getString("patientFirstName"));
            a.setPatientLastName(rs.getString("patientLastName"));
            a.setPatientId(rs.getString("patientId"));
            return a;
        });
    }

    /**
     * Updates an appointment's status (e.g. CONFIRMED or REJECTED).
     */
    public int updateAppointmentStatus(String apptId, String newStatus) {
        String sql = "UPDATE Appointment SET status = ? WHERE appt_id = ?";
        return jdbcTemplate.update(sql, newStatus, apptId);
    }
}
