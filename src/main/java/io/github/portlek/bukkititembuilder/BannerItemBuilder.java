package io.github.portlek.bukkititembuilder;

import java.util.Arrays;
import java.util.List;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

public final class BannerItemBuilder {

    @NotNull
    private final ItemStackBuilder builder;

    @NotNull
    private final BannerMeta bannerMeta;

    public BannerItemBuilder(@NotNull final ItemStackBuilder builder, @NotNull final BannerMeta bannerMeta) {
        this.builder = builder;
        this.bannerMeta = bannerMeta;
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder color(@NotNull final DyeColor color) {
        return this.change(() ->
            this.bannerMeta.setBaseColor(color));
    }

    @NotNull
    private BannerItemBuilder change(@NotNull final Runnable runnable) {
        runnable.run();
        this.builder.setItemMeta(this.bannerMeta);
        return this;
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder removePatterns(@NotNull final int... index) {
        return this.change(() ->
            Arrays.stream(index).forEach(this.bannerMeta::removePattern));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder addPatterns(@NotNull final Pattern... patterns) {
        return this.change(() ->
            Arrays.stream(patterns).forEach(this.bannerMeta::addPattern));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPatterns(@NotNull final Pattern... patterns) {
        return this.setPatterns(Arrays.asList(patterns));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPatterns(@NotNull final List<Pattern> patterns) {
        return this.change(() ->
            this.bannerMeta.setPatterns(patterns));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPattern(@NotNull final int index, @NotNull final Pattern pattern) {
        return this.change(() ->
            this.bannerMeta.setPattern(index, pattern));
    }

    @NotNull
    public ItemStack build() {
        return this.builder.build();
    }

}
