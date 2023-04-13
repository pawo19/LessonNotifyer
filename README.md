# LessonNotifyer
A little program that reads Data from two Excel files, processes the Data, and then adds the processed data to a database and sends out email notifications.

The program is meant to send out customized emails from a teacher to his/her students taking regular lessons. 

The user has to enter two arguments in the command line. First the month as a number, and then the year as a number (e.g. "4", "2023").

The program then calculates all lessons for all students in this specific month, and sends out personalised eamils to them. The emails use the lesson information relevant for the students converted to the students timeZone, information relevant for the teacher in the teacher's time zone is printed to the lessonLog.txt
Every regular timed lesson of a student is also added to the Database.

The program creates Customer objects from an Excel file called "Lesson_Info" (The file must be in the right format). This mostly consists of data
needed to send the email such as the name, parent's name (if applicable), time zone and duration
The Excel file "times" consists of the studentID taking the lesson and the information about the student's regular lesson times. 
e.g. Mondays 7am at the student's time zone

The student and lesson files are filled with test data. The LessonLog file shows the calculated lessons for  April and December 2023.
