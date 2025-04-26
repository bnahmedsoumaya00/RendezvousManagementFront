package com.rendezvous.management.rvmangfr.controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("Welcome Doctor !");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #1E3A8A;");

        // Buttons
        Button manageClientsBtn = createStyledButton("Manage Patients");
        Button manageAppointmentsBtn = createStyledButton("Manage Appointments");

        // Actions
        manageClientsBtn.setOnAction(e -> openClientScreen(primaryStage));
        manageAppointmentsBtn.setOnAction(e -> openAppointmentScreen(primaryStage));

        // Layout
        VBox root = new VBox(20, titleLabel, manageClientsBtn, manageAppointmentsBtn);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white;");

        // Scene
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Home - Appointment Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #3B82F6; -fx-text-fill: white; -fx-font-size: 16px;");
        button.setPrefWidth(200);
        return button;
    }

    private void openClientScreen(Stage primaryStage) {
        try {
            ClientController clientController = new ClientController();
            clientController.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAppointmentScreen(Stage primaryStage) {
        try {
            AppointmentController appointmentController = new AppointmentController();
            appointmentController.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
