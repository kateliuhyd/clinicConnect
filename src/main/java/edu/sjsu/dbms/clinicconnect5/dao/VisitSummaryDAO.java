package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.AppointmentDetails;
import edu.sjsu.dbms.clinicconnect5.model.Prescription;
import edu.sjsu.dbms.clinicconnect5.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VisitSummaryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JDBCConfiguration jdbcConfiguration;
    private ResultSet res = null;
    private PreparedStatement s = null;
    private Connection c = null;

    public VisitSummaryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Visit> summaryOfVisits(String patientId) {
        List<Visit> visits = new ArrayList<>();
        try {
            c = jdbcConfiguration.getConnection();
            String sql = "SELECT A.appt_id, A.date, A.patient_id, A.doc_id, A.appt_summary, D.first_name, D.last_name" +
                    " FROM Appointment A, Doctor D WHERE A.doc_id = D.doc_id "   +
                    " AND A.date >= NOW() - INTERVAL 30 DAY AND A.date <= NOW()  AND A.patient_id = ?";
            s = c.prepareStatement(sql);
            s.setString(1, patientId);
            res = s.executeQuery();
            if (res != null) {
                while (res.next()) {
                    visits.add(new Visit(
                            res.getString("appt_id"),
                            res.getDate("date"),
                            res.getString("patient_id"),
                            res.getString("doc_id"),
                            res.getString("appt_summary"),
                            res.getString("first_name"),
                            res.getString("last_name")
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
        return visits;
    }
}
