package my.fkptesttask.task.fs;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.task.Task;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FsUtilTest {
    @TempDir
    Path tempDir;

    @MockBean
    Task task;

    @Autowired
    FsUtil fsUtil;

    @Test
    public void whenTwoFilesExistThenResultContainsTwoEntries() throws IOException {
        Path file1 =  tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        Files.writeString(file1, "123", StandardOpenOption.CREATE);
        Files.writeString(file2, "123", StandardOpenOption.CREATE);
        List<FileEntry> result = fsUtil.findFiles(tempDir);
        assertEquals(2, result.size());
    }

    @Test
    public void whenNoneFilesExistThenResultContainsNoEntry() {
        List<FileEntry> result = fsUtil.findFiles(tempDir);
        assertEquals(0, result.size());
    }

    @Disabled
    public void whenTargetDirDoesNotExistsThenThrowException() {
        assertThrows(NoSuchFileException.class, () -> fsUtil.findFiles(Path.of("wrong path")));
    }
}