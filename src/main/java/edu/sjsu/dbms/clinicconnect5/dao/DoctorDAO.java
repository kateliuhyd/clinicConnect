package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.Doctor;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
//import edu.sjsu.dbms.clinicconnect5.model.

// Inside a method in your DatabaseConnector class

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoctorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JDBCConfiguration jdbcConfiguration;
    private ResultSet res = null;
    private PreparedStatement s = null;
    private Connection c = null;

    public List<Doctor> searchDoctor(Integer deptId) {
        List<Doctor> doctors = new ArrayList<>();

        try {
            c = jdbcConfiguration.getConnection();
            String sql = "SELECT doc_id, first_name, last_name, S.specialization_name FROM Doctor D, Speciliazation S  WHERE Doctor.specialization_id = Specialization.specialization_id and dept_id = ?";
            s = c.prepareStatement(sql);
            s.setInt(1, deptId);
            res = s.executeQuery();
            if (res != null) {
                while (res.next()) {
                    doctors.add(new Doctor(
                            res.getString("doc_id"),
                            res.getString("first_name"),
                            res.getString("last_name"),
                            res.getString("specialization_name")
                    ));
                }
            }
            res.close();
            s.close();
            c.close();
        } catch (SQLException E) {
                if (res != null) {
                    try {
                        res.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
        return doctors;
    }

    public void addDoctor(Doctor doctor) {
        try {
             c = jdbcConfiguration.getConnection();
            String sql = "INSERT INTO doctors (doc_id, first_name, last_name, specialization_id VALUES (?, ?, ? ,? )";
            s = c.prepareStatement(sql);
            s.setString(1, doctor.getDoc_id());
            s.setString(2, doctor.getFirst_name());
            s.setString(3, doctor.getLast_name());
            s.setString(5, doctor.getSpecialization_id());
            s.executeUpdate();
        }
        catch (SQLException E) {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
    }

    public void deleteDoctor(String id) {
        try {
            c = jdbcConfiguration.getConnection();
            String sql = "DELETE FROM doctors WHERE id=?";
            s = c.prepareStatement(sql);
            s.setString(1,
                    id);
            s.executeUpdate();
        }
        catch (SQLException E) {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("SQLException:" + E.getMessage());
            System.out.println("SQLState:" + E.getSQLState());
            System.out.println("VendorError:" + E.getErrorCode());
        }
    }
}
