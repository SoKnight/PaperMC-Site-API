package me.soknight.papermc.site.api.network.download.monitor;

import me.soknight.papermc.site.api.network.download.DownloadCallback;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ProgressUpdateListener {

    void onUpdate(@NotNull DownloadCallback callback, long currentSize, long totalSize, double percentage);

}
