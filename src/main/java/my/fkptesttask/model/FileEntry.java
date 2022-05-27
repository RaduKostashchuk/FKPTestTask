package my.fkptesttask.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class FileEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String path;

    private long size;

    private String hash;

    public static FileEntry of(String name, String path, long size, String hash) {
        FileEntry entry = new FileEntry();
        entry.name = name;
        entry.path = path;
        entry.size = size;
        entry.hash = hash;
        return entry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileEntry entry = (FileEntry) o;
        return size == entry.size
                && Objects.equals(name, entry.name)
                && Objects.equals(path, entry.path)
                && Objects.equals(hash, entry.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, size, hash);
    }
}
