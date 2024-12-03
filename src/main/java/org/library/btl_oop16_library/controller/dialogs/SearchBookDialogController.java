package org.library.btl_oop16_library.controller.dialogs;

import atlantafx.base.controls.ModalPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.library.btl_oop16_library.controller.views.BookListViewController;
import org.library.btl_oop16_library.model.Book;
import org.library.btl_oop16_library.services.GoogleBookAPI;

import java.io.IOException;
import java.util.List;

import static org.library.btl_oop16_library.utils.general.GlobalVariables.BOOK_LIST_VIEW_PATH;

public class SearchBookDialogController {
    @FXML
    public Button backButton;

    @FXML
    private Button addButton;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane searchPane;

    private Book selectedBook;

    public Button getBackButton() {
        return backButton;
    }

    @FXML
    private void onSearchButtonClick(ActionEvent event) throws IOException {
        String searchText = searchField.getText();

        System.out.println("Search text: " + searchText);

        List<Book> books = GoogleBookAPI.searchBooks(searchText);
        mainPane.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(BOOK_LIST_VIEW_PATH));
        Parent root = loader.load();

        BookListViewController controller = loader.getController();
        controller.setGeneralPane(searchPane);

        controller.setBooks(books);
        mainPane.setCenter(root);
    }

    @FXML
    private void initialize() {
        ModalPane detailsPane = new ModalPane();
        detailsPane.setId("detailsPane");
        VBox detailsVBox = new VBox();
        detailsVBox.setId("detailsVBox");
        detailsVBox.setPrefSize(1280,720);
        detailsPane.setPrefSize(1280,720);
        searchPane.getChildren().addAll(detailsPane, detailsVBox);
        detailsVBox.toBack();
    }
}

