package com.example.demo10;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminInterface implements Initializable {
    public Button Return;
    private ObservableList<Rule> ruleList = FXCollections.observableArrayList();
    public TextField symptomsField;
    public TextField conditionField;
    public TextField confidenceField;
    public TextField recommendationArea;
    public Button addRule;
    public Button DleteRule;
    public javafx.scene.control.TableView<Rule> TableView;
    public TableColumn<Rule, Integer> id;
    public TableColumn<Rule, String> symptoms;
    public TableColumn<Rule, String> conditions;
    public TableColumn<Rule, Integer> confidence;
    public TableColumn<Rule, String> recommendation;
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        symptoms.setCellValueFactory(new PropertyValueFactory<>("symptoms"));
        conditions.setCellValueFactory(new PropertyValueFactory<>("conditions"));
        confidence.setCellValueFactory(new PropertyValueFactory<>("confidence"));
        recommendation.setCellValueFactory(new PropertyValueFactory<>("recommendation"));

        TableView.setItems(ruleList);


        addRule.setOnAction(e -> {
            String symptomsText = symptomsField.getText().trim();
            String conditionText = conditionField.getText().trim();
            String confidenceText = confidenceField.getText().trim();
            String recommendationText = recommendationArea.getText().trim();

            if (symptomsText.isEmpty() || conditionText.isEmpty() || confidenceText.isEmpty() || recommendationText.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Internal Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            }

            try {
                int confidenceValue = Integer.parseInt(confidenceText);

                try (Connection conn = DatabaseConnection.connect()) {
                    String sql = "INSERT INTO rules (symptoms, conditions, confidence, recommendation) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, symptomsText);
                    stmt.setString(2, conditionText);
                    stmt.setInt(3, confidenceValue);
                    stmt.setString(4, recommendationText);
                    stmt.executeUpdate();

                    loadRules();


                    symptomsField.clear();
                    conditionField.clear();
                    confidenceField.clear();
                    recommendationArea.clear();
                }
            } catch (NumberFormatException ex) {

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        DleteRule.setOnAction(e -> {
            Rule selectedRule = TableView.getSelectionModel().getSelectedItem();
            if (selectedRule != null) {
                try (Connection conn = DatabaseConnection.connect()) {
                    String sql = "DELETE FROM rules WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, selectedRule.getId());
                    stmt.executeUpdate();
                    loadRules();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        loadRules();

    }

    private void loadRules() {
        ruleList.clear();
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rules")) {
            while (rs.next()) {
                ruleList.add(new Rule(
                        rs.getInt("id"),
                        rs.getString("symptoms"),
                        rs.getString("conditions"),
                        rs.getInt("confidence"),
                        rs.getString("recommendation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TableView.setItems(ruleList);
    }

    public void ReturnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Main_well.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),1280, 720);
            Stage ReturnMain = new Stage();
            ReturnMain.setScene(scene);
            ReturnMain.show();
            ((Stage) Return.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}

