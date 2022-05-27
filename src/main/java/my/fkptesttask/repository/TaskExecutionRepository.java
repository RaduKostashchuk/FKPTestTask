package my.fkptesttask.repository;

import my.fkptesttask.model.TaskExecution;
import org.springframework.data.repository.CrudRepository;

public interface TaskExecutionRepository extends CrudRepository<TaskExecution, Integer> {
}
