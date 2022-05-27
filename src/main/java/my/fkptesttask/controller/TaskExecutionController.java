package my.fkptesttask.controller;

import my.fkptesttask.model.TaskExecution;
import my.fkptesttask.service.TaskExecutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taskexecution")
public class TaskExecutionController {
    private final TaskExecutionService service;

    public TaskExecutionController(TaskExecutionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<TaskExecution>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
