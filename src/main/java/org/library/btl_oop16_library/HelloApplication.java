package org.library.btl_oop16_library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.resizableProperty().setValue(Boolean.FALSE);
        Parent root = FXMLLoader.load(getClass().getResource("bookView.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        DatabaseConnector databaseConnector = new DatabaseConnector();
        boolean isConnected = databaseConnector.isConnectionValid();

        if(isConnected){
            System.out.println("OK");
        }
    }

    public static void main(String[] args) {

        launch();
    }
}