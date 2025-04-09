package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private Map<Integer, Task> tasks = new HashMap<>();

    @GetMapping
    public Collection<Task> getTasks() {
        return tasks.values();
    }

    @PostMapping
    public String addTask(@RequestBody Task task) {
        tasks.put(task.getId(), task);
        return "Task added successfully!";
    }
    @PutMapping("/{id}")
    public String updateTask(@PathVariable int id, @RequestBody Task task) {
        if(tasks.containsKey(id)) {
            tasks.put(id, task);
            return "Task updated successfully!";
        }
        return "Task not found!";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable int id) {
        if(tasks.containsKey(id)) {
            tasks.remove(id);
            return "Task deleted successfully!";
        }
        return "Task not found!";
    }

    @GetMapping("/completed")
    public List<Task> getCompletedTasks() {
        return tasks.values().stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }
}
