package io.github.portlek.bukkititembuilder;

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

public final class FireworkItemBuilder {

    @NotNull
    private final ItemStackBuilder builder;

    @NotNull
    private final FireworkMeta fireworkMeta;

    public FireworkItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final FireworkMeta fireworkMeta) {
        this.builder = builder;
        this.fireworkMeta = fireworkMeta;
    }

    @NotNull
    public FireworkItemBuilder power(final int power) {
        return this.change(() -> this.fireworkMeta.setPower(power));
    }

    @NotNull
    private FireworkItemBuilder change(@NotNull final Runnable runnable) {
        runnable.run();
        this.builder.setItemMeta(this.fireworkMeta);
        return this;
    }

    @NotNull
    public ItemStack build() {
        return this.builder.build();
    }

    @NotNull
    public FireworkItemBuilder removeEffect(final int effectid) {
        return this.change(() ->
            this.fireworkMeta.removeEffect(effectid));
    }

    @NotNull
    public FireworkItemBuilder clearEffects() {
        return this.change(this.fireworkMeta::clearEffects);
    }

    @NotNull
    public FireworkItemBuilder addEffect(@NotNull final FireworkEffect effect) {
        return this.change(() ->
            this.fireworkMeta.addEffect(effect));
    }

    @NotNull
    public FireworkItemBuilder addEffects(@NotNull final FireworkEffect... effects) {
        return this.change(() ->
            this.fireworkMeta.addEffects(effects));
    }

    @NotNull
    public FireworkItemBuilder addEffects(@NotNull final Iterable<FireworkEffect> effects) {
        return this.change(() ->
            this.fireworkMeta.addEffects(effects));
    }

}
