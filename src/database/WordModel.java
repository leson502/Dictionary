package database;

/**
 * Class đại diện cho những properties trong database !!!!
 */
public class WordModel {

    // Chỉ số tăng dần chỉ mục của các từ trong csdl
    private int index;
    // Biến đại diện cho từ trong csdl
    private String word;
    // Biến đại diện cho nghĩa của từ
    private String meaning;

    /**
     * Hàm khởi tạo ít sẽ có nhiệm vụ thêm từ vào danh sách
     * @param index String
     * @param word String
     * @param meaning String
     */
    public WordModel(int index, String word, String meaning) {
        this.index = index;
        this.word = word;
        this.meaning = meaning;
    }

    public WordModel() {}

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return word;
    }
}
