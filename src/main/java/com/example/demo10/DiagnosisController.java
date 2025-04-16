package com.example.demo10;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class DiagnosisController {
    private final List<CheckBox> symptomCheckBoxes = new ArrayList<>();
    public FlowPane Flowpane;
    public Button historyBu;
    public ScrollPane scrollPane;
    public FlowPane symptomsPane;
    public Button Analyze1;
    @FXML
    public TextField TemOfPa;
    @FXML
    public TextField WiOfPa;
    @FXML
    public TextField HiOfPa;
    public Label resultLabel;


    private void initilise(){
         loadSymptomsFromDB(symptomsPane);

         Analyze1.setOnAction(e -> {
             if(TemOfPa.getText().isEmpty() || WiOfPa.getText().isEmpty() || HiOfPa.getText().isEmpty()){
                 resultLabel.setText("Please fill all the fields");
             }else {
             handleAnalysis();}});
         historyBu.setOnAction(e -> {
             try {
                 FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HistoryViewerUI.fxml"));
                 Scene Hisz = new Scene(fxmlLoader.load(),1044, 588);
                 Stage His = new Stage();
                 His.setScene(Hisz);
                 His.show();

             } catch (Exception e1) {
                 e1.printStackTrace();
                 e1.getCause();
             }
         });

    }

    private void handleAnalysis() {
        String temp = TemOfPa.getText();
        String weight = WiOfPa.getText();
        String height = HiOfPa.getText();

        List<String> selectedSymptoms = new ArrayList<>();
        for (CheckBox cb : symptomCheckBoxes) {
            if (cb.isSelected()) {
                selectedSymptoms.add(cb.getText().toLowerCase());
            }
        }


        String joinedSymptoms = String.join(",", selectedSymptoms);

        String diagnosis = "Unknown";
        int matchedSymptoms = 0;
        int ruleTotalSymptoms = 1;
        String recommendation = "Please consult a doctor.";

        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "SELECT * FROM rules";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                String ruleSymptoms = rs.getString("symptoms");
                String[] ruleKeywords = ruleSymptoms.split(",");
                int matchCount = 0;
                for (String symptom : selectedSymptoms) {
                    for (String keyword : ruleKeywords) {
                        if (symptom.trim().equalsIgnoreCase(keyword.trim())) {
                            matchCount++;
                        }
                    }
                }
                if (matchCount > matchedSymptoms) {
                    matchedSymptoms = matchCount;
                    ruleTotalSymptoms = rs.getInt("confidence");
                    diagnosis = rs.getString("conditions");
                    recommendation = rs.getString("recommendation");
                }
            }

            String insertSql = "INSERT INTO diagnosis_history (user_id, temperature, weight, height, selected_symptoms, diagnosis, confidence, recommendation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertSql);
            stmt.setInt(1, MainControler.loggedInUserId);
            stmt.setDouble(2, Double.parseDouble(temp));
            stmt.setDouble(3, Double.parseDouble(weight));
            stmt.setDouble(4, Double.parseDouble(height));
            stmt.setString(5, joinedSymptoms);
            stmt.setString(6, diagnosis);
            stmt.setInt(7, matchedSymptoms);
            stmt.setString(8, recommendation);
            stmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int percentMatch = (int) ((matchedSymptoms / (double) ruleTotalSymptoms) * 100);
        resultLabel.setText("Diagnosis: " + diagnosis +
                "\nMatch Confidence: " + percentMatch + "%" +
                "\nRecommendation: " + recommendation);
    }

    private void loadSymptomsFromDB(Pane container) {
        Set<String> uniqueSymptoms = new TreeSet<>();
        try (Connection conn = DatabaseConnection.connect();
             ResultSet rs = conn.createStatement().executeQuery("SELECT symptoms FROM rules")) {
            while (rs.next()) {
                String[] symptomArray = rs.getString("symptoms").split(",");
                for (String s : symptomArray) {
                    uniqueSymptoms.add(s.trim().toLowerCase());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String symptom : uniqueSymptoms) {
            CheckBox cb = new CheckBox(symptom);
            cb.setPrefWidth(180);
            symptomCheckBoxes.add(cb);
            container.getChildren().add(cb);
        }
    }


    @FXML

    public void isHelloButtonClick(javafx.event.ActionEvent actionEvent) {
        initilise();
    }
    public  void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}