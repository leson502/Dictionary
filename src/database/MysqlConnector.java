package database;

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
    private static String password = "zJPkyYe#rLZVWL@2";
    // địa chỉ thông qua local host để kết nối đến đúng database trong mysql
    private static String urlConnection = "jdbc:mysql://localhost:3306/ditc?";

    private static Connection con;


    public static void init() {
        try {
            con = DriverManager.getConnection(urlConnection, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static {
        init();
    }

    /**
     * Hàm get dữ liệu và đẩy từ vào trong list các từ !
     *
     * @param listWord ArrayList
     */
    public static void takeDataToArray(ArrayList<WordModel> listWord) {
        try {
            if (con != null) {
                System.out.println("Đã kết nối đến cơ sở dữ liệu");

                // Tạo truy vấn
                Statement statement = (Statement) con.createStatement();
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

    public static void takeDataMeaningToArray(ArrayList<String> listWord, String input) {
        try {
            if (con != null) {
                System.out.println("Đã kết nối đến cơ sở dữ liệu");

                // Tạo truy vấn
                Statement statement = (Statement) con.createStatement();
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
            Connection con = DriverManager.getConnection(urlConnection, userName, password);
            if (con != null) {
                System.out.println("Đã kết nối đến cơ sở dữ liệu");

                // Tạo truy vấn
                Statement statement = (Statement) con.createStatement();
                // Lệnh truy vấn lấy tất cả dữ liệu từ bảng tbl_dict có trong kết nối.
                String sql = "SELECT id,word,meaning FROM main";
                // Thực hiện truy vấn
                ResultSet rs = statement.executeQuery(sql);
                // Nếu như vẫn còn dữ liệu thì xử lí gán dữ liệu
                while (rs.next()) {
                    Integer index = rs.getInt("id");
                    String word = rs.getString("word");
                    String meaning = rs.getString("meaning");
                    wordModelObservableList.add(new WordModel(index, word, meaning));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ! Không thể kết nối đến mysql");
            e.printStackTrace();
        }
    }

    public static void addingWord(int id,String word, String meaning) {
        try {
            if (con != null) {
                System.out.println("Đã kết nối đến cơ sở dữ liệu");

                // truy vấn lấy id cuối danh sách
                Statement statement = (Statement) con.createStatement();
                String sql = "SELECT id FROM main ORDER BY id DESC LIMIT 1";
                ResultSet rs = statement.executeQuery(sql);
                int lastId = 0;
                if (rs.next()) {
                    lastId = rs.getInt("id");
                }

                //truy vấn thêm từ
                PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO main VALUES (?, ?, ?)");
                preparedStatement.setInt(1,id);
                preparedStatement.setString(2, word);
                preparedStatement.setString(3, meaning);

                preparedStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ! Không thể kết nối đến mysql");
            e.printStackTrace();
        }
    }

    public static ArrayList<WordModel> prefixSearch(String word, int limit) {
        ArrayList<WordModel> result = new ArrayList<>();
        try {
            if (con != null) {
                String sql = "SELECT * from main where word LIKE ? LIMIT ?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, word+"%");
                statement.setInt(2, limit);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    result.add(new WordModel(rs.getInt("id"), rs.getString("word"),
                            rs.getString("meaning")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ! Không thể kết nối đến mysql");
            e.printStackTrace();
        }
        return result;
    }

    public static void deleteWord(int id) {
        try {
            if (con != null) {
                System.out.println("Đã kết nối đến cơ sở dữ liệu");

                // Tạo truy vấn
                String sql = "DELETE FROM main WHERE id = ?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, String.valueOf(id));
                statement.execute();

            }
        } catch (SQLException e) {
            System.out.println("Lỗi ! Không thể kết nối đến mysql");
            e.printStackTrace();
        }
    }

    public static void updateWord(int id, String meaning) {
        try {
            if (con != null) {
                System.out.println("Đã kết nối đến cơ sở dữ liệu");

                String sql = "UPDATE main SET meaning = ? WHERE id = ?";
                // Tạo truy vấn
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, meaning);
                statement.setInt(2, id);
                statement.execute();

            }
        } catch (SQLException e) {
            System.out.println("Lỗi ! Không thể kết nối đến mysql");
            e.printStackTrace();
        }
    }

}
