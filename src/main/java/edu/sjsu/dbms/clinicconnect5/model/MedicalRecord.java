package edu.sjsu.dbms.clinicconnect5.model;

public class MedicalRecord {
    private String patientId;
    private String medicineList;

    public MedicalRecord() {}

    public MedicalRecord(String patientId, String medicineList) {
        this.patientId   = patientId;
        this.medicineList = medicineList;
    }

    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMedicineList() {
        return medicineList;
    }
    public void setMedicineList(String medicineList) {
        this.medicineList = medicineList;
    }
}
