package controller;

import api.VoiceRSS;
import connection.MysqlConnector;
import connection.WordModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class searchWordController implements Initializable {

    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private AnchorPane anchorPaneTranslate;
    @FXML
    TextField inputWord;

    @FXML
    TextArea textShowMeaning;
    @FXML
    TableView<WordModel> tableViewWord;
    @FXML
    TableColumn<WordModel, String> wordInTable;
    @FXML
    TableColumn<WordModel, String> meaningInTable;
    @FXML
    TableColumn<WordModel, Integer> indexInTable;

    ObservableList<WordModel> wordModelObservableList = FXCollections.observableArrayList();
    private AutoCompletionBinding<WordModel> listViewInput;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textShowMeaning.setEditable(false);
        MysqlConnector.getTableViewWordData(wordModelObservableList);
        indexInTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        wordInTable.setCellValueFactory(new PropertyValueFactory<>("word"));
        meaningInTable.setCellValueFactory(new PropertyValueFactory<>("meaning"));
        meaningInTable.setVisible(false);
        indexInTable.setVisible(false);
        tableViewWord.setItems(wordModelObservableList);
        FilteredList<WordModel> filteredData = new FilteredList<>(wordModelObservableList, b -> true);
        inputWord.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(wordModel -> {
                // Nếu ô input trống thì hiển thị toàn bộ danh sách
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String inputWord = newValue.toLowerCase();
                if (wordModel.getWord().toLowerCase().compareToIgnoreCase(inputWord) == 0 || wordModel.getWord().toLowerCase().compareToIgnoreCase(inputWord) > 0) {
                    return true;
                } else return false; // ko thay
            });
        });

        SortedList<WordModel> sortedWordList = new SortedList<>(filteredData);
        sortedWordList.comparatorProperty().bind(tableViewWord.comparatorProperty());
        tableViewWord.setItems(filteredData);

        tableViewWord.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                WordModel word = tableViewWord.getItems().get(tableViewWord.getSelectionModel().getSelectedIndex());
                textShowMeaning.setText(word.getMeaning());
            }
        });
        tableViewWord.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //Don't show header
                Pane header = (Pane) tableViewWord.lookup("TableHeaderRow");
                if (header.isVisible()) {
                    header.setMaxHeight(0);
                    header.setMinHeight(0);
                    header.setPrefHeight(0);
                    header.setVisible(false);
                }
            }
        });

    }


    // Vì hiện tại đang ở chức năng dịch từ nên ko cần set action
    @FXML
    private void btnTranslateClick(MouseEvent mouseEvent) {
        Alert("Bạn đang ở trang dịch từ", "Bạn đã ở đây rồi !", "/image/infomation.png");
    }


    // Xem hướng dẫn sử dụng phần mềm
    @FXML
    private void btnShowIntroClick(MouseEvent mouseEvent) {
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

    // Action thoát khỏi ứng dụng
    @FXML
    private void exitButtonClick(MouseEvent mouseEvent) {
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

    public void btnVoiceUSClick(MouseEvent mouseEvent) {
        if (tableViewWord.getSelectionModel().getSelectedItem() == null) {
            speak("Chi", "vi-vn", 0.85, "Bạn chưa chọn từ vựng, hãy chọn từ vựng trước !!");
        } else {
            WordModel word = tableViewWord.getItems().get(tableViewWord.getSelectionModel().getSelectedIndex());
            String wordToAudio = word.getWord();
            speak("Linda", "en-gb", 0.85, wordToAudio);
        }
    }

    public void btnVoiceUKClick(MouseEvent mouseEvent) {
        if (tableViewWord.getSelectionModel().getSelectedItem() == null) {
            speak("Linda", "vi-vn", 0.85, "Bạn chưa chọn từ vựng, hãy chọn từ vựng trước !!");
        } else {
            WordModel word = tableViewWord.getItems().get(tableViewWord.getSelectionModel().getSelectedIndex());
            String wordToAudio = word.getWord();
            speak("Chi", "en-us", 0.85, wordToAudio);
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
