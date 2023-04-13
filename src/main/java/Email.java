import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Email extends MimeMessage {

    public Email(Session session) {
        super(session);
    }

    public void createEmail(String contact, String text, String month){
        try{
            this.addHeader("Content-type", "text/HTML; charset=UTF-8");
            this.addHeader("format", "flowed");
            this.addHeader("Content-Transfer-Encoding", "8bit");
            this.setFrom(new InternetAddress("myEmail@Address.com"));
            this.setSubject("German lessons in " +month, "UTF-8");
            this.setText(text, "UTF-8");
            this.setRecipients(Message.RecipientType.TO, InternetAddress.parse(contact, false));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sendEmails(FileWriter writer){
        String myEmail = "myEmail@Address.com";
        String password = "Password";
        Properties prop= new Properties();
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        };
        javax.mail.Session emailSession = javax.mail.Session.getInstance(prop, auth);
        Email em;
        for(Customer c : Customer.getCustomerList()){
            if(c.getCommunicationTool() == Customer.CommunicationTool.EMAIL){
                em = new Email(emailSession);
                em.createEmail(c.getContact(), c.getMessage(),Main.getFormattedMonth());
                try {
                    Transport.send(em);
                    writer.write("The Email to " + c.getName() + " has been successfully sent.\n");
                    writer.flush();
                }
               catch(MessagingException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        try {
            writer.write("--------------------------------------------------------" + "\n\n\n\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
