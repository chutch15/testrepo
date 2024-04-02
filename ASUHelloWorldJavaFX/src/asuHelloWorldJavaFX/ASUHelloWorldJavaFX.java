package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class ASUHelloWorldJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI Layout
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
     // Title label added
        Label title = new Label("Welcome to Heart Health Imaging and Recording System");
        vbox.getChildren().add(title);

        // Title
        Button btnPatientIntake = new Button("Patient Intake");
        btnPatientIntake.setOnAction(e -> openPatientIntake(primaryStage));

        Button btnCTScanTechView = new Button("CT Scan Tech View");
        btnCTScanTechView.setOnAction(e -> openCTScanTechView(primaryStage));

        Button btnPatientView = new Button("Patient View");
        btnPatientView.setOnAction(e -> openPatientView(primaryStage));

        // Adding buttons to the layout
        vbox.getChildren().addAll(btnPatientIntake, btnCTScanTechView, btnPatientView);

        Scene scene = new Scene(vbox, 488, 343);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Heart Health Imaging and Recording System");
        primaryStage.show();
    }

    private void openPatientIntake(Stage primaryStage) {
        // Implement the opening of the patient intake view
        ReceptionistView receptionistView = new ReceptionistView();
        receptionistView.display();
    }

    private void openCTScanTechView(Stage primaryStage) {
        // Implement the opening of the CT Scan Technician view
        TechView technicianView = new TechView();
        technicianView.display();
    }

    private void openPatientView(Stage primaryStage) {
        // Implement the opening of the patient view
        PatientView patientView = new PatientView();
        patientView.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
