package edu.sjsu.dbms.clinicconnect5.controller;

import edu.sjsu.dbms.clinicconnect5.dao.UserDAO;
import edu.sjsu.dbms.clinicconnect5.model.User;
import edu.sjsu.dbms.clinicconnect5.model.UserProfileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    public static final Set<String> ALLOWED_USER_TYPES = Set.of("admin", "doctor", "patient");
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserDAO userDao;

    public UserController(UserDAO userDao){
        this.userDao = userDao;
    }

/**
     * Validates user credentials against the database.
     * @param request The login request containing the user's email and the client-side hashed password.
     * @return A response entity containing the user's role on success, or 401 Unauthorized on failure.
     */
    @PostMapping("/login/validate")
    public ResponseEntity<User> validateLogin(@RequestBody User request, HttpSession session) {
        // fetch the user from DB
        User user = userDao.findUserByEmail(request.getUserId());

        if (user != null && user.getPassword().equals(request.getPassword())) {
            log.info("Login validation successful for user={} and userType={}", user.getUserId(), user.getUserType());

            // ✅ Store in session
            session.setAttribute("user_id", user.getUserId());
            session.setAttribute("user_type", user.getUserType());

            return ResponseEntity.ok(new User(user.getUserId(), user.getUserType()));
        } else {
            log.info("Validation failed for user={}", request.getUserId());
            // On failure, return an HTTP 401 Unauthorized status.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Fetches user profile details based on the user type and their ID.
     * @param usertype The type of user ('admin', 'doctor', or 'patient').
     * @param id The unique ID of the user.
     * @return A response entity containing the user's profile information or 404 Not Found.
     */
    @GetMapping("/{usertype}/{id}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String usertype, @PathVariable String id) {
        log.info("Fetching profile for userType={} and id={}", usertype, id);

        try {
            // Validate the usertype to ensure it's one of the allowed values.
            if (!ALLOWED_USER_TYPES.contains(usertype.toLowerCase())) {
                log.warn("Invalid userType requested: {}", usertype);
                // Return a 400 Bad Request error if the usertype is invalid.
                return ResponseEntity.badRequest().build();
            }

            // fetch user details from the DB
            return ResponseEntity.ok(userDao.findUserDetails(usertype, id));

        }catch (Exception e){
            log.error("Exception while fetching user profile response for user={}", id);
        }

        //return not found in case of exception
        return ResponseEntity.notFound().build();
    }
}
