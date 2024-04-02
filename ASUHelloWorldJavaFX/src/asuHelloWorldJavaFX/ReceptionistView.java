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
import java.util.Random;
import javafx.geometry.Pos;

public class ReceptionistView {
    private Stage window;
    private GridPane grid;
    private Button saveButton;
    private Button closeButton;

    public void display() {
        window = new Stage();
        window.setTitle("Patient Intake Form");

        // Layout setup
        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Form fields
        TextField firstNameInput = createTextField("First Name:", 0);
        TextField lastNameInput = createTextField("Last Name:", 1);
        TextField emailInput = createTextField("Email:", 2);
        TextField phoneInput = createTextField("Phone Number:", 3);
        TextField healthHistoryInput = createTextField("Health History:", 4);
        TextField insuranceIDInput = createTextField("Insurance ID:", 5);

        saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 1, 6);
        saveButton.setOnAction(e -> savePatientInfo(firstNameInput, lastNameInput, emailInput, phoneInput, healthHistoryInput, insuranceIDInput));

        // Adding components to the grid
        grid.getChildren().addAll(saveButton);

        Scene scene = new Scene(grid, 636, 404);
        window.setScene(scene);
        window.show();
    }

    private TextField createTextField(String labelText, int row) {
        Label label = new Label(labelText);
        GridPane.setConstraints(label, 0, row);
        TextField textField = new TextField();
        GridPane.setConstraints(textField, 1, row);
        grid.getChildren().addAll(label, textField);
        return textField;
    }

    private void savePatientInfo(TextField firstNameInput, TextField lastNameInput, TextField emailInput, TextField phoneInput, TextField healthHistoryInput, TextField insuranceIDInput) {
        // Ensure all fields are filled
        if (isAnyFieldEmpty(firstNameInput, lastNameInput, emailInput, phoneInput, healthHistoryInput, insuranceIDInput)) {
            AlertBox.display("Error", "All fields are required!");
            return;
        }

        // Generate a unique patient ID and create file
        String patientID = generatePatientID();
        String filename = patientID + "_PatientInfo.txt";
        try {
            saveToFile(filename, firstNameInput.getText(), lastNameInput.getText(), emailInput.getText(), phoneInput.getText(), healthHistoryInput.getText(), insuranceIDInput.getText());
            AlertBox.display("Success", "Patient information saved successfully with ID: " + patientID);

            // Add "New Patient" and "Close Window" buttons
            Button newPatientButton = new Button("New Patient");
            GridPane.setConstraints(newPatientButton, 0, 6);
            newPatientButton.setOnAction(e -> {
                // Clear input fields
                firstNameInput.clear();
                lastNameInput.clear();
                emailInput.clear();
                phoneInput.clear();
                healthHistoryInput.clear();
                insuranceIDInput.clear();
                // Remove newPatientButton and closeButton
                grid.getChildren().removeAll(newPatientButton, closeButton);
                // Restore saveButton
                grid.getChildren().add(saveButton);
            });

            closeButton = new Button("Close Window");
            GridPane.setConstraints(closeButton, 1, 6);
            closeButton.setOnAction(e -> window.close());

            // Remove previous saveButton and add new buttons
            grid.getChildren().remove(saveButton);
            grid.getChildren().addAll(newPatientButton, closeButton);

        } catch (IOException e) {
            AlertBox.display("Error", "An error occurred while saving patient information.");
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

    private String generatePatientID() {
        Random random = new Random();
        int patientID = random.nextInt(90000) + 10000; // Ensuring a 5-digit ID
        return String.valueOf(patientID);
    }

    private void saveToFile(String filename, String... details) throws IOException {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(filename)))) {
            writer.println("First Name: " + details[0]);
            writer.println("Last Name: " + details[1]);
            writer.println("Email: " + details[2]);
            writer.println("Phone Number: " + details[3]);
            writer.println("Health History: " + details[4]);
            writer.println("Insurance ID: " + details[5]);
        }
    }
}

// AlertBox class used for showing error/success messages
class AlertBox {
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);

        // Layout for the alert box
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label label = new Label(message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());

        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(closeButton, 1, 1);
        grid.getChildren().addAll(label, closeButton);
        grid.setAlignment(Pos.CENTER);

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();  // Use showAndWait to block interaction with other windows until this one is closed
    }
}
