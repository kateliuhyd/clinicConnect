package edu.sjsu.dbms.clinicconnect5.model;

public class Prescription {

    private int id;
    private int patientId;
    private String medication;
    private String dosage;

    public Prescription() {}

    public Prescription(int patientId, String medication, String dosage) {
        this.id = id;
        this.patientId = patientId;
        this.medication = medication;
        this.dosage = dosage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
