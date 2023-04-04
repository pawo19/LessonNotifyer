import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Util {

    // formats months from number to word e.g. "4" to "April"
    public static String formatMonth(){
        String m = Month.of(Main.main.getMonth()).toString();
        String temp1 = m.substring(0,1);
        String temp2 = m.substring(1).toLowerCase();
        m = temp1+temp2;
        return m;
    }

    // adds for a specific month in a specific year all instances of a weekday to the corresponding list. e.g. all mondays to the monday list
    // the finished lists are added to the DayList Map in Main
    public static void calculateDaysInMonth(){
        LocalDate firstDay;
        LocalDate lastDay;
        List<LocalDate> mondays = new ArrayList<>();
        List<LocalDate> tuesdays = new ArrayList<>();
        List<LocalDate> wednesdays = new ArrayList<>();
        List<LocalDate> thursdays = new ArrayList<>();
        List<LocalDate> fridays = new ArrayList<>();
        List<LocalDate> saturdays = new ArrayList<>();
        List<LocalDate> sundays = new ArrayList<>();

        if(Main.main.getMonth() == 12){
            firstDay = LocalDate.of(Main.main.getYear(), Main.main.getMonth(), 1);
            lastDay = LocalDate.of(Main.main.getYear()+1, 1, 1);
        }
        else {
            firstDay = LocalDate.of(Main.main.getYear(), Main.main.getMonth(), 1);
            lastDay = LocalDate.of(Main.main.getYear(), Main.main.getMonth() + 1, 1);
        }
        while(firstDay.isBefore(lastDay)) {
            DayOfWeek dow = firstDay.getDayOfWeek();
            switch(dow){
                case MONDAY:
                    mondays.add(firstDay);
                    break;
                case TUESDAY:
                    tuesdays.add(firstDay);
                    break;
                case WEDNESDAY:
                    wednesdays.add(firstDay);
                    break;
                case THURSDAY:
                    thursdays.add(firstDay);
                    break;
                case FRIDAY:
                    fridays.add(firstDay);
                    break;
                case SATURDAY:
                    saturdays.add(firstDay);
                    break;
                case SUNDAY:
                    sundays.add(firstDay);
                    break;
            }
            firstDay = firstDay.plusDays(1);
        }
        Main.getDayList().put(DayOfWeek.MONDAY, mondays);
        Main.getDayList().put(DayOfWeek.TUESDAY, tuesdays);
        Main.getDayList().put(DayOfWeek.WEDNESDAY, wednesdays);
        Main.getDayList().put(DayOfWeek.THURSDAY, thursdays);
        Main.getDayList().put(DayOfWeek.FRIDAY, fridays);
        Main.getDayList().put(DayOfWeek.SATURDAY, saturdays);
        Main.getDayList().put(DayOfWeek.SUNDAY, sundays);
    }
}
