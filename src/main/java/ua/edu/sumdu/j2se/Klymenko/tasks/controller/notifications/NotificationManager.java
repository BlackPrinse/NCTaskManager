package ua.edu.sumdu.j2se.Klymenko.tasks.controller.notifications;

import ua.edu.sumdu.j2se.Klymenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.Task;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.Tasks;
import ua.edu.sumdu.j2se.Klymenko.tasks.view.ConsoleView;
import ua.edu.sumdu.j2se.Klymenko.tasks.view.View;

import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

/**
 * Class a notification manager that after a certain time period notifies
 * the user to mail and console cliet about upcoming active tasks.
 */
public class NotificationManager extends Thread {
    private static final Logger logger = Logger.getLogger(NotificationManager.class);
    private final static long TIMER = 1200000; //20 min
    private AbstractTaskList list;
    private Notifications emailNotification;
    private Notifications consoleNotification;
    private View view;

    /**
     * Constructor that sets the list and create e-mail and console notification
     *
     * @param list task list.
     */
    public NotificationManager(AbstractTaskList list) {
        this.list = list;
        view = new ConsoleView();
        emailNotification = EmailNotification.getInstance();
        consoleNotification = ConsoleNotification.getInstance();
    }

    /**
     * Sends a notification to the console client and e-mail
     */
    @Override
    public void run() {
        SortedMap<LocalDateTime, Set<Task>> map = null;
        while (true) {
            try {
                map = Tasks.calendar(list, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (map.isEmpty()) {
                logger.info("Map is empty! Calendar has no tasks for the next period.");
            } else {
                emailNotification.send(map);
                consoleNotification.send(map);
                view.displayMenu();

            }
            try {
                Thread.sleep(TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("Interrupted exception.", e);
            }
        }
    }
}
