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
    public BannerItemBuilder baseColor(@NotNull final DyeColor color) {
        this.bannerMeta.setBaseColor(color);
        this.change();
        return this;
    }

    private void change() {
        this.builder.setItemMeta(this.bannerMeta);
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder removePatterns(@NotNull final int... index) {
        Arrays.stream(index).forEach(this.bannerMeta::removePattern);
        this.change();
        return this;
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder addPatterns(@NotNull final Pattern... patterns) {
        Arrays.stream(patterns).forEach(this.bannerMeta::addPattern);
        this.change();
        return this;
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPatterns(@NotNull final Pattern... patterns) {
        return this.setPatterns(Arrays.asList(patterns));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPatterns(@NotNull final List<Pattern> patterns) {
        this.bannerMeta.setPatterns(patterns);
        this.change();
        return this;
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPattern(@NotNull final int index, @NotNull final Pattern pattern) {
        this.bannerMeta.setPattern(index, pattern);
        this.change();
        return this;
    }

    @NotNull
    public ItemStack build() {
        return this.builder.build();
    }

}
