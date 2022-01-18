package me.soknight.papermc.site.api.network.download.monitor;

import me.soknight.papermc.site.api.network.download.DownloadCallback;
import me.soknight.papermc.site.api.utility.Validate;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class DownloadProgressMonitor implements Runnable {

    private static final AtomicInteger THREAD_INDEXER = new AtomicInteger(0);

    private final DownloadCallback downloadCallback;
    private final Path outputFilePath;

    private final Set<ProgressUpdateListener> updateListeners;
    private final long updatePeriod;
    private final boolean updateOnlyWhenFinish;

    private Thread thread;

    public DownloadProgressMonitor(@NotNull DownloadCallback downloadCallback, long updatePeriod, @NotNull Path outputFilePath) {
        this.downloadCallback = downloadCallback;
        this.outputFilePath = outputFilePath;

        this.updateListeners = new LinkedHashSet<>();
        this.updatePeriod = Math.max(updatePeriod, 1L);
        this.updateOnlyWhenFinish = updatePeriod <= 0L;
    }

    public @NotNull DownloadProgressMonitor addUpdateListener(@NotNull ProgressUpdateListener updateListener) {
        Validate.notNull(updateListener, "updateListener");
        updateListeners.add(updateListener);
        return this;
    }

    public @NotNull DownloadProgressMonitor removeUpdateListener(@NotNull ProgressUpdateListener updateListener) {
        Validate.notNull(updateListener, "updateListener");
        updateListeners.remove(updateListener);
        return this;
    }

    public void start() {
        if(thread != null && thread.isAlive())
            throw new IllegalStateException("This download monitor is already running!");

        this.thread = new Thread(this, getThreadName());
        this.thread.start();
    }

    public void startAndWait() throws InterruptedException {
        start();
        thread.join();
    }

    public void stop() {
        if(thread != null)
            thread.interrupt();
    }

    @Override
    public void run() {
        boolean working = true;
        while(working) {
            long currentFileSize = getCurrentFileSize();
            long requiredFileSize = downloadCallback.getContentLength();
            double percentage = (double) currentFileSize / requiredFileSize;

            boolean isDone = currentFileSize == requiredFileSize;
            if(isDone) {
                working = false;
            }

            if(!updateOnlyWhenFinish || isDone) {
                for(ProgressUpdateListener listener : updateListeners) {
                    listener.onUpdate(downloadCallback, currentFileSize, requiredFileSize, percentage);
                }
            }

            try {
                long updatePeriod = updateOnlyWhenFinish ? 1000L : this.updatePeriod;
                Thread.sleep(updatePeriod);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private long getCurrentFileSize() {
        try {
            return Files.size(outputFilePath);
        } catch (IOException ignored) {
            return 0L;
        }
    }

    private @NotNull String getThreadName() {
        return String.format("PaperMC-Download-Monitor-%d", THREAD_INDEXER.getAndIncrement());
    }

}
