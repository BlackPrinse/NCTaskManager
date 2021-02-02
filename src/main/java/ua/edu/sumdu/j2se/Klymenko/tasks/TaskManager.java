package ua.edu.sumdu.j2se.Klymenko.tasks;

import ua.edu.sumdu.j2se.Klymenko.tasks.controller.Controller;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.ListTypes;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.Klymenko.tasks.model.TaskListFactory;
import ua.edu.sumdu.j2se.Klymenko.tasks.view.ConsoleView;

import org.apache.log4j.Logger;
/**
 * Console task manager implemented using mvc template
 */
public class TaskManager {
    public final static String DATA_JSON_PATH = "./data/data.json";
    private static final Logger logger = Logger.getLogger(TaskManager.class);
    private AbstractTaskList list;

    /**
     * Constructor initializes the task list (array)
     */
    public TaskManager() throws ClassNotFoundException {
        this.list = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
    }

    /**
     * Runs task manager. Load task list from storage-file, start cobtroller and initialize view
     * When the application is correctly finished saves data to storage (json file)
     *
     * @throws ClassNotFoundException
     */
    public void startManager() throws ClassNotFoundException {
        logger.info("Program started by user.");
        TaskIO.readFromFile(list, DATA_JSON_PATH);
        ConsoleView view = new ConsoleView();
        Controller controller = new Controller(list, view);
        controller.start();
        TaskIO.save2File(list, DATA_JSON_PATH);
        logger.info("Program finished by user.");
    }
}
