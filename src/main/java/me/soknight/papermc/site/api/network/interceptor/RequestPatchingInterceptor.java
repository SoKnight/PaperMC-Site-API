package me.soknight.papermc.site.api.network.interceptor;

import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@AllArgsConstructor
public final class RequestPatchingInterceptor implements Interceptor {

    private final String userAgent;

    @Override
    public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("User-Agent", userAgent)
                .addHeader("Accept", "application/json")
                .addHeader("Accept", "application/java-archive")
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }

}
