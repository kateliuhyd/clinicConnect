package edu.sjsu.dbms.clinicconnect5.model;

public class AppointmentRequest {
    private String docId;
    private String patientId;
    // appointment time in ISO format, e.g., "2025-07-22T09:00:00"
    private String appointmentTimestamp;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentTimestamp() {
        return appointmentTimestamp;
    }

    public void setAppointmentTimestamp(String appointmentTimestamp) {
        this.appointmentTimestamp = appointmentTimestamp;
    }
}
