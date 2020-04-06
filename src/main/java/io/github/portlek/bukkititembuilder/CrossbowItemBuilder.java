package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import io.github.portlek.bukkititembuilder.base.ItemStackBuilder;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CrossbowItemBuilder extends Builder<CrossbowItemBuilder, CrossbowMeta> {

    public CrossbowItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final CrossbowMeta meta) {
        super(builder, meta);
    }

    @NotNull
    public CrossbowItemBuilder chargedProjectiles(@Nullable final ItemStack... projectiles) {
        return this.chargedProjectiles(Arrays.asList(projectiles));
    }

    @NotNull
    public CrossbowItemBuilder chargedProjectiles(@Nullable final List<ItemStack> projectiles) {
        return this.change(meta ->
            meta.setChargedProjectiles(projectiles));
    }

    @NotNull
    public CrossbowItemBuilder chargedProjectile(@NotNull final ItemStack projectile) {
        return this.change(meta ->
            meta.addChargedProjectile(projectile));
    }

    @NotNull
    @Override
    protected CrossbowItemBuilder get() {
        return this;
    }

}
