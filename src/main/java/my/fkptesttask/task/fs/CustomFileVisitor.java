package my.fkptesttask.task.fs;

import my.fkptesttask.model.FileEntryKey;
import my.fkptesttask.model.FileEntry;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class CustomFileVisitor extends SimpleFileVisitor<Path> {
    private final Set<FileEntry> files = new HashSet<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String md5Hash = DigestUtils.md5DigestAsHex(Files.readAllBytes(file));
        long size = Files.size(file);
        String fileName = file.getFileName().toString();
        String filePath = file.getParent().toString();
        FileEntry fileEntry = FileEntry.of(fileName, filePath, size, md5Hash);

        files.add(fileEntry);

        return FileVisitResult.CONTINUE;
    }

    public Map<FileEntryKey, List<FileEntry>> getDuplicates() {
        Map<FileEntryKey, List<FileEntry>> result = new HashMap<>();

        for (FileEntry fileEntry : files) {
            FileEntryKey key = FileEntryKey.of(fileEntry.getSize(), fileEntry.getHash());
            result.computeIfPresent(key, (k, v) -> {
                v.add(fileEntry);
                return v;
            });
            result.computeIfAbsent(key, k -> {
                List<FileEntry> list = new ArrayList<>();
                list.add(fileEntry);
                return list;
            });
        }

        result.entrySet().removeIf(v -> v.getValue().size() < 2);

        return result;
    }
}
