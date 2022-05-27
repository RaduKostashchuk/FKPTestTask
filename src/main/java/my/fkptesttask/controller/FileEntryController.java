package my.fkptesttask.controller;

import my.fkptesttask.dto.DuplicateDTO;
import my.fkptesttask.model.FileEntry;
import my.fkptesttask.service.FileEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileEntryController {
    private final FileEntryService fileEntryService;

    public FileEntryController(FileEntryService fileEntryService) {
        this.fileEntryService = fileEntryService;
    }

    @GetMapping("/fileentry")
    public ResponseEntity<Iterable<FileEntry>> getAllFiles() {
        return new ResponseEntity<>(fileEntryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/duplicate")
    public ResponseEntity<List<DuplicateDTO>> getAllDuplicates() {
        return new ResponseEntity<>(fileEntryService.findAllDuplicates(), HttpStatus.OK);
    }

}
