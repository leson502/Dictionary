package controller;

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

public class EditandDeleteController implements Initializable {

    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private AnchorPane anchorPaneTranslate;

    @FXML
    Button btnTranslate;
    @FXML
    Button btnEditWord;
    @FXML
    Button btnDeleteWord;
    @FXML
    Button btnConfirm;
    @FXML
    Button btnCancel;
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
    @FXML
    VBox VboxMenu;
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
        btnTranslate.setFocusTraversable(false);
        btnParagraphTranslate.setFocusTraversable(false);
        btnAddWord.setFocusTraversable(false);
        btnEditAndDelete.setFocusTraversable(true);
        btnIntro.setFocusTraversable(false);
        btnGoBack.setFocusTraversable(false);
        btnExit.setFocusTraversable(false);
        MysqlConnector.getTableViewWordData(wordModelObservableList);
        btnConfirm.setDisable(true);
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


    public void btnParagrapTranslateClick(MouseEvent mouseEvent) {
        btnParagraphTranslate.setDisable(true);
        Stage stage = (Stage) btnParagraphTranslate.getScene().getWindow();
        try {
            Parent translateParaUI = FXMLLoader.load(getClass().getResource("/view/paragraphtranslate.fxml"));
            stage.setScene(new Scene(translateParaUI));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnAddWordClick(MouseEvent mouseEvent) {
        Stage tage = (Stage) btnTranslate.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/add_word.fxml"));
            tage.setScene(new Scene(root));
            tage.centerOnScreen();
            tage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnEditAndDeleteClick(MouseEvent mouseEvent) {
        Alert("Bạn đang ở trang sửa / xoá từ", "Bạn đã ở đây rồi !", "/image/warning.png");
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

    public void backSceneButton(MouseEvent mouseEvent) {
        Stage primaryStage = (Stage) VboxMenu.getScene().getWindow();
        try {
            Parent root = new FXMLLoader().load(getClass().getResource("/view/rundictionaryapp.fxml"));
            primaryStage.setTitle("Dictionary Eng-Vie v1.0");
            Scene sceneMainApp = new Scene(root);
            primaryStage.setScene(sceneMainApp);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image("/Image/icon-app.png"));
            primaryStage.show();
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

    public void btnEditWordClick(MouseEvent mouseEvent) {
        if (tableViewWord.getSelectionModel().getSelectedItem() != null) {
            btnDeleteWord.setDisable(true);
            btnCancel.setDisable(false);
            btnConfirm.setDisable(false);
            textShowMeaning.setEditable(true);
        } else {
            Alert("Bạn chưa chọn từ bên danh sách từ", "Hãy chọn từ !", "/image/warning.png");
        }
    }

    public void btnDeleteWordClick(MouseEvent mouseEvent) {
        if (tableViewWord.getSelectionModel().getSelectedItem() != null) {
            Alert("Xoá dữ liệu thành công", "Xem lại danh sách từ !!", "/image/btnConfirm.png.png");
        } else {
            Alert("Bạn chưa chọn dữ liệu trên bảng", "Hãy chọn dữ liệu trước !!", "/Image/warning.png");
        }
    }

    public void btnConfirmClick(MouseEvent mouseEvent) {
        btnDeleteWord.setDisable(false);
        btnCancel.setDisable(true);
        btnConfirm.setDisable(true);
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
        String s = tableViewWord.getSelectionModel().getSelectedItem().getMeaning().toString();
        textShowMeaning.setText(s);
        Alert("Huỷ bỏ", "Dữ liệu không bị thay đổi !!", "/Image/warning.png");
    }

    public void btnTranslateClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnTranslate.getScene().getWindow();
        try {
            Parent translateUI = FXMLLoader.load(getClass().getResource("/view/searchScene.fxml"));
            stage.setScene(new Scene(translateUI));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
