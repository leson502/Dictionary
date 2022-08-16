package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ParagraphTranslateController implements Initializable {
    @FXML
    Button btnTranslateParagraph;
    @FXML
    private TextArea paragraphContent;
    @FXML
    private TextArea paragraphTranslated;

    @FXML
    private Text languageFrom;
    @FXML
    private Text languageTo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnTranslateParagraph.setFocusTraversable(false);
    }


    // Mở hướng dẫn sử dụng
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

    public void btnTranslateParagraphEventHandle(ActionEvent event) {
        String paragraphs = paragraphContent.getText().toString().toLowerCase();
        if (!paragraphs.isEmpty()) {
            String s1 = languageFrom.getText().toString();
            String s2 = languageTo.getText().toString();
            String result = (String) api.GoogleTranslateAPI.googleTranslate(splitString(s1), splitString(s2), paragraphs);
            paragraphTranslated.setText(result);
        } else {
            Alert("Bạn chưa nhập văn bản cần dịch", "Hãy nhập văn bản !", "/image/warning.png");
        }
    }

    @FXML
    private void btnChangeLanguageEventHandle(ActionEvent event) {
        String s1 = null;
        s1 = languageFrom.getText().toString();
        String s2 = null;
        s2 = languageTo.getText().toString();
        languageFrom.setText(s2);
        languageTo.setText(s1);
        paragraphContent.setText("");
        paragraphTranslated.setText("");
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

    private String splitString(String word) {
        String[] child = word.split("-");
        return child[0].trim();
    }
}
