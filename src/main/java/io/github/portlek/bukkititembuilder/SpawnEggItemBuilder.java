package io.github.portlek.bukkititembuilder;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jetbrains.annotations.NotNull;

public final class SpawnEggItemBuilder extends Builder<SpawnEggItemBuilder, SpawnEggMeta> {

    public SpawnEggItemBuilder(@NotNull final ItemStack itemstack, @NotNull final SpawnEggMeta meta) {
        super(itemstack, meta);
    }

    @NotNull
    public SpawnEggItemBuilder type(@NotNull final EntityType type) {
        return this.update(meta ->
            meta.setSpawnedType(type));
    }

    @NotNull
    @Override
    public SpawnEggItemBuilder chain() {
        return this;
    }

}
