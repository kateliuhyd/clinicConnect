package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.model.User;
import edu.sjsu.dbms.clinicconnect5.model.UserProfileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO used for read/write actions against User table
 */
@Repository
public class UserDAO {

    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * API to get User record from DB
     * @param userId
     * @return User
     */
    public User findUserByEmail(String userId) {
        String sql = "SELECT user_id, password, user_type FROM User WHERE user_id = ?";

        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getString("user_id"),
                rs.getString("password"),
                rs.getString("user_type")
        );

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, userId);
        } catch (Exception e) {
            log.error("Exception while fetching user details for user={}",userId);
        }
        return null;
    }

    /**
     * Finds user profile details from the correct table (Admin, Doctor, or Patient).
     * @param userType The type of user, which determines the table to query.
     * @param userId The ID of the user to find.
     * @return An Optional containing the user's profile details if found.
     */
    public UserProfileResponse findUserDetails(String userType, String userId) {
        String tableName;
        String idColumnName;

        switch (userType.toLowerCase()) {
            case "admin":
                tableName = "Admin";
                idColumnName = "admin_id";
                break;
            case "doctor":
                tableName = "Doctor";
                idColumnName = "doc_id";
                break;
            case "patient":
                tableName = "Patient";
                idColumnName = "patient_id";
                break;
            default:
                // If the userType is not one of the allowed values, return empty.
                System.err.println("Invalid userType passed to DAO: " + userType);
                return null;
        }

        String sql = "SELECT " + idColumnName + " as id, first_name, last_name FROM " + tableName + " WHERE " + idColumnName + " = ?";

        RowMapper<UserProfileResponse> rowMapper = (rs, rowNum) -> new UserProfileResponse(
                rs.getString("id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );

        try {
            // Execute the query.
            return jdbcTemplate.queryForObject(sql, rowMapper, userId);
        } catch (Exception e) {
            log.error("Exception while fetching userprofile details for user={}",userId, e);
        }

        return null;
    }
}
