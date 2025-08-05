package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class MedicalRecordDAO {
    @Autowired
    private JDBCConfiguration jdbcConfiguration;

    /**
     * Lookup the single medical_record row for a given patient.
     */
    public Optional<MedicalRecord> findByPatientId(String patientId) {
        String sql = "SELECT pid, medicine_list FROM Medical_Record WHERE pid = ?";
        try ( Connection c = jdbcConfiguration.getConnection();
              PreparedStatement ps = c.prepareStatement(sql) ) {

            ps.setString(1, patientId);
            try ( ResultSet rs = ps.executeQuery() ) {
                if (rs.next()) {
                    return Optional.of(new MedicalRecord(
                            rs.getString("pid"),
                            rs.getString("medicine_list")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
