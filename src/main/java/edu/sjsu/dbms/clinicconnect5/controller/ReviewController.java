package edu.sjsu.dbms.clinicconnect5.controller;

import edu.sjsu.dbms.clinicconnect5.dao.ReviewDAO;
import edu.sjsu.dbms.clinicconnect5.model.Review;
import edu.sjsu.dbms.clinicconnect5.model.ReviewDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewDAO reviewDao;

    public ReviewController(ReviewDAO reviewDao) {
        this.reviewDao = reviewDao;
    }

    @GetMapping("/getReviewDetails")
    public ResponseEntity<List<ReviewDetails>> getReviewDetails(@RequestParam("doctorId") String doctorId) {
        List<ReviewDetails> reviewDetails = reviewDao.getReviewDetails(doctorId);
        return ResponseEntity.ok(reviewDetails);
    }


    @PostMapping("/addReview")
    public ResponseEntity<Void> addReview(@RequestBody Review r) {
        int rows = reviewDao.addReview(r);
        if (rows == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

