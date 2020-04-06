package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import io.github.portlek.bukkititembuilder.base.ItemStackBuilder;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

public final class FireworkItemBuilder extends Builder<FireworkItemBuilder, FireworkMeta> {

    public FireworkItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final FireworkMeta meta) {
        super(builder, meta);
    }

    @NotNull
    public FireworkItemBuilder power(final int power) {
        return this.change(meta -> meta.setPower(power));
    }

    @NotNull
    public FireworkItemBuilder removeEffect(final int effectid) {
        return this.change(meta ->
            meta.removeEffect(effectid));
    }

    @NotNull
    public FireworkItemBuilder clearEffects() {
        return this.change(FireworkMeta::clearEffects);
    }

    @NotNull
    public FireworkItemBuilder addEffect(@NotNull final FireworkEffect effect) {
        return this.change(meta ->
            meta.addEffect(effect));
    }

    @NotNull
    public FireworkItemBuilder addEffects(@NotNull final FireworkEffect... effects) {
        return this.change(meta ->
            meta.addEffects(effects));
    }

    @NotNull
    public FireworkItemBuilder addEffects(@NotNull final Iterable<FireworkEffect> effects) {
        return this.change(meta ->
            meta.addEffects(effects));
    }

    @NotNull
    @Override
    protected FireworkItemBuilder get() {
        return this;
    }

}
