package com.rendezvous.management.rvmangfr.controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Label
        Label titleLabel = new Label("Welcome to Rendezvous Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #1E3A8A;"); // Nice blue

        // Buttons
        Button manageClientsBtn = new Button("Manage Clients");
        Button manageAppointmentsBtn = new Button("Manage Appointments");

        manageClientsBtn.setStyle("-fx-background-color: #3B82F6; -fx-text-fill: white; -fx-font-size: 16px;");
        manageAppointmentsBtn.setStyle("-fx-background-color: #3B82F6; -fx-text-fill: white; -fx-font-size: 16px;");

        manageClientsBtn.setPrefWidth(200);
        manageAppointmentsBtn.setPrefWidth(200);

        // Button Actions
        manageClientsBtn.setOnAction(e -> {
            try {
                openClientScreen(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        manageAppointmentsBtn.setOnAction(e -> openAppointmentScreen(primaryStage));

        // Layout
        VBox root = new VBox(20, titleLabel, manageClientsBtn, manageAppointmentsBtn);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white;");

        // Scene
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Home - Rendezvous Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Navigate to Client Management
    private void openClientScreen(Stage primaryStage) throws IOException, InterruptedException {
        ClientController clientController = new ClientController();
        clientController.start(primaryStage);
    }

    // Navigate to Appointment Management
    private void openAppointmentScreen(Stage primaryStage) {
        AppointmentController appointmentController = new AppointmentController();
        appointmentController.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
