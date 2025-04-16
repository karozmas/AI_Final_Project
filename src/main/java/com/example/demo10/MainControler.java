package com.example.demo10;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import java.net.URL;


public class MainControler implements Initializable {
    public ImageView Heaticon;

    @Override
    public  void  initialize(URL url, ResourceBundle resourceBundle) {
        File branding = new File("icon/heart.png");
        Image image = new Image(branding.toURI().toString());
       Heaticon.setImage(image);
    }
    public static int loggedInUserId = -1;
    public TextField UserNameText;
    public PasswordField UserPassText;
    public Button Login;
    public Button Register;
    public Label statusLabel;


    public void LogInAction(ActionEvent actionEvent) {
        if (UserNameText.getText().equals("admin") || UserPassText.getText().equals("1234")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminInterface.fxml"));
                Scene admins = new Scene(fxmlLoader.load(),1266, 720);
                Stage Admin = new Stage();
                Admin.setScene(admins);
                Admin.show();
                ((Stage) Login.getScene().getWindow()).close();

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, UserNameText.getText());
            stmt.setString(2, UserPassText.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loggedInUserId = rs.getInt("id");
                statusLabel.setText("✅ Login successful! User ID: " + loggedInUserId);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Main_Anly.fxml"));
                    Scene sceneA = new Scene(fxmlLoader.load(),1280, 720);
                    Stage Anlystage = new Stage();
                    Anlystage.setScene(sceneA);
                    Anlystage.show();
                    ((Stage) Login.getScene().getWindow()).close();

                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                }

            } else {
                statusLabel.setText("Invalid username or password.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Login error.");
        }

    }

    public void RegisterAction(ActionEvent actionEvent) {
        try {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),333, 400);
        Stage Register = new Stage();
        Register.setScene(scene);
        Register.show(); }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }
}