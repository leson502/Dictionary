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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddWordController implements Initializable {

    @FXML
    TextField wordAddText;

    @FXML
    TextField classifier;

    @FXML
    TextField pronunciation;

    @FXML
    TextArea meaningText;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void btnAddWordClick(MouseEvent mouseEvent) {
        Alert("Bạn đang ở trang thêm từ", "Bạn đã ở đây rồi !", "/image/warning.png");
    }


    public void btnShowIntroClick(MouseEvent mouseEvent) {
        try {
            String s = System.getProperty("user.dir");
            File introFile = new File(s + "\\src\\intro\\file.txt");
            if (introFile.exists()) {
                Desktop.getDesktop().open(introFile);
            } else {
                Alert("Không tìm thấy file", "Hãy thử lại", "/image/warning.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void exitButtonClick(MouseEvent mouseEvent) {
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


    private void Alert(String alertContent, String guideContent, String icon) {
        // Tạo thông báo là kiểu cảnh báo
        Alert alert = new Alert(Alert.AlertType.WARNING);
        // Lấy biểu tượng qua stage
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/Image/icon-app.png").toString()));
        // tạo ảnh cảnh báo trên alert
        ImageView warningPic = new ImageView(this.getClass().getResource(icon).toString());
        warningPic.setFitWidth(50);
        warningPic.setFitHeight(50);
        alert.setGraphic(warningPic);
        // tạo thông tin alert
        alert.setTitle("Thông báo");
        alert.setHeaderText(alertContent);
        alert.setContentText(guideContent);
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        // Xử lí khi bấm các button
        alert.getButtonTypes().setAll(buttonTypeOK);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {
            alert.close();
        }
    }

    public void btnAddWordToDatabaseClick(MouseEvent mouseEvent) {
        String s1 = wordAddText.getText().toString();
        String s2 = classifier.getText().toString();
        String s3 = pronunciation.getText().toString();
        String s4 = meaningText.getText().toString();
        if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty()) {
            Alert("Bạn điền thiếu thông tin", "Hãy đảm bảo bạn đã điền đầy đủ thông tin trong 4 ô đã cho !", "/image/warning.png");
        } else {
            Alert("Đã thêm từ thành công !", "Quay lại \"tra từ \" để xem kết quả ! ", "/image/btnConfirm.png");
        }

    }

    public void btnClearClick(MouseEvent mouseEvent) {
        wordAddText.setText("");
    }

    public void btnClearClick1(MouseEvent mouseEvent) {
        classifier.setText("");

    }

    public void btnClearClick2(MouseEvent mouseEvent) {
        pronunciation.setText("");
    }

    public void btnClearClick3(MouseEvent mouseEvent) {
        meaningText.setText("");
    }
}
