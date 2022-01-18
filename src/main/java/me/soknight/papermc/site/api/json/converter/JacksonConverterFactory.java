package me.soknight.papermc.site.api.json.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import me.soknight.papermc.site.api.annotation.ListWrappingModel;
import me.soknight.papermc.site.api.exception.ApiResponseException;
import me.soknight.papermc.site.api.network.response.FailureResponse;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@AllArgsConstructor
public final class JacksonConverterFactory extends Converter.Factory {

    private final ObjectMapper objectMapper;

    @Override
    public @NotNull Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return value -> {
            String jsonContent = value.string();
            JsonNode jsonTree = objectMapper.readTree(jsonContent);

            try {
                if(type instanceof Class) {
                    Class<?> typeClass = (Class<?>) type;
                    ListWrappingModel annotation = typeClass.getAnnotation(ListWrappingModel.class);
                    if(annotation != null) {
                        String property = annotation.value();
                        JsonNode node = jsonTree.get(property);
                        if(node != null) {
                            return objectMapper.treeToValue(node, typeClass);
                        } else {
                            deserializeAsFailureResponse(jsonContent);
                        }
                    }
                }

                JavaType valueType = objectMapper.constructType(type);
                return objectMapper.readValue(jsonContent, valueType);
            } catch (JsonProcessingException ignored) {
                deserializeAsFailureResponse(jsonContent);
                return null;
            }
        };
    }

    private void deserializeAsFailureResponse(@NotNull String jsonContent) throws ApiResponseException, JsonProcessingException {
        FailureResponse failureResponse = objectMapper.readValue(jsonContent, FailureResponse.class);
        throw new ApiResponseException(failureResponse);
    }

}
