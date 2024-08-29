package storage;

import common.TaskList;
import tasks.Task;
import java.io.*;

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
                System.out.println("Something went wrong trying to create the non-existing Friday.txt file: " + e.getMessage());
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = Task.fromString(line);
                    list.add(task);
                }
            } catch (IOException e) {
                System.out.println("Something went wrong trying to load tasks from an existing Friday.txt file: " + e.getMessage());
            }
        }
    }

    public void saveTasks(TaskList list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Task task : list.getList()) {
                writer.println(task.toString());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while saving to Friday.txt: " + e.getMessage());
        }
    }

}
