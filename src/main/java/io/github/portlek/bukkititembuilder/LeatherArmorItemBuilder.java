package io.github.portlek.bukkititembuilder;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LeatherArmorItemBuilder {

    @NotNull
    private final ItemStackBuilder builder;

    @NotNull
    private final LeatherArmorMeta leatherArmorMeta;

    public LeatherArmorItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final LeatherArmorMeta leatherArmorMeta) {
        this.builder = builder;
        this.leatherArmorMeta = leatherArmorMeta;
    }

    @NotNull
    public LeatherArmorItemBuilder color(@Nullable final Color color) {
        return this.change(() ->
            this.leatherArmorMeta.setColor(color));
    }

    @NotNull
    private LeatherArmorItemBuilder change(@NotNull final Runnable runnable) {
        runnable.run();
        this.builder.setItemMeta(this.leatherArmorMeta);
        return this;
    }

    @NotNull
    public ItemStack build() {
        return this.builder.build();
    }

}
