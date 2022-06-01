package my.fkptesttask.task.misc;

import my.fkptesttask.model.FileEntry;
import my.fkptesttask.model.FileEntryKey;

import java.util.*;

public class Util {
    public static List<FileEntry> findDuplicates(List<FileEntry> list) {
        Map<FileEntryKey, List<FileEntry>> groupedEqualFiles = groupEqualFiles(list);
        removeNonDuplicates(groupedEqualFiles);
        return groupedEqualFiles
                .values().stream()
                .flatMap(Collection::stream).toList();
    }

    private static Map<FileEntryKey, List<FileEntry>> groupEqualFiles(List<FileEntry> fileEntries) {
        Map<FileEntryKey, List<FileEntry>> map = new HashMap<>();
        for (FileEntry fileEntry : fileEntries) {
            FileEntryKey key = FileEntryKey.of(fileEntry.getSize(), fileEntry.getHash());
            map.computeIfPresent(key, (k, v) -> {
                v.add(fileEntry);
                return v;
            });
            map.computeIfAbsent(key, k -> {
                List<FileEntry> files = new ArrayList<>();
                files.add(fileEntry);
                return files;
            });
        }
        return map;
    }

    private static void removeNonDuplicates(Map<FileEntryKey, List<FileEntry>> fileEntries) {
        fileEntries.entrySet().removeIf(v -> v.getValue().size() < 2);
    }

    public static int getDuplicateCount(List<FileEntry> duplicates) {
        Set<FileEntryKey> fileEntryKeys = new HashSet<>();
        for (FileEntry fileEntry : duplicates) {
            FileEntryKey key = FileEntryKey.of(fileEntry.getSize(), fileEntry.getHash());
            fileEntryKeys.add(key);
        }
        return fileEntryKeys.size();
    }
}
