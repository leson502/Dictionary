import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public static String searchScene = "SearchScene.fxml";
    @FXML
    public AnchorPane searchPane;

    @FXML
    public AnchorPane mainPage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            searchPane = FXMLLoader.load(getClass().getResource("SearchScene.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPage.getChildren().setAll(searchPane);

    }

    public void switchPageSearch(ActionEvent e) {
        switchPane(searchPane);
    }

    public void switchPane(AnchorPane switchedPane) {
        mainPage.getChildren().clear();
        mainPage.getChildren().addAll(switchedPane);
    }
}
