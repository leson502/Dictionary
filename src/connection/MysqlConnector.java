package connection;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class có nhiệm vụ kết nối đến cơ sở dữ liệu và truyền các dữ liệu lấy được vào một mảng !
 */
public class MysqlConnector {

    // UserName đăng nhập vào mysql
    private static String userName = "root";
    // password đăng nhập vào mysql
    private static String password = "%M0unt41n";
    // địa chỉ thông qua local host để kết nối đến đúng database trong mysql
    private static String urlConnection = "jdbc:mysql://localhost:3306/ditc?";

    /**
     * Hàm get dữ liệu và đẩy từ vào trong list các từ !
     * @param listWord ArrayList
     */
    public static void takeDataToArray(ArrayList<WordModel> listWord) {
        try {
            Connection conn = DriverManager.getConnection(urlConnection, userName, password);
            if (conn != null) {
                System.out.println("Đã kết nối đến cơ sở dữ liệu");

                // Tạo truy vấn
                Statement statement = (Statement) conn.createStatement();
                // Lệnh truy vấn lấy tất cả dữ liệu từ bảng tbl_dict có trong kết nối.
                String sql = "SELECT * FROM main";
                // Thực hiện truy vấn
                ResultSet rs = statement.executeQuery(sql);
                // Nếu như vẫn còn dữ liệu thì xử lí gán dữ liệu
                while (rs.next()) {
                    int index = rs.getInt("id");
                    String word1 = rs.getString("word");
                    String meaning = rs.getString("meaning");
                    // Khi lấy được 1 dòng dữ liệu thì gán dữ liệu cho đối tượng mới bằng dữ liệu vừa truy vấn được
                    WordModel wordModel = new WordModel(index, word1, meaning);
                    // thêm từ vào ArrayList để phục vụ các chức năng
                    listWord.add(wordModel);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ! Không thể kết nối đến mysql");
            e.printStackTrace();
        }
    }

    public static void takeDataMeaningToArray(ArrayList<String> listWord, String input){
        try {
            Connection conn = DriverManager.getConnection(urlConnection, userName, password);
            if (conn != null) {
                System.out.println("Đã kết nối đến cơ sở dữ liệu");

                // Tạo truy vấn
                Statement statement = (Statement) conn.createStatement();
                // Lệnh truy vấn lấy tất cả dữ liệu từ bảng tbl_dict có trong kết nối.
                String sql = "SELECT meaning FROM main where meaning='" + input + "'";
                // Thực hiện truy vấn
                ResultSet rs = statement.executeQuery(sql);
                // Nếu như vẫn còn dữ liệu thì xử lí gán dữ liệu
                while (rs.next()) {
                    String meaning = rs.getString("meaning");
                    listWord.add(meaning);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ! Không thể kết nối đến mysql");
            e.printStackTrace();
        }
    }

    public static void getTableViewWordData(ObservableList<WordModel> wordModelObservableList) {
        try {
            Connection conn = DriverManager.getConnection(urlConnection, userName, password);
            if (conn != null) {
                System.out.println("Đã kết nối đến cơ sở dữ liệu");

                // Tạo truy vấn
                Statement statement = (Statement) conn.createStatement();
                // Lệnh truy vấn lấy tất cả dữ liệu từ bảng tbl_dict có trong kết nối.
                String sql = "SELECT id,word,meaning FROM main";
                // Thực hiện truy vấn
                ResultSet rs = statement.executeQuery(sql);
                // Nếu như vẫn còn dữ liệu thì xử lí gán dữ liệu
                while (rs.next()) {
                    Integer index = rs.getInt("id");
                    String word = rs.getString("word");
                    String meaning = rs.getString("meaning");
                    wordModelObservableList.add(new WordModel(index,word,meaning));
                }

            }
        } catch (SQLException e) {
            System.out.println("Lỗi ! Không thể kết nối đến mysql");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<WordModel> list = new ArrayList<WordModel>();
        takeDataToArray(list);
        for (WordModel e:
             list) {
            System.out.println(e.getWord() + " | " + e.getMeaning());
        }
    }
}
