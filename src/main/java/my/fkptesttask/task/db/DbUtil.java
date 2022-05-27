package my.fkptesttask.task.db;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.model.FileEntryKey;
import my.fkptesttask.model.TaskExecution;
import my.fkptesttask.service.FileEntryService;
import my.fkptesttask.service.TaskExecutionService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DbUtil {

    public static void saveTaskExecution(int duplicateCount, TaskExecutionService taskExecutionService) {
        TaskExecution taskExecution = new TaskExecution();
        taskExecution.setDuplicateCount(duplicateCount);
        taskExecutionService.save(taskExecution);
    }

    public static void updateDuplicates(Map<FileEntryKey, List<FileEntry>> duplicates,
                                 FileEntryService fileEntryService) {
        Set<FileEntry> filesFromDb = StreamSupport.stream(fileEntryService.findAll().spliterator(), false)
                .collect(Collectors.toSet());
        Set<FileEntry> inputFiles = new HashSet<>();

        duplicates.values().forEach(inputFiles::addAll);

        for (FileEntry dbEntry : filesFromDb) {
            if (!inputFiles.contains(dbEntry)) {
                fileEntryService.deleteById(dbEntry.getId());
            }
        }

        for (FileEntry inputEntry : inputFiles) {
            if (!filesFromDb.contains(inputEntry)) {
                fileEntryService.save(inputEntry);
            }
        }
    }
}
