package my.fkptesttask.task.fs;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.model.FileEntryKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import static my.fkptesttask.task.fs.FsUtil.searchDuplicates;

import static org.junit.jupiter.api.Assertions.*;

class FsUtilTest {
    @TempDir
    Path tempDir;

    @Test
    public void whenTwoFilesAreEqualThenMapContainsOneEntry() throws IOException {
        Path file1 =  tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        Files.writeString(file1, "123", StandardOpenOption.CREATE);
        Files.writeString(file2, "123", StandardOpenOption.CREATE);
        Map<FileEntryKey, List<FileEntry>> result = searchDuplicates(tempDir);
        assertEquals(1, result.keySet().size());
    }

    @Test
    public void whenTwoFilesAreNotEqualThenMapContainsNoEntry() throws IOException {
        Path file1 =  tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        Files.writeString(file1, "123", StandardOpenOption.CREATE);
        Files.writeString(file2, "1234", StandardOpenOption.CREATE);
        Map<FileEntryKey, List<FileEntry>> result = searchDuplicates(tempDir);
        assertEquals(0, result.keySet().size());
    }

    @Test
    public void whenTargetDirDoesNotExistsThenThrowException() {
        assertThrows(NoSuchFileException.class, () -> searchDuplicates(Path.of("wrong path")));
    }
}