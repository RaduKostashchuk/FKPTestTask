package my.fkptesttask.dto;

import my.fkptesttask.model.FileEntryKey;

import java.util.ArrayList;
import java.util.List;

public class DuplicateDTO {
    private long size;
    private String hash;
    private final List<FileDTO> files = new ArrayList<>();

    public static DuplicateDTO of(FileEntryKey key) {
        DuplicateDTO dto = new DuplicateDTO();
        dto.size = key.getSize();
        dto.hash = key.getHash();
        return dto;
    }

    public long getSize() {
        return size;
    }

    public String getHash() {
        return hash;
    }

    public void setFiles(List<FileDTO> fileDTOS) {
        this.files.addAll(fileDTOS);
    }

    public List<FileDTO> getFiles() {
        return files;
    }
}
