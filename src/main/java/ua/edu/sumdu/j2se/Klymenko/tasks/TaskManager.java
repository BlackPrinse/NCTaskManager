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
    public TaskManager() {
        try {
            this.list = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        } catch (ClassNotFoundException ex) {
            logger.error("Error with create task manager (creating list with factory)");
        }
    }

    /**
     * Runs task manager. Load task list from storage-file, start cobtroller and initialize view
     * When the application is correctly finished saves data to storage (json file)
     */
    public void startManager(){
        logger.info("Program started by user.");
        TaskIO.readFromFile(list, DATA_JSON_PATH);
        ConsoleView view = new ConsoleView();
        try {
            Controller controller = new Controller(list, view);
            controller.start();
        } catch (ClassNotFoundException ex) {
            logger.error("Error with controller creating");
        }

        TaskIO.save2File(list, DATA_JSON_PATH);
        logger.info("Program finished by user.");
    }
}
