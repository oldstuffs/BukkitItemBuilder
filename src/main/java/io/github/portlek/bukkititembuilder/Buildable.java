package io.github.portlek.bukkititembuilder;

import java.util.function.Consumer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public interface Buildable<X, T extends ItemMeta> {

    @NotNull
    ItemStack build();

    @NotNull
    X update(@NotNull Consumer<T> consumer);

    @NotNull
    X chain();

}
