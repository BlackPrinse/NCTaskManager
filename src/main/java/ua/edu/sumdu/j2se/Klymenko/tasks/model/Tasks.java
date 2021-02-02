package ua.edu.sumdu.j2se.Klymenko.tasks.model;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Сlass for working with collections that store tasks,
 * which allows you to use any collection to store tasks
 */
public class Tasks {

    /**
     * The method that returns tasks that are scheduled to run at least once after "start" and no later than "end".
     *
     * @param tasks collection of tasks.
     * @param start start time of a specified period.
     * @param end end time of a specified period.
     * @return subset of tasks.
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end)
            throws ClassNotFoundException {
        AbstractTaskList res = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        for (Task task : tasks) {
            if (task.nextTimeAfter(start) != null && task.nextTimeAfter(start).compareTo(end) <= 0) {
                res.add(task);
            }
        }
        return (Iterable<Task>) res;
    }

    /**
     * A method that creates a calendar of tasks for a specific period (from start to end),
     * where each date corresponds to a set of tasks to be performed at that time
     *
     * @param tasks task collection.
     * @param start start time of a specified period.
     * @param end   end time of a specified period.
     * @return returns map where each date corresponds to a set of tasks to be completed at that time.
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start,
                                                               LocalDateTime end) throws ClassNotFoundException {
        SortedMap<LocalDateTime, Set<Task>> sortedMap = new TreeMap<>();
        Iterable<Task> taskIterable = incoming(tasks, start, end);

        for (Task task : taskIterable) {
            LocalDateTime nextTime = task.nextTimeAfter(start);

            while (nextTime != null && (nextTime.isBefore(end) || nextTime.isEqual(end))) {
                if (!sortedMap.containsKey(nextTime)) {
                    Set<Task> taskSet = new HashSet<>();
                    taskSet.add(task);
                    sortedMap.put(nextTime, taskSet);
                } else {
                    sortedMap.get(nextTime).add(task);
                }
                nextTime = task.nextTimeAfter(nextTime);
            }
        }
        return sortedMap;
    }
}
