package com.example.demo10;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Register  {
    public Label lableinva;
    public Button Cancle;
    public Button Register;
    public TextField UserName;
    public TextField UserPass;
    public TextField UserFullName;
    public DatePicker DateOfBirthday;


    public void CancleAction(ActionEvent actionEvent) {

        ((Stage) Cancle.getScene().getWindow()).close();
    }

    public void RegisterAction(ActionEvent actionEvent) {

        if (UserName.getText().equals("")||UserName.getText().equals("admin") || UserPass.getText().equals("") || UserFullName.getText().equals("")) {
            lableinva.setText("Please fill all the fields");
        } else if (UserName.getText().length() <= 3) {
            lableinva.setText("Username must be more than 3 characters");
        } else if (UserPass.getText().length() <= 7) {
            lableinva.setText("Password must be more than 7 characters");
        } else if (UserFullName.getText().length() <= 9) {
            lableinva.setText("Full name must be more than 9 characters");
        } else {
            lableinva.setText("");
            try (Connection conn = DatabaseConnection.connect()) {
                PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
                checkStmt.setString(1, UserName.getText());
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    lableinva.setText("Username already exists");
                }else {
                    String sql = "INSERT INTO users (username, password, full_name, date_of_birth) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, UserName.getText());
                    stmt.setString(2, UserPass.getText());
                    stmt.setString(3, UserFullName.getText());
                    stmt.setString(4, DateOfBirthday.getValue().toString());
                    stmt.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration");
                    alert.setHeaderText(null);
                    alert.setContentText("Registration successful!");
                    alert.showAndWait();
                    ((Stage) Cancle.getScene().getWindow()).close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                lableinva.setText("❌ Registration failed.");
            }
        }
    }
}
