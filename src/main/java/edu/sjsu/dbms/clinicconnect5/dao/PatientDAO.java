package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PatientDAO {
    @Autowired private JdbcTemplate jdbc;
    @Autowired private JDBCConfiguration jdbcConfig;

    public List<Patient> findAll() {
        String sql = "SELECT patient_id, first_name, last_name, dob, address_line, city, state, zip_code, country, medical_insurance, preferred_pharmacy "
                + "FROM Patient ORDER BY last_name, first_name";
        return jdbc.query(sql, (rs, i) -> mapPatient(rs));
    }

    public int addPatient(Patient p) {
        String sql = "INSERT INTO Patient(patient_id, first_name, last_name, dob, address_line, city, state, zip_code, country, medical_insurance, preferred_pharmacy) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbc.update(sql,
                p.getPatientId(), p.getFirstName(), p.getLastName(), p.getDob(),
                p.getAddressLine(), p.getCity(), p.getState(), p.getZipCode(),
                p.getCountry(), p.getMedicalInsurance(), p.getPreferredPharmacy()
        );
    }

    public int deletePatient(String patientId) {
        return jdbc.update("DELETE FROM Patient WHERE patient_id = ?", patientId);
    }

    private Patient mapPatient(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setPatientId(rs.getString("patient_id"));
        p.setFirstName(rs.getString("first_name"));
        p.setLastName(rs.getString("last_name"));
        p.setDob(rs.getDate("dob").toLocalDate());
        p.setAddressLine(rs.getString("address_line"));
        p.setCity(rs.getString("city"));
        p.setState(rs.getString("state"));
        p.setZipCode(rs.getString("zip_code"));
        p.setCountry(rs.getString("country"));
        p.setMedicalInsurance(rs.getString("medical_insurance"));
        p.setPreferredPharmacy(rs.getString("preferred_pharmacy"));
        return p;
    }
}

