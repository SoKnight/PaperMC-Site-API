package me.soknight.papermc.site.api.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@Getter
public final class Project implements PrettyPrintable {

    @JsonProperty("project_id")
    private String projectId;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("version_groups")
    private List<String> versionGroups;

    @JsonProperty("versions")
    private List<String> versions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project other = (Project) o;
        return Objects.equals(projectId, other.projectId) &&
                Objects.equals(projectName, other.projectName) &&
                Objects.equals(versionGroups, other.versionGroups) &&
                Objects.equals(versions, other.versions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, versionGroups, versions);
    }

    @Override
    public @NotNull String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", versionGroups=" + versionGroups +
                ", versions=" + versions +
                '}';
    }

}
