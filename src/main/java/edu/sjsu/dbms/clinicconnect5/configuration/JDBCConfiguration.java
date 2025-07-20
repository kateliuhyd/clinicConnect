package edu.sjsu.dbms.clinicconnect5.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class JDBCConfiguration {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception E) {
            System.err.println("Unable to load driver.");
            E.printStackTrace();
        }
    }
    @Value("${spring.datasource.url}")
    private String jdbcURL;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    public String getJdbcURL() {
        return jdbcURL;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConnection() throws SQLException {
        Connection C = DriverManager.getConnection(jdbcURL,
                   userName, password);
        return C;
    }
}
