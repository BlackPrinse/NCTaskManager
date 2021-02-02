package ua.edu.sumdu.j2se.Klymenko.tasks.model;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;

import org.apache.log4j.Logger;

/**
 * Task class that consists of properties: <b>logger<b/>, <b>time<b/>,
 * <b>start<b/>, <b>end<b/>, <b>interval<b/>, <b>title<b/>, <b>active<b/>,
 * <b>repeated<b/>.
 * It describes in detail the tasks - name, active or inactive,
 * repeatable or not, execution time (start and end if repeatiable)
 */
public class Task implements Cloneable{

    private static final Logger logger = Logger.getLogger(Task.class);
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private String title;
    private boolean active;
    private boolean repeated;

    /**
     * Default constructor
     */
    public Task() {
    }

    /**
     * Constructor to make an inactive task that runs at a specific time without repetitions and has a name (title)
     *
     * @param title is a field that contains task's title
     * @param time is a field containing the task execution time
     * @see #Task(String, LocalDateTime)
     * @see #Task(String, LocalDateTime, LocalDateTime, int)
     */
    public Task(String title, LocalDateTime time) throws IllegalArgumentException{
        if (title == null) {
            logger.error("Title is null");
            throw new IllegalArgumentException("Title of task can't be null or empty");
        }
        if (time == null) {
            logger.error("Time is null");
            throw new IllegalArgumentException("Start point of task can't be less than 0");
        }
        this.time = time;
        this.title = title;
        this.repeated = false;
        this.active = false;
    }

    /**
     * Constructor to make an inactive task that runs at a specific time interval
     * (start -> end) at a certain interval and has a name (title)
     *
     * @param title contains task's title
     * @param start contains time to start running task
     * @param end contains time to end running task
     * @param interval contains time period between repeating task
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException{
        if (title == null) {
            logger.error("Title is null");
            throw new IllegalArgumentException("Title of task can't be null or empty");
        }
        if (start == null) {
            logger.error("Illegal start agrument");
            throw new IllegalArgumentException("Start point of task can't be less than 0");
        }
        if (end == null) {
            logger.error("Illegal end agrument");
            throw new IllegalArgumentException("End point of task can't be less than 0");
        }
        if (interval < 0) {
            logger.error("Illegal interval agrument");
            throw new IllegalArgumentException("Interval of task can't be less than 0");
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.title = title;
        this.repeated = true;
        this.active = false;
    }

    /**
     *Gets the value of the title property
     *@return property value title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property
     * @param title contains task's title
     */
    public void setTitle(String title) throws IllegalArgumentException{
        if (title == null || title.isEmpty()) {
            logger.error("Illegal title argument");
            throw new IllegalArgumentException("Title of task can't be null or empty");
        }
        this.title = title;
    }

    /**
     *Сheck the task and gets task's satatus
     *@return property value active, return true if task is active, false - inactive
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Sets task's status
     * @param active contains task's status (active - true, inactive - false)
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets task's time.
     *
     * @return task time or start task time for repeating task.
     */
    public LocalDateTime getTime() {
        if (!this.repeated) {
            return time;
        } else {
            return start;
        }
    }

    /**
     * Sets task time to start non-repeating task.
     * If task was repeating made it non-repeating.
     *
     * @param time is a specified time to start non-repeating task.
     */
    public void setTime(LocalDateTime time) throws IllegalArgumentException {
        if (time == null) {
            logger.error("Illegal time argument");
            throw new IllegalArgumentException("Time of task can't be less than 0");
        }
        this.time = time;
        if (this.repeated)
            this.repeated = false;
    }

    /**
     * Gets start time for repeating task.
     *
     * @return start time.
     */
    public LocalDateTime getStartTime(){
        if (repeated)
            return start;
        else return time;
    }

    /**
     * Gets end time for repeating task
     * If task non-repeating returns time
     *
     * @return end time.
     */
    public LocalDateTime getEndTime(){
        if (repeated)
            return end;
        else return time;
    }

    /**
     * Gets time interval of task repetition
     * If task non-repeating than returns 0
     *
     * @return time interval
     */
    public int getRepeatInterval(){
        if (repeated)
            return interval;
        else return 0;
    }

    /**
     * Sets time to repeating task
     *
     * @param start set a new time to start running task
     * @param end set a new time to end running task
     * @param interval set new time period between repeating task
     */
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        if (start == null) {
            logger.error("Illegal start agrument");
            throw new IllegalArgumentException("Start point of task can't be less than 0");
        }
        if (end == null) {
            logger.error("Illegal end agrument");
            throw new IllegalArgumentException("End point of task can't be less than 0");
        }
        if (interval < 0) {
            logger.error("Illegal interval argument");
            throw new IllegalArgumentException("Interval of task can't be less than 0");
        }

        this.start = start;
        this.end = end;
        this.interval = interval;

        if(!this.repeated)
            this.repeated = true;
    }

    /**
     * Gets boolean value that shows task is repeated or not.
     *
     * @return true if task is repeated.
     */
    public boolean isRepeated(){
        return repeated;
    }

//    public int nextTimeAfter(int current){
//        if (!active)
//            return -1;
//        else{
//            if (!repeated)
//                if(current < time)
//                    return time;
//                else return -1;
//        }
//        if (current < start)
//            return start;
//        if (current > end)
//            return -1;
//        if ((current - start) % interval == 0){
//            if ((current + interval) <= end)
//                return (current + interval);
//            else return -1;
//        } else{
//            int div = (current - start) / interval;
//            if ((start + interval * (div + 1)) <= end)
//                return (start + interval * (div + 1));
//            else return -1;
//        }
//    }

    /**
     * Gets next executing time of active task after current time
     *
     * @param current is current time
     * @return next time point. If task have no next time returns null
     */
public LocalDateTime nextTimeAfter(LocalDateTime current){
        if (active && !isRepeated()) {
            if (current.isBefore(time)) {
                return time;
            }
            return null;
        }
        if (active && isRepeated()) {
            LocalDateTime nextTime = start;
            if (current.isBefore(start)) {
                return start;
            }
            else if (current.isAfter(end)) {
                return null;
            }
            else {
                while (nextTime.isBefore(end) || nextTime.isEqual(end)) {
                    if (nextTime.isAfter(current)) {
                        return nextTime;
                    }
                    nextTime = nextTime.plusSeconds(interval);
                }
            }
        }
        return null;
}

    /**
     * Overrided toString() method
     *
     * @return string that describe object Task
     */
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }

    /**
     * Overrided hashCode() method
     *
     * @return object's hash
     */
    @Override
    public int hashCode() {
        return (int)(Math.sqrt((Objects.hash(start, end, time, interval, Calendar.getInstance().getTimeInMillis()/3333)
                - 77 + Calendar.getInstance().getTimeInMillis()/1000) * Math.PI));
    }

    /**
     * Compares two instances of Task (this instance of Task to another)
     *
     * @param obj takes an object to comparing
     * @return true if both objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Task task = (Task) obj;

        if (this.title.equals(task.title) && this.time == task.time && this.start == task.start && this.end == task.end
                && this.interval == task.interval && this.active == task.active) {
            return  true;
        }
        return  false;
    }

    /**
     * Overrided clone() method that returns cloned task
     *
     * @return cloned task
     * @throws CloneNotSupportedException
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        Task newTask = (Task) super.clone();
        newTask.title = new String(this.title);

        return  newTask;
    }
}
