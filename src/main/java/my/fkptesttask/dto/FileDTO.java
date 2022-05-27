package my.fkptesttask.dto;

import my.fkptesttask.model.FileEntry;

public class FileDTO {
    private String name;
    private String path;

    public static FileDTO of(FileEntry fileEntry) {
        FileDTO dto = new FileDTO();
        dto.name = fileEntry.getName();
        dto.path = fileEntry.getPath();
        return dto;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
