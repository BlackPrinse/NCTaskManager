package ua.edu.sumdu.j2se.Klymenko.tasks.controller.notifications;

import ua.edu.sumdu.j2se.Klymenko.tasks.model.Task;
import ua.edu.sumdu.j2se.Klymenko.tasks.additions.PropertyLoad;

import org.apache.log4j.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.Map;

/**
 * Provides an implementation for sending e-mail notifications to a user via TLS protocol
 */
public class EmailNotification implements Notifications {
    private static final Logger logger = Logger.getLogger(EmailNotification.class);
    private static final EmailNotification EMAIL_NOTIFICATION = new EmailNotification();
    private Session session;
    private Properties properties;

    /**
     * Constructor to load properties and create session
     */
    public EmailNotification() {
        properties = PropertyLoad.getProperties("/mail.properties");
        session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("username"),
                        properties.getProperty("password"));
            }
        });

    }

    /**
     * The method to get EmailNotification instance
     * by pattern Singleton
     *
     * @return EmailNotification instance
     */
    public static EmailNotification getInstance() {
        return EMAIL_NOTIFICATION;
    }

    /**
     * Method to convert task list to html content that we can sent on e-mail
     *
     * @param calendar map contains an incoming tasks in selected period.
     * @return a string that contains content that can be processed by the browser and ready to send
     */
    public static String getHTMLContent(SortedMap<LocalDateTime, Set<Task>> calendar) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        StringBuilder h = new StringBuilder();
        h.append("<html><body>" +
                "<h2><b> Tasks for the next hour: </b></h2><br>" +
                "<table bordercolor=\"black\" border=\"1\" cellpadding=\"7\">");
        h.append("<th> Date: </th>").append("<th> Tasks: </th>");
        for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
            h.append("<tr>");
            h.append("<td> ").append(entry.getKey().format(formatter)).append(" </td>");
            h.append("<td> ");
            for (Task task : entry.getValue()) {
                h.append(task.getTitle()).append("<br>");
            }
            h.append(" </td>").append("</tr>");
        }
        h.append("</table></body></html>");
        return h.toString();
    }

    /**
     * Sends email message to the user.
     *
     * @param calendar map contains an incoming tasks in selected period
     */
    @Override
    public void send(SortedMap<LocalDateTime, Set<Task>> calendar) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("from")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(properties.getProperty("recipients")));
            message.setSubject("Task Manager. You have upcoming tasks!");
            message.setContent(getHTMLContent(calendar), "text/html");
            message.setSentDate(new Date());
            Transport.send(message);
            logger.info("Tasks for the next hour is sent on email: " + properties.getProperty("recipients"));
        } catch (MessagingException e) {
            logger.error("Failed to send email.");
        }
    }

}
