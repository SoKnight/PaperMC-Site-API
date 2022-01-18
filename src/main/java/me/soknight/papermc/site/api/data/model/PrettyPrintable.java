package me.soknight.papermc.site.api.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import me.soknight.papermc.site.api.client.PaperSiteApiClient;
import org.jetbrains.annotations.NotNull;

public interface PrettyPrintable {

    @SneakyThrows(JsonProcessingException.class)
    default @NotNull String toPrettyString() {
        return PaperSiteApiClient.getPrettyPrinter().writeValueAsString(this);
    }

}
