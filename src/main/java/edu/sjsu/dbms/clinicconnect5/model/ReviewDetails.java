package edu.sjsu.dbms.clinicconnect5.model;


import java.util.Date;

public class ReviewDetails {

    private String id;


    private String patientFirstName;
    private String patientLastName;
    private String comment;
    private int rating; // e.g. 1-5 stars

    private Date reviewDate;

    public ReviewDetails() {}



    public ReviewDetails(String id, String patientFirstName, String patientLastName, String comment, int rating, Date revDate) {
        this.id = id;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.comment = comment;
        this.rating = rating;
        reviewDate = revDate;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
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
