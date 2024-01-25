package com.example.pop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    private double x, y;
    public Parent root;
    public FXMLLoader loader;
    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        root = loader.load();

        HomeController homeController = loader.getController();

        //acceder a la fenetre du splashscreen qui sera affichee a chaque ouverture de l'application
        AnchorPane splashScreenPane = (AnchorPane) root.lookup("#SplashScreen");
        splashScreenPane.toFront();

        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}