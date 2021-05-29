/*
 * MIT License
 *
 * Copyright (c) 2021 Hasan Demirtaş
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

/**
 * a class that represents banner item builders.
 */
public final class BannerItemBuilder extends Builder<BannerItemBuilder, BannerMeta> {

  /**
   * ctor.
   *
   * @param itemStack the item stack.
   * @param meta the meta.
   */
  BannerItemBuilder(@NotNull final BannerMeta meta, @NotNull final ItemStack itemStack) {
    super(meta, itemStack);
  }

  /**
   * adds patterns to the banner.
   *
   * @param patterns the patterns to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder addPatterns(@NotNull final Pattern... patterns) {
    Arrays.stream(patterns).forEach(this.getItemMeta()::addPattern);
    return this.self();
  }

  /**
   * removes patterns from the banner.
   *
   * @param index the index to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder removePatterns(final int... index) {
    Arrays.stream(index).forEach(this.getItemMeta()::removePattern);
    return this.self();
  }

  @NotNull
  @Override
  public BannerItemBuilder self() {
    return this;
  }

  /**
   * sets base color of the banner.
   *
   * @param color the color to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  @Deprecated
  public BannerItemBuilder setBaseColor(@NotNull final DyeColor color) {
    this.getItemMeta().setBaseColor(color);
    return this.self();
  }

  /**
   * sets pattern of the banner.
   *
   * @param index the index to set.
   * @param pattern the pattern to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder setPattern(final int index, @NotNull final Pattern pattern) {
    this.getItemMeta().setPattern(index, pattern);
    return this.self();
  }

  /**
   * sets pattern of the banner.
   *
   * @param patterns the patterns to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder setPatterns(@NotNull final Pattern... patterns) {
    return this.setPatterns(List.of(patterns));
  }

  /**
   * sets pattern of the banner.
   *
   * @param patterns the patterns to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder setPatterns(@NotNull final List<Pattern> patterns) {
    this.getItemMeta().setPatterns(patterns);
    return this.self();
  }
}
