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

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents crossbow item builders.
 */
public final class FireworkItemBuilder extends Builder<FireworkItemBuilder, FireworkMeta> {

  /**
   * ctor.
   *
   * @param itemMeta the item meta.
   * @param itemStack the item stack.
   */
  public FireworkItemBuilder(@NotNull final FireworkMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * adds effect to the firework.
   *
   * @param effect the effect to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder addEffect(@NotNull final FireworkEffect effect) {
    return this.update(meta -> meta.addEffect(effect));
  }

  /**
   * adds effect to the firework.
   *
   * @param effects the effects to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder addEffects(@NotNull final FireworkEffect... effects) {
    return this.update(meta -> meta.addEffects(effects));
  }

  /**
   * adds effect to the firework.
   *
   * @param effects the effects to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder addEffects(@NotNull final Iterable<FireworkEffect> effects) {
    return this.update(meta -> meta.addEffects(effects));
  }

  /**
   * clears effects of the firework.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder clearEffects() {
    return this.update(FireworkMeta::clearEffects);
  }

  /**
   * removes effect from the firework.
   *
   * @param effectId the effect id to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder removeEffect(final int effectId) {
    return this.update(meta -> meta.removeEffect(effectId));
  }

  @NotNull
  @Override
  public FireworkItemBuilder self() {
    return this;
  }

  /**
   * sets power of the firework.
   *
   * @param power the power to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public FireworkItemBuilder setPower(final int power) {
    return this.update(meta -> meta.setPower(power));
  }
}
