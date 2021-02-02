package ua.edu.sumdu.j2se.Klymenko.tasks.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;


/**
 * An abstract class that describes the operations that can be performed on a task list
 * and implements type-independent list methods (array or linked)
 */
abstract public class AbstractTaskList implements Iterable<Task>, Cloneable {

    /**
     * Add tasks to the list
     *
     * @param task element to be appended to this list
     * @throws IllegalArgumentException
     */
    abstract public void add(Task task) throws IllegalArgumentException;

    /**
     * Remove tasks from a list
     * Returns true if such a task was listed
     * If there are more than one task in the list, it deletes one of them

     * @param task element to be removed from this list, if present
     * @return reslt deleting (true if such a task was listed)
     * @throws IllegalArgumentException
     */
    abstract public boolean remove(Task task) throws IllegalArgumentException;

    /**
     * Gets number of tasks in the list
     *
     * @return number of elements in this list
     */
    abstract public int size();

    /**
     * Gets a task from list by index,
     * the first task has an index of 0
     *
     * @param index index of the task to return.
     * @return the task at the specified position in this list.
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    abstract public Task getTask(int index) throws IndexOutOfBoundsException;

    /**
     * Gets tasks scheduled to be completed at least once after
     * from and no later than to.
     *
     * @param from interval check start point
     * @param to   interval check утв point
     * @return a subset of tasks.
     */
    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) throws ClassNotFoundException {
        AbstractTaskList incomingList;
        if (this.getClass().getSimpleName().equals("ArrayTaskList")) {
            incomingList = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        } else {
            incomingList = TaskListFactory.createTaskList(ListTypes.types.LINKED);
        }

//        getStream().filter(
//                task -> task != null && task.isActive()
//                        && task.nextTimeAfter(from) != -1 && task.nextTimeAfter(to) == -1
//        ).forEach(incomingList::add);

        getStream().filter(task -> task.nextTimeAfter(from).isAfter(from)
                && task.nextTimeAfter(to).isBefore(to)).forEach(incomingList::add);

        return incomingList;
    }
    /**
     * Method that permit to work with the task list as a stream (foreach)
     *
     * @return stream of tasks.
     */
    public abstract Stream<Task> getStream();
}

