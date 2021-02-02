package ua.edu.sumdu.j2se.Klymenko.tasks.model;

import java.util.*;
import java.util.stream.Stream;

/**
 * The class that consists of properties: array <b>tasksList<b/>, <b>size<b/>, <b>capacity<b/>,
 * inherits from AbstractTaskList and implements those abstract operations that
 * depend on the storage method using a list (array).
 */
public class ArrayTaskList extends AbstractTaskList {
    private Task[] tasksList;
    private int size;
    private int capacity;

    /**
     * Constructs an empty list with the specified initial capacity.
     */
    public ArrayTaskList() {
        this.size = 0;
        this.capacity = 16;
        this.tasksList = new Task[this.capacity];
    }

    /**
     * Constructor array list of a certain capacity
     *
     * @param capacity max count element of list
     * @throws IllegalArgumentException
     */
    public ArrayTaskList(int capacity) {
        this.size = 0;
        if (capacity > 0) {
            this.capacity = capacity;
            this.tasksList = new Task[this.capacity];
        } else throw new IllegalArgumentException("Task list capacity can not be " +
                "less or equal 0");
    }

    /**
     * Method for increasing the capacity by 1.5 times
     */
    private void increaseCapacity() {
        int newCapacity = (int) (this.capacity * 1.5);
        Task[] newList = new Task[newCapacity + 1];

        int len = size >= 0 ? size : 0;
        System.arraycopy(this.tasksList, 0, newList, 0, len);
    }



    /**
     * {@inheritDoc}
     *
     * @param task element to be appended to this list
     * @throws IllegalArgumentException
     */
    public void add(Task task) throws IllegalArgumentException{
        if (capacity * .9 < size) {
            increaseCapacity();
        }
        if (task == null) {
            throw new IllegalArgumentException("Illegal incoming argument. Task must not be null.");
        } else {
            tasksList[size++] = task;
        }
    }


    /**
     * {@inheritDoc}
     *
     * @param task element to be removed from this list, if it exists and not null
     * @return boolean result of removing task -
     * true if such a task existed and was successfully removed from list
     * @throws IllegalArgumentException
     */
    public boolean remove(Task task) throws IllegalArgumentException{
        if (task == null) {
            throw new IllegalArgumentException("Illegal incoming argument. Task must not be null.");
        }
        if (this.size > 0) {
            for (int index = 0; index < this.size; index++) {
                if (tasksList[index].equals(task)) {
                    int len = this.size - 1 - index >= 0 ? this.size - 1 - index : 1;
                    System.arraycopy(this.tasksList, index + 1,
                            this.tasksList, index, len);
                    this.size--;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     *
     * @param index index of the task to return.
     * @return the task by its index in the list
     * @throws IndexOutOfBoundsException
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if ((index < 0) || (this.size() <= index)) {
            throw new IndexOutOfBoundsException("Index is out of arr");
        }
        return this.tasksList[index];
    }

    /**
     * Gets an iterator over elements of type Task
     *
     * @return an Iterator
     */
    @Override
    public Iterator iterator() {
        Iterator <Task> iterator = new Iterator<Task>() {
            private int index = 0;
            private int lastReturned = -1;
            @Override
            public boolean hasNext() {
                if (index < size && tasksList[index] != null) {
                    return true;
                }
                return false;
            }

            @Override
            public Task next() throws NoSuchElementException {
                lastReturned = index;
                try {
                    return getTask(index++);
                } catch (NoSuchElementException ex){
                    throw new NoSuchElementException("There are not next element");
                }
            }

            @Override
            public void remove() throws IllegalStateException{
                if (index > 0) {
                    ArrayTaskList.this.remove(tasksList[lastReturned]);
                    index--;
                } else  {
                    throw new IllegalStateException("Can not doing remove operation");
                }
            }
        };
        return iterator;
    }


    /**
     * Gets the hash code value for this list
     *
     * @return the hash code value for this list
     */
    @Override
    public int hashCode() {
        return (int)(Math.sqrt((Objects.hash(size, capacity) + Calendar.getInstance().getTimeInMillis()/1000) * Math.PI));
    }

    /**
     * Gets a copy of this instance
     *
     * @return a new list that is a clone of this instance
     * @throws CloneNotSupportedException
     */
    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList newList =  (ArrayTaskList) super.clone();
        newList.tasksList = new Task[capacity];
        System.arraycopy(tasksList, 0, newList.tasksList, 0, size);
        return newList;
    }

    /**
     * Getting the result of comparing two instances of type ArrayTaskList
     *
     * @param obj the object to be compared for equality with this task list
     * @return true if the lists are identical
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        ArrayTaskList tasks = (ArrayTaskList) obj;

        if (this.size == tasks.size && this.capacity == tasks.capacity && Arrays.equals(this.tasksList, tasks.tasksList)) {
            return  true;
        }
        return  false;
    }

    /**
     * Gets a string representation of the array task list.
     *
     * @return a string.
     */
    @Override
    public String toString() {
        return "TaskList{" +
                "size='" + size + '\n' +
                ",capacity=" + capacity + '\n' +
                "array=" + Arrays.toString(tasksList) +
                '}';
    }

    /**
     * {@inheritDoc}
     *
     * @return stream of tasks
     */
    public Stream<Task> getStream() {
        Task [] tasks = new Task[this.size];

        for (int i = 0; i < size; i++) {
            tasks[i] = getTask(i);
        }
        return Arrays.stream(tasks);
    }
}

