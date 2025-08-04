package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.model.Patient;
//import edu.sjsu.dbms.clinicconnect5.model.Prescription;
import edu.sjsu.dbms.clinicconnect5.model.Prescription;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDAO {
/*
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getString("id"),
                        rs.getString("name"),
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patients (patient_id, first_name, last_name, age, diagnosis) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE id=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Example: ordering a prescription (simple insert)
    public void orderPrescription(int patientId, Prescription prescription) {
        String sql = "INSERT INTO prescriptions (patient_id, medication, dosage) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ps.setString(2, prescription.getMedication());
            ps.setString(3, prescription.getDosage());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Reviews could be stored in DoctorDAO, but here is a stub for adding review
    public void addReview(int doctorId, Review review) {
        String sql = "INSERT INTO reviews (doctor_id, patient_id, comment, rating) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, review.getPatientId());
            ps.setString(3, review.getComment());
            ps.setInt(4, review.getRating());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 */
}
