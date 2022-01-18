package me.soknight.papermc.site.api.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public final class Build implements PrettyPrintable {

    private static final String APPLICATION_DOWNLOAD_ID = "application";
    private static final String MOJANG_MAPPINGS_DOWNLOAD_ID = "mojang-mappings";

    @JsonProperty("project_id")
    private String projectId;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("version")
    private String version;

    @JsonProperty("build")
    private int buildNumber;

    @JsonProperty("time")
    private String buildTime;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("promoted")
    private boolean promoted;

    @JsonProperty("changes")
    private List<BuildChange> changes;

    @JsonProperty("downloads")
    private Map<String, BuildDownload> downloads;

    public @NotNull Optional<BuildDownload> getDownload(@NotNull String downloadId) {
        if(downloads == null || downloads.isEmpty())
            return Optional.empty();

        return Optional.ofNullable(downloads.get(downloadId));
    }

    public @NotNull Optional<BuildDownload> getApplicationDownload() {
        return getDownload(APPLICATION_DOWNLOAD_ID);
    }

    public @NotNull Optional<BuildDownload> getMojangMappingsDownload() {
        return getDownload(MOJANG_MAPPINGS_DOWNLOAD_ID);
    }

    public @NotNull List<String> getChangesSummaryList() {
        if(changes == null || changes.isEmpty())
            return Collections.emptyList();

        return changes.stream()
                .map(BuildChange::getSummary)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public @NotNull List<String> getChangesCommitList() {
        if(changes == null || changes.isEmpty())
            return Collections.emptyList();

        return changes.stream()
                .map(BuildChange::getCommitId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Build other = (Build) o;
        return buildNumber == other.buildNumber &&
                promoted == other.promoted &&
                Objects.equals(projectId, other.projectId) &&
                Objects.equals(projectName, other.projectName) &&
                Objects.equals(version, other.version) &&
                Objects.equals(buildTime, other.buildTime) &&
                Objects.equals(channel, other.channel) &&
                Objects.equals(changes, other.changes) &&
                Objects.equals(downloads, other.downloads);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                projectId, projectName, version, buildNumber, buildTime,
                channel, promoted, changes, downloads
        );
    }

    @Override
    public @NotNull String toString() {
        return "Build{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", version='" + version + '\'' +
                ", buildNumber=" + buildNumber +
                ", buildTime='" + buildTime + '\'' +
                ", channel='" + channel + '\'' +
                ", promoted=" + promoted +
                ", changes=" + changes +
                ", downloads=" + downloads +
                '}';
    }

}
