package my.fkptesttask.task.fs;

import my.fkptesttask.model.FileEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static my.fkptesttask.task.fs.FsUtil.findFiles;

import static org.junit.jupiter.api.Assertions.*;

class FsUtilTest {
    @TempDir
    Path tempDir;

    @Test
    public void whenTwoFilesExistThenResultContainsTwoEntries() throws IOException {
        Path file1 =  tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        Files.writeString(file1, "123", StandardOpenOption.CREATE);
        Files.writeString(file2, "123", StandardOpenOption.CREATE);
        List<FileEntry> result = findFiles(tempDir);
        assertEquals(2, result.size());
    }

    @Test
    public void whenNoneFilesExistThenResultContainsNoEntry() throws IOException {
        List<FileEntry> result = findFiles(tempDir);
        assertEquals(0, result.size());
    }

    @Test
    public void whenTargetDirDoesNotExistsThenThrowException() {
        assertThrows(NoSuchFileException.class, () -> findFiles(Path.of("wrong path")));
    }
}