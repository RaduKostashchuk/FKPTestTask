package my.fkptesttask.task.db;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.model.FileEntryKey;
import my.fkptesttask.model.TaskExecution;
import my.fkptesttask.service.FileEntryService;
import my.fkptesttask.service.TaskExecutionService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DbUtil {

    public static void updateDb(List<FileEntry> files,
                                TaskExecutionService taskExecutionService,
                                FileEntryService fileEntryService) {
        Map<FileEntryKey, List<FileEntry>> duplicates = getDuplicates(files);
        int duplicateCount = duplicates.keySet().size();

        saveTaskExecution(duplicateCount, taskExecutionService);
        saveDuplicates(duplicates, fileEntryService);
    }

    public static void saveTaskExecution(int duplicateCount, TaskExecutionService taskExecutionService) {
        TaskExecution taskExecution = new TaskExecution();
        taskExecution.setDuplicateCount(duplicateCount);
        taskExecutionService.save(taskExecution);
    }

    public static void saveDuplicates(Map<FileEntryKey, List<FileEntry>> duplicates,
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

    private static Map<FileEntryKey, List<FileEntry>> getDuplicates(List<FileEntry> files) {
        Map<FileEntryKey, List<FileEntry>> duplicates = new HashMap<>();

        for (FileEntry fileEntry : files) {
            FileEntryKey key = FileEntryKey.of(fileEntry.getSize(), fileEntry.getHash());
            duplicates.computeIfPresent(key, (k, v) -> {
                v.add(fileEntry);
                return v;
            });
            duplicates.computeIfAbsent(key, k -> {
                List<FileEntry> fileEntries = new ArrayList<>();
                fileEntries.add(fileEntry);
                return fileEntries;
            });
        }

        duplicates.entrySet().removeIf(v -> v.getValue().size() < 2);

        return duplicates;
    }
}
