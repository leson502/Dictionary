package Conection;

import java.sql.*;
import java.util.ArrayList;

public class MySqlConector {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1Vithien2k";
    private static final String connectToHost = "jdbc:mysql://localhost:3306/javafx?";

    public static void takeDataToArr(ArrayList<DictionWordModel> words) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectToHost, USERNAME, PASSWORD);
            System.out.println("Connected");
            Statement statement = (Statement) conn.createStatement();
            String sql = "select * from tbl_edict";
            int count = 0;
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                DictionWordModel model = new DictionWordModel();
                model.setIndex(rs.getInt("idx"));
                model.setWord(rs.getString("word"));
                model.setMeaning(rs.getString("detail"));
                words.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<DictionWordModel> data = new ArrayList<>();
        takeDataToArr(data);
        for (DictionWordModel e:
             data) {
            System.out.println("index:" + e.index + " | Word: " + e.word + " | Meaning: " + e.meaning);
        }

    }
}
