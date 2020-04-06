package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import io.github.portlek.bukkititembuilder.base.ItemStackBuilder;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SkullItemBuilder extends Builder<SkullItemBuilder, SkullMeta> {

    public SkullItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final SkullMeta meta) {
        super(builder, meta);
    }

    @NotNull
    @Deprecated
    public SkullItemBuilder owner(@Nullable final String owner) {
        return this.change(meta ->
            meta.setOwner(owner));
    }

    @NotNull
    public SkullItemBuilder owner(@Nullable final OfflinePlayer player) {
        return this.change(meta ->
            meta.setOwningPlayer(player));
    }

    @NotNull
    @Override
    protected SkullItemBuilder get() {
        return this;
    }

}
