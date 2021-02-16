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
    private final static long TIMER_SENDING = 30000; // hour
    private final static long TIMER_REPEATING = 10000; //30 sec
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
                if (ConsoleView.consoleIsAble) {
                    consoleNotification.send(map);
                } else {
                    while (true) {
                        try {
                            Thread.sleep(TIMER_REPEATING);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            logger.error("Interrupted exception.", e);
                        }
                        if (ConsoleView.consoleIsAble) {
                            emailNotification.send(map);
                            break;
                        }
                    }
                }
                view.displayMenu();
            }
            try {
                Thread.sleep(TIMER_SENDING);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("Interrupted exception.", e);
            }
        }
    }
}
