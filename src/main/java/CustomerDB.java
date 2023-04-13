import java.sql.*;
import java.time.LocalDateTime;

public class CustomerDB {

    static Connection conn = null;
    static String url = "jdbc:mariadb://localhost:3306/lessonnotifyer";
    static String user = "user";
    static String password =  "Password";
    static String insertLesson = "INSERT INTO lessons (lessonID, studentID, lessonDate, timeZone, duration, price, currency)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";
    static PreparedStatement statement = null;

    public static void connect() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertLessonsToDatabase(Customer customer, Lesson lesson){
      try{
          LocalDateTime dateTime = LocalDateTime.of(lesson.getDate(), lesson.getTime());
          statement = conn.prepareStatement(insertLesson);
          statement.setLong(1, lesson.getLessonID());
          statement.setInt(2, customer.getStudentID());
          statement.setTimestamp(3,Timestamp.valueOf(dateTime));
          statement.setString(4, customer.getTimeZone().toZoneId().toString());
          statement.setInt(5, (int)lesson.getDuration().toMinutes());
          statement.setInt(6, lesson.getPrice());
          statement.setString(7, customer.getCurrency());
          int update = statement.executeUpdate();
      }catch(Exception e){
          e.printStackTrace();
      }
    }

    public static int getLastLessonID(){
        String selectID = "SELECT MAX(lessonID) as maxID FROM lessons";
        int i = 1;
        try {
            Statement sta = conn.createStatement();
            ResultSet resultSet = sta.executeQuery(selectID);
            while(resultSet.next()) {
                i = resultSet.getInt("maxID") + 1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return i;
    }

}
