package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CrossbowItemBuilder extends Builder<CrossbowItemBuilder, CrossbowMeta> {

    public CrossbowItemBuilder(@NotNull final ItemStack item, @NotNull final CrossbowMeta meta) {
        super(item, meta);
    }

    @NotNull
    public CrossbowItemBuilder chargedProjectiles(@Nullable final ItemStack... projectiles) {
        return this.chargedProjectiles(Arrays.asList(projectiles));
    }

    @NotNull
    public CrossbowItemBuilder chargedProjectiles(@Nullable final List<ItemStack> projectiles) {
        return this.update(meta ->
            meta.setChargedProjectiles(projectiles));
    }

    @NotNull
    public CrossbowItemBuilder chargedProjectile(@NotNull final ItemStack projectile) {
        return this.update(meta ->
            meta.addChargedProjectile(projectile));
    }

    @NotNull
    @Override
    public CrossbowItemBuilder chain() {
        return this;
    }

}
