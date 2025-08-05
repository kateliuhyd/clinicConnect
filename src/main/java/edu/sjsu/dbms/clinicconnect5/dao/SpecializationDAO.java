package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.Specialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpecializationDAO {
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private JDBCConfiguration jdbcConfiguration;

    public List<Specialization> findAll() {
        String sql = "SELECT specialization_id, specialization_name FROM Specialization ORDER BY specialization_name";
        RowMapper<Specialization> mapper = (rs, rowNum) -> new Specialization(
                rs.getString("specialization_id"), rs.getString("specialization_name")
        );
        return jdbcTemplate.query(sql, mapper);
    }

    public int add(Specialization s) {
        String sql = "INSERT INTO Specialization(specialization_id, specialization_name) VALUES (?, ?)";
        return jdbcTemplate.update(sql, s.getSpecializationId(), s.getSpecializationName());
    }
}