package com.ascendingdc.training.project2020.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    private static Logger logger = LoggerFactory.getLogger(JDBCUtils.class);

    //Database information
    private static final String jdbcURL = "jdbc:postgresql://localhost:5432/pilot";
//    private static final String jdbcURL = "jdbc:h2:mem:testdb";
    // JDBC driver name and database URL
//    static final String JDBC_DRIVER = "org.h2.Driver";
//    private static final String jdbcURL = "jdbc:h2:~/testdb";
    private static final String jdbcUsername = "admin";    //"admin";
    private static final String jdbcPassword = "password";      //""password";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // STEP 1: Register JDBC driver
//            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
//            e.printStackTrace();
            logger.error("SQLException is caught when trying to create DB Connection object, error = " + e.getMessage());
        }
//        } catch (ClassNotFoundException e) {
//            logger.error("ClassNotFoundException is caught when trying to create DB Connection object, error = " + e.getMessage());
//        }
        return connection;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                logger.error("SQLState: " + ((SQLException) e).getSQLState());
                logger.error("Error Code: " + ((SQLException) e).getErrorCode());
                logger.error("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    logger.error("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
