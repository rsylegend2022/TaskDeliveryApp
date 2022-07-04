package com.example.android.recyclerview;

import java.util.ArrayList;
import java.util.List;

public class TaskHolderWapper {
    private static TaskHolderWapper instance;

    private List<TaskHolder> taskHolders = new ArrayList<>();

    public void addTask(TaskHolder taskHolder) {
        taskHolders.add(taskHolder);

        System.out.println("@@@@@@@@@@" + taskHolders.toString());
    }

    public TaskHolder[] getTasks(){
        TaskHolder[] array = new TaskHolder[taskHolders.size()];
        return taskHolders.toArray(array);
    }

    public int getSize() {
        return taskHolders.size();
    }

    public static TaskHolderWapper getInstance(){
        if (instance == null) {
            instance = new TaskHolderWapper();
        }
        return instance;
    }


}
