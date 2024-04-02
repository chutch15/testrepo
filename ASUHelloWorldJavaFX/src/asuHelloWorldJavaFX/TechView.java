package asuHelloWorldJavaFX;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TechView {

    public void display() {
        Stage window = new Stage();
        window.setTitle("CT Scan Technician View");

        // Layout setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // CT Scan data fields
        TextField patientIDInput = createTextField("Patient ID:", grid, 0);
        TextField totalAgatstonInput = createTextField("The total Agatston CAC score:", grid, 1);
        TextField lmInput = createTextField("LM:", grid, 2);
        TextField ladInput = createTextField("LAD:", grid, 3);
        TextField lcxInput = createTextField("LCX:", grid, 4);
        TextField rcaInput = createTextField("RCA:", grid, 5);
        TextField pdaInput = createTextField("PDA:", grid, 6);

        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 1, 7);
        saveButton.setOnAction(e -> saveCTScanData(patientIDInput, totalAgatstonInput, lmInput, ladInput, lcxInput, rcaInput, pdaInput));

        // Adding components to the grid
        grid.getChildren().addAll(saveButton);

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.show();
    }

    private TextField createTextField(String labelText, GridPane grid, int row) {
        Label label = new Label(labelText);
        GridPane.setConstraints(label, 0, row);
        TextField textField = new TextField();
        GridPane.setConstraints(textField, 1, row);
        grid.getChildren().addAll(label, textField);
        return textField;
    }

    private void saveCTScanData(TextField patientID, TextField totalAgatston, TextField lm, TextField lad, TextField lcx, TextField rca, TextField pda) {
        if (isAnyFieldEmpty(patientID, totalAgatston, lm, lad, lcx, rca, pda)) {
            AlertBox.display("Error", "All fields are required!");
            return;
        }

        String filename = patientID.getText() + "CTResults.txt";
        try {
            saveToFile(filename, patientID.getText(), totalAgatston.getText(), lm.getText(), lad.getText(), lcx.getText(), rca.getText(), pda.getText());
            AlertBox.display("Success", "CT Scan data saved successfully for patient ID: " + patientID.getText());
        } catch (IOException e) {
            AlertBox.display("Error", "An error occurred while saving CT scan data.");
            e.printStackTrace();
        }
    }

    private boolean isAnyFieldEmpty(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void saveToFile(String filename, String... details) throws IOException {
        if (details.length < 7) { // Check if all details are provided
            throw new IllegalArgumentException("Missing details for CT Scan data.");
        }

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(filename)))) {
            writer.println("Patient ID: " + details[0]);
            writer.println("The total Agatston CAC score: " + details[1]);
            writer.println("LM: " + details[2]);
            writer.println("LAD: " + details[3]);
            writer.println("LCX: " + details[4]);
            writer.println("RCA: " + details[5]);
            writer.println("PDA: " + details[6]);
        }
    }
}
