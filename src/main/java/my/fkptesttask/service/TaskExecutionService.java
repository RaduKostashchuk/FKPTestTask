package my.fkptesttask.service;

import my.fkptesttask.model.TaskExecution;
import my.fkptesttask.repository.TaskExecutionRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutionService {
    private final TaskExecutionRepository repository;

    public TaskExecutionService(TaskExecutionRepository repository) {
        this.repository = repository;
    }

    public Iterable<TaskExecution> findAll() {
        return repository.findAll();
    }

    public void save(TaskExecution taskExecution) {
        repository.save(taskExecution);
    }
}
