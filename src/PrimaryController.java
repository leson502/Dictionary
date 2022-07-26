import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController extends MainController implements Initializable {
    static protected Dictionary dictionary = new Dictionary();
    static protected String dictionaryURL = "content/dictionary.txt";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            dictionary.importFromFile(dictionaryURL);
    }
}
