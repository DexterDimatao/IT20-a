package groupinngs; 
import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class TodoList {
    private String[] tasks;
    private boolean[] completed;
    private int taskCount;
    private Stack<TaskState> undoStack;

   
    private static class TaskState {
        String[] tasks;
        boolean[] completed;
        int taskCount;

        TaskState(String[] tasks, boolean[] completed, int taskCount) {
            this.tasks = tasks.clone();
            this.completed = completed.clone();
            this.taskCount = taskCount;
        }
    }

    public TodoList() {
        tasks = new String[10];
        completed = new boolean[10];
        taskCount = 0;
        undoStack = new Stack<>();
        manageTasks();
    }

    private void manageTasks() {
        while (true) {
            String[] options = {"Add Task", "Remove Task", "Mark as Done", "View Tasks", "Undo", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Choose an action:", "To-Do List",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    saveState();
                    if (taskCount < tasks.length) {
                        JPanel panel = new JPanel();
                        panel.setLayout(new GridBagLayout());

                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);

                        JLabel label = new JLabel("Enter a new task:");
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        panel.add(label, gbc);

                        JTextField newTaskField = new JTextField(20);
                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        panel.add(newTaskField, gbc);

                        int result = JOptionPane.showConfirmDialog(null, panel, "Add Task", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            String newTask = newTaskField.getText().trim();
                            if (!newTask.isEmpty()) {
                                tasks[taskCount] = newTask;
                                completed[taskCount] = false;
                                taskCount++;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Task list is full!");
                    }
                    break;

                case 1: 
                    saveState();
                    String removeTaskIndex = JOptionPane.showInputDialog("Enter the task number to remove (1 to " + taskCount + "):");
                    if (removeTaskIndex != null) {
                        int index = Integer.parseInt(removeTaskIndex) - 1;
                        if (index >= 0 && index < taskCount) {
                            for (int i = index; i < taskCount - 1; i++) {
                                tasks[i] = tasks[i + 1];
                                completed[i] = completed[i + 1];
                            }
                            tasks[--taskCount] = null; 
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid task number.");
                        }
                    }
                    break;

                case 2: 
                    saveState();
                    String doneTaskIndex = JOptionPane.showInputDialog("Enter the task number to mark as done (1 to " + taskCount + "):");
                    if (doneTaskIndex != null) {
                        int index = Integer.parseInt(doneTaskIndex) - 1;
                        if (index >= 0 && index < taskCount) {
                            completed[index] = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid task number.");
                        }
                    }
                    break;

                case 3: 
                    StringBuilder taskList = new StringBuilder("Tasks:\n");
                    for (int i = 0; i < taskCount; i++) {
                        taskList.append((i + 1) + ". " + tasks[i] + (completed[i] ? " (Done)" : "") + "\n");
                    }
                    JOptionPane.showMessageDialog(null, taskList.toString());
                    break;

                case 4: 
                    if (!undoStack.isEmpty()) {
                        TaskState previousState = undoStack.pop();
                        tasks = previousState.tasks;
                        completed = previousState.completed;
                        taskCount = previousState.taskCount;
                        JOptionPane.showMessageDialog(null, "Last action undone.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No actions to undo.");
                    }
                    break;

                case 5: 
                    return;

                default:
                    break;
            }
        }
    }

    private void saveState() {
        
        undoStack.push(new TaskState(tasks, completed, taskCount));
    }

    public static void main(String[] args) {
        new TodoList();
    }
}
