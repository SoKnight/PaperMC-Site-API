package me.soknight.papermc.site.api.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

@Getter
public final class Version implements PrettyPrintable {

    @JsonProperty("project_id")
    private String projectId;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("version")
    private String version;

    @JsonProperty("builds")
    private List<Integer> builds;

    public @NotNull OptionalInt getLastBuild() {
        if(builds == null || builds.isEmpty())
            return OptionalInt.empty();

        return builds.stream()
                .mapToInt(Integer::intValue)
                .max();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version other = (Version) o;
        return Objects.equals(projectId, other.projectId) &&
                Objects.equals(projectName, other.projectName) &&
                Objects.equals(version, other.version) &&
                Objects.equals(builds, other.builds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, version, builds);
    }

    @Override
    public @NotNull String toString() {
        return "Version{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", version='" + version + '\'' +
                ", builds=" + builds +
                '}';
    }

}
