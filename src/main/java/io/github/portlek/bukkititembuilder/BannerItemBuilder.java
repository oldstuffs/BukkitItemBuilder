/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.portlek.bukkititembuilder;

import java.util.Arrays;
import java.util.List;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

public final class BannerItemBuilder extends Builder<BannerItemBuilder, BannerMeta> {

    public BannerItemBuilder(@NotNull final ItemStack item, @NotNull final BannerMeta meta) {
        super(item, meta);
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder color(@NotNull final DyeColor color) {
        return this.update(meta ->
            meta.setBaseColor(color));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder removePatterns(@NotNull final int... index) {
        return this.update(meta ->
            Arrays.stream(index).forEach(meta::removePattern));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder addPatterns(@NotNull final Pattern... patterns) {
        return this.update(meta ->
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
        return this.update(meta ->
            meta.setPatterns(patterns));
    }

    @NotNull
    @Deprecated
    public BannerItemBuilder setPattern(@NotNull final int index, @NotNull final Pattern pattern) {
        return this.update(meta ->
            meta.setPattern(index, pattern));
    }

    @NotNull
    @Override
    public BannerItemBuilder get() {
        return this;
    }

}
