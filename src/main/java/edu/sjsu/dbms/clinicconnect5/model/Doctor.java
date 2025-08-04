package edu.sjsu.dbms.clinicconnect5.model;

import java.time.LocalDate;

public class Doctor {
    private String docId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Integer deptId;
    private String specializationId;
    private String specializationName;

    public Doctor() {}

    public Doctor(String docId, String firstName, String lastName, String specializationName) {
        this.docId = docId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specializationName = specializationName;
    }

    public String getDocId() { return docId; }
    public void setDocId(String docId) { this.docId = docId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public Integer getDeptId() { return deptId; }
    public void setDeptId(Integer deptId) { this.deptId = deptId; }

    public String getSpecializationId() { return specializationId; }
    public void setSpecializationId(String specializationId) { this.specializationId = specializationId; }

    public String getSpecializationName() { return specializationName; }
    public void setSpecializationName(String specializationName) { this.specializationName = specializationName; }

    // Alias setter for JSON 'specialization'
    public void setSpecialization(String specialization) {
        this.specializationName = specialization;
    }
}
