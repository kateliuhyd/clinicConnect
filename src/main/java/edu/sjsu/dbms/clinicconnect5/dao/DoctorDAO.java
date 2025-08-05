// src/main/java/edu/sjsu/dbms/clinicconnect5/dao/DoctorDAO.java
package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoctorDAO {

    @Autowired
    private JDBCConfiguration jdbcConfiguration;

    /**
     * Admin: list all doctors (no department filter).
     *  – Alias every column so that rs.getXXX("docId") really sets the model's docId, etc.
     */
    public List<Doctor> findAllDoctors() {
        String sql = """
        SELECT
          D.doc_id               AS docId,
          D.first_name           AS firstName,
          D.last_name            AS lastName,
          D.dept_id              AS deptId,
          S.specialization_name  AS specializationName
        FROM Doctor D
        LEFT JOIN Specialization S
          ON D.specialization_id = S.specialization_id
        ORDER BY D.last_name, D.first_name
      """;

        try (
                Connection c = jdbcConfiguration.getConnection();
                PreparedStatement s = c.prepareStatement(sql);
                ResultSet rs = s.executeQuery();
        ) {
            List<Doctor> list = new ArrayList<>();
            while (rs.next()) {
                Doctor d = new Doctor();
                d.setDocId(rs.getString("docId"));
                d.setFirstName(rs.getString("firstName"));
                d.setLastName(rs.getString("lastName"));
                d.setDeptId(rs.getInt("deptId"));
                d.setSpecializationName(rs.getString("specializationName"));
                list.add(d);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Doctor> searchDoctor(int deptId) {
        List<Doctor> doctors = new ArrayList<>();
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = jdbcConfiguration.getConnection();
            String sql = """
                SELECT
                  D.doc_id,
                  D.first_name,
                  D.last_name,
                  D.dept_id,
                  S.specialization_name AS specialization
                FROM Doctor D
                LEFT JOIN Specialization S
                  ON D.specialization_id = S.specialization_id
                WHERE D.dept_id = ?
                ORDER BY D.last_name, D.first_name
            """;
            s = c.prepareStatement(sql);
            s.setInt(1, deptId);
            rs = s.executeQuery();
            while (rs.next()) {
                Doctor d = new Doctor();
                d.setDocId(    rs.getString("doc_id")        );
                d.setFirstName(rs.getString("first_name")    );
                d.setLastName( rs.getString("last_name")     );
                d.setDeptId(   rs.getInt(   "dept_id")       );
                d.setSpecializationId(rs.getString("specialization"));
                doctors.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (s  != null) s.close(); } catch (SQLException ignored) {}
            try { if (c  != null) c.close(); } catch (SQLException ignored) {}
        }
        return doctors;
    }

    /**
     * Admin: add a new doctor.
     */
    public int addDoctor(Doctor doctor) {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = jdbcConfiguration.getConnection();
            String sql = "INSERT INTO Doctor (doc_id, first_name, last_name, dob, dept_id, specialization_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            s = c.prepareStatement(sql);
            s.setString(1, doctor.getDocId());
            s.setString(2, doctor.getFirstName());
            s.setString(3, doctor.getLastName());
            s.setDate(4, Date.valueOf(doctor.getDob()));
            s.setInt(5, doctor.getDeptId());
            s.setString(6, doctor.getSpecializationId());
            return s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try { if (s != null) s.close(); } catch (SQLException ignored) {}
            try { if (c != null) c.close(); } catch (SQLException ignored) {}
        }
    }

    /**
     * Admin: delete a doctor by ID.
     */
    public int deleteDoctor(String docId) {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = jdbcConfiguration.getConnection();
            String sql = "DELETE FROM Doctor WHERE doc_id = ?";
            s = c.prepareStatement(sql);
            s.setString(1, docId);
            return s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try { if (s != null) s.close(); } catch (SQLException ignored) {}
            try { if (c != null) c.close(); } catch (SQLException ignored) {}
        }
    }
}
