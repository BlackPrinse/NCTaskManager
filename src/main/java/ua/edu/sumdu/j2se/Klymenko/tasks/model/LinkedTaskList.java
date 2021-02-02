package ua.edu.sumdu.j2se.Klymenko.tasks.model;

import java.util.*;
import java.util.stream.Stream;

/**
 * The class that consists of properties:  <b>size<b/>, <b>firsTask<b/>
 * inherits from AbstractTaskList and implements those abstract operations that
 * depend on the storage method using a linked list.
 */
public class LinkedTaskList extends AbstractTaskList{
    private int size;
    private Node firsTask;

    private class Node {
        Task task;
        Node nextTask;

        public Node(Task task) {
            this.task = task;
            this.nextTask = null;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;
            Node node = (Node) obj;

            return Objects.equals(task, node.task) && Objects.equals(nextTask, node.nextTask);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task, nextTask);
        }
    }



    /**
     * {@inheritDoc}
     *
     * @param task element to be appended to this list
     * @throws IllegalArgumentException
     */
    public void add(Task task) throws IllegalArgumentException{
        if (task == null) {
            throw new IllegalArgumentException("Illegal incoming argument. Task must not be null.");
        }
        if (this.firsTask == null) {
            this.firsTask = new Node(task);
            this.size++;
        } else {
            Node temp = firsTask;

            while (temp.nextTask != null) {
                temp = temp.nextTask;
            }

            temp.nextTask = new Node(task);
            this.size++;
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
        if (firsTask.task.equals(task)) {
            firsTask = firsTask.nextTask;
            size--;
            return true;
        }
        Node temp = firsTask;
        Node prevTemp = temp;

        while (temp != null) {
            if (temp.task.equals(task)) {
                prevTemp.nextTask = temp.nextTask;
                size--;
                return true;
            }
            prevTemp = temp;
            temp = temp.nextTask;
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
    public Task getTask(int index) throws IndexOutOfBoundsException{
        if (firsTask == null || index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException("An array has been accessed with an illegal index");
        }
        Node temp = firsTask;

        for (int i = 0; temp != null; i++) {
            if (i == index) {
                return temp.task;
            }
            temp = temp.nextTask;
        }
        return null;
    }

    /**
     * Gets an iterator over elements of type Task
     *
     * @return an Iterator
     */
    @Override
    public Iterator<Task> iterator() {
        Iterator <Task> iterator = new Iterator<Task>() {
            private int index = 0;
            private int returnedIndex = -1;
            @Override
            public boolean hasNext(){
                if (index < size() && getTask(index) != null) {
                    return true;
                }
                return false;
            }

            @Override
            public Task next() throws NoSuchElementException{
                returnedIndex = index;
                Task returnedElem = getTask(index++);
                if ( returnedElem!= null){
                    return returnedElem;
                } else {
                    throw new NoSuchElementException("There are not next element");
                }
            }

            @Override
            public void remove() throws IllegalStateException{
                if (index > 0) {
                    LinkedTaskList.this.remove(getTask(returnedIndex));
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
        return (int)(Math.sqrt((Objects.hash(size, Calendar.getInstance().getTimeInMillis()/777)
                + Calendar.getInstance().getTimeInMillis()/1000) * Math.PI));
    }

    /**
     * Gets a string representation of the linked task list.
     *
     * @return a string.
     */
    @Override
    public String toString() {
        return "TaskList{" +
                "size='" + size + '\'' +
                '}';
    }

    /**
     * Getting the result of comparing two instances of type ДШтлувЕфылДшые
     *
     * @param obj the object to be compared for equality with this task list
     * @return true if the lists are identical
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        LinkedTaskList list = (LinkedTaskList) obj;

        Iterator<Task> il = list.iterator();
        Iterator<Task> it = this.iterator();

        while (il.hasNext() && it.hasNext()) {
            if (!il.next().equals(it.next())) {
                return false;
            }
        }

        if(this.size != list.size) {
            return false;
        }
        return true;
    }

    /**
     * Gets a copy of this instance
     *
     * @return a new list that is a clone of this instance
     * @throws CloneNotSupportedException
     */
    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList newList = new LinkedTaskList();
        Node node = firsTask;
        while (node != null) {
            newList.add(node.task);
            node = node.nextTask;
        }
        return newList;
    }
    /**
     * Convert list (nodes) to usual array tasks
     *
     * @return arrays tasks of this list
     */
    private Task[] toArray() {
        Task[] result = new Task[size];
        int i = 0;
        for (Node node = firsTask; node != null; node = node.nextTask) {
            result[i++] = node.task;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return stream of tasks
     */
    @Override
    public Stream<Task> getStream() {
        return Stream.of(this.toArray());
    }
}

