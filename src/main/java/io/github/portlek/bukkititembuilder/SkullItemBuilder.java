package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import io.github.portlek.bukkititembuilder.base.ItemStackBuilder;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SkullItemBuilder extends Builder<SkullItemBuilder, SkullMeta> {

    public SkullItemBuilder(@NotNull ItemStack itemstack, @NotNull SkullMeta meta) {
        super(itemstack, meta);
    }

    @NotNull
    @Deprecated
    public SkullItemBuilder owner(@Nullable final String owner) {
        return this.update(meta ->
            meta.setOwner(owner));
    }

    @NotNull
    public SkullItemBuilder owner(@Nullable final OfflinePlayer player) {
        return this.update(meta ->
            meta.setOwningPlayer(player));
    }

    @NotNull
    @Override
    public SkullItemBuilder chain() {
        return this;
    }

}
