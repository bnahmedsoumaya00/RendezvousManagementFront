
package com.rendezvous.management.rvmangfr.view;

import com.rendezvous.management.rvmangfr.controller.ClientController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientView {

    private final ClientController controller;

    public ClientView() {
        this.controller = new ClientController(); // Fixed: initialize controller correctly
    }

    public void start(Stage stage) {
        show(stage);
    }

    public void show(Stage primaryStage) {
        BorderPane layout = new BorderPane();

        // Top Title
        Label title = new Label("Patients Management");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: #007BFF; -fx-padding: 20px;");
        layout.setTop(title);

        // Client List
        ListView<String> clientList = new ListView<>();
        clientList.setStyle("-fx-control-inner-background: white; -fx-font-size: 14px;");

        // Buttons
        Button addButton = new Button("Add Patient");
        Button editButton = new Button("Edit Patient");
        Button deleteButton = new Button("Delete Patient");

        addButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");
        editButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

        HBox buttons = new HBox(10, addButton, editButton, deleteButton);
        buttons.setStyle("-fx-padding: 20px;");

        VBox center = new VBox(10, clientList, buttons);
        layout.setCenter(center);

        // Actions
        addButton.setOnAction(e -> controller.handleAddClient());
        editButton.setOnAction(e -> controller.handleEditClient());
        deleteButton.setOnAction(e -> {
            try {
                controller.handleDeleteClient();
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Patients Management");
        primaryStage.show();
    }
}
