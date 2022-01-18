package me.soknight.papermc.site.api.utility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Validate {

    public static void notNull(@Nullable Object object, @NotNull String param) {
        if(object == null)
            throw new IllegalArgumentException(String.format("'%s' cannot be null!", param));
    }

    public static void notEmpty(@Nullable String object, @NotNull String param) {
        if(object == null || object.isEmpty())
            throw new IllegalArgumentException(String.format("'%s' cannot be null or empty!", param));
    }

    public static void isTrue(boolean condition, @NotNull String message) {
        if(!condition)
            throw new IllegalArgumentException(message);
    }

}
