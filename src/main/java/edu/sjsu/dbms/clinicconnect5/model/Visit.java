package edu.sjsu.dbms.clinicconnect5.model;

import java.util.Date;

public class Visit {
    private String apptId;
    private Date apptDate;
    private String patientId;
    private String docId;
    private String apptSummary;
    private String doctorFirstName;
    private String doctorLastName;

    public Visit() {}

    public String getApptSummary() {
        return apptSummary;
    }

    public void setApptSummary(String apptSummary) {
        this.apptSummary = apptSummary;
    }

    public Visit(String apptId, Date apptDate, String patientId, String docId, String summary, String docFirstName, String docLastName) {
        this.apptId = apptId;
        this.apptDate = apptDate;
        this.patientId = patientId;
        this.docId = docId;
        this.apptSummary = summary;
        doctorFirstName = docFirstName;
        doctorLastName = docLastName;
    }

    public String getApptId() {
        return apptId;
    }

    public void setApptId(String apptId) {
        this.apptId = apptId;
    }

    public Date getApptDate() {
        return apptDate;
    }

    public void setApptDate(Date apptDate) {
        this.apptDate = apptDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
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
}
