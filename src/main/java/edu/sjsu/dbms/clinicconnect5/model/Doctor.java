package edu.sjsu.dbms.clinicconnect5.model;

public class Doctor {

    private String doc_id;
    private String first_name;
    private String last_name;
    private String location;
    private String specialization;


    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Doctor(String id, String first_name, String last_name, String location, String specialization) {
        this.doc_id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.location = location;
        this.specialization = specialization;
    }

}
