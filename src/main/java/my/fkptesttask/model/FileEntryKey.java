package my.fkptesttask.model;

import java.util.Objects;

public class FileEntryKey {
    private long size;
    private String hash;

    public static FileEntryKey of(long size, String hash) {
        FileEntryKey key = new FileEntryKey();
        key.size = size;
        key.hash = hash;
        return key;
    }

    public long getSize() {
        return size;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileEntryKey that = (FileEntryKey) o;
        return size == that.size && Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, hash);
    }

    @Override
    public String toString() {
        return "FileEntryKey{"
                + "size=" + size
                + ", hash='" + hash + '\''
                + '}';
    }
}
