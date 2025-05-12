package com.rendezvous.management.rvmangfr.controller;

import com.rendezvous.management.rvmangfr.model.Appointment;
import com.rendezvous.management.rvmangfr.service.AppointmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentController {

    private TableView<Appointment> appointmentTable;
    private ObservableList<Appointment> appointmentData;
    private Stage primaryStage;
    private final AppointmentService appointmentService = new AppointmentService();

    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setScene(createAppointmentScene());
        stage.setTitle("Appointments Management");
        stage.show();
        try {
            loadAppointments();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Scene createAppointmentScene() {
        // Title
        Label titleLabel = new Label("Appointment Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #1E3A8A;");

        // Appointment Table
        appointmentTable = createAppointmentTable();

        // Buttons
        VBox buttonBox = createButtonBox();

        // Layout
        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setCenter(appointmentTable);
        root.setRight(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(20));

        root.setStyle("-fx-background-color: white;");

        return new Scene(root, 800, 600);
    }

    private TableView<Appointment> createAppointmentTable() {
        TableView<Appointment> table = new TableView<>();
        table.setPlaceholder(new Label("No appointments available"));

        TableColumn<Appointment, String> clientCol = new TableColumn<>("Client ID");
        clientCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getId() != null ? data.getValue().getId().toString() : ""
        ));

        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getDate() != null ? data.getValue().getDate().toString() : ""
        ));

        TableColumn<Appointment, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getTime() != null ? data.getValue().getTime().toString() : ""
        ));

        table.getColumns().addAll(clientCol, dateCol, timeCol);

        return table;
    }

    private VBox createButtonBox() {
        Button addButton = createStyledButton("Add Appointment", "#3B82F6", "white");
        Button editButton = createStyledButton("Edit Appointment", "#3B82F6", "white");
        Button deleteButton = createStyledButton("Delete Appointment", "#EF4444", "white");
        Button backButton = createStyledButton("Back", "#6B7280", "white");

        addButton.setOnAction(e -> handleAddAppointment());
        editButton.setOnAction(e -> handleEditAppointment());
        deleteButton.setOnAction(e -> {
            try {
                handleDeleteAppointment();
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton.setOnAction(e -> navigateBack());

        VBox vbox = new VBox(10, addButton, editButton, deleteButton, backButton);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private Button createStyledButton(String text, String bgColor, String textColor) {
        Button button = new Button(text);
        button.setPrefWidth(180);
        button.setStyle("-fx-background-color: " + bgColor + "; -fx-text-fill: " + textColor + ";");
        return button;
    }

    private void loadAppointments() throws IOException, InterruptedException {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        appointmentData = FXCollections.observableArrayList(appointments);
        appointmentTable.setItems(appointmentData);
    }

    // Button Handlers
    public void handleAddAppointment() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Appointment");
        dialog.setHeaderText("Add New Appointment (Format: ClientId,yyyy-MM-dd, HH:mm)");

        dialog.showAndWait().ifPresent(input -> {
            String[] parts = input.split(",");
            if (parts.length == 3) {
                try {
                    Long clientId = Long.parseLong(parts[0].trim());
                    LocalDate date = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDateTime dateTime = LocalDateTime.parse(parts[2].trim(), DateTimeFormatter.ofPattern(" HH:mm"));
                    Appointment newAppointment = new Appointment(clientId, date, dateTime); // Correct constructor usage
                    appointmentService.addAppointment(newAppointment); // Assuming this method exists in AppointmentService
                    loadAppointments(); // Reload appointments after adding
                } catch (Exception e) {
                    showAlert("Error", "Invalid input format or server error.");
                }
            } else {
                showAlert("Invalid Input", "Please provide ClientId, Date, and Time separated by a comma.");
            }
        });
    }

    public void handleEditAppointment() {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            TextInputDialog dialog = new TextInputDialog(selected.getId() + "," + selected.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            dialog.setTitle("Edit Appointment");
            dialog.setHeaderText("Edit Appointment (Format: ClientId,yyyy-MM-dd HH:mm)");

            dialog.showAndWait().ifPresent(input -> {
                String[] parts = input.split(",");
                if (parts.length == 2) {
                    try {
                        selected.setId(Long.parseLong(parts[0].trim()));
                        selected.setDate(LocalDate.from(LocalDateTime.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
                        appointmentService.updateAppointment(selected.getId(), selected);
                        loadAppointments();
                    } catch (Exception e) {
                        showAlert("Error", "Invalid input format or server error.");
                    }
                } else {
                    showAlert("Invalid Input", "Please provide ClientId and DateTime separated by a comma.");
                }
            });
        } else {
            showAlert("No Selection", "Please select an appointment to edit.");
        }
    }

    public void handleDeleteAppointment() throws IOException, InterruptedException {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            appointmentService.deleteAppointment(selected.getId());
            loadAppointments();
        } else {
            showAlert("No Selection", "Please select an appointment to delete.");
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
