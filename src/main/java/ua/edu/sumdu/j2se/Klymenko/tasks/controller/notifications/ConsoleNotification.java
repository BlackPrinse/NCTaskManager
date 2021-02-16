package ua.edu.sumdu.j2se.Klymenko.tasks.controller.notifications;

import ua.edu.sumdu.j2se.Klymenko.tasks.additions.ListStrings;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.Task;
import ua.edu.sumdu.j2se.Klymenko.tasks.view.View;
import ua.edu.sumdu.j2se.Klymenko.tasks.view.ConsoleView;

import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedMap;
import java.util.Map;

/**
 * Provides an implementation for sending console notifications to the client in the console
 */
public class ConsoleNotification implements Notifications {
    private static final Logger logger = Logger.getLogger(EmailNotification.class);
    private static final ConsoleNotification CONSOLE_NOTIFICATION = new ConsoleNotification();
    private View view;

    /**
     * The method to get ConsoleNotification instance
     * by pattern Singleton
     *
     * @return EmailNotification instance
     */
    public static ConsoleNotification getInstance() {
        return CONSOLE_NOTIFICATION;
    }

    /**
     * Sends console notification to user console client
     *
     * @param calendar map contains an incoming tasks in selected period
     */
    @Override
    public void send(SortedMap<LocalDateTime, Set<Task>> calendar) {
        view = new ConsoleView();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ListStrings.timePattern);
        view.println("\nTask Manager. \n" +
                "There are several uncompleted tasks");
        view.println("Tasks for the next hour:");
        view.println(String.format("Date:%14s Task:", " "));

        for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
            view.print(entry.getKey().format(formatter) + "\t");
            for (Task task : entry.getValue()) {
                view.println(task.getTitle());
            }
        }
        logger.info("\n" +
                "The list of planned tasks was displayed in the console");
    }
}
