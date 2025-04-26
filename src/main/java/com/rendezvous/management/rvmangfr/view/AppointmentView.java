package com.rendezvous.management.rvmangfr.view;

import com.rendezvous.management.rvmangfr.model.Appointment;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class AppointmentView {

    private TableView<Appointment> table;

    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f9ff;");

        Label title = new Label("Appointments");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.web("#1e3a8a"));

        table = new TableView<>();

        TableColumn<Appointment, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Appointment, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Appointment, String> clientCol = new TableColumn<>("Client Name");
        clientCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));

        table.getColumns().addAll(idCol, descCol, dateCol, clientCol);

        Button addButton = new Button("Add Appointment");
        addButton.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-font-size: 14px;");

        Button editButton = new Button("Edit Appointment");
        editButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 14px;");

        Button deleteButton = new Button("Delete Appointment");
        deleteButton.setStyle("-fx-background-color: #1d4ed8; -fx-text-fill: white; -fx-font-size: 14px;");

        HBox buttons = new HBox(10, addButton, editButton, deleteButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));

        VBox vbox = new VBox(10, title, table, buttons);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.TOP_CENTER);

        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Appointment Management");
        stage.setScene(scene);
        stage.show();
    }
}
