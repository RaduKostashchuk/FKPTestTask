package my.fkptesttask.task;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.task.db.DbUtil;
import my.fkptesttask.task.fs.FsUtil;
import my.fkptesttask.task.misc.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.*;

@Component
public class Task {
    private final DbUtil dbUtil;
    private final FsUtil fsUtil;

    public Task(DbUtil dbUtil, FsUtil fsUtil) {
        this.dbUtil = dbUtil;
        this.fsUtil = fsUtil;
    }

    private Path targetFolder;

    @Value("${target-folder}")
    public void setTargetFolder(String targetFolder) {
        this.targetFolder = Path.of(targetFolder);
    }


    @Scheduled(fixedDelayString = "#{${run-interval} + \"000\"}")
    public void run() {
        List<FileEntry> foundFiles = fsUtil.findFiles(targetFolder);
        List<FileEntry> duplicates = Util.findDuplicates(foundFiles);
        int duplicateCount = Util.getDuplicateCount(duplicates);
        dbUtil.updateDb(duplicates, duplicateCount);
    }
}
