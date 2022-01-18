package me.soknight.papermc.site.api.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@Getter
public final class VersionGroupBuilds implements PrettyPrintable {

    @JsonProperty("project_id")
    private String projectId;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("version_group")
    private String versionGroup;

    @JsonProperty("versions")
    private List<String> versions;

    @JsonProperty("builds")
    private List<Build> builds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VersionGroupBuilds other = (VersionGroupBuilds) o;
        return Objects.equals(projectId, other.projectId) &&
                Objects.equals(projectName, other.projectName) &&
                Objects.equals(versionGroup, other.versionGroup) &&
                Objects.equals(versions, other.versions) &&
                Objects.equals(builds, other.builds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, versionGroup, versions, builds);
    }

    @Override
    public @NotNull String toString() {
        return "VersionGroupBuilds{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", versionGroup='" + versionGroup + '\'' +
                ", versions=" + versions +
                ", builds=" + builds +
                '}';
    }

}
