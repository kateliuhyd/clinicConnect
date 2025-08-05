package edu.sjsu.dbms.clinicconnect5.model;

public class DoctorProfile extends UserProfileResponse {

    private Integer deptId;
    private String specialization;

    public DoctorProfile() {
        super();
    }

    public DoctorProfile(String id, String firstName, String lastName, Integer deptId, String specialization) {
        super(id, firstName, lastName);

        // Initialize fields specific to this class
        this.deptId = deptId;
        this.specialization = specialization;
    }


    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
