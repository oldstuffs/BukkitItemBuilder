package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import io.github.portlek.bukkititembuilder.base.ItemStackBuilder;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jetbrains.annotations.NotNull;

public final class SpawnEggItemBuilder extends Builder<SpawnEggItemBuilder, SpawnEggMeta> {

    public SpawnEggItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final SpawnEggMeta meta) {
        super(builder, meta);
    }

    @NotNull
    public SpawnEggItemBuilder type(@NotNull final EntityType type) {
        return this.change(() ->
            this.meta.setSpawnedType(type));
    }

    @NotNull
    @Override
    protected SpawnEggItemBuilder get() {
        return this;
    }

}
