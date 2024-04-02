package asuHelloWorldJavaFX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PatientView {

    private Label lastMessageLabel = null; // Store the last displayed message

    public void display() {
        Stage window = new Stage();
        window.setTitle("Patient View (Seeing the Results)");

        // Layout setup
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        // Patient ID input
        TextField patientIdInput = new TextField();
        patientIdInput.setPromptText("Patient ID");
        GridPane.setConstraints(patientIdInput, 0, 0);

        Button showResultsButton = new Button("Show Results");
        showResultsButton.setOnAction(e -> showPatientResults(patientIdInput.getText(), grid));
        GridPane.setConstraints(showResultsButton, 1, 0);

        grid.getChildren().addAll(patientIdInput, showResultsButton);

        Scene scene = new Scene(grid, 706, 391);
        window.setScene(scene);
        window.show();
    }

    private void showPatientResults(String patientId, GridPane grid) {
        clearLastMessage(grid); // Clear any previous message

        if (patientId.isEmpty()) {
            displayMessage("Please enter a Patient ID.", grid);
            return;
        }

        // Attempt to load patient data
        try {
            String patientInfoPath = patientId + "_PatientInfo.txt";
            String ctResultsPath = patientId + "CTResults.txt";

            // Read patient info and CT results
            String patientName = readPatientName(patientInfoPath);
            String[] ctResults = readCTResults(ctResultsPath);

            if (patientName == null || ctResults == null) {
                displayMessage("No data available for the entered Patient ID.", grid);
                return;
            }

            // Display patient name and CAC score and vessel results
            displayPatientNameAndCACResults(patientName, ctResults, grid);

        } catch (IOException e) {
            displayMessage("Error accessing patient data.", grid);
            e.printStackTrace();
        }
    }

    private String readPatientName(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("First Name:")) {
                    return line.substring(line.indexOf(':') + 2).trim();
                }
            }
        }
        return null;
    }

    private String[] readCTResults(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().toArray(String[]::new);
        }
    }

    private void displayPatientNameAndCACResults(String patientName, String[] ctResults, GridPane grid) {
        // Display patient name
        Label patientNameLabel = new Label("Hello " + patientName);
        GridPane.setConstraints(patientNameLabel, 0, 1);
        grid.getChildren().add(patientNameLabel);

        // Display CAC score and vessel results
        displayCACResults(ctResults, grid);
    }

    private void displayCACResults(String[] ctResults, GridPane grid) {
        for (int i = 0; i < ctResults.length; i++) {
            String[] resultParts = ctResults[i].split(":");
            if (resultParts.length < 2) continue;

            Label label = new Label(resultParts[0].trim() + ":");
            TextField textField = new TextField(resultParts[1].trim());
            textField.setEditable(false);

            GridPane.setConstraints(label, 0, i + 2);
            GridPane.setConstraints(textField, 1, i + 2);

            grid.getChildren().addAll(label, textField);
        }
    }

    private void displayMessage(String message, GridPane grid) {
        // If there is a previous message, remove it first
        clearLastMessage(grid);

        lastMessageLabel = new Label(message);
        GridPane.setConstraints(lastMessageLabel, 0, grid.getChildren().size() + 1);
        grid.getChildren().add(lastMessageLabel);
    }

    private void clearLastMessage(GridPane grid) {
        if (lastMessageLabel != null) {
            grid.getChildren().remove(lastMessageLabel);
            lastMessageLabel = null; // Reset the reference after removal
        }
    }
}

