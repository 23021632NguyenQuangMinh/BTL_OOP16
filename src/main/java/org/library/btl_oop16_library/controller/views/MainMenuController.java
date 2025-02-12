package org.library.btl_oop16_library.controller.views;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import atlantafx.base.controls.ModalPane;
import eu.iamgio.animated.transition.AnimatedSwitcher;
import eu.iamgio.animated.transition.AnimationPair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.library.btl_oop16_library.utils.general.ApplicationAlert;
import org.library.btl_oop16_library.utils.general.SessionManager;
import org.library.btl_oop16_library.utils.general.Motion;

import static org.library.btl_oop16_library.utils.general.GlobalVariables.*;

public class MainMenuController {


    @FXML
    private AnchorPane rootPane;

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
    private Button menuSettings;

    @FXML
    private ModalPane settingsPane;

    private AnimatedSwitcher switcher;

    private void initializeRoleBasedAccess() {
        menuUser.setVisible(SessionManager.getInstance().getCurrentUser().getRole().equals("admin"));
        FXMLLoader loader;
        if (SessionManager.getInstance().getCurrentUser().getRole().equals("admin")) {
            loader = new FXMLLoader(getClass().getResource(DASHBOARD_VIEW_PATH));
        } else {
            loader = new FXMLLoader(getClass().getResource(USER_DASHBOARD_VIEW_PATH));
        }
        Pane pane;
        try {
            pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        switcher.setChild(pane);
    }

    private void startClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");

        LocalDateTime now = LocalDateTime.now();
        clockField.setText(now.format(formatter));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime current = LocalDateTime.now();
            clockField.setText(current.format(formatter));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void switchToBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(BOOK_VIEW_PATH));
        Pane pane = loader.load();
        switcher.setChild(pane);
    }

    @FXML
    private void switchToCatalog(ActionEvent event) throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SERVICES_VIEW_PATH));
        Pane pane = loader.load();
        switcher.setChild(pane);
    }

    @FXML
    private void switchToDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader;
        if(SessionManager.getInstance().getCurrentUser().getRole().equals("admin")) {
            loader = new FXMLLoader(getClass().getResource(DASHBOARD_VIEW_PATH));
        } else {
            loader = new FXMLLoader(getClass().getResource(USER_DASHBOARD_VIEW_PATH));

        }
        Pane pane = loader.load();
        switcher.setChild(pane);
    }

    @FXML
    private void switchToUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(Objects.requireNonNull(getClass().getResource(USER_VIEW_PATH)));
        switcher.setChild(pane);
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        boolean result = ApplicationAlert.areYouSureAboutThat();
        if (result) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
            Scene loginScene = new Scene(loader.load());
            Motion.fadeTransition((Stage) logOutButton.getScene().getWindow(), logOutButton.getScene(), loginScene);
        }
    }

    public void settingsPaneSetup() {
        settingsPane = new ModalPane();
        settingsPane.setPrefSize(1280, 720);
        VBox settings = new VBox();
        settings.setId("settings");
        settings.setAlignment(Pos.CENTER);
        settings.setMaxSize(400, 500);
        settings.setStyle("-fx-background-color: -color-bg-default;");

        FXMLLoader loader = new FXMLLoader(getClass().getResource(SETTINGS_PATH));
        VBox target;
        try {
            target = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        settings.getChildren().setAll(target);
        rootPane.getChildren().addAll(settingsPane);
        menuSettings.setOnAction(actionEvent -> settingsPane.show(settings));
    }

    public void bookPaneSetup() {
        ModalPane modalPane = new ModalPane();
        modalPane.setId("bookContent");
        modalPane.setPrefSize(1280, 720);
        VBox contentHolder = new VBox();
        contentHolder.setId("bookContentVBox");
        contentHolder.setAlignment(Pos.CENTER);
        contentHolder.setMaxSize(1280, 720);
        contentHolder.setStyle("-fx-background-color: -color-bg-default;");
        rootPane.getChildren().addAll(modalPane, contentHolder);
    }

    @FXML
    private void initialize() {
        switcher = new AnimatedSwitcher(AnimationPair.zoom().setSpeed(2, .8));
        switcher.setId("switcher");
        mainPane.setCenter(switcher);
        startClock();
        initializeRoleBasedAccess();
        settingsPaneSetup();
        bookPaneSetup();
    }
}
