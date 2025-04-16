package com.example.demo10;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class HistoryViewerUI implements Initializable {
    public static class History {
        private final String date;
        private final String symptoms;
        private final String diagnosis;
        private final int confidence;
        private final String recommendation;

        public History(String date, String symptoms, String diagnosis, int confidence, String recommendation) {
            this.date = date;
            this.symptoms = symptoms;
            this.diagnosis = diagnosis;
            this.confidence = confidence;
            this.recommendation = recommendation;
        }

        public String getDate() { return date; }
        public String getSymptoms() { return symptoms; }
        public String getDiagnosis() { return diagnosis; }
        public int getConfidence() { return confidence; }
        public String getRecommendation() { return recommendation; }
    }

    ObservableList<History> historyList = FXCollections.observableArrayList();
    public TableView<History>tableView;
    public TableColumn<History, Integer> date;
    public TableColumn<History, String> symptoms;
    public TableColumn<History, String> diagnosis;
    public TableColumn<History, Integer> confidence;
    public TableColumn<History, String> recommendation;


    public void initialize(URL location, ResourceBundle resources) {
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        symptoms.setCellValueFactory(new PropertyValueFactory<>("symptoms"));
        diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        confidence.setCellValueFactory(new PropertyValueFactory<>("confidence"));
        recommendation.setCellValueFactory(new PropertyValueFactory<>("recommendation"));
        tableView.getColumns().addAll(date, symptoms, diagnosis, confidence, recommendation);
        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "SELECT created_at, selected_symptoms, diagnosis, confidence, recommendation " +
                    "FROM diagnosis_history WHERE user_id = ? ORDER BY created_at DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, MainControler.loggedInUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                historyList.add(new History(
                        rs.getString("created_at"),
                        rs.getString("selected_symptoms"),
                        rs.getString("diagnosis"),
                        rs.getInt("confidence"),
                        rs.getString("recommendation")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        tableView.setItems(historyList);
    }
}
