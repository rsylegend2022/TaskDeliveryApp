package com.rsy.mapper;

import com.rsy.model.SearchCriteria;
import com.rsy.model.Task;
import com.rsy.model.TaskPatch;

import java.util.List;

public interface TaskMapper {

    List<Task> selectTasksInCriteria(SearchCriteria searchCriteria);

    void insert(Task task);

    void acceptTask(TaskPatch taskName);

    void finishTask(TaskPatch taskName);
}
