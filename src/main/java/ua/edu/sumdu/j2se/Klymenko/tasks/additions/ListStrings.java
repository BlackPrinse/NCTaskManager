package ua.edu.sumdu.j2se.Klymenko.tasks.additions;

public class ListStrings {
    public static final String enterIndex = "Enter the task index to ";

    public static final String incorectData (String field) {
        return "Incorrect " + field + ". Please enter correct value: ";
    }
    public static final String fieldIsEmpty (String field) {
        return field + " field is empty. Please enter correct value: ";
    }
    public static final String taskSelected (String taskType) {
        return taskType + " task selected";
    }
    public static final String enterPeriod (String pointPeriod) {
        return "Enter " + pointPeriod + "date of the period: ";
    }
    public static final String successsAdded (String taskName) {
        return "The task " + taskName + "was successfully added to the list";
    }
    public static final String successChanged (String field) {
        return field +  " was changed successfully on: ";
    }
    public static final String wrongField (String field) {
        return "Wrong " + field + ". Please try again";
    }
}
