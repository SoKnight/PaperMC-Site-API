package me.soknight.papermc.site.api.utility;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.soknight.papermc.site.api.client.PaperSiteApiClient;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Timeouts {

    public static final Timeouts DEFAULT = new Builder().create();

    private final Duration connectTimeout;
    private final Duration downloadTimeout;
    private final Duration readTimeout;
    private final Duration writeTimeout;

    public static @NotNull Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timeouts timeouts = (Timeouts) o;
        return Objects.equals(connectTimeout, timeouts.connectTimeout) &&
                Objects.equals(downloadTimeout, timeouts.downloadTimeout) &&
                Objects.equals(readTimeout, timeouts.readTimeout) &&
                Objects.equals(writeTimeout, timeouts.writeTimeout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectTimeout, downloadTimeout, readTimeout, writeTimeout);
    }

    @Override
    public @NotNull String toString() {
        return "Timeouts{" +
                "connectTimeout=" + connectTimeout +
                ", downloadTimeout=" + downloadTimeout +
                ", readTimeout=" + readTimeout +
                ", writeTimeout=" + writeTimeout +
                '}';
    }

    public static final class Builder {

        private Duration connectTimeout = PaperSiteApiClient.DEFAULT_CONNECT_TIMEOUT;
        private Duration downloadTimeout = PaperSiteApiClient.DEFAULT_DOWNLOAD_TIMEOUT;
        private Duration readTimeout = PaperSiteApiClient.DEFAULT_READ_TIMEOUT;
        private Duration writeTimeout = PaperSiteApiClient.DEFAULT_WRITE_TIMEOUT;

        public @NotNull Timeouts create() {
            return new Timeouts(connectTimeout, downloadTimeout, readTimeout, writeTimeout);
        }

        public @NotNull Builder withConnectTimeout(@NotNull Duration duration) {
            Validate.notNull(duration, "duration");
            this.connectTimeout = duration;
            return this;
        }

        public @NotNull Builder withConnectTimeout(long duration, @NotNull TimeUnit timeUnit) {
            Validate.notNull(timeUnit, "timeUnit");
            return withConnectTimeout(duration > 0L ? Duration.of(duration, asChronoUnit(timeUnit)) : Duration.ZERO);
        }

        public @NotNull Builder withDownloadTimeout(@NotNull Duration duration) {
            Validate.notNull(duration, "duration");
            this.downloadTimeout = duration;
            return this;
        }

        public @NotNull Builder withDownloadTimeout(long duration, @NotNull TimeUnit timeUnit) {
            Validate.notNull(timeUnit, "timeUnit");
            return withDownloadTimeout(duration > 0L ? Duration.of(duration, asChronoUnit(timeUnit)) : Duration.ZERO);
        }

        public @NotNull Builder withReadTimeout(@NotNull Duration duration) {
            Validate.notNull(duration, "duration");
            this.readTimeout = duration;
            return this;
        }

        public @NotNull Builder withReadTimeout(long duration, @NotNull TimeUnit timeUnit) {
            Validate.notNull(timeUnit, "timeUnit");
            return withReadTimeout(duration > 0L ? Duration.of(duration, asChronoUnit(timeUnit)) : Duration.ZERO);
        }

        public @NotNull Builder withWriteTimeout(@NotNull Duration duration) {
            Validate.notNull(duration, "duration");
            this.writeTimeout = duration;
            return this;
        }

        public @NotNull Builder withWriteTimeout(long duration, @NotNull TimeUnit timeUnit) {
            Validate.notNull(timeUnit, "timeUnit");
            return withWriteTimeout(duration > 0L ? Duration.of(duration, asChronoUnit(timeUnit)) : Duration.ZERO);
        }

        private static @NotNull ChronoUnit asChronoUnit(@NotNull TimeUnit timeUnit) {
            switch (timeUnit) {
                case DAYS:
                    return ChronoUnit.DAYS;
                case HOURS:
                    return ChronoUnit.HOURS;
                case MICROSECONDS:
                    return ChronoUnit.MICROS;
                case MILLISECONDS:
                    return ChronoUnit.MILLIS;
                case MINUTES:
                    return ChronoUnit.MINUTES;
                case NANOSECONDS:
                    return ChronoUnit.NANOS;
                case SECONDS:
                    return ChronoUnit.SECONDS;
                default:
                    throw new IllegalArgumentException("Unexpected time unit: " + timeUnit);
            }
        }

    }

}
