import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<String> tasks = new ArrayList<>();

    public void addTask(String task) {
        tasks.add(task);
    }

    public void markTaskAsCompleted(int taskIndex) {
        String updatedTask = tasks.get(taskIndex).concat(" (completed)");
        tasks.remove(taskIndex);
        tasks.add(taskIndex, updatedTask);
    }

    public void removeTask(int taskIndex) {
        String task = tasks.remove(taskIndex);
    }

    public void printTaskList() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(tasks.get(i));
        }
    }

    public List<String> getTasks() {
        return tasks;
    }
}