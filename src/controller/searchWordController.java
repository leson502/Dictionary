package controller;

import api.VoiceRSS;
import database.DictionaryData;
import database.WordModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class searchWordController implements Initializable {
    @FXML
    private AnchorPane anchorPaneTranslate;
    @FXML
    TextField inputWord;
    @FXML
    TextArea textShowMeaning;
    @FXML
    ListView<WordModel> wordList;
    private WordModel selectedWord;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textShowMeaning.setEditable(false);
        wordList.getItems().clear();
        wordList.getItems().addAll(DictionaryData.prefixSearch(""));
    }

    public void btnVoiceUSClick(MouseEvent mouseEvent) {
        if (selectedWord == null) {
            speak("Chi", "vi-vn", 0.85, "Bạn chưa chọn từ vựng, hãy chọn từ vựng trước !!");
        } else {
            speak("Linda", "en-gb", 0.85, selectedWord.getWord());
        }
    }

    public void wordListClickedEventHandle(Event e) {
        selectedWord = wordList.getSelectionModel().getSelectedItem();
        if (selectedWord != null)
            textShowMeaning.setText(selectedWord.getMeaning());
        else
            textShowMeaning.setText("");
    }

    public void inputWordEventHandle(KeyEvent e) {
        wordList.getItems().clear();
        wordList.getItems().addAll(DictionaryData.prefixSearch(inputWord.getText()));
    }

    public void btnVoiceUKClick(MouseEvent mouseEvent) {
        if (selectedWord == null) {
            speak("Linda", "vi-vn", 0.85, "Bạn chưa chọn từ vựng, hãy chọn từ vựng trước !!");
        } else {
            speak("Chi", "en-gb", 0.85, selectedWord.getWord());
        }
    }

    private void speak(String name, String language, double speed, String word) {
        try {
            VoiceRSS.Name = name;
            VoiceRSS.language = language;
            VoiceRSS.speed = speed;
            VoiceRSS.speakWord(word);
        } catch (Exception e) {
            e.printStackTrace();
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
}
