package ua.edu.sumdu.j2se.Klymenko.tasks.controller.notifications;

import ua.edu.sumdu.j2se.Klymenko.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

/**
 * Notification interface
 */
public interface Notifications {

    /**
     * Represents notification sending to the task manager through notification manager
     *
     * @param calendar map contains tasks in selected (hour) time period
     */
    void send (SortedMap<LocalDateTime, Set<Task>> calendar);
}
