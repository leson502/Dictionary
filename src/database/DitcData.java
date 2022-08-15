package database;


import java.util.ArrayList;
import java.util.regex.Matcher;

public class DitcData {

    private static int idMax = 0;
    private static final RadixTree<WordModel> tree = new RadixTree<>();

    public static void insertWord(String word, String meaning) {
        idMax++;
        tree.insert(new String(word + String.valueOf(idMax)).toLowerCase(), new WordModel(idMax, word, meaning));
        MysqlConnector.addingWord(idMax, word, meaning);
    }

    public static void delete(WordModel word) {
        tree.delete(new String(word.getWord() + String.valueOf(word.getIndex())).toLowerCase());
        MysqlConnector.deleteWord(word.getIndex());
    }

    public static void update(WordModel word, String newMeaning) {
        word.setMeaning(newMeaning);
        MysqlConnector.updateWord(word.getIndex(), newMeaning);
    }
    public static ArrayList<WordModel> prefixSearch(String prefix) {
        prefix = prefix.toLowerCase();
        return tree.prefixSearch(prefix, 100);
    }

    public static void importFromDatabase() {
        ArrayList<WordModel> wordModels = new ArrayList<>();
        MysqlConnector.takeDataToArray(wordModels);
        for (WordModel w : wordModels) {
            idMax = Math.max(w.getIndex(), idMax);
            tree.insert(new String(w.getWord() + String.valueOf(w.getIndex())).toLowerCase(), w);
        }
    }
}
