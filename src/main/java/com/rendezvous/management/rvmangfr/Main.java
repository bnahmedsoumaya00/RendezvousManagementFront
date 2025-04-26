package com.rendezvous.management.rvmangfr;


import com.rendezvous.management.rvmangfr.controller.HomeController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        HomeController homeController = new HomeController();
        homeController.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}