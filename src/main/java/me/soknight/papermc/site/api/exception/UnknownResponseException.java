package me.soknight.papermc.site.api.exception;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public final class UnknownResponseException extends HttpResponseException {

    private static final String MESSAGE_FORMAT = "PaperMC API has returned an unknown response [%d/%s]: %s";

    private final int statusCode;
    private final String statusMessage;
    private final String rawResponse;

    public UnknownResponseException(int statusCode, @Nullable String statusMessage, @Nullable String rawResponse) {
        super(String.format(MESSAGE_FORMAT, statusCode, statusMessage, rawResponse));
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.rawResponse = rawResponse;
    }

}
