package ua.edu.sumdu.j2se.Klymenko.tasks.view;

import ua.edu.sumdu.j2se.Klymenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.Task;

import java.time.LocalDateTime;

/**
 * View
 */
public interface View {
    /**
     * Output text to the console without carriage transfer
     *
     * @param str a message to be displayed to the console client
     */
    void print(String str);

    /**
     * Output text to the console with carriage transfer
     *
     * @param str a message to be displayed to console client
     */
    void println(String str);

    /**
     * Output the console menu to the console
     */
    void displayMenu();

    /**
     * Output the console edit menu to the console
     */
    void displayEditMenu();

    /**
     * Output the console list tasks to the console
     *
     * @param model task list.
     */
    void showTaskList(AbstractTaskList model);

    /**
     * Output to the console a message about the selection of the task
     * and the return of the selected task of the controller
     *
     * @param list task list
     * @return index of element in task list
     */
    int selectTask(AbstractTaskList list);

    /**
     * Output to the console a message about deleting the task and a request to delete the task
     *
     * @param model task list
     * @return index of element in task list
     */
    int removeTask(AbstractTaskList model);

    /**
     * Displays the date request for the calendar. The calendar for the selected period is then displayed
     *
     * @param list task list
     * @throws ClassNotFoundException
     */
    void getCalendar(AbstractTaskList list) throws ClassNotFoundException;

    /**
     * Gets title from client and verify it
     *
     * @return string title into a controller
     */
    String getTitle();

    /**
     * Gets activity status from user
     *
     * @return activity status (boolean) True if task is active
     */
    boolean getActiveStatus();

    /**
     * Gets task repetition status from user
     *
     * @return repetition status integer value into a controller
     */
    int getIsTaskRepeated();

    /**
     * Gets time interval in minutes from user
     *
     * @return atime interval value in minutes into a controller
     */
    int getInterval();

    /**
     * Used to get date time from representation.
     *
     * @return date time.
     */
    LocalDateTime parseDateTime();

    /**
     * Displays an information about task to representation.
     *
     * @param task task object.
     */
    void displayTaskInfo(Task task);

    /**
     * Checks user confirmation from console client
     *
     * @return result checking conformation in boolean type (true if user confirmed request)
     */
    boolean checkUserAnswer();
}

