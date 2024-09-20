package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import common.TaskList;
import tasks.Task;

/**
 * The Storage class handles loading and saving tasks to and from a specified .txt file.
 * Tasks are stored persistently by writing them to a file, and tasks are loaded into a TaskList
 * when the chatbot is initialized.
 */
public class Storage {

    private final String filePath;

    public Storage(String filepath) {
        this.filePath = filepath;
    }

    /**
     * Loads tasks from the specified .txt file into the TaskList.
     * If the file or its parent directory does not exist, they are created.
     * Each line in the file represents a task and is added to the TaskList. If the file is missing or there are
     * issues reading it, the necessary file or directory is created.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     */
    public void loadTasks(TaskList list) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Something went wrong trying to create the non-existing ui.Friday.txt file: "
                        + e.getMessage());
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = Task.fromString(line);
                    list.add(task);
                }
            } catch (IOException e) {
                System.out.println("Something went wrong trying to load tasks from an existing ui.Friday.txt file: "
                        + e.getMessage());
            }
        }
    }

    /**
     * Saves the tasks from the TaskList into the specified .txt file.
     * Each task is written to a new line in the file. If the file already exists, it is overwritten.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     */
    public void saveTasks(TaskList list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Task task : list.getList()) {
                writer.println(task.toString());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while saving to ui.Friday.txt: " + e.getMessage());
        }
    }

}
