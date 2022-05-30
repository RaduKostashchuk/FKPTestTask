package my.fkptesttask.service;

import my.fkptesttask.dto.DuplicateDTO;
import my.fkptesttask.dto.FileDTO;
import my.fkptesttask.model.FileEntry;
import my.fkptesttask.model.FileEntryKey;
import my.fkptesttask.repository.FileEntryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileEntryService {
    private final FileEntryRepository repository;

    public FileEntryService(FileEntryRepository repository) {
        this.repository = repository;
    }

    public Iterable<FileEntry> findAll() {
        return repository.findAll();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public void save(FileEntry fileEntry) {
        repository.save(fileEntry);
    }

    public Collection<DuplicateDTO> findAllDuplicates() {
        Map<FileEntryKey, DuplicateDTO> duplicates = new HashMap<>();

        for (FileEntry file : repository.findAll()) {
            FileEntryKey key = FileEntryKey.of(file.getSize(), file.getHash());
            duplicates.computeIfPresent(key, (k, v) -> {
                v.addFile(FileDTO.of(file));
                return v;
            });
            duplicates.computeIfAbsent(key, k -> {
                DuplicateDTO duplicateDTO = DuplicateDTO.of(key);
                duplicateDTO.addFile(FileDTO.of(file));
                return duplicateDTO;
            });
        }

        return duplicates.values();
    }
}
