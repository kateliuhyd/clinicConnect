package edu.sjsu.dbms.clinicconnect5.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AppointmentDetails {
    private String apptId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;         // Align with DAO's dateTime

    // For doctor-view schedule
    private String patientFirstName;
    private String patientLastName;
    private String patientId;

    // For patient-view appointments
    private String doctorFirstName;
    private String doctorLastName;
    private String specialization;



    private String apptSummary;
    private String status;

    public AppointmentDetails() {}

    /** Patient-side constructor */
    public AppointmentDetails(String apptId,
                              LocalDateTime dateTime,
                              String doctorFirstName,
                              String doctorLastName,
                              String specialization) {
        this.apptId = apptId;
        this.dateTime = dateTime;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.specialization = specialization;
    }

    // ─── All getters & setters ───────────────────────────────────────

    public String getApptId() {
        return apptId;
    }
    public void setApptId(String apptId) {
        this.apptId = apptId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // ── Patient-schedule fields ──────────────────────────────────────

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

    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    // ── Patient-view fields ──────────────────────────────────────────

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

    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getApptSummary() {
        return apptSummary;
    }

    public void setApptSummary(String apptSummary) {
        this.apptSummary = apptSummary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}