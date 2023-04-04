import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Lesson {

    private static AtomicLong count = new AtomicLong(1);
    private final long lessonID;
    private Duration duration;
    private int price;
    private LocalDate date;
    private LocalTime time;

    public Lesson(Duration duration, int price, LocalDate date, LocalTime time){
        lessonID = count.getAndIncrement();
        this.duration = duration;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    public long getLessonID() {
        return lessonID;
    }

    public static AtomicLong getCount() {
        return count;
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
