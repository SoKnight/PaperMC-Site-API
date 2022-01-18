package me.soknight.papermc.site.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import me.soknight.papermc.site.api.data.model.*;
import me.soknight.papermc.site.api.exception.ApiResponseException;
import me.soknight.papermc.site.api.exception.HttpRequestException;
import me.soknight.papermc.site.api.exception.HttpResponseException;
import me.soknight.papermc.site.api.exception.UnknownResponseException;
import me.soknight.papermc.site.api.json.converter.JacksonConverterFactory;
import me.soknight.papermc.site.api.network.download.PaperDownloadAgent;
import me.soknight.papermc.site.api.network.interceptor.RequestPatchingInterceptor;
import me.soknight.papermc.site.api.network.response.FailureResponse;
import me.soknight.papermc.site.api.utility.Timeouts;
import me.soknight.papermc.site.api.utility.Validate;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.time.Duration;
import java.util.function.Supplier;

public final class PaperSiteApiClient {

    public static final String API_ENDPOINT = "https://papermc.io/api/v2/";
    public static final String USER_AGENT = "PaperMC-Site-API/Java";

    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(10L);
    public static final Duration DEFAULT_DOWNLOAD_TIMEOUT = Duration.ZERO;
    public static final Duration DEFAULT_READ_TIMEOUT = Duration.ofSeconds(10L);
    public static final Duration DEFAULT_WRITE_TIMEOUT = Duration.ofSeconds(10L);

    private static final String JACKSON_MODULE_NAME = "PaperMC-Site-API";
    private static final ObjectWriter PRETTY_PRINTER = new ObjectMapper().writerWithDefaultPrettyPrinter();

    private final ObjectMapper objectMapper;
    private final PaperSiteApiService apiService;
    private final PaperDownloadAgent downloadAgent;

    private PaperSiteApiClient(@NotNull String userAgent, @NotNull Timeouts timeouts) {
        this.objectMapper = new ObjectMapper();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new RequestPatchingInterceptor(userAgent))
                .connectTimeout(timeouts.getConnectTimeout())
                .readTimeout(timeouts.getReadTimeout())
                .writeTimeout(timeouts.getWriteTimeout())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(new JacksonConverterFactory(objectMapper))
                .baseUrl(API_ENDPOINT)
                .client(httpClient)
                .build();

        this.apiService = retrofit.create(PaperSiteApiService.class);
        this.downloadAgent = new PaperDownloadAgent(httpClient, objectMapper, timeouts);
    }

    public static @NotNull PaperSiteApiClient create(@NotNull String userAgent, @NotNull Timeouts timeouts) {
        Validate.notEmpty(userAgent, "userAgent");
        Validate.notNull(timeouts, "timeouts");
        return new PaperSiteApiClient(userAgent, timeouts);
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static @NotNull ObjectWriter getPrettyPrinter() {
        return PRETTY_PRINTER;
    }

    public @NotNull PaperDownloadAgent getDownloadAgent() {
        return downloadAgent;
    }

    public @NotNull Projects getProjects() throws HttpRequestException, HttpResponseException {
        return executeRequest(apiService::getProjects);
    }

    public @NotNull Project getProjectInfo(@NotNull String projectId) throws HttpRequestException, HttpResponseException {
        Validate.notEmpty(projectId, "projectId");
        return executeRequest(() -> apiService.getProjectInfo(projectId));
    }

    public @NotNull Version getVersionInfo(@NotNull String projectId, @NotNull String version) throws HttpRequestException, HttpResponseException {
        Validate.notEmpty(projectId, "projectId");
        Validate.notEmpty(version, "version");
        return executeRequest(() -> apiService.getVersionInfo(projectId, version));
    }

    public @NotNull Build getBuildInfo(@NotNull String projectId, @NotNull String version, int buildNumber) throws HttpRequestException, HttpResponseException {
        Validate.notEmpty(projectId, "projectId");
        Validate.notEmpty(version, "version");
        Validate.isTrue(buildNumber >= 0, "'buildNumber' must be equal or more than 0!");
        return executeRequest(() -> apiService.getBuildInfo(projectId, version, buildNumber));
    }

    public @NotNull VersionGroup getVersionGroupInfo(@NotNull String projectId, @NotNull String family) throws HttpRequestException, HttpResponseException {
        Validate.notEmpty(projectId, "projectId");
        Validate.notEmpty(family, "family");
        return executeRequest(() -> apiService.getVersionGroupInfo(projectId, family));
    }

    public @NotNull VersionGroupBuilds getVersionGroupBuilds(@NotNull String projectId, @NotNull String family) throws HttpRequestException, HttpResponseException {
        Validate.notEmpty(projectId, "projectId");
        Validate.notEmpty(family, "family");
        return executeRequest(() -> apiService.getVersionGroupBuilds(projectId, family));
    }

    private <T> @NotNull T executeRequest(@NotNull Supplier<Call<T>> callSupplier) throws HttpRequestException, HttpResponseException {
        Call<T> call = callSupplier.get();
        Response<T> response;

        try {
            response = call.execute();
        } catch (IOException ex) {
            throw new HttpRequestException(ex);
        }

        if(response.isSuccessful())
            return response.body();

        int statusCode = response.code();
        String statusMessage = response.message();
        String errorJsonContent;

        try {
            ResponseBody errorBody = response.errorBody();
            errorJsonContent = errorBody.string();
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

    public static final class Builder {

        private String userAgent = USER_AGENT;
        private Timeouts timeouts = Timeouts.DEFAULT;

        public @NotNull PaperSiteApiClient create() {
            return new PaperSiteApiClient(userAgent, timeouts);
        }

        public @NotNull Builder withUserAgent(@NotNull String userAgent) {
            Validate.notEmpty(userAgent, "userAgent");
            this.userAgent = userAgent;
            return this;
        }

        public @NotNull Builder withTimeouts(@NotNull Timeouts timeouts) {
            Validate.notNull(timeouts, "timeouts");
            this.timeouts = timeouts;
            return this;
        }

    }

}
