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
        String sql = """
          SELECT
            D.doc_id                      AS docId,
            D.first_name                  AS firstName,
            D.last_name                   AS lastName,
            S.specialization_name         AS specialization
          FROM Doctor D
          JOIN Specialization S
            ON D.specialization_id = S.specialization_id
          WHERE D.dept_id = ?
      """;

        RowMapper<DoctorProfile> rowMapper = (rs, rowNum) -> new DoctorProfile(
                /* id: */              rs.getString("docId"),
                /* firstName: */       rs.getString("firstName"),
                /* lastName: */        rs.getString("lastName"),
                /* deptId: */          deptId,
                /* specialization: */  rs.getString("specialization")
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
          A.appt_id          AS apptId,
          A.date             AS dateTime,
          D.first_name       AS doctorFirstName,
          D.last_name        AS doctorLastName,
          S.specialization_name AS specialization,
          A.appt_summary     AS apptSummary,
          A.status           AS status
        FROM Appointment A
        JOIN Doctor D  ON A.doc_id = D.doc_id
        JOIN Specialization S ON D.specialization_id = S.specialization_id
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
            a.setApptSummary(rs.getString("apptSummary"));
            a.setStatus(rs.getString("status"));
            return a;
        });

    }

    /**
     * Doctor-side: view schedule for a doctor.
     */
    public List<AppointmentDetails> viewDoctorAppointments(String doctorId) {
        String sql = """
        SELECT
          A.appt_id                AS apptId,
          A.date                   AS dateTime,
          P.first_name             AS patientFirstName,
          P.last_name              AS patientLastName,
          P.patient_id             AS patientId,
          A.appt_summary           AS apptSummary,
          A.status                 AS status
        FROM Appointment A
        JOIN Patient P
          ON A.patient_id = P.patient_id
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
            a.setApptSummary(rs.getString("apptSummary"));
            a.setStatus(rs.getString("status"));
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

    /**
     * Updates the visit summary text of an appointment.
     */
    public int updateAppointmentSummary(String apptId, String summary) {
        String sql = "UPDATE Appointment SET appt_summary = ? WHERE appt_id = ?";
        return jdbcTemplate.update(sql, summary, apptId);
    }

}
