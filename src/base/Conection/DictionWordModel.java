package Conection;

public class DictionWordModel {
    protected int index;
    protected String word;
    protected String meaning;

    public DictionWordModel(int index, String word, String meaning) {
        this.index = index;
        this.word = word;
        this.meaning = meaning;
    }

    public DictionWordModel() {

    }

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
}
