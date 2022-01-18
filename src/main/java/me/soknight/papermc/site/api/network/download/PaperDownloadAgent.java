package me.soknight.papermc.site.api.network.download;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.soknight.papermc.site.api.client.PaperSiteApiClient;
import me.soknight.papermc.site.api.exception.ApiResponseException;
import me.soknight.papermc.site.api.exception.HttpRequestException;
import me.soknight.papermc.site.api.exception.HttpResponseException;
import me.soknight.papermc.site.api.exception.UnknownResponseException;
import me.soknight.papermc.site.api.network.response.FailureResponse;
import me.soknight.papermc.site.api.utility.Timeouts;
import me.soknight.papermc.site.api.utility.Validate;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

public final class PaperDownloadAgent {

    private static final String DOWNLOAD_URL_PATTERN = PaperSiteApiClient.API_ENDPOINT + "projects/%s/versions/%s/builds/%d/downloads/%s";

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public PaperDownloadAgent(@NotNull OkHttpClient httpClient, @NotNull ObjectMapper objectMapper, @NotNull Timeouts timeouts) {
        this.httpClient = httpClient.newBuilder()
                .readTimeout(timeouts.getDownloadTimeout())
                .build();

        this.objectMapper = objectMapper;
    }

    public @NotNull DownloadCallback download(
            @NotNull String projectId,
            @NotNull String version,
            int buildNumber,
            @NotNull String fileName
    ) throws HttpRequestException, HttpResponseException {
        Validate.notEmpty(projectId, "projectId");
        Validate.notEmpty(version, "version");
        Validate.isTrue(buildNumber >= 0, "'buildNumber' must be equal or more than 0!");
        Validate.notEmpty(fileName, "fileName");

        String url = String.format(DOWNLOAD_URL_PATTERN, projectId, version, buildNumber, fileName);
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        Response response;
        try {
            Call call = httpClient.newCall(request);
            response = call.execute();
        } catch (IOException ex) {
            throw new HttpRequestException(ex);
        }

        ResponseBody responseBody = response.body();
        if(!response.isSuccessful()) {
            int statusCode = response.code();
            String statusMessage = response.message();
            String errorJsonContent;

            try {
                errorJsonContent = responseBody.string();
            } catch (IOException ex) {
                throw new HttpResponseException(ex);
            }

            try {
                FailureResponse failureResponse = objectMapper.readValue(errorJsonContent, FailureResponse.class);
                throw new ApiResponseException(failureResponse);
            } catch (JsonProcessingException ignored) {
                throw new UnknownResponseException(statusCode, statusMessage, errorJsonContent);
            }
        }

        long contentLength = responseBody.contentLength();
        MediaType contentType = responseBody.contentType();
        InputStream source = responseBody.byteStream();

        return new DownloadCallback(projectId, version, buildNumber, fileName, contentLength, contentType, source);
    }

}
