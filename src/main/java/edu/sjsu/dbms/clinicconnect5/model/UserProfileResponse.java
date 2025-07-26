package edu.sjsu.dbms.clinicconnect5.model;

public class UserProfileResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String addressLine;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Integer deptId;
    private String specialization;

    // No-argument constructor
    public UserProfileResponse() {
    }

    // Constructor with all fields
    public UserProfileResponse(String id, String firstName, String lastName, String addressLine, String city, String state, String zipCode, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.deptId = deptId;
        this.specialization = specialization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

