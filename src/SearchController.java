import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


import java.net.URL;
import java.util.ResourceBundle;

public class SearchController extends PrimaryController {
    @FXML
    TextField textField;
    @FXML
    ListView<WordDescription> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        listView.getItems().addAll(dictionary.prefixSearch(""));
    }

    public void searchEventHandle(KeyEvent e) {
        listView.getItems().clear();
        listView.getItems().addAll(dictionary.prefixSearch(textField.getText()));

    }
}
