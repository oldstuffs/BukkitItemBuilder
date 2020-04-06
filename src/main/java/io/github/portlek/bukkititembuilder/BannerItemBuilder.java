package io.github.portlek.bukkititembuilder;

import io.github.portlek.bukkititembuilder.base.Builder;
import io.github.portlek.bukkititembuilder.base.ItemStackBuilder;
import java.util.Arrays;
import java.util.List;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

public final class BannerItemBuilder extends Builder<BannerItemBuilder, BannerMeta> {

    public BannerItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final BannerMeta meta) {
        super(builder, meta);
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder color(@NotNull final DyeColor color) {
        return this.change(meta ->
            meta.setBaseColor(color));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder removePatterns(@NotNull final int... index) {
        return this.change(meta ->
            Arrays.stream(index).forEach(meta::removePattern));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder addPatterns(@NotNull final Pattern... patterns) {
        return this.change(meta ->
            Arrays.stream(patterns).forEach(meta::addPattern));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPatterns(@NotNull final Pattern... patterns) {
        return this.setPatterns(Arrays.asList(patterns));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPatterns(@NotNull final List<Pattern> patterns) {
        return this.change(meta ->
            meta.setPatterns(patterns));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPattern(@NotNull final int index, @NotNull final Pattern pattern) {
        return this.change(meta ->
            meta.setPattern(index, pattern));
    }

    @NotNull
    @Override
    protected BannerItemBuilder get() {
        return this;
    }

}
