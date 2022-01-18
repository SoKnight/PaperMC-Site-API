package me.soknight.papermc.site.api.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class HttpRequestException extends IOException {

    public HttpRequestException(@NotNull String message) {
        this(message, null);
    }

    public HttpRequestException(@NotNull Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public HttpRequestException(@NotNull String message, @Nullable Throwable cause) {
        super(message, cause);
    }

}
