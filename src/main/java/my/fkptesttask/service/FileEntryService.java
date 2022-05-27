package my.fkptesttask.service;

import my.fkptesttask.dto.DuplicateDTO;
import my.fkptesttask.dto.FileDTO;
import my.fkptesttask.model.FileEntry;
import my.fkptesttask.model.FileEntryKey;
import my.fkptesttask.repository.FileEntryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<DuplicateDTO> findAllDuplicates() {
        List<DuplicateDTO> result = new ArrayList<>();
        Map<FileEntryKey, List<FileDTO>> map = new HashMap<>();

        for (FileEntry file : repository.findAll()) {
            FileEntryKey key = FileEntryKey.of(file.getSize(), file.getHash());
            map.computeIfPresent(key, (k, v) -> {
                v.add(FileDTO.of(file));
                return v;
            });
            map.computeIfAbsent(key, k -> {
                List<FileDTO> list = new ArrayList<>();
                list.add(FileDTO.of(file));
                return list;
            });
        }

        for (FileEntryKey key : map.keySet()) {
            DuplicateDTO duplicate = DuplicateDTO.of(key);
            duplicate.setFiles(map.get(key));
            result.add(duplicate);
        }

        return result;
    }
}
