package edu.sjsu.dbms.clinicconnect5.model;

import java.time.LocalDateTime;

public class AppointmentDetails {
    private String apptId;
    private LocalDateTime date;
    private String doctorFirstName;
    private String doctorLastName;
    private String departmentName;
    private String specialization;
    private String status;

    public AppointmentDetails() {
    }

    public AppointmentDetails(String apptId, LocalDateTime date, String doctorFirstName,  String doctorLastName, String specialization) {
        this.apptId = apptId;
        this.date = date;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.specialization = specialization;
    }

    public String getApptId() {
        return apptId;
    }

    public void setApptId(String apptId) {
        this.apptId = apptId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
