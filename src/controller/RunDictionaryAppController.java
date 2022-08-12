package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.RunDictionaryApp;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RunDictionaryAppController implements Initializable {

    @FXML
    private Button startButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void buttonStartClick(MouseEvent mouseEvent) {
        // Lấy stage đang được scene đầu tiên sử dụng
        Stage getStage = (Stage) startButton.getScene().getWindow();
        try {
            Scene sceneUse = RunDictionaryApp.sceneStore.getAppScene();
            getStage.setTitle("Dictionary Eng-Vie v1.0");
            getStage.setScene(sceneUse);
            getStage.centerOnScreen();
            getStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitButtonClick(MouseEvent mouseEvent) {
        // Tạo thông báo là kiểu cảnh báo
        Alert alert = new Alert(Alert.AlertType.WARNING);
        // Lấy biểu tượng qua stage
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/image/icon-app.png").toString()));
        // tạo ảnh cảnh báo trên alert
        ImageView warningPic = new ImageView(this.getClass().getResource("/image/warning.png").toString());
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
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);
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
