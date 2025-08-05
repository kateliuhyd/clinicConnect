package edu.sjsu.dbms.clinicconnect5.dao;

import edu.sjsu.dbms.clinicconnect5.configuration.JDBCConfiguration;
import edu.sjsu.dbms.clinicconnect5.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentDAO {
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private JDBCConfiguration jdbcConfiguration;

    public List<Department> findAll() {
        String sql = "SELECT dept_id, dept_name FROM Department ORDER BY dept_name";
        RowMapper<Department> mapper = (rs, rowNum) -> new Department(
                rs.getInt("dept_id"), rs.getString("dept_name")
        );
        return jdbcTemplate.query(sql, mapper);
    }

    public int add(Department dept) {
        String sql = "INSERT INTO Department(dept_id, dept_name) VALUES (?, ?)";
        return jdbcTemplate.update(sql, dept.getDeptId(), dept.getDeptName());
    }
}