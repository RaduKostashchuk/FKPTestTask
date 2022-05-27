package my.fkptesttask.repository;

import my.fkptesttask.model.FileEntry;
import org.springframework.data.repository.CrudRepository;

public interface FileEntryRepository extends CrudRepository<FileEntry, Integer> {
}
