package my.fkptesttask.task;

import my.fkptesttask.model.FileEntryKey;
import my.fkptesttask.model.FileEntry;
import my.fkptesttask.service.FileEntryService;
import my.fkptesttask.service.TaskExecutionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static my.fkptesttask.task.fs.FsUtil.searchDuplicates;
import static my.fkptesttask.task.db.DbUtil.saveTaskExecution;
import static my.fkptesttask.task.db.DbUtil.updateDuplicates;

@Component
public class Task {
    private final TaskExecutionService taskExecutionService;
    private final FileEntryService fileEntryService;
    private final ApplicationContext context;

    public Task(TaskExecutionService taskExecutionService,
                FileEntryService fileEntryService,
                ApplicationContext context) {
        this.taskExecutionService = taskExecutionService;
        this.fileEntryService = fileEntryService;
        this.context = context;
    }

    @Value("${target-folder}")
    private String targetFolder;


    @Scheduled(fixedDelayString = "#{${run-interval} + \"000\"}")
    public void run() {
        try {
            Map<FileEntryKey, List<FileEntry>> duplicates = searchDuplicates(Path.of(targetFolder));
            int duplicateCount = duplicates.keySet().size();

            saveTaskExecution(duplicateCount, taskExecutionService);
            updateDuplicates(duplicates, fileEntryService);
        } catch (IOException e) {
            e.printStackTrace();
            SpringApplication.exit(context, () -> 10);
        }
    }
}
