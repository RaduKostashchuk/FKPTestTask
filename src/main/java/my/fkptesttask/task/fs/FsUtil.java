package my.fkptesttask.task.fs;

import my.fkptesttask.model.FileEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class FsUtil {
    private final ApplicationContext context;

    public FsUtil(ApplicationContext context) {
        this.context = context;
    }

    public List<FileEntry> findFiles(Path targetFolder) {
        List<Path> paths;
        List<FileEntry> fileEntries = new ArrayList<>();

        try (Stream<Path> streamOfPaths = Files.walk(targetFolder, FileVisitOption.FOLLOW_LINKS)) {
            paths = streamOfPaths.toList();
            fileEntries = convertToFileEntries(paths);
        } catch (IOException e) {
            e.printStackTrace();
            SpringApplication.exit(context, () -> 10);
        }

        return fileEntries;
    }

    private static List<FileEntry> convertToFileEntries(List<Path> paths) throws IOException {
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
