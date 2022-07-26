import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public static String searchSceneURL = "SearchScene.fxml";
    public static String editSceneURL = "EditScene.fxml";
    @FXML
    AnchorPane mainPage;
    AnchorPane searchPane;
    AnchorPane editPane;

    AnchorPane currPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            searchPane = FXMLLoader.load(getClass().getResource(searchSceneURL));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            editPane = FXMLLoader.load(getClass().getResource(editSceneURL));
        } catch (Exception e) {
            e.printStackTrace();
        }
        currPane = searchPane;
        mainPage.getChildren().addAll(currPane);
    }

    public void switchPageSearch(ActionEvent e) throws Exception {
        swichPane(searchPane);
    }

    public void switchPageEdit(ActionEvent e) throws Exception {
        swichPane(editPane);
    }

    public void swichPane(AnchorPane switchedPane) {
        if (currPane != switchedPane) {
            mainPage.getChildren().remove(currPane);
            currPane = switchedPane;
            mainPage.getChildren().addAll(currPane);

        }
    }
}
