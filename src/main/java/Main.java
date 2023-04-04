import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Main {
    static Main main;
    private static String formattedMonth;
    private int month;
    private int year;
    private TimeZone myTimeZone;
    private static HashMap<DayOfWeek, List<LocalDate>> dayList = new HashMap<>();

    public Main(int month, int year){
        this.month  = month;
        this.year = year;
        myTimeZone = TimeZone.getTimeZone("Europe/Vienna");
    }

    public static HashMap<DayOfWeek, List<LocalDate>> getDayList() {
        return dayList;
    }
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public TimeZone getMyTimeZone() {
        return myTimeZone;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public static String getFormattedMonth() {
        return formattedMonth;
    }

    public static void main(String[] args) {
        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
        File file = new File("C:\\Users\\pwolf\\IdeaProjects\\LessonNotifyer\\src\\main\\resources\\LessonLog.txt");
        FileWriter writer;
        try {
            // "m" is the month to be processed, "y" the year; e.g. m= "4" and y = "2023" stands for April 2023
            int m = Integer.parseInt(re.readLine());
            int y = Integer.parseInt(re.readLine());
            main = new Main(m, y);
            formattedMonth = Util.formatMonth();
            Util.calculateDaysInMonth();
            Customer.createCustomersFromFile();
            writer = new FileWriter(file, true);
            Customer.createMessages(writer);
            Email.sendEmails(writer);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}










