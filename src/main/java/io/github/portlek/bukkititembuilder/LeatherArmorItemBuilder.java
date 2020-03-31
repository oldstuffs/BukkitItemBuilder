package io.github.portlek.bukkititembuilder;

import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

public final class LeatherArmorItemBuilder {

    @NotNull
    private final ItemStackBuilder builder;

    @NotNull
    private final LeatherArmorMeta leatherArmorMeta;

    public LeatherArmorItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final LeatherArmorMeta leatherArmorMeta) {
        this.builder = builder;
        this.leatherArmorMeta = leatherArmorMeta;
    }

}
