package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import common.TaskList;

import tasks.Task;

public class Storage {

    private final String FILE_PATH;

    public Storage(String filepath) {
        this.FILE_PATH = filepath;
    }

    public void loadTasks(TaskList list) {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Something went wrong trying to create the non-existing main.Friday.txt file: " + e.getMessage());
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = Task.fromString(line);
                    list.add(task);
                }
            } catch (IOException e) {
                System.out.println("Something went wrong trying to load tasks from an existing main.Friday.txt file: " + e.getMessage());
            }
        }
    }

    public void saveTasks(TaskList list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Task task : list.getList()) {
                writer.println(task.toString());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while saving to main.Friday.txt: " + e.getMessage());
        }
    }

}
