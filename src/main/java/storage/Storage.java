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

    /**
     * Checks if the .txt file contains task strings exits. If the file does not exist, the relevant parent directory
     * the .txt file itself is created. If the file exists, each line of the file is read using a BufferedReader object.
     * Each line is then added into the TaskList.
     * <p>
     * Throws IOExceptions if creating the .txt file or if loading tasks fail.
     *
     * @param list A TaskList containing all tasks.
     */
    public void loadTasks(TaskList list) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Making new file lol.");
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Something went wrong trying to create the non-existing ui.Friday.txt file: " + e.getMessage());
            }
        } else {
            System.out.println("Loading tasks from: " + FILE_PATH);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = Task.fromString(line);
                    list.add(task);
                }
            } catch (IOException e) {
                System.out.println("Something went wrong trying to load tasks from an existing ui.Friday.txt file: " + e.getMessage());
            }
        }
    }

    /**
     * Writes the Tasks contained in the TaskList to a new .txt file.
     * <p>
     * Throws an IOException is writing to the .txt file fails.
     *
     * @param list A TaskList containing all tasks.
     */
    public void saveTasks(TaskList list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Task task : list.getList()) {
                writer.println(task.toString());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while saving to ui.Friday.txt: " + e.getMessage());
        }
    }

}
