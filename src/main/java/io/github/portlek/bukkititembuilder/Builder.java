package io.github.portlek.bukkititembuilder;

import java.util.function.Consumer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public abstract class Builder<X, T extends ItemMeta> implements Buildable<X, T> {

    @NotNull
    private final T meta;

    @NotNull
    private final ItemStack itemstack;

    public Builder(@NotNull final ItemStack itemstack, @NotNull final T meta) {
        this.itemstack = itemstack;
        this.meta = meta;
    }

    @Override
    @NotNull
    public final ItemStack itemStack() {
        return this.itemstack;
    }

    @Override
    @NotNull
    public final ItemMeta meta() {
        return this.meta;
    }

    @Override
    @NotNull
    public final X update(@NotNull final Consumer<T> consumer) {
        consumer.accept(this.meta);
        this.itemstack.setItemMeta(this.meta);
        return this.get();
    }

}
