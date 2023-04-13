import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Lesson{

    private static AtomicLong monthCount = new AtomicLong(0);
    private static AtomicLong DBLessonCount = new AtomicLong(Main.main.getLastID());
    private final long lessonID;
    private Duration duration;
    private int price;
    private LocalDate date;
    private LocalTime time;

    public Lesson(Duration duration, int price, LocalDate date, LocalTime time){
        lessonID = DBLessonCount.getAndIncrement();
        this.duration = duration;
        this.price = price;
        this.date = date;
        this.time = time;
        monthCount.getAndIncrement();
    }

    public long getLessonID() {
        return lessonID;
    }

    public static AtomicLong getMonthCount() {
        return monthCount;
    }

    public static AtomicLong getDBLessonCount() {
        return DBLessonCount;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
