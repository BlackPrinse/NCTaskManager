package ua.edu.sumdu.j2se.Klymenko.tasks.view;

import ua.edu.sumdu.j2se.Klymenko.tasks.additions.ListStrings;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.Task;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.Tasks;
import ua.edu.sumdu.j2se.Klymenko.tasks.additions.TimeConvetrer;

import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;



/**
 * The class is designed to create a console menu. To set up good information exchange in the console.
 * Prints data to the CLI and requests the value of the controller.
 */
public class ConsoleView implements View{
    private static final Logger logger = Logger.getLogger(ConsoleView.class);
    private Scanner scanner = new Scanner(System.in);

    public static boolean consoleIsAble = true;

    /**
     * Constructor to make start console view
     */
    public ConsoleView() {
    }

    /**
     * Output text to the console without carriage transfer
     *
     * @param str a message to be displayed to the console client
     */
    @Override
    public void print(String str) {
        System.out.print(str);
    }

    /**
     * Output text to the console with carriage transfer
     *
     * @param str a message to be displayed to console client
     */
    @Override
    public void println(String str) {
        System.out.println(str);
    }

    /**
     * Output the console menu to the console
     */
    @Override
    public void displayMenu() {
        println("\n---------------> Task Manager <---------------");

        println("1 -> Add any task");
        println("2 -> Edit exist task");
        println("3 -> Delete exist task");
        println("4 -> Show task list");
        println("5 -> Show calendar");
        println("0 -> Exit");
        println("----------------------------------------------");
        print("Select an action and enterv its number here -> ");
    }

    /**
     * Output to the console of the editing menu
     */
    @Override
    public void displayEditMenu() {
        println("\n---------------> Editing menu <---------------");

        println("1 -> Edit title");
        println("2 -> Edit activity status");
        println("3 -> Edit time or make task non-repeating");
        println("4 -> Edit time/interval or make task repeating");
        println("0 -> Return to main menu");
        println("----------------------------------------------");
        print("Select an action and enter its number here -> ");
    }

    /**
     * Output the console list tasks to the console
     *
     * @param tasks task list.
     */
    @Override
    public void showTaskList(AbstractTaskList tasks) {
        if (tasks.size() == 0) {
            emptyList();
        } else {
            notEmptyList(tasks);
        }
    }

    /**
     * Output to the console a message about the selection of the task
     * and the return of the selected task of the controller
     *
     * @param list task list
     * @return index of element in task list
     */
    @Override
    public int selectTask(AbstractTaskList list) {
        int id;
        if (list.size() == 0) {
            emptyList();
            return 0;
        } else {
            notEmptyList(list);
            print(ListStrings.enterIndex + "edit: ");
            id = readInt(1, list.size());
        }
        return id;
    }

    /**
     * Output to the console a message about deleting the task and a request to delete the task
     *
     * @param list task list
     * @return index of element in task list
     */
    @Override
    public int removeTask(AbstractTaskList list) {
        if (list.size() == 0) {
            emptyList();
            return 0;
        } else {
            notEmptyList(list);
            print(ListStrings.enterIndex + "remove: ");
            int id = readInt(1, list.size());
            print("To remove you should enter \"Yes\"" +
                    "\nAre you sure you want to exit (Yes/No)? ");
            if (checkUserAnswer()) {
                return id;
            }
        }
        return -1;
    }

    /**
     * Displays the date request for the calendar. The calendar for the selected period is then displayed
     *
     * @param list task list
     * @throws ClassNotFoundException
     */
    @Override
    public void getCalendar(AbstractTaskList list) throws ClassNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ListStrings.timePattern);
        while (true) {
            print("Enter start" + ListStrings.enterPeriod);
            LocalDateTime start = parseDateTime();
            print("Enter end" + ListStrings.enterPeriod);
            LocalDateTime end = parseDateTime();

            if (!start.isAfter(end)) {
                SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(list, start, end);
                println("-----------------> CALENDAR <-----------------");
                if (calendar.isEmpty()) {
                    println("There are no upcoming tasks");
                } else {
                    println("Tasks for the selected period: ");
                    for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
                        String date = entry.getKey().format(formatter);
                        println("Date: " + date);
                        for (Task task : entry.getValue()) {
                            println("Task: " + task.getTitle());
                        }
                        println("----------------------------------------------");
                    }
                }
                break;
            } else {
                print("Wrong parameters! Enter the correct time period for the calendar.");
            }
        }
    }

    /**
     * Gets title from client and verify it
     *
     * @return string title into a controller
     */
    @Override
    public String getTitle() {
        String title = "";
        print("Enter task name: ");
        while (title.isEmpty()) {
            title = scanner.nextLine();
            if (title.isEmpty()) {
                print("Title" + ListStrings.fieldIsEmpty);
            }
        }
        return title;
    }

    /**
     * Gets activity status from user
     *
     * @return activity status (boolean) True if task is active
     */
    @Override
    public boolean getActiveStatus() {
        String s = "";
        print("Enter activity status (true/false): ");
        while (!(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))) {
            s = scanner.nextLine();
            if (s.isEmpty()) {
                print("Activity status" + ListStrings.fieldIsEmpty);
            } else if (!(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))) {
                print("Wrong status." + ListStrings.ptr);
            }
        }
        return Boolean.parseBoolean(s);
    }

    /**
     * Gets task repetition status from user
     *
     * @return repetition status integer value into a controller
     */
    @Override
    public int getIsTaskRepeated() {
        int choice;
        do {
            print("Enter [1] to create simple non-repeating task or [2] to repeating task: ");
            choice = readInt(1, 2);
            if (choice == 1) {
                print("Non-repeating " + ListStrings.taskSelected);
                return choice;
            } else if (choice == 2) {
                print("Repeating " + ListStrings.taskSelected);
                return choice;
            } else if (choice == -1) {
                break;
            }
        } while (choice != -1);
        return choice;
    }

    /**
     * Gets time interval in minutes from user
     *
     * @return atime interval value in minutes into a controller
     */
    @Override
    public int getInterval() {
        print("Enter the interval in minutes: ");
        return readInt(1, Integer.MAX_VALUE) * 60;

    }

    /**
     * Used to get date time from representation.
     *
     * @return date time.
     */
    @Override
    public LocalDateTime parseDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ListStrings.timePattern);
        String date = "";
        LocalDateTime time;
        print("Enter the date of the following format: " + ListStrings.timePattern + " ");
        while (true) {
            try {
                date = scanner.nextLine();
                time = LocalDateTime.parse(date, formatter);
                break;
            } catch (DateTimeParseException e) {
                logger.error("Incorrect date format");
            }
            print("Incorrect! Please enter the date of the following time: " + ListStrings.timePattern + " ");
        }
        return time;
    }

    /**
     * Displays an information about task to representation.
     *
     * @param task task object.
     */
    @Override
    public void displayTaskInfo(Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ListStrings.timePattern);
        StringBuilder builder = new StringBuilder();
        builder.append("Task title: " + task.getTitle());
        if (!task.isRepeated()) {
            builder.append("\ntime: " + task.getTime().format(formatter));
        } else {
            builder.append(",\nstart time: " + task.getStartTime().format(formatter)
                    + ",\nend time: " + task.getEndTime().format(formatter))
                    .append(",\ninterval: " + TimeConvetrer.convertSec(task.getRepeatInterval()));
        }
        builder.append(",\nactive: " + task.isActive() + ".");
        println(builder.toString());
    }

    /**
     * Checks user confirmation from console client
     *
     * @return result checking conformation in boolean type (true if user confirmed request)
     */
    @Override
    public boolean checkUserAnswer() {
        return userAnswer().equalsIgnoreCase("yes");
    }

    /**
     * Method of displaying a message on the console that the list is empty
     */
    private void emptyList() {
        print("The task list is empty! Please create at least one task.\n");
    }

    /**
     * A method for displaying non-empty tasks in the console
     *
     * @param tasks tasks list to output in console
     */
    private void notEmptyList(AbstractTaskList tasks) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ListStrings.timePattern);

        StringBuffer strBuffer = new StringBuffer();
        int size = tasks.size();
        for (int i = 0; i < size; i++) {

            Task task = tasks.getTask(i);
            String title = task.getTitle();
            String time = task.getTime().format(formatter);
            String start = task.getStartTime().format(formatter);
            String end = task.getEndTime().format(formatter);
            int interval = task.getRepeatInterval();
            String active = task.isActive() ? "active" : "inactive";

            strBuffer.append("\n-------------------> Task <-------------------");
            if (task.isRepeated()) {
                strBuffer.append("\nIndex -> ");
                strBuffer.append(i + 1);
                strBuffer.append("\nTitle -> ");
                strBuffer.append(title);
                strBuffer.append("\nStart time -> ");
                strBuffer.append(start);
                strBuffer.append("\nEnd time -> ");
                strBuffer.append(end);
                strBuffer.append("\nInterval -> ");
                strBuffer.append(TimeConvetrer.convertSec(interval));
                strBuffer.append("\nIs active -> ");
                strBuffer.append(active);
            } else if (!task.isRepeated()) {
                strBuffer.append("\nIndex -> ");
                strBuffer.append(i + 1);
                strBuffer.append("\nTitle -> ");
                strBuffer.append(title);
                strBuffer.append("\nTime -> ");
                strBuffer.append(time);
                strBuffer.append("\nIs repeating -> ");
                strBuffer.append("non-repeating");
                strBuffer.append("\nIs active -> ");
                strBuffer.append(active);
            }
            strBuffer.append("\n----------------------------------------------");
            println(strBuffer.toString());
            strBuffer.delete(0, strBuffer.length());
        }

    }

    /**
     * Output to the console a request to confirm the action by the user
     * and transfer it to controller
     *
     * @return user ansver to controller
     */
    private String userAnswer() {
        String userAnswer = scanner.nextLine();
        while (!userAnswer.equalsIgnoreCase("yes") & !userAnswer.equalsIgnoreCase("no")) {
            println("You should select [yes] or [no]: ");
            userAnswer = scanner.nextLine().toLowerCase(Locale.ROOT);
        }
        return userAnswer;
    }

    /**
     *Reading integers matching specified conditions
     *
     * @param min the minimum suitable value
     * @param max the maximum suitable value
     * @return the entered number that satisfies the conditions
     */
    private int readInt(int min, int max) {
        while (true) {
            String line = scanner.nextLine();
            try {
                if (line.equalsIgnoreCase("quit")) {
                    return -1;
                }
                int n = Integer.parseInt(line);
                if (n >= min && n <= max) {
                    return n;
                }
            } catch (NumberFormatException e) {
                logger.error("Incorrect input value.");
            }
            print("Please, enter correct value or [quit] to cancel: ");
        }

    }
}