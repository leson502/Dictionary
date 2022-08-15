package controller;

import database.DitcData;
import database.WordModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditandDeleteController implements Initializable {

    @FXML
    Button btnDeleteWord;
    @FXML
    Button btnConfirm;
    @FXML
    Button btnCancel;
    @FXML
    TextField inputWord;
    @FXML
    TextArea textShowMeaning;
    @FXML
    ListView<WordModel> wordList;
    private WordModel selectedWord;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnConfirm.setDisable(true);
        wordList.getItems().clear();
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
        wordList.getItems().addAll(DitcData.prefixSearch(inputWord.getText()));
    }

    public void btnEditAndDeleteClick(MouseEvent mouseEvent) {
        Alert("Bạn đang ở trang sửa / xoá từ", "Bạn đã ở đây rồi !", "/image/warning.png");
    }

    public void btnEditWordClick(MouseEvent mouseEvent) {
        if (selectedWord != null) {
            btnDeleteWord.setDisable(true);
            btnCancel.setDisable(false);
            btnConfirm.setDisable(false);
            textShowMeaning.setEditable(true);
        } else {
            Alert("Bạn chưa chọn từ bên danh sách từ", "Hãy chọn từ !", "/image/warning.png");
        }
    }

    public void btnDeleteWordClick(MouseEvent mouseEvent) {
        if (selectedWord != null) {
            DitcData.delete(selectedWord);
            selectedWord = null;
            Alert("Xoá dữ liệu thành công", "Xem lại danh sách từ !!", "/image/btnConfirm.png");
        } else {
            Alert("Bạn chưa chọn dữ liệu trên bảng", "Hãy chọn dữ liệu trước !!", "/Image/warning.png");
        }
    }

    public void btnConfirmClick(MouseEvent mouseEvent) {
        btnDeleteWord.setDisable(false);
        btnCancel.setDisable(true);
        btnConfirm.setDisable(true);
        DitcData.update(selectedWord, textShowMeaning.getText());
        Alert("Hoàn thành", "Dữ liệu đã được sửa, hãy xem lại thay đổi !", "/image/btnConfirm.png");
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

    public void btnCancel(MouseEvent mouseEvent) {
        btnDeleteWord.setDisable(false);
        btnConfirm.setDisable(true);
        textShowMeaning.setEditable(false);
        btnCancel.setDisable(true);
        String s = selectedWord.getMeaning();
        textShowMeaning.setText(s);
        Alert("Huỷ bỏ", "Dữ liệu không bị thay đổi !!", "/Image/warning.png");
    }
}
