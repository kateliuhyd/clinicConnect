package edu.sjsu.dbms.clinicconnect5.model;

public class Review {

    private int id;
    private int doctorId;
    private int patientId;
    private String comment;
    private int rating; // e.g. 1-5 stars

    public Review() {}

    public Review(int id, int doctorId, int patientId, String comment, int rating) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.comment = comment;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
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
