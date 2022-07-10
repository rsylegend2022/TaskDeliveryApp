package com.neo.web;

import com.neo.mapper.TaskMapper;
import com.neo.mapper.UserMapper;
import com.neo.model.QueryInCriterion;
import com.neo.model.SearchCriteria;
import com.neo.model.Task;
import com.neo.model.User;
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
import java.util.stream.Collectors;

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
    public List<Task> getTasks(@RequestParam Optional<String> status, @RequestParam Optional<String> owner, @RequestBody Optional<String> creator) {
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

    // criteria : area -> list of requirements
    public List<Task> selectTasks(Map<String, List<String>> criteria) {
        SearchCriteria clause = new SearchCriteria();
        for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
            clause.setInCriteria(List.of(new QueryInCriterion<>(entry.getKey(),entry.getValue())));
        }
        return taskMapper.selectTasksInCriteria(clause);
    }
}
