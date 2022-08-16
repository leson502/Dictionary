package main;

import database.DictionaryData;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

/**
 * Class chính để chạy chương trình
 */

public class RunDictionaryApp extends Application {
    public Stage stage;
    public static SceneStore sceneStore = new SceneStore();
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        try {
            stage.setScene(sceneStore.getStartScene());
            stage.setTitle("Phần mềm từ điển v1.0");
            stage.setOnCloseRequest(closeAppNotice());
            stage.setResizable(false);
            stage.getIcons().add(new Image("/image/icon-app.png"));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xử lí sự kiện khi nhấp dấu "X" tắt chương trình.
     *
     * @return EventHandler<WindowEvent>
     */
    private EventHandler<WindowEvent> closeAppNotice() {
        EventHandler<WindowEvent> eventHandler = new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // Đánh dấu Sự kiện này là đã tiêu thụ. Điều này ngăn chặn sự lan truyền tiếp theo của nó.
                event.consume();
                // Tạo cửa sổ thông báo là kiểu cảnh báo
                Alert alert;
                alert = new Alert(Alert.AlertType.WARNING);
                // Lấy biểu tượng cho cửa sổ thông báo qua stage
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
        };
        return eventHandler;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
