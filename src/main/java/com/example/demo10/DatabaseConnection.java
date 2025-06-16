package com.example.demo10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connect() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/expert_system", // DB name
                    "root",                                          // Username
                    ""                                               // Password (empty)
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}