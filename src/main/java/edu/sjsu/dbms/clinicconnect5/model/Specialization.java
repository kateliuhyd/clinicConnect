package edu.sjsu.dbms.clinicconnect5.model;

public class Specialization {
    private String specializationId;
    private String specializationName;

    public Specialization() {}

    public Specialization(String specializationId, String specializationName) {
        this.specializationId = specializationId;
        this.specializationName = specializationName;
    }

    public String getSpecializationId() {
        return specializationId;
    }
    public void setSpecializationId(String specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecializationName() {
        return specializationName;
    }
    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "specializationId='" + specializationId + '\'' +
                ", specializationName='" + specializationName + '\'' +
                '}';
    }
}