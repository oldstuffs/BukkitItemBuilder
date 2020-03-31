package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import io.github.portlek.bukkititembuilder.base.ItemStackBuilder;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LeatherArmorItemBuilder extends Builder<LeatherArmorItemBuilder, LeatherArmorMeta> {

    public LeatherArmorItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final LeatherArmorMeta meta) {
        super(builder, meta);
    }

    @NotNull
    public LeatherArmorItemBuilder color(@Nullable final Color color) {
        return this.change(() ->
            this.meta.setColor(color));
    }

    @NotNull
    @Override
    protected LeatherArmorItemBuilder get() {
        return this;
    }

}
