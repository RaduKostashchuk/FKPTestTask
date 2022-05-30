package my.fkptesttask.task;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.service.FileEntryService;
import my.fkptesttask.service.TaskExecutionService;
import my.fkptesttask.task.db.DbUtil;
import my.fkptesttask.task.fs.FsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

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
            List<FileEntry> files = FsUtil.findFiles(Path.of(targetFolder));
            DbUtil.updateDb(files, taskExecutionService, fileEntryService);
        } catch (IOException e) {
            e.printStackTrace();
            SpringApplication.exit(context, () -> 10);
        }
    }
}
