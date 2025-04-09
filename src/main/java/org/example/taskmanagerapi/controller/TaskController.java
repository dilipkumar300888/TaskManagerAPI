package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.model.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private int idCounter = 0; //Server generates ids instead of the client
    private Map<Integer, Task> tasks = new HashMap<>();

    // Error messages
    private static final String TASK_NOT_FOUND = "Task not found with ID: ";

    // GET /api/v1/tasks
    // GET /api/v1/tasks?completed=true
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) Boolean completed) {

        List<Task> result = tasks.values().stream()
                .filter(task -> completed == null || task.isCompleted() == completed)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // GET /api/v1/tasks/id
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id) {

        Task possibleTask = tasks.get(id);

        if (possibleTask == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(TASK_NOT_FOUND + id);
        } else {
            return ResponseEntity.ok(possibleTask);
        }
    }

    // POST /api/v1/tasks
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {

        System.out.println("Received task: " + task.getTitle()); // Debug what's actually received

        //TO-DO : Make it thread safe, maybe atomics?
        idCounter++;
        task.setId(idCounter);
        tasks.put(idCounter, task);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(idCounter)
                .toUri();

        return ResponseEntity.created(location).body(task);
    }

    // PUT /api/v1/tasks/id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Task task) {

        if (task.getId() != 0 && task.getId() != id) {
            return ResponseEntity.badRequest()
                    .body("Path ID and task ID must match");
        }

        if (!tasks.containsKey(id)) {
            return ResponseEntity.notFound().build();
            // There will not be case where task id was not set, as the server is responsible to set it.
        }

        tasks.put(id, task);

        return ResponseEntity.ok(task);
    }

    //TO-DO add a patch mapping to partially update a task

    // DELETE /api/v1/tasks/id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {

        Task removedTask = tasks.remove(id);

        if (removedTask != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}