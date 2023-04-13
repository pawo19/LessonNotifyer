import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Customer {
    private static ArrayList<Customer> customerList = new ArrayList<>();
    private int StudentID;
    private String name;
    private String parent;
    private CommunicationTool communicationTool;
    enum CommunicationTool {EMAIL, WECHAT}
    private String contact;
    private List<Lesson> lessonList;
    private TimeZone timeZone;
    private String currency;
    private String message;

    public Customer(int ID, String name, String parent, CommunicationTool communicationTool, TimeZone timeZone, String curr,String contact, List<Lesson> l) {
        this.StudentID = ID;
        this.name = name;
        this.parent = parent;
        this.communicationTool = communicationTool;
        this.timeZone = timeZone;
        this.currency = curr;
        this.contact = contact;
        this.lessonList = l;
    }

    public String getName() {
        return name;
    }
    public static ArrayList<Customer> getCustomerList() {
        return customerList;
    }
    public CommunicationTool getCommunicationTool() {
        return communicationTool;
    }
    public int getStudentID() {
        return StudentID;
    }
    public List<Lesson> getLessonList() {
        return lessonList;
    }
    public String getCurrency() {
        return currency;
    }
    public String getParent() {
        return parent;
    }
    public TimeZone getTimeZone() {
        return timeZone;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    //reads the data about customer information and lessons from an excel file(.xls), creates customer objects and stores them in the static customerList
    static public void createCustomersFromFile() {
        try {
            POIFSFileSystem fis = new POIFSFileSystem(new File("C:\\Users\\pwolf\\IdeaProjects\\LessonNotifyer\\src\\main\\resources\\lesson_info.xls"));
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);
            FileInputStream fisTimes = new FileInputStream(new File("C:\\Users\\pwolf\\IdeaProjects\\LessonNotifyer\\src\\main\\resources\\times.xls"));
            HSSFWorkbook wbTimes= new HSSFWorkbook(fisTimes);
            HSSFSheet sheetTimes = wbTimes.getSheetAt(0);
            // read the data from every row in the lesson_info file and create an instance for every customer
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Cell c = row.getCell(0);
                if(c == null) break;
                int ID = 0;
                String name = null;
                String par = null;
                Customer.CommunicationTool comm = null;
                TimeZone tz = null;
                String curr = null;
                String cont = null;
                for (Cell cell : row) {
                    if (cell.getColumnIndex() == 0) {
                        ID = (int) cell.getNumericCellValue();
                    } else if (cell.getColumnIndex() == 1) {
                        name = cell.getStringCellValue();
                    } else if (cell.getColumnIndex() == 2) {
                        par = cell.getStringCellValue();
                    } else if (cell.getColumnIndex() == 3) {
                        if (cell.getStringCellValue().equals("email")) {
                            comm = Customer.CommunicationTool.EMAIL;
                        } else comm = Customer.CommunicationTool.WECHAT;
                    } else if (cell.getColumnIndex() == 4) {
                        String zoneid = cell.getStringCellValue();
                        tz = TimeZone.getTimeZone(zoneid);
                    }else if(cell.getColumnIndex() == 5){
                        curr = cell.getStringCellValue();
                    }
                    else if(cell.getColumnIndex() == 6){
                        cont = cell.getStringCellValue();
                    } else break;
                }
                //read the rows in the times file corresponding to this customer and add them to the lessonTime array of this customer
                List<LocalDate> ld = new ArrayList<>();
                LocalTime time = null;
                Duration dur = null;
                int price = 0;
                List<Lesson> lessonL = new ArrayList<>();
                for (int j = 1; j < sheetTimes.getLastRowNum() + 1; j++) {
                    Row rowTimes = sheetTimes.getRow(j);
                    if(rowTimes.getCell(0).getNumericCellValue() == ID) {
                        for (int k = 1; k < (int) rowTimes.getLastCellNum(); k++) {
                            Cell cellTime = rowTimes.getCell(k);
                            if(cellTime.getColumnIndex()==1) {
                                String day = cellTime.getStringCellValue();
                                day = day.toUpperCase();
                                DayOfWeek d = DayOfWeek.valueOf(day);
                                for(LocalDate date: Main.getDayList().get(d)){
                                    ld.add(date);
                                }
                            }else if(cellTime.getColumnIndex() ==2){
                                LocalDateTime field = cellTime.getLocalDateTimeCellValue();
                                int hour = field.getHour();
                                int min = field.getMinute();
                                time = LocalTime.of(hour, min);
                            }else if(cellTime.getColumnIndex()==3){
                                dur = Duration.ofMinutes((long)cellTime.getNumericCellValue());
                            }else if(cellTime.getColumnIndex()==4){
                                price = (int)cellTime.getNumericCellValue();
                            }
                        }
                        for(LocalDate t: ld){
                            lessonL.add(new Lesson(dur, price, t, time));
                        }
                        Collections.sort(lessonL, new LessonSortComparator());
                        ld.clear();
                    }
                }
                customerList.add(new Customer(ID, name, par, comm, tz, curr, cont, lessonL));
            }
            fis.close();
            fisTimes.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fills the message instance variable with the customized text for each Customer in their local timeZone, logs the lesson information in the user's timeZone
    public static void createMessages(FileWriter writer) throws IOException{
        //writer is used to Log the retrieved info to a log file
        writer.write("Total lessons in " + Month.of(Main.main.getMonth()) + ", " +Main.main.getYear()+ " = " + Lesson.getMonthCount() +"\n"+"\n");
        String message;
        int cusTotal=0;
        int monthTotalRMB=0;
        int monthTotalEUR=0;
        for(Customer c : Customer.getCustomerList()){
            writer.write("Student = " + c.getName()+ " having " + c.getLessonList().size()+ " lessons this month"+"\n");
            writer.write("Lesson List: "+"\n");
            if(c.getParent().equals("none")) {
                message = "Hi " + c.getName() + ",\n\n" + "about your German lessons in "+ Main.getFormattedMonth() +"\n";
            }else{
                message = "Hi " + c.getParent() + ",\n"+ "about " + c.getName() + "'s German lesson in "+ Main.getFormattedMonth() +"\n";
            }
            message += "We would have "+c.getLessonList().size() +" lessons on the following days:\n";
            for(Lesson l : c.getLessonList()){
                ZonedDateTime zonedLessonAUT = ZonedDateTime.of(l.getDate(), l.getTime(), Main.main.getMyTimeZone().toZoneId());
                ZonedDateTime zonedLessonLocalTime = zonedLessonAUT.withZoneSameInstant(c.getTimeZone().toZoneId());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm");
                String LocalformatString = zonedLessonLocalTime.format(formatter);
                String AUTformatString = zonedLessonAUT.format(formatter);
                writer.write(AUTformatString+ ", Duration = "+l.getDuration().getSeconds()/60+" min\n");
                cusTotal = cusTotal + l.getPrice();
                message += LocalformatString+"\n";
                switch(c.getCurrency()){
                    case "EUR":
                        monthTotalEUR = monthTotalEUR + l.getPrice();
                        break;
                    case "RMB":
                        monthTotalRMB = monthTotalRMB + l.getPrice();
                        break;
                }
                //insert this lesson instance to the database
                CustomerDB.insertLessonsToDatabase(c,l);
            }
            message += "All times in " + c.getTimeZone().toZoneId()+ " time.\n" + "Please let me know if all the suggested lessons work for you.\n\nBest regards,\nPaul";
            c.setMessage(message);
            writer.write(c.getName()+ " total = " +cusTotal+ " " + c.getCurrency()+"\n"+"\n");
            writer.flush();
            cusTotal = 0;
        }
        writer.write(Month.of(Main.main.getMonth())+ "'s total is:      RMB= " + monthTotalRMB +"     EURO= "+monthTotalEUR+"\n");
        writer.flush();
    }

    public static class LessonSortComparator implements Comparator<Lesson> {

        @Override
        public int compare(Lesson o1, Lesson o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    }

}
