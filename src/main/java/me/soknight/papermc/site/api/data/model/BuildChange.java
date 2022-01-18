package me.soknight.papermc.site.api.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public final class BuildChange implements PrettyPrintable {

    @JsonProperty("commit")
    private String commitId;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("message")
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildChange other = (BuildChange) o;
        return Objects.equals(commitId, other.commitId) &&
                Objects.equals(summary, other.summary) &&
                Objects.equals(message, other.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commitId, summary, message);
    }

    @Override
    public @NotNull String toString() {
        return "BuildChange{" +
                "commitId='" + commitId + '\'' +
                ", summary='" + summary + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
