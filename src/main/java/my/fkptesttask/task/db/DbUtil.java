package my.fkptesttask.task.db;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.model.FileEntryKey;
import my.fkptesttask.model.TaskExecution;
import my.fkptesttask.service.FileEntryService;
import my.fkptesttask.service.TaskExecutionService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DbUtil {
    private final FileEntryService fileEntryService;
    private final TaskExecutionService taskExecutionService;

    public DbUtil(FileEntryService fileEntryService, TaskExecutionService taskExecutionService) {
        this.fileEntryService = fileEntryService;
        this.taskExecutionService = taskExecutionService;
    }

    public void updateDb(List<FileEntry> duplicates, int duplicateCount) {
        saveTaskExecution(duplicateCount);
        saveDuplicates(duplicates);
    }

    public void saveTaskExecution(int duplicateCount) {
        TaskExecution taskExecution = new TaskExecution();
        taskExecution.setDuplicateCount(duplicateCount);
        taskExecutionService.save(taskExecution);
    }

    public void saveDuplicates(List<FileEntry> duplicates) {
        Set<FileEntry> filesFromDb = StreamSupport.stream(fileEntryService.findAll().spliterator(), false)
                .collect(Collectors.toSet());
        Set<FileEntry> filesToSave = new HashSet<>(duplicates);

        for (FileEntry fileFromDb : filesFromDb) {
            if (!filesToSave.contains(fileFromDb)) {
                fileEntryService.deleteById(fileFromDb.getId());
            }
        }

        for (FileEntry fileToSave : filesToSave) {
            if (!filesFromDb.contains(fileToSave)) {
                fileEntryService.save(fileToSave);
            }
        }
    }
}
