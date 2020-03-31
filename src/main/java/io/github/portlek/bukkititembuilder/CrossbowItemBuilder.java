package io.github.portlek.bukkititembuilder;

import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CrossbowItemBuilder {

    @NotNull
    private final ItemStackBuilder builder;

    @NotNull
    private final CrossbowMeta crossbowMeta;

    public CrossbowItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final CrossbowMeta crossbowMeta) {
        this.builder = builder;
        this.crossbowMeta = crossbowMeta;
    }

    @NotNull
    public CrossbowItemBuilder chargedProjectiles(@Nullable final ItemStack... projectiles) {
        return this.chargedProjectiles(Arrays.asList(projectiles));
    }

    @NotNull
    public CrossbowItemBuilder chargedProjectiles(@Nullable final List<ItemStack> projectiles) {
        this.crossbowMeta.setChargedProjectiles(projectiles);
        this.change();
        return this;
    }

    @NotNull
    private CrossbowItemBuilder change(@NotNull final Runnable runnable) {
        runnable.run();
        this.builder.setItemMeta(this.crossbowMeta);
        return this;
    }

    @NotNull
    public CrossbowItemBuilder chargedProjectile(@NotNull final ItemStack projectile) {
        return this.change(() ->
            this.crossbowMeta.addChargedProjectile(projectile));
    }

    @NotNull
    public ItemStack build() {
        return this.builder.build();
    }

}
