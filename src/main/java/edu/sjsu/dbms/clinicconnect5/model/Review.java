package edu.sjsu.dbms.clinicconnect5.model;

import java.util.Date;

public class Review {

    private String id;
    private String doctorId;
    private String patientId;
    private String comment;
    private int rating; // e.g. 1-5 stars
    private Date reviewDate;

    public Review() {}

    public Review(String id, String doctorId, String patientId, String comment, int rating, Date revDate) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.comment = comment;
        this.rating = rating;
        reviewDate = revDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
