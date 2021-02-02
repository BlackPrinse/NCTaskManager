package ua.edu.sumdu.j2se.Klymenko.tasks.model;

/**
 * A class for creating objects of type ArrayTaskList and LinkedTaskList,
 * depending on the parameter passed to it. (pattern: Abstract factory)
 */
public class TaskListFactory {
    /**
     * Creates objects of type ArrayTaskList and LinkedTaskList depending
     * on the parameter passed to it {@link ListTypes.types} (ARRAY or LINKED)
     *
     * @param types contains ARRAY and LINKED values.
     * @return returns created an ArrayTaskList or LinkedTaskList object.
     * @throws ClassNotFoundException
     */
    public static AbstractTaskList createTaskList(ListTypes.types types) throws ClassNotFoundException {
        switch (types) {
            case ARRAY: return new ArrayTaskList();
            case LINKED:return new LinkedTaskList();
            default: throw new ClassNotFoundException("Enters class type not founded");
        }
    }
}
