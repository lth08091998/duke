import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * TaskCollection stores the implementation of data structure underlying Tasks.
 * Also implements Observer Pattern on state change of tasks
 */
public class TaskCollection {
    private ArrayList<Task> tasks;
    private ArrayList<PropertyChangeListener> listeners;

    public TaskCollection() {
        this.loadTasks();
        this.listeners = new ArrayList<PropertyChangeListener>();
    }

    private void loadTasks() {
        try {
            File tmpDir = new File("../../tasks.tmp");
            if (tmpDir.exists()) {
                FileInputStream fis = new FileInputStream("../../../tasks.tmp");
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.tasks = (ArrayList<Task>) ois.readObject();
                ois.close();
            } else {
                this.tasks = new ArrayList<Task>();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Integer size() {
        return this.tasks.size();
    }

    public Task get(Integer taskIndex) {
        return this.tasks.get(taskIndex);
    }

    /**
     * Add new task to collections.
     * @param task object
     */
    public void add(Task task) {
        ArrayList<Task> oldTasks = this.tasks;
        this.tasks.add(task);
        notifyListeners(this, "add", oldTasks, this.tasks);
    }

    /**
     * remove task from collection at task index.
     * @param taskIndex index of removed task
     */
    public void remove(Integer taskIndex) {
        ArrayList<Task> oldTasks = this.tasks;
        this.tasks.remove(taskIndex.intValue());
        notifyListeners(this, "remove", oldTasks, this.tasks);
    }

    private void notifyListeners(Object object, String property, ArrayList<Task> oldTasks, ArrayList<Task> newTasks) {
        for (PropertyChangeListener name : listeners) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldTasks, newTasks));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listeners.add(newListener);
    }
}