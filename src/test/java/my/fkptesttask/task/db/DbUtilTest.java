package my.fkptesttask.task.db;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.service.FileEntryService;
import my.fkptesttask.service.TaskExecutionService;
import my.fkptesttask.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static my.fkptesttask.task.db.DbUtil.updateDb;
import static org.junit.jupiter.api.Assertions.*;
import static my.fkptesttask.task.db.DbUtil.saveTaskExecution;

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
        assertTrue(taskExecutionService.findAll().iterator().hasNext());
    }

    @Test
    public void whenSaveTwoEqualFileEntriesThenDbContainsTwoFileEntries() {
        FileEntry file1 = FileEntry.of("file1.txt", "c://temp", 1000L, "hash");
        FileEntry file2 = FileEntry.of("file2.txt", "c://temp//dir", 1000L, "hash");
        List<FileEntry> files = List.of(file1, file2);
        updateDb(files, taskExecutionService, fileEntryService);
        List<FileEntry> result = StreamSupport.stream(fileEntryService.findAll().spliterator(), false).toList();
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(file1)),
                () -> assertTrue(result.contains(file2))
        );
    }

    @Test
    public void whenSaveEmptyFileEntriesMapThenDbCleared() {
        FileEntry file1 = FileEntry.of("file1.txt", "c://temp", 1000L, "hash");
        FileEntry file2 = FileEntry.of("file2.txt", "c://temp//dir", 1000L, "hash");
        List<FileEntry> files = List.of(file1, file2);
        updateDb(files, taskExecutionService, fileEntryService);
        files = new ArrayList<>();
        updateDb(files, taskExecutionService, fileEntryService);
        assertFalse(fileEntryService.findAll().iterator().hasNext());
    }

}