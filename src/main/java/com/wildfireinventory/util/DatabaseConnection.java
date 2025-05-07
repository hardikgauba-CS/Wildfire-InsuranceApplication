package com.wildfireinventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://aws-0-us-east-2.pooler.supabase.com:5432/postgres?user=postgres.jwgzujeqvmxyblzayovl&password=dzUgurjS7dS7Zrg3&sslmode=require";
    
    private static Connection connection;
    
    private DatabaseConnection() {}
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException("Failed to connect to database", e);
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close database connection", e);
            }
        }
    }
}
