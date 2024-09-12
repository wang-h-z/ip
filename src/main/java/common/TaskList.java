package common;

import java.sql.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

import tasks.Task;


public class TaskList {
    private ArrayList<Task> list = new ArrayList<>();

    public void add(Task task) {
        this.list.add(task);
    }

    public Task remove(int idx) {
        return this.list.remove(idx);
    }

    public ArrayList<Task> getList() {
        return this.list;
    }

    public void setList(ArrayList<Task> list) {
        this.list = list;
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public int size() {
        return this.list.size();
    }

    public Task get(int i) {
        return this.list.get(i);
    }

    public TaskList findTasks(String keyword) {
        TaskList filteredList = new TaskList();
        ArrayList<Task> filtered = new ArrayList<>(this.list.stream()
                                       .filter(task -> task.getDescription()
                                       .contains(keyword)).collect(Collectors.toList()));
        filteredList.setList(filtered);
        return filteredList;
    }
}
