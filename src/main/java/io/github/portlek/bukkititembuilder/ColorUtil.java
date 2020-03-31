package io.github.portlek.bukkititembuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public final class ColorUtil {

    private ColorUtil() {
    }

    @NotNull
    public static List<String> colored(@NotNull final String... array) {
        return ColorUtil.colored(Arrays.asList(array));
    }

    @NotNull
    public static List<String> colored(@NotNull final Collection<String> list) {
        return list.stream().map(ColorUtil::colored).collect(Collectors.toList());
    }

    @NotNull
    public static String colored(@NotNull final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
