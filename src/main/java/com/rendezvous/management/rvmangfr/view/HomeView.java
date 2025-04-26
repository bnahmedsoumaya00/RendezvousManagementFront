package com.rendezvous.management.rvmangfr.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeView {

    public void start(Stage stage) {
        Text title = new Text("Welcome to Rendezvous Manager");
        title.setFont(Font.font(24));
        title.setFill(Color.DARKBLUE);

        Button clientButton = new Button("Clients");
        clientButton.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-size: 14px;");

        Button appointmentButton = new Button("Appointments");
        appointmentButton.setStyle("-fx-background-color: #5F9EA0; -fx-text-fill: white; -fx-font-size: 14px;");

        VBox root = new VBox(20, title, clientButton, appointmentButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();

        clientButton.setOnAction(e -> new ClientView().start(stage));
        appointmentButton.setOnAction(e -> new AppointmentView().start(stage));
    }
}
