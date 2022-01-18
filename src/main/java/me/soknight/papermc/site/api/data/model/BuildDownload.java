package me.soknight.papermc.site.api.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public final class BuildDownload implements PrettyPrintable {

    @JsonProperty("name")
    private String fileName;

    @JsonProperty("sha256")
    private String hashSha256;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildDownload other = (BuildDownload) o;
        return Objects.equals(fileName, other.fileName) &&
                Objects.equals(hashSha256, other.hashSha256);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, hashSha256);
    }

    @Override
    public @NotNull String toString() {
        return "BuildDownload{" +
                "fileName='" + fileName + '\'' +
                ", hashSha256='" + hashSha256 + '\'' +
                '}';
    }

}
