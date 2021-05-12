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

import com.cryptomorin.xseries.SkullUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents skull item builders.
 */
public final class SkullItemBuilder extends Builder<SkullItemBuilder, SkullMeta> {

  /**
   * ctor.
   *
   * @param itemMeta the item meta.
   * @param itemStack the item stack.
   */
  SkullItemBuilder(@NotNull final SkullMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * removes owner of the skull.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public SkullItemBuilder removeOwner() {
    return this.update(meta -> {
      if (Builder.VERSION < 13) {
        meta.setOwner(null);
      } else {
        meta.setOwningPlayer(null);
      }
    });
  }

  @NotNull
  @Override
  public SkullItemBuilder self() {
    return this;
  }

  /**
   * sets owner of the skull.
   *
   * @param texture the texture to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public SkullItemBuilder setOwner(@NotNull final String texture) {
    return this.update(meta -> SkullUtils.applySkin(meta, texture));
  }
}
