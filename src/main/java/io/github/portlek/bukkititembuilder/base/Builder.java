package io.github.portlek.bukkititembuilder.base;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public abstract class Builder<X, T extends ItemMeta> {

    @NotNull
    protected final T meta;

    @NotNull
    private final ItemStackBuilder builder;

    protected Builder(@NotNull final ItemStackBuilder builder, @NotNull final T meta) {
        this.builder = builder;
        this.meta = meta;
    }

    @NotNull
    public final ItemStack builder() {
        return this.builder.build();
    }

    @NotNull
    protected final X change(@NotNull final Runnable runnable) {
        runnable.run();
        this.builder.setItemMeta(this.meta);
        return this.get();
    }

    @NotNull
    protected abstract X get();

}
