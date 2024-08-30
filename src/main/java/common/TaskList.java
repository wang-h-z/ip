package common;

import java.util.ArrayList;

import tasks.Task;


public class TaskList {
    private final ArrayList<Task> list = new ArrayList<>();

    public void add(Task task) {
        this.list.add(task);
    }

    public Task remove(int idx) {
        return this.list.remove(idx);
    }

    public ArrayList<Task> getList() {
        return this.list;
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
}
