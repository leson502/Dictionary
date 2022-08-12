package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.RunDictionaryApp;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {
    @FXML
    AnchorPane mainPane;
    @FXML
    Button btnSearch;
    @FXML
    Button btnParagraphTranslate;
    @FXML
    Button btnAddWord;
    @FXML
    Button btnEditAndDelete;
    @FXML
    Button btnIntro;
    @FXML
    Button btnGoBack;
    @FXML
    Button btnExit;
    AnchorPane searchPane;
    AnchorPane translatePane;
    AnchorPane addWordPane;
    AnchorPane editDeletePane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            searchPane = FXMLLoader.load(getClass().getResource("/view/searchScene.fxml"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            translatePane = FXMLLoader.load(getClass().getResource("/view/paragraphtranslate.fxml"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            addWordPane = FXMLLoader.load(getClass().getResource("/view/add_word.fxml"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            editDeletePane = FXMLLoader.load(getClass().getResource("/view/edit_delete.fxml"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setDefaultButtonFocus();
        setMainPane(searchPane);
    }

    public void setDefaultButtonFocus() {
        btnSearch.setFocusTraversable(false);
        btnParagraphTranslate.setFocusTraversable(false);
        btnAddWord.setFocusTraversable(false);
        btnEditAndDelete.setFocusTraversable(false);
        btnIntro.setFocusTraversable(false);
        btnGoBack.setFocusTraversable(false);
        btnExit.setFocusTraversable(false);
    }

    public void setMainPane(AnchorPane pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().addAll(pane);
    }

    public void btnSearchEventHandle(ActionEvent event) {
        setDefaultButtonFocus();
        btnSearch.setFocusTraversable(true);
        setMainPane(searchPane);
    }

    public void btnParagraphTranslateEventHandle(ActionEvent event) {
        setDefaultButtonFocus();
        btnParagraphTranslate.setFocusTraversable(true);
        setMainPane(translatePane);
    }

    public void btnAddWordEventHandle(ActionEvent event) {
        setDefaultButtonFocus();
        btnAddWord.setFocusTraversable(true);
        setMainPane(addWordPane);
    }

    public void btnEditAndDeleteEventHandle(ActionEvent event) {
        setDefaultButtonFocus();
        btnEditAndDelete.setFocusTraversable(true);
        setMainPane(editDeletePane);
    }

    public void btnIntroEventHandle(ActionEvent event) {
        setDefaultButtonFocus();
        btnIntro.setFocusTraversable(true);
    }
    public void btnGoBackEventHandle(ActionEvent event) {

        Stage getStage = (Stage) mainPane.getScene().getWindow();
        try {
            Scene sceneUse = RunDictionaryApp.sceneStore.getStartScene();
            getStage.setTitle("Dictionary Eng-Vie v1.0");
            getStage.setScene(sceneUse);
            getStage.centerOnScreen();
            getStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void btnExitEventHandle(ActionEvent event) {
        // Tạo thông báo là kiểu cảnh báo
        Alert alert = new Alert(Alert.AlertType.WARNING);
        // Lấy biểu tượng qua stage
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/Image/icon-app.png").toString()));
        // tạo ảnh cảnh báo trên alert
        ImageView warningPic = new ImageView(this.getClass().getResource("/Image/warning.png").toString());
        warningPic.setFitWidth(50);
        warningPic.setFitHeight(50);
        alert.setGraphic(warningPic);
        // tạo thông tin alert
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn có thật sự muốn thoát chương trình ?");
        alert.setContentText("Bấm \"Có\" để thoát, bấm \"Quay lại\" để quay lại !");
        ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Quay lại", ButtonBar.ButtonData.NO);
        // Xử lí khi bấm các button
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            // Nếu bấm nút "Có" thì thoát chương trình
            System.exit(0);
        } else {
            // Nếu không thì quay trở lại Scene đang hiển thị
            alert.close();
        }
    }


}
