package my.fkptesttask.task.db;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.model.FileEntryKey;
import my.fkptesttask.service.FileEntryService;
import my.fkptesttask.service.TaskExecutionService;
import my.fkptesttask.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static my.fkptesttask.task.db.DbUtil.saveTaskExecution;
import static my.fkptesttask.task.db.DbUtil.updateDuplicates;

@SpringBootTest
class DbUtilTest {

    @MockBean
    private Task task;

    @Autowired
    TaskExecutionService taskExecutionService;

    @Autowired
    FileEntryService fileEntryService;

    @Test
    public void whenSaveTaskExecutionThenDbUpdated() {
        int duplicateCount = 10;
        saveTaskExecution(duplicateCount, taskExecutionService);
        assertEquals(duplicateCount, taskExecutionService.findAll().iterator().next().getDuplicateCount());
    }

    @Test
    public void whenSaveTwoFileEntriesThenDbContainsTwoFileEntries() {
        FileEntryKey key = FileEntryKey.of(1000L, "hash");
        FileEntry file1 = FileEntry.of("file1.txt", "c://temp", 1000L, "hash");
        FileEntry file2 = FileEntry.of("file2.txt", "c://temp//dir", 1000L, "hash");
        Map<FileEntryKey, List<FileEntry>> duplicates = Map.of(key, List.of(file1, file2));
        updateDuplicates(duplicates, fileEntryService);
        List<FileEntry> result = StreamSupport.stream(fileEntryService.findAll().spliterator(), false).toList();
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(file1)),
                () -> assertTrue(result.contains(file2))
        );
    }

    @Test
    public void whenSaveEmptyFileEntriesMapThenDbCleared() {
        FileEntryKey key = FileEntryKey.of(1000L, "hash");
        FileEntry file1 = FileEntry.of("file1.txt", "c://temp", 1000L, "hash");
        FileEntry file2 = FileEntry.of("file2.txt", "c://temp//dir", 1000L, "hash");
        Map<FileEntryKey, List<FileEntry>> duplicates = Map.of(key, List.of(file1, file2));
        updateDuplicates(duplicates, fileEntryService);
        duplicates = new HashMap<>();
        updateDuplicates(duplicates, fileEntryService);
        assertFalse(fileEntryService.findAll().iterator().hasNext());
    }

}