import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    private final RadixTree<WordDescription> tree;

    public Dictionary() {
        tree = new RadixTree<>();
    }

    public void insertWord(String word, WordDescription description) {
        tree.insert(word, description);
    }

    public void delete(String word) {
        tree.delete(word);
    }

    public void importFromFile(String filePath) {
        try {
            Scanner scanner = new Scanner(Files.newInputStream(Paths.get(filePath)));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int firstSpacePosition = 0;
                while (firstSpacePosition < (line.length() - 1) && line.charAt(firstSpacePosition) != ' ') {
                    firstSpacePosition++;
                }

                String word = line.substring(0, firstSpacePosition);
                word = word.toLowerCase();
                WordDescription description = new WordDescription(word, line.substring(firstSpacePosition + 1));
                insertWord(word, description);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public WordDescription search(String word) {
        word = word.toLowerCase();
        return tree.search(word);
    }

    public ArrayList<WordDescription> prefixSearch(String prefix) {
        prefix = prefix.toLowerCase();
        return tree.prefixSearch(prefix, 50);
    }

    public void exportToFile(String filePath) {
        try {
            File file = new File(filePath);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            ArrayList<WordDescription> words = prefixSearch("");
            for (WordDescription word : words) {
                fileWriter.write(word.getWord() + " " + word.getMeaning() + '\n');
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        Dictionary dictionary = new Dictionary();
        dictionary.importFromFile("content/dictionary.txt");

        dictionary.exportToFile("content/dictionary.txt");
    }
}