import java.io.*;
import java.util.ArrayList;

public class Storage {

    private static final String FILE_PATH = "./data/Friday.txt";

    public void loadTasks(ArrayList<Task> list) {
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

    public void saveTasks(ArrayList<Task> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Task task : list) {
                writer.println(task.toString());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while saving to Friday.txt: " + e.getMessage());
        }
    }

}
