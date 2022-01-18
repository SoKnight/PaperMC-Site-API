package me.soknight.papermc.site.api.network.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import me.soknight.papermc.site.api.data.model.PrettyPrintable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public final class FailureResponse implements PrettyPrintable {

    @JsonProperty("error")
    private String message;

    @JsonProperty("status")
    private int status;

    @JsonProperty("path")
    private String targetPath;

    @JsonProperty("timestamp")
    private String timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FailureResponse other = (FailureResponse) o;
        return status == other.status &&
                Objects.equals(message, other.message) &&
                Objects.equals(targetPath, other.targetPath) &&
                Objects.equals(timestamp, other.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, status, targetPath, timestamp);
    }

    @Override
    public @NotNull String toString() {
        return "FailureResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", targetPath='" + targetPath + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
