package me.soknight.papermc.site.api.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class HttpResponseException extends IOException {

    private static final String MESSAGE_FORMAT = "Couldn't handle HTTP response from the PaperMC API: %s";

    public HttpResponseException(@NotNull String message) {
        this(message, null);
    }

    public HttpResponseException(@NotNull Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public HttpResponseException(@NotNull String message, @Nullable Throwable cause) {
        super(message, cause);
    }

}
