package edu.sjsu.dbms.clinicconnect5.model;

import java.time.LocalDate;

public class Patient {
    private String patientId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String addressLine;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String medicalInsurance;
    private String preferredPharmacy;

    public Patient() {}

    public Patient(
            String patientId,
            String firstName,
            String lastName,
            LocalDate dob,
            String addressLine,
            String city,
            String state,
            String zipCode,
            String country,
            String medicalInsurance,
            String preferredPharmacy
    ) {
        this.patientId         = patientId;
        this.firstName         = firstName;
        this.lastName          = lastName;
        this.dob               = dob;
        this.addressLine       = addressLine;
        this.city              = city;
        this.state             = state;
        this.zipCode           = zipCode;
        this.country           = country;
        this.medicalInsurance  = medicalInsurance;
        this.preferredPharmacy = preferredPharmacy;
    }

    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
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

    public String getMedicalInsurance() {
        return medicalInsurance;
    }
    public void setMedicalInsurance(String medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public String getPreferredPharmacy() {
        return preferredPharmacy;
    }
    public void setPreferredPharmacy(String preferredPharmacy) {
        this.preferredPharmacy = preferredPharmacy;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", medicalInsurance='" + medicalInsurance + '\'' +
                ", preferredPharmacy='" + preferredPharmacy + '\'' +
                '}';
    }
}
