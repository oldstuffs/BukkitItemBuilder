package io.github.portlek.bukkititembuilder;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LeatherArmorItemBuilder extends Builder<LeatherArmorItemBuilder, LeatherArmorMeta> {

    public LeatherArmorItemBuilder(@NotNull final ItemStack item, @NotNull final LeatherArmorMeta meta) {
        super(item, meta);
    }

    @NotNull
    public LeatherArmorItemBuilder color(@Nullable final Color color) {
        return this.update(meta ->
            meta.setColor(color));
    }

    @NotNull
    @Override
    public LeatherArmorItemBuilder chain() {
        return this;
    }

}
