package my.fkptesttask.task.fs;

import my.fkptesttask.model.FileEntry;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FsUtil {
    public static List<FileEntry> findFiles(Path targetFolder) throws IOException {
        List<Path> paths;

        try (Stream<Path> streamOfPaths = Files.walk(targetFolder, FileVisitOption.FOLLOW_LINKS)) {
            paths = streamOfPaths.toList();
        }

        return convertPathsToFileEntries(paths);
    }

    private static List<FileEntry> convertPathsToFileEntries(List<Path> paths) throws IOException {
        List<FileEntry> files = new ArrayList<>();
        for (Path path : paths) {
            if (Files.isDirectory(path)) {
                continue;
            }
            files.add(
                    FileEntry.of(
                            path.getFileName().toString(),
                            path.getParent().toString(),
                            Files.size(path),
                            DigestUtils.md5DigestAsHex(Files.readAllBytes(path))
                    )
            );
        }
        return files;
    }
}
