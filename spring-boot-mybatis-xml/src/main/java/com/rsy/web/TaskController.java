package com.rsy.web;

import com.rsy.mapper.TaskMapper;
import com.rsy.model.QueryInCriterion;
import com.rsy.model.SearchCriteria;
import com.rsy.model.Task;
import com.rsy.model.TaskPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(value="/newTask", method= RequestMethod.POST)
    public int newTask(@RequestBody Task task) {
        taskMapper.insert(task);
        return 200;
    }

    @RequestMapping(value="/getTasks", method= RequestMethod.GET)
    public List<Task> getTasks(@RequestParam Optional<String> status, @RequestParam Optional<String> owner, @RequestParam Optional<String> creator) {
        Map<String, List<String>> criteria = new HashMap<>();
        if (status.isPresent()) {
            criteria.put("status", List.of(status.get()));
        }
        if (owner.isPresent()) {
            criteria.put("owner", List.of(owner.get()));
        }
        if (creator.isPresent()) {
            criteria.put("creator", List.of(creator.get()));
        }
        return selectTasks(criteria);
    }

    @RequestMapping(value="/acceptTask", method= RequestMethod.PATCH)
    public Integer acceptTasks(@RequestBody TaskPatch taskPatch) {
        try {
            taskMapper.acceptTask(taskPatch);
            return 200;
        } catch (Exception e) {
            System.out.println(e);
            return 500;
        }
    }

    @RequestMapping(value="/finishTask", method= RequestMethod.PATCH)
    public Integer finishTasks(@RequestBody TaskPatch taskPatch) {
        try {
            taskMapper.finishTask(taskPatch);
            return 200;
        } catch (Exception e) {
            System.out.println(e);
            return 500;
        }
    }

    // criteria : area -> list of requirements
    public List<Task> selectTasks(Map<String, List<String>> criteria) {
        SearchCriteria clause = new SearchCriteria();
        List<QueryInCriterion<?>> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
            list.add(new QueryInCriterion<>(entry.getKey(),entry.getValue()));
        }
        clause.setInCriteria(list);
        return taskMapper.selectTasksInCriteria(clause);
    }
}
