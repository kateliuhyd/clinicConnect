package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoctorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JDBCConfiguration jdbcConfiguration;

    private Connection c = null;
    private PreparedStatement s = null;
    private ResultSet res = null;

    /**
     * Search for doctors in a department, joining the Specialization table.
     */
    public List<Doctor> searchDoctor(Integer deptId) {
        List<Doctor> doctors = new ArrayList<>();
        try {
            c = jdbcConfiguration.getConnection();
            String sql = """
                SELECT
                  D.doc_id,
                  D.first_name,
                  D.last_name,
                  S.specialization_name AS specialization
                FROM Doctor D
                JOIN Specialization S
                  ON D.specialization_id = S.specialization_id
                WHERE D.dept_id = ?
            """;
            s = c.prepareStatement(sql);
            s.setInt(1, deptId);
            res = s.executeQuery();
            while (res.next()) {
                doctors.add(new Doctor(
                        res.getString("doc_id"),
                        res.getString("first_name"),
                        res.getString("last_name"),
                        res.getString("specialization")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            // Close resources in reverse order of opening
            try { if (res != null) res.close(); } catch (SQLException ignored) {}
            try { if (s   != null) s.close();   } catch (SQLException ignored) {}
            try { if (c   != null) c.close();   } catch (SQLException ignored) {}
        }
        return doctors;
    }

    /**
     * Insert a new doctor record.
     */
    public void addDoctor(Doctor doctor) {
        try {
            c = jdbcConfiguration.getConnection();
            String sql = """
                INSERT INTO Doctor
                  (doc_id, first_name, last_name, specialization_id)
                VALUES (?, ?, ?, ?)
            """;
            s = c.prepareStatement(sql);
            s.setString(1, doctor.getDoc_id());
            s.setString(2, doctor.getFirst_name());
            s.setString(3, doctor.getLast_name());
            s.setString(4, doctor.getSpecialization_id());
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try { if (s != null) s.close(); } catch (SQLException ignored) {}
            try { if (c != null) c.close(); } catch (SQLException ignored) {}
        }
    }

    /**
     * Delete a doctor by ID.
     */
    public void deleteDoctor(String docId) {
        try {
            c = jdbcConfiguration.getConnection();
            String sql = "DELETE FROM Doctor WHERE doc_id = ?";
            s = c.prepareStatement(sql);
            s.setString(1, docId);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try { if (s != null) s.close(); } catch (SQLException ignored) {}
            try { if (c != null) c.close(); } catch (SQLException ignored) {}
        }
    }
}
