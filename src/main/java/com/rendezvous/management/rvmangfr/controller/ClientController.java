package com.rendezvous.management.rvmangfr.controller;

import com.rendezvous.management.rvmangfr.model.Client;
import com.rendezvous.management.rvmangfr.service.ClientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientController {

    private TableView<Client> clientTable;
    private ObservableList<Client> clientData;
    private Stage primaryStage;
    private final ClientService clientService = new ClientService();

    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setScene(createClientScene());
        stage.setTitle("Patients - Appointment Management");
        stage.show();
        loadClients();
    }

    private Scene createClientScene() {
        Label titleLabel = new Label("Patients Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #1E3A8A;");

        clientTable = createClientTable();
        VBox buttonBox = createButtonBox();

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setCenter(clientTable);
        root.setRight(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(20));
        root.setStyle("-fx-background-color: white;");

        return new Scene(root, 800, 600);
    }

    private TableView<Client> createClientTable() {
        TableView<Client> table = new TableView<>();
        table.setPlaceholder(new Label("No Patients available"));

        TableColumn<Client, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Client, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Client, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhone()));

        table.getColumns().addAll(nameCol, emailCol, phoneCol);

        return table;
    }

    private VBox createButtonBox() {
        Button addButton = createStyledButton("Add Client", "#3B82F6", "white");
        Button editButton = createStyledButton("Edit Client", "#3B82F6", "white");
        Button deleteButton = createStyledButton("Delete Client", "#EF4444", "white");
        Button backButton = createStyledButton("Back", "#6B7280", "white");

        addButton.setOnAction(e -> handleAddClient());
        editButton.setOnAction(e -> handleEditClient());
        deleteButton.setOnAction(e -> {
            try {
                handleDeleteClient();
            } catch (IOException | InterruptedException ex) {
                showAlert("Error", "Failed to delete client: " + ex.getMessage());
            }
        });
        backButton.setOnAction(e -> navigateBack());

        VBox vbox = new VBox(10, addButton, editButton, deleteButton, backButton);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private Button createStyledButton(String text, String bgColor, String textColor) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setStyle("-fx-background-color: " + bgColor + "; -fx-text-fill: " + textColor + ";");
        return button;
    }

    private void loadClients() {
        Task<ObservableList<Client>> task = new Task<>() {
            @Override
            protected ObservableList<Client> call() throws IOException, InterruptedException {
                return FXCollections.observableArrayList(clientService.getAllClients());
            }
        };

        task.setOnSucceeded(e -> clientTable.setItems(task.getValue()));

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            showAlert("Error", "Failed to load clients: " + ex.getMessage());
            ex.printStackTrace(); // Use logging in production
        });

        new Thread(task).start();
    }

    public void handleAddClient() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Patient");
        dialog.setHeaderText("Add New Patient (Format: Name,Email,Phone)");

        dialog.showAndWait().ifPresent(input -> {
            String[] parts = input.split(",");
            if (parts.length == 3) {
                Client newClient = new Client(null, parts[0].trim(), parts[1].trim(), parts[2].trim());
                try {
                    clientService.addClient(newClient);
                    loadClients(); // Refresh table
                } catch (IOException | InterruptedException e) {
                    showAlert("Error", "Failed to add client: " + e.getMessage());
                }
            } else {
                showAlert("Invalid Input", "Please provide Name, Email, and Phone separated by commas.");
            }
        });
    }

    public void handleEditClient() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            TextInputDialog dialog = new TextInputDialog(selected.getName() + "," + selected.getEmail() + "," + selected.getPhone());
            dialog.setTitle("Edit Patient");
            dialog.setHeaderText("Edit Patient (Format: Name,Email,Phone)");

            dialog.showAndWait().ifPresent(input -> {
                String[] parts = input.split(",");
                if (parts.length == 3) {
                    selected.setName(parts[0].trim());
                    selected.setEmail(parts[1].trim());
                    selected.setPhone(parts[2].trim());
                    try {
                        clientService.updateClient(selected);
                        loadClients(); // Refresh
                    } catch (IOException | InterruptedException e) {
                        showAlert("Error", "Failed to update client: " + e.getMessage());
                    }
                } else {
                    showAlert("Invalid Input", "Please provide Name, Email, and Phone separated by commas.");
                }
            });
        } else {
            showAlert("No Selection", "Please select a client to edit.");
        }
    }

    public void handleDeleteClient() throws IOException, InterruptedException {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            clientService.deleteClient(selected.getId());
            loadClients();
        } else {
            showAlert("No Selection", "Please select a patient to delete.");
        }
    }

    private void navigateBack() {
        HomeController homeController = new HomeController();
        homeController.start(primaryStage);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
