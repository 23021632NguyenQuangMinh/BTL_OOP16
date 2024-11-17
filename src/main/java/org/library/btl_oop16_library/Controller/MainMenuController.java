package org.library.btl_oop16_library.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.library.btl_oop16_library.Util.ApplicationAlert;
import org.library.btl_oop16_library.Util.Transtition;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button logOutButton;

    @FXML
    private Button menuBook;

    @FXML
    private Label clockField;

    @FXML
    private Button menuCatalog;

    @FXML
    private Button menuDashboard;

    @FXML
    private Button menuUser;

    @FXML
    private VBox menuVbox;

    private void startClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            clockField.setText(now.format(formatter));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    void switchToBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(getClass().getResource("/org/library/btl_oop16_library/view/BookView.fxml"));
        mainPane.setCenter(pane);
    }

    @FXML
    void switchToCatalog(ActionEvent event) throws IOException  {
        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(getClass().getResource("/org/library/btl_oop16_library/view/CatalogView.fxml"));
        mainPane.setCenter(pane);
    }

    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(getClass().getResource("/org/library/btl_oop16_library/view/CatalogView.fxml"));
        mainPane.setCenter(pane);
    }

    @FXML
    void switchToUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(getClass().getResource("/org/library/btl_oop16_library/view/UserView.fxml"));
        mainPane.setCenter(pane);
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        Optional<ButtonType> result = ApplicationAlert.areYouSureAboutThat();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/library/btl_oop16_library/view/Login.fxml"));
            Scene loginScene = new Scene(loader.load());
            Transtition.fadeTransition((Stage) logOutButton.getScene().getWindow(), logOutButton.getScene(), loginScene);
        }

        return;
    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        for (Node button : menuVbox.getChildren().filtered(node -> node instanceof Button)) {
            button.getStyleClass().remove("selected");
        }

        Button selectedButton = (Button) event.getSource();
        selectedButton.getStyleClass().add("selected");
    }

    @FXML
    void initialize() {
        assert menuBook != null : "fx:id=\"menuBook\" was not injected: check your FXML file 'MainMenu.fxml'.";
        assert menuDashboard != null : "fx:id=\"menuDashboard\" was not injected: check your FXML file 'MainMenu.fxml'.";
        assert menuUser != null : "fx:id=\"menuUser\" was not injected: check your FXML file 'MainMenu.fxml'.";
        assert menuVbox != null : "fx:id=\"menuVbox\" was not injected: check your FXML file 'MainMenu.fxml'.";
        startClock();
    }



}
