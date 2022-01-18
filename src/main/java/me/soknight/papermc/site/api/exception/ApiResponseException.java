package me.soknight.papermc.site.api.exception;

import lombok.Getter;
import me.soknight.papermc.site.api.network.response.FailureResponse;
import org.jetbrains.annotations.NotNull;

@Getter
public final class ApiResponseException extends HttpResponseException {

    private static final String MESSAGE_FORMAT = "PaperMC API has returned a failure response with code %d: %s";

    private final FailureResponse failureResponse;

    public ApiResponseException(@NotNull FailureResponse failureResponse) {
        super(String.format(MESSAGE_FORMAT, failureResponse.getStatus(), failureResponse.getMessage()));
        this.failureResponse = failureResponse;
    }

}
