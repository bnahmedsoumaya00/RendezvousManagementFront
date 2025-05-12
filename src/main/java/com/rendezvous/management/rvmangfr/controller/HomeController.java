package com.rendezvous.management.rvmangfr.controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Welcome Doctor!");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: #1E3A8A;");

        Button manageClientsBtn = createStyledButton("Manage Patients");
        Button manageAppointmentsBtn = createStyledButton("Manage Appointments");

        manageClientsBtn.setOnAction(e -> openClientScreen(primaryStage));
        manageAppointmentsBtn.setOnAction(e -> openAppointmentScreen(primaryStage));

        VBox root = new VBox(25, titleLabel, manageClientsBtn, manageAppointmentsBtn);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #ffffff;");
        root.setPrefSize(600, 400);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Home - Appointment Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("""
            -fx-background-color: #3B82F6;
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-padding: 10 20 10 20;
            -fx-background-radius: 8;
        """);
        button.setPrefWidth(220);
        return button;
    }

    private void openClientScreen(Stage primaryStage) {
        try {
            ClientController clientController = new ClientController();
            clientController.start(primaryStage);
        } catch (Exception e) {
            showError("Failed to open the patient screen. Please check the API or data format.", e);
        }
    }

    private void openAppointmentScreen(Stage primaryStage) {
        try {
            AppointmentController appointmentController = new AppointmentController();
            appointmentController.start(primaryStage);
        } catch (Exception e) {
            showError("Failed to open the appointment screen.", e);
        }
    }

    private void showError(String message, Exception e) {
        e.printStackTrace(); // Useful for logs during dev
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An unexpected error occurred");
        alert.setContentText(message + "\n\n" + e.getMessage());
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
