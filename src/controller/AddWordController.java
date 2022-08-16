package controller;

import database.DictionaryData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
            DictionaryData.insertWord(s1, "@" + s1 + " " + s3 + "\n" + s1 + "\n" + s4);
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
