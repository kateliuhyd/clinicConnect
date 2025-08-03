package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.AppointmentDetails;
import edu.sjsu.dbms.clinicconnect5.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Date;            // or java.util.Date

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class PrescriptionDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JDBCConfiguration jdbcConfiguration;
    private ResultSet res = null;
    private PreparedStatement s = null;
    private Connection c = null;

    public PrescriptionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Prescription> getPrescriptions(String patientId) {
        List<Prescription> prescriptions = new ArrayList<>();
        try {
            c = jdbcConfiguration.getConnection();
            String sql = "SELECT P.medicine_name, P.prescription_date, D.first_name, D.last_name, P.pid, P.did " +
                    "FROM Prescription P, Doctor D WHERE P.did = D.doc_id AND P.pid = ?";
            s = c.prepareStatement(sql);
            s.setString(1, patientId);
            res = s.executeQuery();
            if (res != null) {
                while (res.next()) {
                    prescriptions.add(new Prescription(
                            res.getString("medicine_name"),
                            res.getDate("prescription_date"),
                            res.getString("first_name"),
                            res.getString("last_name"),
                            res.getString("pid"),
                            res.getString("did")
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
        return prescriptions;
    }


    public int addPrescription(Prescription p) {
        String sql = "INSERT INTO Prescription "
                + "(medicine_name, prescription_date, pid, did) "
                + "VALUES (?, ?, ?, ?)";

        // Wrap the java.util.Date in java.sql.Date for JDBC
        java.sql.Date sqlDate = new java.sql.Date(p.getDate().getTime());

        return jdbcTemplate.update(
                sql,
                p.getMedicineName(),
                sqlDate,               // supply java.sql.Date here
                p.getPatientId(),
                p.getDoctorId()
        );
    }

}
