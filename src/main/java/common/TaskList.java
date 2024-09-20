package common;

import java.util.ArrayList;
import java.util.stream.Collectors;

import tasks.Task;

/**
 * Represents a list of tasks in the chatbot. The TaskList allows tasks to be added, removed, and searched.
 * It provides various operations to manipulate and query tasks within the list.
 */
public class TaskList {

    private ArrayList<Task> list = new ArrayList<>();

    /**
     * Adds a task to the TaskList.
     *
     * @param task The Task object to be added.
     */
    public void add(Task task) {
        this.list.add(task);
    }

    /**
     * Removes a task from the TaskList at the specified index.
     *
     * @param idx The index of the task to be removed.
     * @return The Task object that was removed.
     */
    public Task remove(int idx) {
        return this.list.remove(idx);
    }

    /**
     * Returns the list of tasks.
     *
     * @return The ArrayList of Task objects.
     */
    public ArrayList<Task> getList() {
        return this.list;
    }

    /**
     * Sets the TaskList to a new list of tasks.
     *
     * @param list The new ArrayList of Task objects to set.
     */
    public void setList(ArrayList<Task> list) {
        this.list = list;
    }

    /**
     * Checks if the TaskList is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    /**
     * Returns the number of tasks in the TaskList.
     *
     * @return The size of the TaskList.
     */
    public int size() {
        return this.list.size();
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param i The index of the task to retrieve.
     * @return The Task object at the specified index.
     * @throws AssertionError If the index is less than 0.
     */
    public Task get(int i) {
        assert i >= 0 : "index should be greater than 0";
        return this.list.get(i);
    }

    /**
     * Finds tasks in the TaskList that contain the specified keyword in their description.
     *
     * @param keyword The keyword to search for in the task descriptions.
     * @return A TaskList containing tasks that match the keyword.
     */
    public TaskList findTasks(String keyword) {
        TaskList filteredList = new TaskList();
        ArrayList<Task> filtered = new ArrayList<>(this.list.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new)));
        filteredList.setList(filtered);
        return filteredList;
    }
}
