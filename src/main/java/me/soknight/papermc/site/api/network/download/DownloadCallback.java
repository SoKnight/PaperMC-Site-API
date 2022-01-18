package me.soknight.papermc.site.api.network.download;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.soknight.papermc.site.api.network.download.monitor.DownloadProgressMonitor;
import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public final class DownloadCallback {

    private final String projectId;
    private final String version;
    private final int buildNumber;
    private final String fileName;

    private final long contentLength;
    private final MediaType contentType;
    private final InputStream source;

    public void saveTo(@NotNull File file) throws IOException {
        saveTo(file.toPath());
    }

    public void saveTo(@NotNull Path path) throws IOException {
        Files.copy(source, path, StandardCopyOption.REPLACE_EXISTING);
    }

    public void saveWithProgress(
            @NotNull File file,
            @NotNull Consumer<DownloadProgressMonitor> modifier,
            long updatePeriod,
            @NotNull TimeUnit timeUnit
    ) throws IOException {
        saveWithProgress(file.toPath(), modifier, updatePeriod, timeUnit);
    }

    public void saveWithProgress(
            @NotNull Path path,
            @NotNull Consumer<DownloadProgressMonitor> modifier,
            long updatePeriod,
            @NotNull TimeUnit timeUnit
    ) throws IOException {
        DownloadProgressMonitor progressMonitor = new DownloadProgressMonitor(this, timeUnit.toMillis(updatePeriod), path);
        modifier.accept(progressMonitor);

        progressMonitor.start();
        Files.copy(source, path, StandardCopyOption.REPLACE_EXISTING);
    }

}
