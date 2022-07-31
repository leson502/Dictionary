import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController extends MainController {
    protected static final Dictionary dictionary = new Dictionary();
    protected static final String dictionaryURL = "content/dictionary.txt";

    private static final int NEW_WORD = 0;
    private static final int EDIT_WORD = 1;

    private static int editMode = NEW_WORD;
    public WordDescription selectedWord;
    @FXML
    private TextField searchBar;
    @FXML
    public ListView<WordDescription> listView;
    @FXML
    public Label wordLabel;
    @FXML
    public Label meaningLabel;
    @FXML
    public TextField editWordField;
    @FXML
    public TextArea editMeaningArea;
    @FXML
    public Tab searchTab;
    @FXML
    public Tab editTab;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dictionary.importFromFile(dictionaryURL);
        listView.getItems().clear();
        listView.getItems().addAll(dictionary.prefixSearch(""));
    }

    public void searchBarEventHandle(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            selectedWord = dictionary.search(searchBar.getText());
            setLabels();
        }
        listView.getItems().clear();
        listView.getItems().addAll(dictionary.prefixSearch(searchBar.getText()));
    }

    public void listViewClickedEventHandle(Event e) {
        selectedWord = listView.getSelectionModel().getSelectedItem();
        setLabels();
        setEditField();
    }

    public void searchTabSelectionEventHandle(Event e) {
        setLabels();
    }

    public void editTabSelectionEventHandle(Event e) {
        setEditField();
    }

    public void setLabels() {
        if (selectedWord == null) {
            wordLabel.setText("");
            meaningLabel.setText("");
        } else {
            wordLabel.setText(selectedWord.getWord());
            meaningLabel.setText(selectedWord.getMeaning());
        }
    }

    public void setEditField() {
        if (selectedWord == null) {
            editWordField.setText("");
            editWordField.setEditable(true);
            editMeaningArea.setText("");
        } else {
            editMode = EDIT_WORD;
            editWordField.setText(selectedWord.getWord());
            editWordField.setEditable(false);
            editMeaningArea.setText(selectedWord.getMeaning());
        }
    }

    public void newButtonEventHandle(ActionEvent e) {
        editMode = NEW_WORD;
        editWordField.setEditable(true);
        editWordField.setText("NewWord");
        editWordField.selectAll();
        editMeaningArea.setText("Meaning");
        editMeaningArea.selectAll();
        editWordField.requestFocus();
    }

    public void editWordFieldKeyEventHandle(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            editMeaningArea.requestFocus();
        }
    }

    public void addButtonEventHandle(ActionEvent e) {
        if (editMode == NEW_WORD) {
            dictionary.insertWord(editWordField.getText()
                    , new WordDescription(editWordField.getText(), editMeaningArea.getText()));
        }

    }

    public void deleteButtonEventHandle(ActionEvent e) {
        if (editMode == EDIT_WORD)
            dictionary.delete(editWordField.getText());
    }

    public void saveButtonEventHandle(ActionEvent e) {
        if (editMode == EDIT_WORD) {
            selectedWord.setMeaning(editMeaningArea.getText());
        }
    }
}
