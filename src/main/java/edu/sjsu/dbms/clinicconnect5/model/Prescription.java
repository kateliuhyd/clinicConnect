package edu.sjsu.dbms.clinicconnect5.model;

import java.util.Date;

public class Prescription {

    private String patientId;
    private String doctorId;
    private String medicineName;
    private String doctorFirstName;
    private String doctorLastName;
    private Date date;

    public Prescription() {}

    public Prescription(String medicineName, Date date, String docFirstName, String docLastName, String patientId, String doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medicineName = medicineName;
        doctorFirstName = docFirstName;
        doctorLastName = docLastName;
        this.date = date;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
