package ua.edu.sumdu.j2se.Klymenko.tasks.additions;

public class ListStrings {
    public static final String [] mainMenu = new String[] {
            "\n---------------> Task Manager <---------------",
            "1 -> Add any task",
            "2 -> Edit exist task",
            "3 -> Delete exist task",
            "4 -> Show task list",
            "5 -> Show calendar",
            "0 -> Exit",
            "----------------------------------------------"
    };
    public static final String [] editMenu = new String[] {
            "\n---------------> Editing menu <---------------",
            "1 -> Edit title",
            "2 -> Edit activity status",
            "3 -> Edit time or make task non-repeating",
            "4 -> Edit time/interval or make task repeating",
            "0 -> Return to main menu",
            "----------------------------------------------"
    };
    public static final String selectAndEnter = "Select an action and enterv its number here -> ";
    public static final String enterIndex = "Enter the task index to ";
    public static final String sureToRemove = "To remove you should enter \"Yes\"" +
            "\nAre you sure you want to exit (Yes/No)? ";
    public static final String pecv = "Please enter correct value: ";
    public static final String fieldIsEmpty = " field is empty. Please enter correct value: ";
    public static final String taskSelected = "task selected";
    public static final String enterPeriod = "date of the period: ";
    public static final String successsAdded = "was successfully added to the list";
    public static final String successChanged = " was changed successfully on: ";
    public static final String ptr = "Please try again";
    public static final String interaptedEx = "Interrupted exception";
    public static final String timePattern = "dd.MM.yyyy HH:mm";


}
