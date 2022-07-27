package com.neo.mapper;

import com.neo.model.SearchCriteria;
import com.neo.model.Task;
import com.neo.model.User;

import java.util.List;

public interface TaskMapper {

    List<Task> selectTasksInCriteria(SearchCriteria searchCriteria);

    void insert(Task task);

    void acceptTask(String taskName);

    void finishTask(String taskName);
}
