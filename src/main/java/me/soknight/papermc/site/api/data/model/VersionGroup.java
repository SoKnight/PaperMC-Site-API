package me.soknight.papermc.site.api.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@Getter
public final class VersionGroup implements PrettyPrintable {

    @JsonProperty("project_id")
    private String projectId;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("version_group")
    private String versionGroup;

    @JsonProperty("versions")
    private List<String> versions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VersionGroup other = (VersionGroup) o;
        return Objects.equals(projectId, other.projectId) &&
                Objects.equals(projectName, other.projectName) &&
                Objects.equals(versionGroup, other.versionGroup) &&
                Objects.equals(versions, other.versions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, versionGroup, versions);
    }

    @Override
    public @NotNull String toString() {
        return "VersionGroup{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", versionGroup='" + versionGroup + '\'' +
                ", versions=" + versions +
                '}';
    }

}
