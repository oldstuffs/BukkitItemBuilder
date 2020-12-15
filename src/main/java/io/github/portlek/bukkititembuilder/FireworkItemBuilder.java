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

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

public final class FireworkItemBuilder extends Builder<FireworkItemBuilder, FireworkMeta> {

  public FireworkItemBuilder(@NotNull final ItemStack item, @NotNull final FireworkMeta meta) {
    super(item, meta);
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
  public FireworkItemBuilder clearEffects() {
    return this.update(FireworkMeta::clearEffects);
  }

  @NotNull
  @Override
  public FireworkItemBuilder get() {
    return this;
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
}
