import java.sql.*;
import java.util.Scanner;

public class Test {
    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.56.101:4567/madang",
                    "jykim", "2028576Kjy!");
            Statement stmt = con.createStatement();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("------------------------");
                System.out.println("| 메뉴를 선택하세요:   |");
                System.out.println("| 1. 데이터 검색       |");
                System.out.println("| 2. 데이터 삽입       |");
                System.out.println("| 3. 데이터 삭제       |");
                System.out.println("| 4. 종료              |");
                System.out.println("------------------------");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        // 데이터 검색
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Book");
                        while (rs.next())
                            System.out.println(rs.getInt("bookid") + " " + rs.getString("bookname") + " "
                                    + rs.getString("publisher"));
                        break;
                    case 2:
                        // 데이터 삽입
                        System.out.print("Book ID를 입력하세요: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("책 제목을 입력하세요: ");
                        String bookName = scanner.nextLine();
                        System.out.print("출판사를 입력하세요: ");
                        String publisher = scanner.nextLine();

                        String insertQuery = "INSERT INTO Book (bookid, bookname, publisher) VALUES (?, ?, ?)";
                        PreparedStatement insertStatement = con.prepareStatement(insertQuery);
                        insertStatement.setInt(1, bookId);
                        insertStatement.setString(2, bookName);
                        insertStatement.setString(3, publisher);

                        int insertResult = insertStatement.executeUpdate();
                        if (insertResult > 0) {
                            System.out.println("데이터가 삽입되었습니다.");
                        } else {
                            System.out.println("데이터 삽입에 실패했습니다.");
                        }
                        break;
                    case 3:
                        // 데이터 삭제
                        System.out.print("삭제할 Book ID를 입력하세요: ");
                        int deleteBookId = scanner.nextInt();

                        String deleteQuery = "DELETE FROM Book WHERE bookid = ?";
                        PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
                        deleteStatement.setInt(1, deleteBookId);

                        int deleteResult = deleteStatement.executeUpdate();
                        if (deleteResult > 0) {
                            System.out.println("데이터가 삭제되었습니다.");
                        } else {
                            System.out.println("데이터 삭제에 실패했습니다.");
                        }
                        break;
                    case 4:
                        System.out.println("프로그램을 종료합니다.");
                        con.close();
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("잘못된 선택입니다. 다시 선택해 주세요.");
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
