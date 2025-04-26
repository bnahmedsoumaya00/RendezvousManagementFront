package com.rendezvous.management.rvmangfr.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AppointmentController {

    public void start(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("Appointment Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #1E3A8A;");

        // TableView for listing appointments
        TableView<String> appointmentTable = new TableView<>();
        appointmentTable.setPlaceholder(new Label("No appointments available"));

        // Add columns (for now simple placeholders)
        TableColumn<String, String> clientCol = new TableColumn<>("Client Name");
        TableColumn<String, String> dateCol = new TableColumn<>("Date");
        TableColumn<String, String> timeCol = new TableColumn<>("Time");

        appointmentTable.getColumns().addAll(clientCol, dateCol, timeCol);

        // Buttons
        Button addButton = new Button("Add Appointment");
        Button editButton = new Button("Edit Appointment");
        Button deleteButton = new Button("Delete Appointment");
        Button backButton = new Button("Back");

        addButton.setPrefWidth(180);
        editButton.setPrefWidth(180);
        deleteButton.setPrefWidth(180);
        backButton.setPrefWidth(180);

        addButton.setStyle("-fx-background-color: #3B82F6; -fx-text-fill: white;");
        editButton.setStyle("-fx-background-color: #3B82F6; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white;");
        backButton.setStyle("-fx-background-color: #6B7280; -fx-text-fill: white;");

        // Button Actions
        backButton.setOnAction(e -> {
            HomeController homeController = new HomeController();
            homeController.start(primaryStage);
        });

        // Layout
        VBox buttonBox = new VBox(10, addButton, editButton, deleteButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setCenter(appointmentTable);
        root.setRight(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(20));

        root.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Appointments - Rendezvous Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
