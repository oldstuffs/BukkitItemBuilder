package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

public final class FireworkItemBuilder extends Builder<FireworkItemBuilder, FireworkMeta> {

    public FireworkItemBuilder(@NotNull final ItemStack item, @NotNull final FireworkMeta meta) {
        super(item, meta);
    }

    @NotNull
    public FireworkItemBuilder power(final int power) {
        return this.update(meta -> meta.setPower(power));
    }

    @NotNull
    public FireworkItemBuilder removeEffect(final int effectid) {
        return this.update(meta ->
            meta.removeEffect(effectid));
    }

    @NotNull
    public FireworkItemBuilder clearEffects() {
        return this.update(FireworkMeta::clearEffects);
    }

    @NotNull
    public FireworkItemBuilder addEffect(@NotNull final FireworkEffect effect) {
        return this.update(meta ->
            meta.addEffect(effect));
    }

    @NotNull
    public FireworkItemBuilder addEffects(@NotNull final FireworkEffect... effects) {
        return this.update(meta ->
            meta.addEffects(effects));
    }

    @NotNull
    public FireworkItemBuilder addEffects(@NotNull final Iterable<FireworkEffect> effects) {
        return this.update(meta ->
            meta.addEffects(effects));
    }

    @NotNull
    @Override
    public FireworkItemBuilder chain() {
        return this;
    }

}
