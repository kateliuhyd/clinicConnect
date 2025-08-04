package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.Review;
import edu.sjsu.dbms.clinicconnect5.model.ReviewDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ReviewDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JDBCConfiguration jdbcConfiguration;
    private ResultSet res = null;
    private PreparedStatement s = null;
    private Connection c = null;

    public ReviewDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReviewDetails> getReviewDetails(String docId) {
        List<ReviewDetails> reviewDetails = new ArrayList<>();
        try {
            c = jdbcConfiguration.getConnection();
            String sql = "SELECT R.rid, P.first_name, P.last_name, R.content, R.rating, R.review_date " +
                    "FROM Review R, Patient P WHERE R.pid = P.patient_id AND R.did = ?";
            s = c.prepareStatement(sql);
            s.setString(1, docId);
            res = s.executeQuery();
            if (res != null) {
                while (res.next()) {
                    reviewDetails.add(new ReviewDetails(
                            res.getString("rid"),
                            res.getString("first_name"),
                            res.getString("last_Name"),
                            res.getString("content"),
                            res.getInt("rating"),
                            res.getDate("review_date")
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
        return reviewDetails;
    }

    public int addReview(Review r) {
        String sql = "INSERT INTO Review "
                + "(rid, pid, did, content, rating, review_date) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // Wrap the java.util.Date in java.sql.Date for JDBC
        String rid = UUID.randomUUID().toString().substring(0, 20);
        Date date = Date.valueOf(LocalDate.now());

        return jdbcTemplate.update(
                sql,
                rid,
                r.getPatientId(),
                r.getDoctorId(),
                r.getComment(),
                r.getRating(),
                date
        );
    }
}
