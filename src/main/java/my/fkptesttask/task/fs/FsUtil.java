package my.fkptesttask.task.fs;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.model.FileEntryKey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class FsUtil {
    public static Map<FileEntryKey, List<FileEntry>> searchDuplicates(Path targetFolder) throws IOException {
        CustomFileVisitor visitor = new CustomFileVisitor();
        Files.walkFileTree(targetFolder, visitor);
        return visitor.getDuplicates();
    }
}
