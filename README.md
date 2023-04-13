# LessonNotifyer
A little program to send out lesson notifications

The program is meant to send out customized emails from a teachers to his/her students taking regular lessons. 

The user has to enter two arguments in the command line. First the month as a number, and then the year as a number (e.g. "4", "2023").

The program then calculates all lessons for all students in this specific month, and sends out personalised eamils to them.

The program creates Customer objects from an Excel file called "Lesson_Info" (The file must be in the right format). This mostly consists of data
needed to send the email such as the name, parent's name (if applicable), time zone and duration
The Excel file "times" consists of the studentID taking the lesson and the information about the student's regular lesson times. 
e.g. Mondays 7am at the student's time zone

The lesson instances for all lessons of all students in the specified month are being calculated. Then personalized emails with all
information about all lessons at the customer's local time in this month are being sent to the customer.

The Lessonlog is meant as an overview for the teacher, all lessons of all students in the specified month are printed to the log in the teacher's
local time (set to Europe/Vienna).
